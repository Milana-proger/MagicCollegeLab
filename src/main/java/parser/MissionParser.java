package parser;

import exception.MissionParserException;
import model.Mission;

public interface MissionParser {
    Mission parse(String data) throws MissionParserException;
    boolean canBeParsed(String data);
}
