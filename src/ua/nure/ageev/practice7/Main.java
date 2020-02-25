package ua.nure.ageev.practice7;

import ua.nure.ageev.practice7.controller.DOMController;
import ua.nure.ageev.practice7.controller.SAXController;
import ua.nure.ageev.practice7.controller.STAXController;
import ua.nure.ageev.practice7.entity.Firearms;
import ua.nure.ageev.practice7.util.Sorter;

/**
 * Entry point for practice 7.
 * 
 * @author A. Ageev
 *
 */
public class Main {
	public static void usage() {
		System.out.println("java ua.nure.test.practice7.Main xmlFileName");
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			usage();
			return;
		}

		String xmlFileName = args[0];
		System.out.println("Input ==> " + xmlFileName);

		////////////////////////////////////////////////////////
		// DOM
		////////////////////////////////////////////////////////

		// get
		DOMController domController = new DOMController(xmlFileName);
		domController.parse(true);
		Firearms firearms = domController.getFirearms();

		// sort (case 1)
		Sorter.sortGunsByModel(firearms);

		// save
		String outputXmlFile = "output.dom.xml";
		DOMController.saveToXML(firearms, outputXmlFile);
		System.out.println("Output ==> " + outputXmlFile);

		////////////////////////////////////////////////////////
		// SAX
		////////////////////////////////////////////////////////

		// get
		SAXController saxController = new SAXController(xmlFileName);
		saxController.parse(true);
		firearms = saxController.getFirearms();

		// sort (case 2)
		Sorter.sortSortGunsByMaterial(firearms);

		// save
		outputXmlFile = "output.sax.xml";

		// other way:
		DOMController.saveToXML(firearms, outputXmlFile);
		System.out.println("Output ==> " + outputXmlFile);

		////////////////////////////////////////////////////////
		// StAX
		////////////////////////////////////////////////////////

		// get
		STAXController staxController = new STAXController(xmlFileName);
		staxController.parse();
        firearms = staxController.getFirearms();

		// sort (case 3)
		Sorter.sortSortGunsByOrigin(firearms);

		// save
		outputXmlFile = "output.stax.xml";
		DOMController.saveToXML(firearms, outputXmlFile);
		System.out.println("Output ==> " + outputXmlFile);
	}

}
