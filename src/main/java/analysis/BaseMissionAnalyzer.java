package analysis;

import model.Mission;
import parser.MissionParserFactory;
import ui.DetailedMissionReportStrategy;
import ui.MissionReportStrategy;

import java.util.ArrayList;
import java.util.List;

public class BaseMissionAnalyzer implements MissionAnalyzer {
    public final static MissionReportStrategy DEFAULT_STRATEGY = new DetailedMissionReportStrategy();
    private MissionReportStrategy missionReportStrategy;
    private final List<Mission> missionsToAnalyze = new ArrayList<>();

    public BaseMissionAnalyzer() {
        this.missionReportStrategy = DEFAULT_STRATEGY;
    }

    public BaseMissionAnalyzer(MissionReportStrategy missionReportStrategy) {
        this.missionReportStrategy = missionReportStrategy;
    }

    @Override
    public void analyze() {
        for (int i = 0; i<missionsToAnalyze.size(); i++) {
            System.out.println(missionReportStrategy.generateMission(missionsToAnalyze.get(i)));
        }
    }

    @Override
    public void addMissionToAnalyze(Mission mission) {
        if (mission != null) {
            missionsToAnalyze.add(mission);
        }
    }

    @Override
    public void resetAnalyzer() {
        this.missionsToAnalyze.clear();
    }

    @Override
    public void setMissionReportStrategy(MissionReportStrategy missionReportStrategy) {
        this.missionReportStrategy = missionReportStrategy;
    }
}