package ua.nure.ageev.practice7.util;

import ua.nure.ageev.practice7.constants.Constants;
import ua.nure.ageev.practice7.controller.DOMController;
import ua.nure.ageev.practice7.entity.Firearms;
import ua.nure.ageev.practice7.entity.Gun;

import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;

/**
 * Contains static methods for sorting.
 *
 * @author A .Ageev
 *
 */
public class Sorter {

    /**
     * Sorts guns by model
     */
    public static final Comparator<Gun> SORT_GUNS_BY_MODEL = new Comparator<Gun>() {
        @Override
        public int compare(Gun o1, Gun o2) {
            return o1.getModel().compareTo(o2.getModel());
        }
    };

    /**
     * Sorts guns by origin
     */
    public static final Comparator<Gun> SORT_GUNS_BY_ORIGIN = new Comparator<Gun>() {
        @Override
        public int compare(Gun o1, Gun o2) {
            return o1.getOrigin().compareTo(o2.getOrigin());
        }
    };

    /**
     * Sorts guns by material
     */
    public static final Comparator<Gun> SORT_GUNS_BY_MATERIAL = new Comparator<Gun>() {
        @Override
        public int compare(Gun o1, Gun o2) {
            return o1.getMaterial().compareTo(o2.getMaterial());
        }
    };

    public static final void sortGunsByModel(Firearms firearms) {
        Collections.sort(firearms.getGun(), SORT_GUNS_BY_MODEL);
    }

    public static final void sortSortGunsByOrigin(Firearms firearms) {
        Collections.sort(firearms.getGun(), SORT_GUNS_BY_ORIGIN);
    }

    public static final void sortSortGunsByMaterial(Firearms firearms) {
        Collections.sort(firearms.getGun(), SORT_GUNS_BY_MATERIAL);
    }

    public static void main(String[] args) throws Exception {
        // Test.xml --> Test object
        DOMController domController = new DOMController(
                Constants.VALID_XML_FILE);
        domController.parse(false);
        Firearms firearms = domController.getFirearms();

        System.out.println("====================================");
        System.out.println(firearms);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.sortGunsByModel(firearms);
        System.out.println(firearms);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.sortSortGunsByMaterial(firearms);
        System.out.println(firearms);
    }


}
