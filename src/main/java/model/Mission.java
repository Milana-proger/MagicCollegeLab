package model;

import java.util.ArrayList;
import java.util.List;

public class Mission {
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private Long damageCost;
    private Curse curse;
    private List<Sorcerer> sorcerers;
    private List<Technique> techniques;
    private String note;
    private EconomicAssessment economicAssessment;
    private List<OperationTimeLine> operationTimeLines;
    private List<EnemyActivity> enemyActivities;
    private CivilianImpact civilianImpact;

    public Mission() {
        this.sorcerers = new ArrayList<>();
        this.techniques = new ArrayList<>();
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Long getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(Long damageCost) {
        this.damageCost = damageCost;
    }

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }

    public void setSorcerers(List<Sorcerer> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EconomicAssessment getEconomicAssessment() {
        return economicAssessment;
    }

    public void setEconomicAssessment(EconomicAssessment economicAssessment) {
        this.economicAssessment = economicAssessment;
    }

    public List<OperationTimeLine> getOperationTimeLines() {
        return operationTimeLines;
    }

    public void setOperationTimeLines(List<OperationTimeLine> operationTimeLines) {
        this.operationTimeLines = operationTimeLines;
    }

    public List<EnemyActivity> getEnemyActivities() {
        return enemyActivities;
    }

    public void setEnemyActivities(List<EnemyActivity> enemyActivities) {
        this.enemyActivities = enemyActivities;
    }

    public CivilianImpact getCivilianImpact() {
        return civilianImpact;
    }

    public void setCivilianImpact(CivilianImpact civilianImpact) {
        this.civilianImpact = civilianImpact;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "missionId='" + missionId + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", outcome='" + outcome + '\'' +
                ", damageCost=" + damageCost +
                ", curse=" + curse +
                ", sorcerers=" + sorcerers +
                ", techniques=" + techniques +
                ", note='" + note + '\'' +
                '}';
    }

    public void addSorcerer(Sorcerer sorcerer) {
        this.sorcerers.add(sorcerer);
    }

    public void addTechnique(Technique technique) {
        this.techniques.add(technique);
    }

    public static MissionBuilder builder() {
        return new MissionBuilder();
    }
}
