package parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.MissionParserException;
import model.Curse;
import model.Mission;
import model.Sorcerer;
import model.Technique;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class JsonMissionParser implements MissionParser {

    @Override
    public Mission parse(String data) throws MissionParserException {
        JsonObject root = JsonParser.parseString(data).getAsJsonObject();
        Mission mission = new Mission();
        mission.setMissionId(getValue("missionId", root));
        mission.setDate(getValue("date", root));
        mission.setLocation(getValue("location", root));
        mission.setOutcome(getValue("outcome", root));
        try {
            Long longDamageCost = Long.parseLong(getValue("damageCost", root));
            mission.setDamageCost(longDamageCost);
        } catch (NumberFormatException e) {mission.setDamageCost(0);}
        mission.setNote(getValue("comment", root));
        if (root.has("curse") && !root.get("curse").isJsonNull()) {
            JsonObject jsonCurse = root.getAsJsonObject("curse");
            Curse curse = new Curse();
            curse.setName(getValue("name", jsonCurse));
            curse.setThreatLevel(getValue("threatLevel", jsonCurse));
            mission.setCurse(curse);
        }
        if (root.has("sorcerers") && !root.get("sorcerers").isJsonNull()) {
            JsonArray sorcerersList = root.getAsJsonArray("sorcerers");
            for (int i = 0; i < sorcerersList.size(); i++) {
                JsonObject jsonSorcerer = sorcerersList.get(i).getAsJsonObject();
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(getValue("name", jsonSorcerer));
                sorcerer.setRank(getValue("rank", jsonSorcerer));
                mission.addSorcerer(sorcerer);
            }
        }
        if (root.has("techniques") && !root.get("techniques").isJsonNull()) {
            JsonArray techniquesList = root.getAsJsonArray("techniques");
            for (int i = 0; i < techniquesList.size(); i++) {
                JsonObject jsonTechnique = techniquesList.get(i).getAsJsonObject();
                Technique technique = new Technique();
                technique.setName(getValue("name", jsonTechnique));
                technique.setType(getValue("type", jsonTechnique));
                technique.setOwner(getValue("owner", jsonTechnique));
                Long longDamage = Long.parseLong(getValue("damage", jsonTechnique));
                technique.setDamage(longDamage);
                mission.addTechnique(technique);
            }
        } return mission;
    }

    @Override
    public boolean canBeParsed(String data) {
        String dataTrimmed = data.trim();
        return dataTrimmed.startsWith("{") && dataTrimmed.endsWith("}");
    }

    private String getValue(String key, JsonObject jsonObject) {
        if (jsonObject.has(key)) {
            JsonElement jsonValue = jsonObject.get(key);
            if (!jsonValue.isJsonNull()) {
                return jsonValue.getAsString();
            }
        } return "";
    }
}
