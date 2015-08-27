package FlexBoxApplication.Order;

import java.util.ArrayList;

/**
 * Order class is used to store a list of OrderLine's. Represents a customer's
 * order
 *
 * @author up678526
 */
public class Order {

    // List of OrderLine's 
    ArrayList<OrderLine> order;

    /**
     * Constructor for Order
     */
    public Order() {
        order = new ArrayList<>();
    }

    /**
     * Returns the list of OrderLine's
     *
     * @return order
     */
    public ArrayList<OrderLine> getOrder() {
        return order;
    }

    /**
     * Adds an OrderLine to the Order
     *
     * @param orderLine
     */
    public void addOrderLine(OrderLine orderLine) {
        order.add(orderLine);
    }

    /**
     * Removes an OrderLine from the Order
     *
     * @param index
     */
    public void removeOrderLine(int index) {
        order.remove(index);
    }

    /**
     * Returns the total cost of all OrderLine's
     *
     * @return orderTotal
     */
    public Double getOrderTotal() {
        double orderTotal = 0;
        for (OrderLine orderLine : order) {
            orderTotal += orderLine.getCost();
        }
        return orderTotal;
    }

}
