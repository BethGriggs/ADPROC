package FlexBoxApplication.Order;

import FlexBoxApplication.Box.Box;

/**
 * OrderLine is comprised of a Quantity and Box
 *
 * @author up678526
 */
public class OrderLine {

    int quantity;
    Box box;

    /**
     * Constructor for Order
     *
     * @param quantity
     * @param box
     */
    public OrderLine(int quantity, Box box) {
        this.quantity = quantity;
        this.box = box;

    }

    /**
     * Returns the quantity
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the Box
     *
     * @return Box
     */
    public Box getBox() {
        return box;
    }

    /**
     * Calculates and returns the cost per line
     *
     * @return lineCost
     */
    public double getCost() {
        double lineCost = quantity * box.calculateCost();
        return lineCost;
    }

}
