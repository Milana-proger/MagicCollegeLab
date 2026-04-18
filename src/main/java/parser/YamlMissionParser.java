package parser;

import exception.MissionParserException;
import model.*;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

public class YamlMissionParser implements MissionParser {

    private final DomainObjectFactory domainObjectFactory = new DomainObjectFactory();
    private final Yaml yaml = new Yaml();

    @Override
    public Mission parse(String data) throws MissionParserException {
        Map<String, Object> yamlMaps = yaml.load(data);
        MissionBuilder missionBuilder = Mission.builder()
                .withMissionId(getString(yamlMaps, "missionId"))
                .setDate(getString(yamlMaps, "date"))
                .setLocation(getString(yamlMaps, "location"))
                .setOutcome(getString(yamlMaps, "outcome"))
                .setDamageCost(getLong(yamlMaps, "damageCost"))
                .setNote(getString(yamlMaps, "comment"));
        Map<String, Object> curseMap = (Map<String, Object>) yamlMaps.get("curse");
        if (curseMap != null) {
            Curse curse = domainObjectFactory.createCurse(getString(curseMap, "name"),
                    getString(curseMap, "threatLevel"));
            missionBuilder.setCurse(curse);
        }
        List<Map<String, Object>> sorcerersMapsList = (List<Map<String, Object>>) yamlMaps.get("sorcerers");
        if (sorcerersMapsList != null) {
            for (int i = 0; i < sorcerersMapsList.size(); i++) {
                Map<String, Object> sorcererMap = sorcerersMapsList.get(i);
                Sorcerer sorcerer = domainObjectFactory.createSorcerer(getString(sorcererMap, "name"),
                        getString(sorcererMap, "rank"));
                missionBuilder.addSorcerer(sorcerer);
            }
        }
        List<Map<String, Object>> techniquesMapsList = (List<Map<String, Object>>) yamlMaps.get("techniques");
        if (techniquesMapsList != null) {
            for (int i = 0; i < techniquesMapsList.size(); i++) {
                Map<String, Object> techniqueMap = techniquesMapsList.get(i);
                Technique technique = domainObjectFactory.createTechnique(getString(techniqueMap, "name"),
                        getString(techniqueMap, "type"),
                        getString(techniqueMap, "owner"),
                        getString(techniqueMap, "damage"));
                missionBuilder.addTechnique(technique);
            }
        }
        Map<String, Object> economicAssessmentMap = (Map<String, Object>) yamlMaps.get("economicAssessment");
        if (economicAssessmentMap != null) {
            EconomicAssessment economicAssessment = domainObjectFactory
                    .createEconomicAssessment(getString(economicAssessmentMap, "totalDamageCost"),
                            getString(economicAssessmentMap, "infrastructureDamage"),
                            getString(economicAssessmentMap, "transportDamage"),
                            getString(economicAssessmentMap, "commercialDamage"),
                            getString(economicAssessmentMap, "recoveryEstimateDays"),
                            getString(economicAssessmentMap, "insuranceCovered"));
            missionBuilder.setEconomicAssessment(economicAssessment);
        }
        return missionBuilder.build();
    }

    @Override
    public boolean canBeParsedFromData(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        if (!data.contains(":")) {
            return false;
        }
        return data.trim().startsWith("missionId:");
    }

    @Override
    public boolean canBeParsedFromExtendtion(String extendtion) {
        return extendtion != null && (extendtion.trim().equalsIgnoreCase(".yaml") ||
                extendtion.trim().equalsIgnoreCase(".yml"));
    }

    private String getString(Map<String, Object> yamlMaps, String key) {
        Object value = yamlMaps.get(key);
        return value != null ? value.toString() : null;
    }

    private Long getLong(Map<String, Object> yamlMaps, String key) {
        Object value = yamlMaps.get(key);
        if (value == null) {
            return null;
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
