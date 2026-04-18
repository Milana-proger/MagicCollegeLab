package parser;

import exception.MissionParserException;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class WithoutExtendtionMissionParser implements MissionParser {

    private final DomainObjectFactory domainObjectFactory = new DomainObjectFactory();

    @Override
    public Mission parse(String data) throws MissionParserException {
        String[] lines = data.split("\n");
        MissionBuilder missionBuilder = Mission.builder();
        List<Sorcerer> sorcerers = new ArrayList<>();
        List<Technique> techniques = new ArrayList<>();
        List<OperationTimeLine> operationTimeLines = new ArrayList<>();
        List<EnemyActivity> enemyActivities = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\|");
            if (parts.length == 0) continue;

            String type = parts[0];
            switch (type) {
                case "MISSION_CREATED":
                    missionBuilder.withMissionId(getPart(parts, 1))
                            .setDate(getPart(parts, 2))
                            .setLocation(getPart(parts, 3));

                    break;

                case "CURSE_DETECTED":
                    Curse curse = domainObjectFactory.createCurse(getPart(parts, 1), getPart(parts, 2));
                    missionBuilder.setCurse(curse);
                    break;

                case "SORCERER_ASSIGNED":
                    Sorcerer sorcerer = domainObjectFactory.createSorcerer(getPart(parts, 1), getPart(parts, 2));
                    sorcerers.add(sorcerer);
                    break;

                case "TECHNIQUE_USED":
                    Technique technique = domainObjectFactory.createTechnique(getPart(parts, 1),
                            getPart(parts, 2),
                            getPart(parts, 3),
                            getPart(parts, 4));
                    techniques.add(technique);
                    break;

                case "TIMELINE_EVENT":
                    OperationTimeLine event = domainObjectFactory.createOperationTimeLine(getPart(parts, 1),
                            getPart(parts, 2),
                            getPart(parts, 3));
                    operationTimeLines.add(event);
                    break;

                case "ENEMY_ACTION":
                    EnemyActivity action = domainObjectFactory.createEnemyActivity(
                            getPart(parts, 1),
                            getPart(parts, 2),
                            getPart(parts, 3),
                            getPart(parts, 4),
                            getPart(parts, 5)
                    );

                    enemyActivities.add(action);
                    break;

                case "CIVILIAN_IMPACT":
                    String evacuated = null, injured = null, missing = null;
                    String publicExposureRisk = null;
                    for (int i = 1; i < parts.length; i++) {
                        String[] kv = parts[i].split("=");
                        if (kv.length == 2) {
                            String key = kv[0].trim();
                            String value = kv[1].trim();
                            switch (key) {
                                case "evacuated":
                                    evacuated = value;
                                    break;
                                case "injured":
                                    injured = value;
                                    break;
                                case "missing":
                                    missing = value;
                                    break;
                                case "publicExposureRisk":
                                    publicExposureRisk = value;
                                    break;
                            }
                        }
                    }
                    CivilianImpact civilianImpact = domainObjectFactory.createCivilianImpact(evacuated, injured, missing, publicExposureRisk);
                    missionBuilder.setCivilianImpact(civilianImpact);
                    break;

                case "MISSION_RESULT":
                    String outcome = getPart(parts, 1);
                    if (outcome != null) {
                        missionBuilder.setOutcome(outcome);
                    }
                    for (int i = 2; i < parts.length; i++) {
                        if (parts[i].startsWith("damageCost=")) {
                            String costStr = parts[i].substring(11);
                            try {
                                missionBuilder.setDamageCost(Long.parseLong(costStr));
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        missionBuilder.addSorcerers(sorcerers)
                .addTechniques(techniques)
                .addOperationTimeLines(operationTimeLines)
                .addEnemyActivities(enemyActivities);

        return missionBuilder.build();
    }

    public String getPart(String[] parts, int index) {
        if (index < parts.length) {
            return parts[index];
        }
        return null;
    }

    @Override
    public boolean canBeParsedFromData(String data) {
        String[] lines = data.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                return line.startsWith("MISSION_CREATED|");
            }
        }
        return false;
    }

    @Override
    public boolean canBeParsedFromExtendtion(String extension) {
        return extension != null && extension.isEmpty();
    }
}