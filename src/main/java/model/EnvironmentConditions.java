package model;

public class EnvironmentConditions {

    private String weather;
    private String timeOfDay;
    private String visibility;
    private Integer cursedEnergyDensity;

    public EnvironmentConditions(String weather, String timeOfDay, String visibility, Integer cursedEnergyDensity) {
        this.weather = weather;
        this.timeOfDay = timeOfDay;
        this.visibility = visibility;
        this.cursedEnergyDensity = cursedEnergyDensity;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Integer getCursedEnergyDensity() {
        return cursedEnergyDensity;
    }

    public void setCursedEnergyDensity(Integer cursedEnergyDensity) {
        this.cursedEnergyDensity = cursedEnergyDensity;
    }
}