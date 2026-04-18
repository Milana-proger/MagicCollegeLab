package model;

import exception.MissionBuilderException;

public class DomainObjectFactory {
    public Curse createCurse(String name, String threatLevel) {
        if (name == null || name.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует имя проклятья!");
        }
        if (threatLevel == null || threatLevel.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует уровень опасности проклятья!");
        }
        return new Curse(name, threatLevel);
    }

    public Sorcerer createSorcerer(String name, String rank) {
        if (name == null || name.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует имя мага!");
        }
        if (rank == null || rank.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует ранг мага!");
        }
        return new Sorcerer(name, rank);
    }

    public Technique createTechnique(String name, String type, String owner, Long damage) {
        if (name == null || name.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует название техники!");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует тип техники!");
        }
        if (owner == null || owner.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует владелец техники!");
        }
        return new Technique(name, type, owner, damage);
    }

    public Technique createTechnique(String name, String type, String owner, String damage) {
        try {
            Long damageLong = Long.parseLong(damage.trim());
            return createTechnique(name, type, owner, damageLong);
        } catch (NumberFormatException e) {
            return new Technique(name, type, owner, null);
        }
    }

    public EconomicAssessment createEconomicAssessment(String totalDamageCost, String infrastructureDamage,
                                                       String transportDamage, String commercialDamage,
                                                       String recoveryEstimateDays, String insuranceCovered) {
        return new EconomicAssessment(getLong(totalDamageCost), getLong(infrastructureDamage),
                getLong(transportDamage), getLong(commercialDamage), getInteger(recoveryEstimateDays),
                getBoolean(insuranceCovered));
    }

    private Long getLong(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer getInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean getBoolean(String string) {
        try {
            return Boolean.parseBoolean(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public OperationTimeLine createOperationTimeLine(String timestamp, String type, String description) {
        if (timestamp == null || timestamp.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует время события");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует тип события");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new MissionBuilderException("Отсутствует описание события");
        }
        return new OperationTimeLine(timestamp, type, description);
    }

    public EnemyActivity createEnemyActivity(String behaviorType,
                                             String targetPriority,
                                             String attackPatterns,
                                             String mobility,
                                             String escalationRisk) {
        return new EnemyActivity(behaviorType, targetPriority, attackPatterns, mobility, escalationRisk);
    }

    public CivilianImpact createCivilianImpact(String evacuated,
                                               String injured,
                                               String missing,
                                               String publicExposureRisk) {
        return createCivilianImpact(getInteger(evacuated), getInteger(injured), getInteger(missing), publicExposureRisk);
    }

    public CivilianImpact createCivilianImpact(Integer evacuated,
                                               Integer injured,
                                               Integer missing,
                                               String publicExposureRisk) {
        return new CivilianImpact(evacuated, injured, missing, publicExposureRisk);
    }


}
