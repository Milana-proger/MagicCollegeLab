package source;

import exception.MissionLoadException;

public interface MissionDataSourse {
    String load(String path) throws MissionLoadException;
}
