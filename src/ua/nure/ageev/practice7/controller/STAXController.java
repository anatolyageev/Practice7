package ua.nure.ageev.practice7.controller;


import ua.nure.ageev.practice7.constants.Constants;
import ua.nure.ageev.practice7.constants.Names;
import ua.nure.ageev.practice7.entity.*;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.StreamSource;
import java.math.BigInteger;

/**
 * Controller for StAX parser.
 *
 * @author A. Ageev
 */
public class STAXController {
    private String xmlFileName;

    // main container
    private Firearms firearms;
    private static ObjectFactory of = new ObjectFactory();
    public Firearms getFirearms() {
        return firearms;
    }

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document with StAX (based on event reader). There is no validation during the
     * parsing.
     */

    public void parse() throws XMLStreamException {
        firearms = new Firearms();
        Gun gun = null;
        PerformanceCharacteristics perChar = null;
        Range range = null;
        // current element name holder
        String currentElement = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

        // creating event reader
        XMLEventReader reader = factory.createXMLEventReader(
                new StreamSource(xmlFileName));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            // skip any empty content
            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }

            // handler for start tags
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                if (Names.RANGE.equals(currentElement)) {
                    range = new Range();
                    continue;
                }

                if (Names.GUN.equals(currentElement)) {
                    gun = new Gun();
                    continue;
                }

                if (Names.PERFOMANCE_CHARACTERISTICS.equals(currentElement)) {
                    perChar = new PerformanceCharacteristics();
                    continue;
                }
            }

            // handler for contents
            if (event.isCharacters()) {
                Characters characters = event.asCharacters();

                if (Names.MODEL.equals(currentElement)) {
                    gun.setModel(characters.getData());
                    continue;
                }

                if (Names.HANDY.equals(currentElement)) {
                    gun.setHandy(Handy.fromValue(characters.getData()));
                    continue;
                }

                if (Names.ORIGIN.equals(currentElement)) {
                    gun.setOrigin(characters.getData());
                    continue;
                }

                if (Names.MATERIAL.equals(currentElement)) {
                    gun.setMaterial(Material.fromValue(characters.getData()));
                    continue;
                }

                if (Names.SHORT_RANGE.equals(currentElement)) {
                    range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeShortRange(Integer.parseInt(characters.getData().trim())));
                    continue;
                }

                if (Names.MIDDLE_RANGE.equals(currentElement)) {
                    range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeShortRange(Integer.parseInt(characters.getData().trim())));
                    continue;
                }

                if (Names.LONG_RANGE.equals(currentElement)) {
                    range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeLongRange(BigInteger.valueOf(Long.parseLong(characters.getData().trim()))));
                    continue;
                }


                if (Names.TARGET_ANGE.equals(currentElement)) {
                    perChar.setTargetAnge(BigInteger.valueOf(Long.parseLong(characters.getData())));
                    continue;
                }

                if (Names.CLIP.equals(currentElement)) {
                    perChar.setClip(Boolean.parseBoolean(characters.getData()));
                    continue;
                }

                if (Names.OPTICAL.equals(currentElement)) {
                    perChar.setOptician(Boolean.parseBoolean(characters.getData()));
                    continue;
                }

            }

            // handler for end tags
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String localName = endElement.getName().getLocalPart();

                if (Names.PERFOMANCE_CHARACTERISTICS.equals(localName)) {
                    gun.setPerformanceCharacteristics(perChar);
                    continue;
                }

                if (Names.RANGE.equals(localName)) {
                    perChar.setRange(range);
                }

                if (Names.GUN.equals(localName)) {
                    firearms.getGun().add(gun);
                }
            }

        }

        reader.close();
    }


    private Range getRangeValue(XMLEventReader reader) {
        Range range = new Range();
        XMLEvent event;
        StartElement element;
        Characters character;
        for (int i = 0; i <= 7; i++) {
            try {
                event = reader.nextEvent();
                if (event.isStartElement()) {
                    element = event.asStartElement();
                    if (element.getName().getLocalPart().equals(Names.SHORT_RANGE)) {
                        event = reader.nextEvent();
                        character = event.asCharacters();
                        range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeShortRange(Integer.parseInt(character.getData())));
                    } else if (element.getName().getLocalPart().equals(Names.MIDDLE_RANGE)) {
                        event = reader.nextEvent();
                        character = event.asCharacters();
                        range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeShortRange(Integer.parseInt(character.getData())));
                    } else if (element.getName().getLocalPart().equals(Names.LONG_RANGE)) {
                        event = reader.nextEvent();
                        character = event.asCharacters();
                        range.setShortRangeOrMiddleRangeOrLongRange(of.createRangeLongRange(BigInteger.valueOf(Long.parseLong(character.getData()))));
                    }
                }
            } catch (XMLStreamException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
        return range;
    }


    public static void main(String[] args) throws Exception {

        // try to parse (valid) XML file (success)
        STAXController staxContr = new STAXController(Constants.VALID_XML_FILE);
        staxContr.parse(); // <-- do parse (success)

        // obtain container
        Firearms test = staxContr.getFirearms();

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + test);
        System.out.println("====================================");
    }
}
