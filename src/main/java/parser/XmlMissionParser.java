package parser;

import exception.MissionParserException;
import model.Curse;
import model.Mission;
import model.Sorcerer;
import model.Technique;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class XmlMissionParser implements MissionParser {
    @Override
    public Mission parse(String data) throws MissionParserException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(data.getBytes("UTF-8")));
            Mission mission = new Mission();
            Element root = document.getDocumentElement();
            mission.setMissionId(getTagValue("missionId", root));
            mission.setDate(getTagValue("date", root));
            mission.setLocation(getTagValue("location", root));
            mission.setOutcome(getTagValue("outcome", root));
            try {
                Long longDamageCost = Long.parseLong(getTagValue("damageCost", root));
                mission.setDamageCost(longDamageCost);
            } catch (NumberFormatException e) {mission.setDamageCost(0);}
            mission.setNote(getTagValue("comment", root));
            NodeList cursesList = root.getElementsByTagName("curse");
            if (cursesList.getLength()>0) {
                Element element = (Element) cursesList.item(0);
                Curse curse = new Curse();
                curse.setName(getTagValue("name", element));
                curse.setThreatLevel(getTagValue("threatLevel", element));
                mission.setCurse(curse);
            }
            NodeList sorcerersList = root.getElementsByTagName("sorcerers");
            for (int i=0;i< sorcerersList.getLength(); i++) {
                Element element = (Element)  sorcerersList.item(i);
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(getTagValue("name", element));
                sorcerer.setRank(getTagValue("rank", element));
                mission.addSorcerer(sorcerer);
            }
            NodeList techniquesList = root.getElementsByTagName("techniques");
            for (int i=0;i< techniquesList.getLength(); i++) {
                Element element = (Element) techniquesList.item(i);
                Technique technique = new Technique();
                technique.setName(getTagValue("name", element));
                technique.setType(getTagValue("type", element));
                technique.setOwner(getTagValue("owner", element));
                Long longDamage = Long.parseLong(getTagValue("damage", element));
                technique.setDamage(longDamage);
                mission.addTechnique(technique);
            } return mission;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new MissionParserException("Ошибка чтения текстового файла");
        }
    }

    @Override
    public boolean canBeParsed(String data) {
        String dataTrimmed = data.trim();
        return dataTrimmed.startsWith("<mission>") && dataTrimmed.endsWith("</mission>");
    }
    public String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength()>0) {
            Node node = nodeList.item(0);
            if (node.getTextContent() != null) {
                return node.getTextContent().trim();
            }
        }
        return null;
    }
}