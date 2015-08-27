package FlexBoxApplication.Box;

import FlexBoxApplication.Order.Extras;

/**
 * Represents the attributes of a ColouredBox
 *
 * @author up678526
 */
public class ColouredBox extends GeneralBox {

    int numberOfColours;

    /**
     * Constructor for ColouredBox
     *
     * @param numberOfColours
     * @param gradeOfCard
     * @param sealableTop
     * @param width
     * @param height
     * @param length
     */
    public ColouredBox(int numberOfColours, int gradeOfCard, boolean sealableTop, double width, double height, double length) {
        super(gradeOfCard, sealableTop, width, height, length);
        this.numberOfColours = numberOfColours;
    }

    /**
     * Returns the number of colours
     *
     * @return numberOfColours
     */
    public int getNumberOfColours() {
        return numberOfColours;
    }

    @Override
    public double calculateCost() {
        double finalCost = getInitialCost();
        if (sealableTop) {
            finalCost += (Extras.SealableTop.getPrice() * getInitialCost());
        }

        if (numberOfColours == 1) {
            finalCost += (Extras.OneColour.getPrice() * getInitialCost());
        } else if (numberOfColours == 2) {
            finalCost += (Extras.TwoColour.getPrice() * getInitialCost());
        }
        return finalCost;
    }

}
