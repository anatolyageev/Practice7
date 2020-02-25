package ua.nure.ageev.practice7.controller;
/**
 * Controller for SAX parser.
 *
 * @author A. Ageev
 *
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.ageev.practice7.constants.Constants;
import ua.nure.ageev.practice7.constants.Names;
import ua.nure.ageev.practice7.entity.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class SAXController extends DefaultHandler {
    private String xmlFileName;

    // current element name holder
    private String currentElement;

    // main container
    private Firearms firearms;
    private Gun gun;
    private PerformanceCharacteristics pChar;
    private ObjectFactory of = new ObjectFactory();
    private Range range;

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }
    /**
     * Parses XML document.
     *
     * @param validate
     *            If true validate XML document against its XML schema. With
     *            this parameter it is possible make parser validating.
     */
    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain sax parser factory
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // XML document contains namespaces
        factory.setNamespaceAware(true);

        // set validation
        if (validate) {
            factory.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);
            factory.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFileName, this);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = localName;
        if (Names.FIREARMS.equals(currentElement)) {
            firearms = new Firearms();
            return;
        }
        if (Names.GUN.equals(currentElement)) {
            gun = new Gun();
            return;
        }

        if(Names.PERFOMANCE_CHARACTERISTICS.equals(currentElement)){
            pChar = new PerformanceCharacteristics();
            return;
        }

        if(Names.RANGE.equals(currentElement)){
            range = of.createRange();
            return;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(Names.GUN.equals(localName)){
            firearms.getGun().add(gun);
            return;
        }
        if(Names.PERFOMANCE_CHARACTERISTICS.equals(localName)){
            gun.setPerformanceCharacteristics(pChar);
            return;
        }
//
//        if(Names.RANGE.equals(localName)){
//            pChar.setRange(range);
//            return;
//        }

    }

    public Firearms getFirearms(){
        return this.firearms;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String elementText = new String(ch, start, length).trim();
        // return if content is empty
        if (elementText.isEmpty()) {
            return;
        }
        if(Names.MODEL.equals(currentElement)){
            gun.setModel(elementText);
            return;
        }
        if(Names.HANDY.equals(currentElement)){
            gun.setHandy(Handy.fromValue(elementText));
            return;
        }
        if(Names.ORIGIN.equals(currentElement)){
            gun.setOrigin(elementText);
            return;
        }
        if(Names.MATERIAL.equals(currentElement)){
            gun.setMaterial(Material.fromValue(elementText));
        }
        if(Names.SHORT_RANGE.equals(currentElement)){
            range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeShortRange(Integer.parseInt(elementText)));
            pChar.setRange(range);
        }
        if(Names.MIDDLE_RANGE.equals(currentElement)){
            range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeMiddleRange(Integer.parseInt(elementText)));
            pChar.setRange(range);
        }
        if(Names.LONG_RANGE.equals(currentElement)){
            range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeLongRange(BigInteger.valueOf(Long.parseLong(elementText))));
            pChar.setRange(range);
        }
        if(Names.TARGET_ANGE.equals(currentElement)){
            pChar.setTargetAnge(BigInteger.valueOf(Long.parseLong(elementText)));
        }
        if(Names.CLIP.equals(currentElement)){
            pChar.setClip(Boolean.parseBoolean(elementText));
        }
        if(Names.OPTICAL.equals(currentElement)){
            pChar.setOptician(Boolean.parseBoolean(elementText));
        }
    }


    @Override
    public void error(SAXParseException e) throws SAXException {
        super.error(e);
    }

    public static void main(String[] args) throws Exception {

        // try to parse valid XML file (success)
        SAXController saxContr = new SAXController(Constants.VALID_XML_FILE);

        // do parse with validation on (success)
        saxContr.parse(true);

        // obtain container
        Firearms test = saxContr.getFirearms();

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + test);
        System.out.println("====================================");

        // now try to parse NOT valid XML (failed)
        saxContr = new SAXController(Constants.INVALID_XML_FILE);
        try {
            // do parse with validation on (failed)
            saxContr.parse(true);
        } catch (Exception ex) {
            System.err.println("====================================");
            System.err.println("Validation is failed:\n" + ex.getMessage());
            System.err
                    .println("Try to print test object:" + saxContr.getFirearms());
            System.err.println("====================================");
        }

        // and now try to parse NOT valid XML with validation off (success)
        saxContr.parse(false);

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + saxContr.getFirearms());
        System.out.println("====================================");
    }


}
