package model;

public class OperationTimeLine {

    private String timestamp;
    private String type;
    private String description;

    public OperationTimeLine(String timestamp, String type, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
