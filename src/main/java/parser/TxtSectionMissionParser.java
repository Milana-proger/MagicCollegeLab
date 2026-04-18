package parser;

import exception.MissionParserException;
import model.*;
import parser.MissionParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TxtSectionMissionParser implements MissionParser {

    private final DomainObjectFactory domainObjectFactory = new DomainObjectFactory();

    @Override
    public Mission parse(String data) throws MissionParserException {
        Map<String, Map<String, String>> sections = parseSections(data);
        MissionBuilder missionBuilder = Mission.builder();

        Map<String, String> missionSection = sections.get("MISSION");
        if (missionSection != null) {
            missionBuilder.withMissionId(missionSection.get("missionId"))
                    .setDate(missionSection.get("date"))
                    .setLocation(missionSection.get("location"))
                    .setOutcome(missionSection.get("outcome"))
                    .setDamageCost(getLong(missionSection.get("damageCost")));
        }

        Map<String, String> curseSection = sections.get("CURSE");
        if (curseSection != null) {
            Curse curse = domainObjectFactory.createCurse(
                    curseSection.get("name"),
                    curseSection.get("threatLevel"));
            missionBuilder.setCurse(curse);
        }

        List<Sorcerer> sorcerers = new ArrayList<>();
        for (int i = 0; i < getSectionsCount(sections, "SORCERER"); i++) {
            String sectionName = i == 0 ? "SORCERER" : "SORCERER_" + i;
            Map<String, String> sorcererSection = sections.get(sectionName);
            if (sorcererSection != null) {
                Sorcerer sorcerer = domainObjectFactory.createSorcerer(
                        sorcererSection.get("name"),
                        sorcererSection.get("rank"));
                sorcerers.add(sorcerer);
            }
        }
        missionBuilder.addSorcerers(sorcerers);

        List<Technique> techniques = new ArrayList<>();
        for (int i = 0; i < getSectionsCount(sections, "TECHNIQUE"); i++) {
            String sectionName = i == 0 ? "TECHNIQUE" : "TECHNIQUE_" + i;
            Map<String, String> techniqueSection = sections.get(sectionName);
            if (techniqueSection != null) {
                Technique technique = domainObjectFactory.createTechnique(
                        techniqueSection.get("name"),
                        techniqueSection.get("type"),
                        techniqueSection.get("owner"),
                        techniqueSection.get("damage"));
                techniques.add(technique);
            }
        }
        missionBuilder.addTechniques(techniques);

        Map<String, String> environmentSection = sections.get("ENVIRONMENT");
        if (environmentSection != null) {
            EnvironmentConditions environment = domainObjectFactory.createEnvironmentConditions(
                    environmentSection.get("weather"),
                    environmentSection.get("timeOfDay"),
                    environmentSection.get("visibility"),
                    environmentSection.get("cursedEnergyDensity"));
            missionBuilder.setEnvironmentConditions(environment);
        }

        return missionBuilder.build();
    }

    private Map<String, Map<String, String>> parseSections(String data) {
        Map<String, Map<String, String>> sections = new LinkedHashMap<>();
        String[] lines = data.split("\n");

        String currentSection = null;
        Map<String, String> currentMap = null;
        int sectionCounter = 0;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("[") && line.endsWith("]")) {
                currentSection = line.substring(1, line.length() - 1);

                if (sections.containsKey(currentSection)) {
                    sectionCounter++;
                    currentSection = currentSection + "_" + sectionCounter;
                } else {
                    sectionCounter = 0;
                }

                currentMap = new LinkedHashMap<>();
                sections.put(currentSection, currentMap);
            } else if (currentSection != null && line.contains("=")) {
                int index = line.indexOf('=');
                String key = line.substring(0, index).trim();
                String value = line.substring(index + 1).trim();
                if (currentMap != null) {
                    currentMap.put(key, value);
                }
            }
        }

        return sections;
    }

    private int getSectionsCount(Map<String, Map<String, String>> sections, String sectionName) {
        int count = 0;
        for (String key : sections.keySet()) {
            if (key.equals(sectionName) || key.startsWith(sectionName + "_")) {
                count++;
            }
        }
        return count;
    }

    private Long getLong(String s) {
        if (s == null) return null;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean canBeParsedFromData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }

        String[] lines = data.split("\n");
        boolean hasSection = false;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("[") && line.endsWith("]")) {
                hasSection = true;
            }

            if (line.contains("=") && !hasSection) {
                return false;
            }
        }

        return hasSection;
    }

    @Override
    public boolean canBeParsedFromExtendtion(String extension) {
        return extension != null && extension.trim().equalsIgnoreCase(".txt");
    }
}