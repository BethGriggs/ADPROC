package FlexBoxApplication.Box;

import FlexBoxApplication.Order.Extras;

/**
 * Represents the attributes of a ReinforcedBox
 *
 * @author up678526
 */
public class ReinforcedBox extends ColouredBox {

    boolean reinforcedBottom;
    boolean reinforcedCorners;

    /**
     * Constructor for ReinforcedBox
     *
     * @param reinforcedCorners
     * @param reinforcedBottom
     * @param numberOfColours
     * @param gradeOfCard
     * @param sealableTop
     * @param width
     * @param height
     * @param length
     */
    public ReinforcedBox(boolean reinforcedCorners, boolean reinforcedBottom, int numberOfColours, int gradeOfCard, boolean sealableTop, double width, double height, double length) {
        super(numberOfColours, gradeOfCard, sealableTop, width, height, length);
        this.reinforcedBottom = reinforcedBottom;
        this.reinforcedCorners = reinforcedCorners;
    }

    /**
     * Returns whether box is reinforced bottom
     *
     * @return reinforcedBottom
     */
    public boolean isReinforcedBottom() {
        return reinforcedBottom;
    }

    /**
     * Returns whether box is reinforced corners
     *
     * @return reinforcedCorners
     */
    public boolean isReinforcedCorners() {
        return reinforcedCorners;
    }

    @Override
    public double calculateCost() {
        double finalCost = getInitialCost();
        if (sealableTop) {
            finalCost += (Extras.SealableTop.getPrice() * getInitialCost());
        }
        
        if (numberOfColours == 1) {
            finalCost += (Extras.OneColour.getPrice() * getInitialCost());
        }

        if (numberOfColours == 2) {
            finalCost += (Extras.TwoColour.getPrice() * getInitialCost());
        }

        if (reinforcedBottom) {
            finalCost += (Extras.ReinforcedBottom.getPrice() * getInitialCost());
        }

        if (reinforcedCorners) {
            finalCost += (Extras.ReinforcedCorner.getPrice() * getInitialCost());
        }
        return finalCost;
    }

}
