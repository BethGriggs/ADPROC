package FlexBoxApplication.Box;

import FlexBoxApplication.Order.Extras;

/**
 * Represents the attributes of a GeneralBox
 *
 * @author up678526
 */
public class GeneralBox extends Box {

    /**
     * Constructor for GeneralBox
     *
     * @param gradeOfCard
     * @param sealableTop
     * @param width
     * @param height
     * @param length
     */
    public GeneralBox(int gradeOfCard, boolean sealableTop, double width, double height, double length) {
        super(gradeOfCard, sealableTop, width, height, length);

    }

    @Override
    public double calculateCost() {
        double finalCost = getInitialCost();
        if (sealableTop) {
            finalCost += (Extras.SealableTop.getPrice() * getInitialCost());
        }
        return finalCost;
    }

}
