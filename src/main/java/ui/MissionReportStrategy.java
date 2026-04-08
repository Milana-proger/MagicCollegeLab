package ui;

import model.Mission;

public interface MissionReportStrategy {
    String generateMission(Mission mission);
    String getFormatName();
}
