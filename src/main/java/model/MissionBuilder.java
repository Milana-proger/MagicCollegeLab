package model;

import exception.MissionBuilderException;
import model.*;
import model.Mission;

import java.util.ArrayList;
import java.util.List;

public class MissionBuilder {
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private Long damageCost;
    private Curse curse;
    private List<Sorcerer> sorcerers = new ArrayList<>();
    private List<Technique> techniques = new ArrayList<>();
    private String note;
    private EconomicAssessment economicAssessment;
    private List<OperationTimeLine> operationTimeLines = new ArrayList<>();
    private List<EnemyActivity> enemyActivities = new ArrayList<>();
    private CivilianImpact civilianImpact;
    private EnvironmentConditions environmentConditions;

    public MissionBuilder withMissionId(String id) {
        this.missionId = id;
        return this;
    }

    public MissionBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public MissionBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public MissionBuilder setOutcome(String outcome) {
        this.outcome = outcome;
        return this;
    }

    public MissionBuilder setDamageCost(Long damageCost) {
        this.damageCost = damageCost;
        return this;
    }

    public MissionBuilder setCurse(Curse curse) {
        this.curse = curse;
        return this;
    }

    public MissionBuilder setNote(String note) {
        this.note = note;
        return this;
    }

    public MissionBuilder addSorcerer(Sorcerer sorcerer) {
        this.sorcerers.add(sorcerer);
        return this;
    }

    public MissionBuilder addSorcerers(List<Sorcerer> sorcerers) {
        this.sorcerers.addAll(sorcerers);
        return this;
    }

    public MissionBuilder addTechnique(Technique technique) {
        this.techniques.add(technique);
        return this;
    }

    public MissionBuilder addTechniques(List<Technique> techniques) {
        this.techniques.addAll(techniques);
        return this;
    }

    public MissionBuilder setEconomicAssessment(EconomicAssessment economicAssessment) {
        this.economicAssessment = economicAssessment;
        return this;
    }

    public MissionBuilder addOperationTimeLine(OperationTimeLine operationTimeLine) {
        this.operationTimeLines.add(operationTimeLine);
        return this;
    }

    public MissionBuilder addOperationTimeLines(List<OperationTimeLine> operationTimeLines) {
        this.operationTimeLines.addAll(operationTimeLines);
        return this;
    }

    public MissionBuilder addEnemyActivity(EnemyActivity enemyActivity) {
        this.enemyActivities.add(enemyActivity);
        return this;
    }

    public MissionBuilder addEnemyActivities(List<EnemyActivity> enemyActivities) {
        this.enemyActivities.addAll(enemyActivities);
        return this;
    }

    public MissionBuilder setCivilianImpact(CivilianImpact civilianImpact) {
        this.civilianImpact = civilianImpact;
        return this;
    }

    public MissionBuilder setEnvironmentConditions(EnvironmentConditions environmentConditions) {
        this.environmentConditions = environmentConditions;
        return this;
    }

    public void validateRequiredFields() {
        List<String> emptyFields = new ArrayList<>();
        if (missionId == null || missionId.trim().isEmpty()) {
            emptyFields.add("missionId");
        }
        if (date == null || date.trim().isEmpty()) {
            emptyFields.add("date");
        }
        if (location == null || location.trim().isEmpty()) {
            emptyFields.add("location");
        }
        if (outcome == null || outcome.trim().isEmpty()) {
            emptyFields.add("outcome");
        }
        if (curse == null) {
            emptyFields.add("curse");
        }
        if (!emptyFields.isEmpty()) {
            throw new MissionBuilderException("Обязательные поля для миссии " + String.join(", ", emptyFields) + " отсутствуют");
        }
    }

    public model.Mission build() {
        validateRequiredFields();
        model.Mission mission = new Mission();
        mission.setMissionId(this.missionId);
        mission.setDate(this.date);
        mission.setLocation(this.location);
        mission.setOutcome(this.outcome);
        mission.setDamageCost(this.damageCost);
        mission.setCurse(this.curse);
        mission.setNote(this.note);
        mission.setSorcerers(this.sorcerers);
        mission.setTechniques(this.techniques);
        mission.setEnemyActivities(enemyActivities);
        mission.setOperationTimeLines(operationTimeLines);
        mission.setEnemyActivities(enemyActivities);
        mission.setCivilianImpact(civilianImpact);
        return mission;
    }
}