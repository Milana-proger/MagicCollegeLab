package parser;

import exception.MissionParserException;

public class MissionParserFactory {
    public MissionParser getMissionParser(String fileExtendtion) throws MissionParserException {
        TxtMissionParser txtMissionParser = new TxtMissionParser();
        JsonMissionParser jsonMissionParser = new JsonMissionParser();
        XmlMissionParser xmlMissionParser = new XmlMissionParser();
        switch (fileExtendtion.toLowerCase()) {
            case ".txt": return txtMissionParser;
            case ".json": return jsonMissionParser;
            case ".xml": return xmlMissionParser;
            default: throw new MissionParserException("Неверное расширение файла!");
        }
    }
}
