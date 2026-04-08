package analysis;

import model.Mission;
import parser.MissionParserFactory;
import ui.DetailedMissionReportStrategy;
import ui.MissionReportStrategy;

public class BaseMissionAnalyzer implements MissionAnalyzer {
    public final static MissionReportStrategy DEFAULT_STRATEGY = new DetailedMissionReportStrategy();
    private final MissionParserFactory missionParserFactory;
    private MissionReportStrategy missionReportStrategy;

    public BaseMissionAnalyzer(MissionParserFactory missionParserFactory) {
        this.missionParserFactory = missionParserFactory;
        this.missionReportStrategy = DEFAULT_STRATEGY;
    }

    public BaseMissionAnalyzer(MissionParserFactory missionParserFactory, MissionReportStrategy missionReportStrategy) {
        this.missionParserFactory = missionParserFactory;
        this.missionReportStrategy = missionReportStrategy;
    }

    public void setMissionReportStrategy(MissionReportStrategy missionReportStrategy) {
        this.missionReportStrategy = missionReportStrategy;
    }

    @Override
    public void analyze() {

    }
}