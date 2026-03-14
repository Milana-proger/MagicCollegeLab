package model;

public class Curse {
    private String name;
    private String threatLevel;

    public Curse(String name, String threatLevel) {
        this.name = name;
        this.threatLevel = threatLevel;
    }

    @Override
    public String toString() {
        return "Curse{" +
                "name='" + name + '\'' +
                ", threatLevel='" + threatLevel + '\'' +
                '}';
    }

    public Curse() {}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getThreatLevel() {return threatLevel;}
    public void setThreatLevel(String threatLevel) {this.threatLevel = threatLevel;}
}