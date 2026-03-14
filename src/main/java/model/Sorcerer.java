package model;

public class Sorcerer {
    private String name;
    private String rank;

    @Override
    public String toString() {
        return "Sorcerer{" +
                "name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }

    public Sorcerer(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }

    public Sorcerer() {}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getRank() {return rank;}
    public void setRank(String rank) {this.rank = rank;}


}
