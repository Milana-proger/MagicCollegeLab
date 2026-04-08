package parser;

import exception.MissionParserException;

import java.util.ArrayList;
import java.util.List;

public class MissionParserFactory {
    List<MissionParser> missionParsers = new ArrayList<>();

    public void registrMissionParser(MissionParser parser) {
        missionParsers.add(parser);
    }
    public MissionParser getMissionParserFromExtendtion(String fileExtendtion) throws MissionParserException {
        String lowExtendtion = fileExtendtion.toLowerCase();
        for (MissionParser parser: missionParsers) {
            if (parser.canBeParsedFromExtendtion(lowExtendtion)) {
                return parser;
            }
        }
        throw new MissionParserException("Неверное расширение файла!");
    }

    public MissionParser getMissionParserFromData(String data) throws MissionParserException {
        for (MissionParser parser: missionParsers) {
            if (parser.canBeParsedFromData(data)) {
                return parser;
            }
        }
        throw new MissionParserException("Неверное содержание файла!");
    }

    public MissionParser getMissionParserFromFileName(String fileName) throws MissionParserException {
        int index = fileName.lastIndexOf(".");
        String fileExtendtion = "";
        if (index>0) {
            fileExtendtion = fileName.substring(index);
        }
        return getMissionParserFromExtendtion(fileExtendtion);
    }
}
