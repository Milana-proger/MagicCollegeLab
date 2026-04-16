package parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.MissionParserException;
import model.*;

public class JsonMissionParser implements MissionParser {

    private DomainObjectFactory domainObjectFactory = new DomainObjectFactory();

    @Override
    public Mission parse(String data) throws MissionParserException {
        JsonObject root = JsonParser.parseString(data).getAsJsonObject();
        MissionBuilder missionBuilder = Mission.builder()
                .withMissionId(getValue("missionId", root))
                .setDate(getValue("date", root))
                .setLocation(getValue("location", root))
                .setOutcome(getValue("outcome", root))
                .setNote(getValue("comment", root));
        try {
            Long longDamageCost = Long.parseLong(getValue("damageCost", root));
            missionBuilder.setDamageCost(longDamageCost);
        } catch (NumberFormatException e) {
            missionBuilder.setDamageCost(null);}
        if (root.has("curse") && !root.get("curse").isJsonNull()) {
            JsonObject jsonCurse = root.getAsJsonObject("curse");
            Curse curse = domainObjectFactory.createCurse(getValue("name", jsonCurse),
                    getValue("threatLevel", jsonCurse));
            missionBuilder.setCurse(curse);
        }
        if (root.has("sorcerers") && !root.get("sorcerers").isJsonNull()) {
            JsonArray sorcerersList = root.getAsJsonArray("sorcerers");
            for (int i = 0; i < sorcerersList.size(); i++) {
                JsonObject jsonSorcerer = sorcerersList.get(i).getAsJsonObject();
                Sorcerer sorcerer = domainObjectFactory.createSorcerer(getValue("name", jsonSorcerer),
                        getValue("rank", jsonSorcerer));
                missionBuilder.addSorcerer(sorcerer);
            }
        }
        if (root.has("techniques") && !root.get("techniques").isJsonNull()) {
            JsonArray techniquesList = root.getAsJsonArray("techniques");
            for (int i = 0; i < techniquesList.size(); i++) {
                JsonObject jsonTechnique = techniquesList.get(i).getAsJsonObject();
                Technique technique = domainObjectFactory.createTechnique(getValue("name", jsonTechnique),
                        getValue("type", jsonTechnique),
                        getValue("owner", jsonTechnique),
                        getValue("damage", jsonTechnique));
                missionBuilder.addTechnique(technique);
            }
        } return missionBuilder.build();
    }

    @Override
    public boolean canBeParsedFromData(String data) {
        String dataTrimmed = data.trim();
        return dataTrimmed.startsWith("{") && dataTrimmed.endsWith("}");
    }

    @Override
    public boolean canBeParsedFromExtendtion(String extendtion) {
        return extendtion != null && extendtion.trim().equalsIgnoreCase(".json");
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
