package parser;

import exception.MissionParserException;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtMissionParser implements MissionParser {

    private final DomainObjectFactory domainObjectFactory = new DomainObjectFactory();

    @Override
    public Mission parse(String data) throws MissionParserException {
        Map<String, String> mapMissionData = getMapMissionData(data);

        MissionBuilder missionBuilder = Mission.builder()
                .withMissionId(mapMissionData.get("missionId"))
                .setDate(mapMissionData.get("date"))
                .setLocation(mapMissionData.get("location"))
                .setOutcome(mapMissionData.get("outcome"))
                .setDamageCost(getLong(mapMissionData.get("damageCost")))
                .setNote(mapMissionData.get("note"));

        Curse curse = domainObjectFactory.createCurse(mapMissionData.get("curse.name"),
                mapMissionData.get("curse.threatLevel"));

        missionBuilder.setCurse(curse)
                .addSorcerers(getSorcerers(mapMissionData))
                .addTechniques(getTechniques(mapMissionData));

        return missionBuilder.build();
    }

    private static Map<String, String> getMapMissionData(String data) {
        Map<String, String> mapMissionData = new HashMap<>();
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            int index = line.indexOf(':');
            if (index > 0) {
                String key = line.substring(0, index).trim();
                String value = line.substring(index + 1).trim();
                mapMissionData.put(key, value);
            }
        }
        return mapMissionData;
    }

    private List<Technique> getTechniques(Map<String, String> mapMissionData) {
        List<String> techniqueKeys = new ArrayList<>();
        for (String key : mapMissionData.keySet()) {
            if (key.startsWith("technique")) {
                techniqueKeys.add(key);
            }
        }

        int techniquesCount = techniqueKeys.size() / 4;
        List<Technique> techniques = new ArrayList<>();

        for (int i = 0; i < techniquesCount; i++) {
            String techniqueKey = "technique[" + i + "]";

            Technique technique = domainObjectFactory.createTechnique(mapMissionData.get(techniqueKey + ".name"),
                    mapMissionData.get(techniqueKey + ".type"),
                    mapMissionData.get(techniqueKey + ".owner"),
                    mapMissionData.get(techniqueKey + ".damage"));
            techniques.add(technique);
        }
        return techniques;
    }

    private List<Sorcerer> getSorcerers(Map<String, String> mapData) {
        List<String> sorcerersKey = new ArrayList<>();
        for (String key : mapData.keySet()) {
            if (key.startsWith("sorcerer")) {
                sorcerersKey.add(key);
            }
        }

        int sorcerersSize = sorcerersKey.size() / 2;
        List<Sorcerer> sorcerers = new ArrayList<>();
        for (int i = 0; i < sorcerersSize; i++) {
            String sorcererKey = "sorcerer[" + i + "]";
            Sorcerer sorcerer = domainObjectFactory.createSorcerer(mapData.get(sorcererKey + ".name"),
                    mapData.get(sorcererKey + ".rank"));
            sorcerers.add(sorcerer);
        }
        return sorcerers;
    }

    private Long getLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean canBeParsedFromData(String data) {
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            int index = line.indexOf(':');
            if (index < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canBeParsedFromExtendtion(String extendtion) {
        return extendtion != null && extendtion.trim().equalsIgnoreCase(".txt");
    }
}