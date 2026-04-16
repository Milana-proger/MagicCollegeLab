package model;

public class EconomicAssessment {
    private Long totalDamageCost;
    private Long infrastructureDamage;
    private Long transportDamage;
    private Long commercialDamage;
    private Integer recoveryEstimateDays;
    private Boolean insuranceCovered;

    public EconomicAssessment(Long totalDamageCost, Long infrastructureDamage,
                              Long transportDamage, Long commercialDamage,
                              Integer recoveryEstimateDays, Boolean insuranceCovered) {
        this.totalDamageCost = totalDamageCost;
        this.infrastructureDamage = infrastructureDamage;
        this.transportDamage = transportDamage;
        this.commercialDamage = commercialDamage;
        this.recoveryEstimateDays = recoveryEstimateDays;
        this.insuranceCovered = insuranceCovered;
    }

    public Long getTotalDamageCost() {
        return totalDamageCost;
    }

    public void setTotalDamageCost(Long totalDamageCost) {
        this.totalDamageCost = totalDamageCost;
    }

    public Long getInfrastructureDamage() {
        return infrastructureDamage;
    }

    public void setInfrastructureDamage(Long infrastructureDamage) {
        this.infrastructureDamage = infrastructureDamage;
    }

    public Long getTransportDamage() {
        return transportDamage;
    }

    public void setTransportDamage(Long transportDamage) {
        this.transportDamage = transportDamage;
    }

    public Long getCommercialDamage() {
        return commercialDamage;
    }

    public void setCommercialDamage(Long commercialDamage) {
        this.commercialDamage = commercialDamage;
    }

    public Integer getRecoveryEstimateDays() {
        return recoveryEstimateDays;
    }

    public void setRecoveryEstimateDays(Integer recoveryEstimateDays) {
        this.recoveryEstimateDays = recoveryEstimateDays;
    }

    public Boolean getInsuranceCovered() {
        return insuranceCovered;
    }

    public void setInsuranceCovered(Boolean insuranceCovered) {
        this.insuranceCovered = insuranceCovered;
    }
}
