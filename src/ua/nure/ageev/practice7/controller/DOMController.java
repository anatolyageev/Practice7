package ua.nure.ageev.practice7.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.ageev.practice7.constants.Constants;
import ua.nure.ageev.practice7.constants.Names;
import ua.nure.ageev.practice7.entity.*;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Controller for DOM parser.
 *
 * @author A. Ageev
 */
public class DOMController {

    private String xmlFileName;
    // current element name holder
    private String currentElement;
    // main container
    private Firearms firearms;
    private Gun gun;
    //   private PerformanceCharacteristics pChar;
    private static ObjectFactory of = new ObjectFactory();

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Firearms getFirearms() {
        return this.firearms;
    }

    /**
     * Parses XML document.
     *
     * @param validate If true validate XML document against its XML schema.
     */
    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // set properties for Factory

        // XML document contains namespaces
        dbf.setNamespaceAware(true);

        // make parser validating
        if (validate) {
            // turn validation on
            dbf.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);

            // turn on xsd validation
            dbf.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        DocumentBuilder db = dbf.newDocumentBuilder();

        // set error handler
        db.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                // throw exception if XML document is NOT valid
                throw e;
            }
        });

        // parse XML document
        Document document = db.parse(xmlFileName);

        // get root element
        Element root = document.getDocumentElement();

        // create container
        firearms = new Firearms();

        // obtain questions nodes
        NodeList gunNodes = root
                .getElementsByTagName(Names.GUN);

        // process questions nodes
        for (int j = 0; j < gunNodes.getLength(); j++) {
            //          Gun gun = gunNodes.item(j);
            // add question to container
            firearms.getGun().add(gun);
        }
    }


    /**
     * Extracts Gun object from the Gun XML node.
     *
     * @param qNode Gun node.
     * @return Gun object.
     */
    private static Gun getGun(Node qNode) {
        Gun gun = new Gun();
        Element qElement = (Element) qNode;
        PerformanceCharacteristics pChar = new PerformanceCharacteristics();
        ObjectFactory of = new ObjectFactory();

        // process Gun

        // Model process
        Node qtNode = qElement.getElementsByTagName(Names.MODEL).item(0);
        gun.setModel(qtNode.getTextContent());

        // Handy process
        gun.setHandy(Handy.fromValue(qElement.getElementsByTagName(Names.HANDY).
                item(0).getTextContent()));

        // Origin process
        gun.setOrigin(qElement.getElementsByTagName(Names.ORIGIN).
                item(0).getTextContent());

        // Material process
        gun.setMaterial(Material.fromValue(qElement.getElementsByTagName(Names.MATERIAL).
                item(0).getTextContent()));

        gun.setPerformanceCharacteristics(getPerfChar(qElement.getElementsByTagName(Names.PERFOMANCE_CHARACTERISTICS).
                item(0).getChildNodes()));

        // process question text
//        Node qtNode = qElement.getElementsByTagName(Names.QUESTION_TEXT)
//                .item(0);
//        // set question text
//        question.setQuestionText(qtNode.getTextContent());
//
//        // process answers
//        NodeList aNodeList = qElement.getElementsByTagName(Names.ANSWER);
//        for (int j = 0; j < aNodeList.getLength(); j++) {
//            Answer answer = getAnswer(aNodeList.item(j));
//
//            // add answer
//            question.getAnswers().add(answer);
        return gun;
    }

    /**
     * Parse NodeList and convert it to PerformanceCharacteristics
     *
     * @param nodeList
     * @return PerformanceCharacteristics
     */
    private static PerformanceCharacteristics getPerfChar(NodeList nodeList) {
        PerformanceCharacteristics pc = new PerformanceCharacteristics();
        Node node;
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (node.getNodeName().equals(Names.TARGET_ANGE)) {
                pc.setTargetAnge(BigInteger.valueOf(Long.parseLong(node.getTextContent())));
            } else if (node.getNodeName().equals(Names.CLIP)) {
                pc.setClip(Boolean.parseBoolean(node.getTextContent()));
            } else if (node.getNodeName().equals(Names.OPTICAL)) {
                pc.setOptician(Boolean.parseBoolean(node.getTextContent()));
            } else{
                pc.setRange(getRengeInfo(node.getChildNodes()));
            }
        }
        return pc;
    }

    private static Range getRengeInfo(NodeList nodeList) {
        Range range = new Range();
        if (nodeList.item(0).equals(Names.SHORT_RANGE)) {
            range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeShortRange(Integer.parseInt(nodeList.item(0).getTextContent())));
        } else if (nodeList.item(0).equals(Names.MIDDLE_RANGE)) {
            range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeMiddleRange(Integer.parseInt(nodeList.item(0).getTextContent())));
        }else{
            range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeLongRange(BigInteger.valueOf(Long.parseLong(nodeList.item(0).getTextContent()))));
        }
        return range;
    }

    /**
     * Saves Test object to XML file.
     *
     * @param firearms    Test object to be saved.
     * @param xmlFileName Output XML file name.
     */
    public static void saveToXML(Firearms firearms, String xmlFileName)
            throws ParserConfigurationException, TransformerException {
        // Test -> DOM -> XML
        saveToXML(getDocument(firearms), xmlFileName);
    }

    /**
     * Save DOM to XML.
     *
     * @param document    DOM to be saved.
     * @param xmlFileName Output XML file name.
     */
    public static void saveToXML(Document document, String xmlFileName)
            throws TransformerException {

        StreamResult result = new StreamResult(new File(xmlFileName));

        // set up transformation
        TransformerFactory tf = TransformerFactory.newInstance();
        javax.xml.transform.Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        // run transformation
        t.transform(new DOMSource(document), result);
    }


    /**
     * Creates and returns DOM of the Test container.
     *
     * @param firearms Test object.
     * @throws ParserConfigurationException
     */
    public static Document getDocument(Firearms firearms) throws ParserConfigurationException {

        // obtain DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // set properties for Factory

        // XML document contains namespaces
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        // create root element
        Element rElement = document.createElement(Names.FIREARMS);

        // add root element
        document.appendChild(rElement);

        // add questions elements
        for (Gun gun : firearms.getGun()) {

            // add question
            Element qElement = document.createElement(Names.GUN);
            rElement.appendChild(qElement);

            // add question text
            Element qtElement =  document.createElement(Names.MODEL);
            qtElement.setTextContent(gun.getModel());
            qElement.appendChild(qtElement);

            qtElement =  document.createElement(Names.HANDY);
            qtElement.setTextContent(gun.getHandy().toString());
            qElement.appendChild(qtElement);

            qtElement =  document.createElement(Names.ORIGIN);
            qtElement.setTextContent(gun.getOrigin());
            qElement.appendChild(qtElement);

            PerformanceCharacteristics perc = gun.getPerformanceCharacteristics();

            Element pcElement =  document.createElement(Names.PERFOMANCE_CHARACTERISTICS);
            qElement.appendChild(pcElement);

            Element rgElement = document.createElement(Names.RANGE);
            pcElement.appendChild(rgElement);

            if(perc.getRange())



            // add answers
            for (Answer answer : question.getAnswers()) {
                Element aElement = document.createElement(Names.ANSWER);
                aElement.setTextContent(answer.getContent());

                // set attribute
                if (answer.isCorrect()) {
                    aElement.setAttribute(Names.CORRECT, "true");
                }
                qElement.appendChild(aElement);
            }
        }

        return document;
    }

    public Number getRangeVal(Range range){
        Number l=0;
        Object wrapped = ((JAXBElement)shortRangeOrMiddleRangeOrLongRange).getValue();
        if(wrapped.getClass() == BigInteger.class){
            l = (BigInteger)wrapped;
        }
        else {
            l = (Integer) wrapped;
        }
        return l;
    }

}