package FlexBoxApplication.Order;

/**
 * Enum to store and return price increases of Extras
 *
 * @author up678526
 */
public enum Extras {

    OneColour(0.12), TwoColour(0.15), ReinforcedBottom(0.12), ReinforcedCorner(0.08), SealableTop(0.06);

    double price;

    Extras(double p) {
        price = p;
    }

    /**
     * Returns the price increase of the Extra
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }
}
