package parser;

import exception.MissionParserException;
import model.*;
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

    private DomainObjectFactory domainObjectFactory = new DomainObjectFactory();

    @Override
    public Mission parse(String data) throws MissionParserException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(data.getBytes("UTF-8")));
            Element root = document.getDocumentElement();
            MissionBuilder mission = Mission.builder()
                    .withMissionId(getTagValue("missionId", root))
                    .setDate(getTagValue("date", root))
                    .setLocation(getTagValue("location", root))
                    .setOutcome(getTagValue("outcome", root))
                    .setNote(getTagValue("comment", root));
            try {
                Long longDamageCost = Long.parseLong(getTagValue("damageCost", root));
                mission.setDamageCost(longDamageCost);
            } catch (NumberFormatException e) {mission.setDamageCost(null);}
            NodeList cursesList = root.getElementsByTagName("curse");
            if (cursesList.getLength()>0) {
                Element element = (Element) cursesList.item(0);
                Curse curse = domainObjectFactory.createCurse(getTagValue("name", element),
                        getTagValue("threatLevel", element));
                mission.setCurse(curse);
            }
            NodeList sorcerersList = root.getElementsByTagName("sorcerers");
            for (int i=0;i< sorcerersList.getLength(); i++) {
                Element element = (Element)  sorcerersList.item(i);
                Sorcerer sorcerer = domainObjectFactory.createSorcerer(getTagValue("name", element),
                        getTagValue("rank", element));
                mission.addSorcerer(sorcerer);
            }
            NodeList techniquesList = root.getElementsByTagName("techniques");
            for (int i=0;i< techniquesList.getLength(); i++) {
                Element element = (Element) techniquesList.item(i);
                Technique technique = domainObjectFactory.createTechnique(getTagValue("name", element),
                        getTagValue("type", element),
                        getTagValue("owner", element),
                        getTagValue("damage", element));
                mission.addTechnique(technique);
            }
            return mission.build();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new MissionParserException("Ошибка чтения текстового файла");
        }
    }

    @Override
    public boolean canBeParsedFromData(String data) {
        String dataTrimmed = data.trim();
        return dataTrimmed.startsWith("<mission>") && dataTrimmed.endsWith("</mission>");
    }

    @Override
    public boolean canBeParsedFromExtendtion(String extendtion) {
        return extendtion != null && extendtion.trim().equalsIgnoreCase(".xml");
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