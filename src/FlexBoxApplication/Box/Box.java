package FlexBoxApplication.Box;

/**
 * Abstract Box class contains all variables and methods that apply to all boxes
 * @author up678526
 */
public abstract class Box {

    protected double width;
    protected double height;
    protected double length;

    protected double[] cardCost = {0.50, 0.59, 0.70, 0.92, 1.35};
    protected int gradeOfCard;
    protected boolean sealableTop;

    /**
     * Constructor for Box
     *
     * @param gradeOfCard
     * @param sealableTop
     * @param width
     * @param height
     * @param length
     */
    public Box(int gradeOfCard, boolean sealableTop, double width, double height, double length) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.gradeOfCard = gradeOfCard;
        this.sealableTop = sealableTop;
    }

    /**
     * Returns width of box
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns height of box
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns length of box
     *
     * @return length
     */
    public double getLength() {
        return length;
    }

    /**
     * Returns the grade of card
     *
     * @return gradeOfCard
     */
    public int getGradeOfCard() {
        return gradeOfCard;
    }

    /**
     * Returns if box is sealable top
     *
     * @return boolean sealable top
     */
    public boolean isSealableTop() {
        return sealableTop;
    }

    /**
     * Method calculates the surface area of a box
     *
     * @return surfaceArea
     */
    protected double getSurfaceArea() {
        double surfaceArea = ((height * width) * 2) + ((width * length) * 2) + ((height * length) * 2);
        return surfaceArea;
    }
    
    /**
     * Cost of the box without any extra features
     * @return initial cost
     */
    public double getInitialCost(){
      return getSurfaceArea() * cardCost[gradeOfCard - 1];
    } 

    /**
     * Method for calculating the cost of a box
     *
     * @return cost
     */
    public double calculateCost() {
        return 0;
    }

}
