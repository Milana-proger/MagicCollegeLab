import analysis.BaseMissionAnalyzer;
import analysis.MissionAnalyzer;
import parser.*;
import ui.MissionConcoleUI;

public class Main {
    public static void main(String[] args) {
        MissionParserFactory missionParserFactory = new MissionParserFactory();
        missionParserFactory.registrMissionParser(new JsonMissionParser());
        missionParserFactory.registrMissionParser(new TxtMissionParser());
        missionParserFactory.registrMissionParser(new XmlMissionParser());
        missionParserFactory.registrMissionParser(new YamlMissionParser());
        missionParserFactory.registrMissionParser(new WithoutExtendtionMissionParser());
        MissionAnalyzer missionAnalyzer = new BaseMissionAnalyzer();
        MissionConcoleUI missionConcoleUI = new MissionConcoleUI(missionParserFactory, missionAnalyzer);
        missionConcoleUI.start();
    }
}
