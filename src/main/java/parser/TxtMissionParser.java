package parser;

import exception.MissionParserException;
import model.Curse;
import model.Mission;
import model.Sorcerer;
import model.Technique;

import java.util.ArrayList;
import java.util.List;

public class TxtMissionParser implements MissionParser {

    @Override
    public Mission parse(String data) throws MissionParserException {
        Mission mission = new Mission();
        mission.setCurse(new Curse());
        List<String> sorcerersKey= new ArrayList<>();
        List<String> sorcerersValue = new ArrayList<>();
        List<String> techniquesKey= new ArrayList<>();
        List<String> techniquesValue = new ArrayList<>();
        String[] lines = data.split("\n");
        for (int i=0; i<lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            int index = line.indexOf(':');
            if (index>0) {
                String key = line.substring(0, index).trim();
                String value = line.substring(index+1, line.length()).trim();
                valueInMission(key, value, mission);
                if (key.startsWith("sorcerer")) {
                    sorcerersKey.add(key);
                    sorcerersValue.add(value);
                } else if (key.startsWith("technique")) {
                    techniquesKey.add(key);
                    techniquesValue.add(value);
                }
            }
        }
        sorcerersInMission(sorcerersKey, sorcerersValue, mission);
        techniquesInMission(techniquesKey, techniquesValue, mission);
        return mission;
    }

    private void valueInMission(String key, String value, Mission mission) {
        switch (key) {
            case "missionId": mission.setMissionId(value);
            break;
            case "date": mission.setDate(value);
                break;
            case "location": mission.setLocation(value);
                break;
            case "outcome": mission.setOutcome(value);
                break;
            case "damageCost": try {
                Long longValue = Long.parseLong(value);
                mission.setDamageCost(longValue);
            } catch (NumberFormatException e) {
                mission.setDamageCost(0);
            }
                break;
            case "curse.name": mission.getCurse().setName(value);
                break;
            case "curse.threatLevel": mission.getCurse().setThreatLevel(value);
                break;
            case "note": mission.setNote(value);
        }
    }

    @Override
    public boolean canBeParsedFromData(String data) {
        String[] lines = data.split("\n");
        for (int i=0; i<lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            int index = line.indexOf(':');
            if (index<0) {
                return false;
            }
        }
        return true;
    }

    private void sorcerersInMission(List<String> sorcerersKey, List<String> sorcerersValue, Mission mission) {
        int sorcerersNumber = sorcerersKey.size()/2;
        List<Sorcerer> sorcerersList = new ArrayList<>();
        for (int i=0; i<sorcerersNumber; i++) {
            sorcerersList.add(new Sorcerer());
        }
        for (int i=0; i<sorcerersKey.size(); i++) {
            String key = sorcerersKey.get(i);
            int index = getIndex(key);
            if (index == -1 || index>sorcerersNumber) {
                continue;
            }
            Sorcerer sorcerer = sorcerersList.get(index);
            if (key.endsWith(".name")) {
                sorcerer.setName(sorcerersValue.get(i));
            } else if (key.endsWith(".rank")) {
                sorcerer.setRank(sorcerersValue.get(i));
            }
        }
        mission.setSorcerers(sorcerersList);
    }

    private void techniquesInMission(List<String> techniquesKey, List<String> techniquesValue, Mission mission) {
        int techniquesNumber = techniquesKey.size()/4;
        List<Technique> techniquesList = new ArrayList<>();
        for (int i=0; i<techniquesNumber; i++) {
            techniquesList.add(new Technique());
        }
        for (int i=0; i<techniquesKey.size(); i++) {
            String key = techniquesKey.get(i);
            int index = getIndex(key);
            if (index == -1 || index>techniquesNumber) {
                continue;
            }
            Technique technique = techniquesList.get(index);
            if (key.endsWith(".name")) {
                technique.setName(techniquesValue.get(i));
            } else if (key.endsWith(".type")) {
                technique.setType(techniquesValue.get(i));
            } else if (key.endsWith(".owner")) {
                technique.setOwner(techniquesValue.get(i));
            } else if (key.endsWith(".damage")) {
                try {
                    Long longDamage = Long.parseLong(techniquesValue.get(i));
                    technique.setDamage(longDamage);
                } catch (NumberFormatException e) {technique.setDamage(0);
                }
            }
        }
        mission.setTechniques(techniquesList);
    }

    private int getIndex(String input) {
        int indexStart = input.indexOf('[');
        int indexEnd = input.indexOf(']');
        if (indexStart>-1 && indexEnd>-1 && indexEnd>indexStart) {
            String number = input.substring(indexStart + 1, indexEnd);
            try {
                int intNumber = Integer.parseInt(number);
                return intNumber;
            } catch (NumberFormatException e) {
                return -1;
            }
        } return -1;
    }
}