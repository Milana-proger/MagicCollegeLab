package model;

public class EnemyActivity {
    private String behaviorType;
    private String targetPriority;
    private String attackPatterns;
    private String mobility;
    private String escalationRisk;

    public EnemyActivity(String behaviorType, String targetPriority, String attackPatterns, String mobility, String escalationRisk) {
        this.behaviorType = behaviorType;
        this.targetPriority = targetPriority;
        this.attackPatterns = attackPatterns;
        this.mobility = mobility;
        this.escalationRisk = escalationRisk;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getTargetPriority() {
        return targetPriority;
    }

    public void setTargetPriority(String targetPriority) {
        this.targetPriority = targetPriority;
    }

    public String getAttackPatterns() {
        return attackPatterns;
    }

    public void setAttackPatterns(String attackPatterns) {
        this.attackPatterns = attackPatterns;
    }

    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public String getEscalationRisk() {
        return escalationRisk;
    }

    public void setEscalationRisk(String escalationRisk) {
        this.escalationRisk = escalationRisk;
    }
}
