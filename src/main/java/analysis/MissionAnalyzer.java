package analysis;

import model.Mission;

public interface MissionAnalyzer {
    void analyze(); // для пункта 8
    void addMissionToAnalyze(Mission mission); // для пункта 3. потенциально можно анализировать несколько миссий
    void resetAnalyzer(); // для метода analyze()
}
