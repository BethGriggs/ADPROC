package FlexBoxApplication.UI;

import FlexBoxApplication.Box.Box;
import FlexBoxApplication.Box.ColouredBox;
import FlexBoxApplication.Box.GeneralBox;
import FlexBoxApplication.Order.OrderLine;
import FlexBoxApplication.Box.ReinforcedBox;
import FlexBoxApplication.Order.Order;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * ApplucationUI class provides a graphical user-interface to allow users to
 * input orders
 *
 * @author up678526
 */
public final class ApplicationUI extends javax.swing.JFrame {

    Order order;

    // Declares column names for the order table
    String[] columnNames = {"Quantity", "Card", "Height",
        "Width",
        "Length",
        "Sealable",
        "Colours", "Bottom",
        "Corners", "Cost"};

    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApplicationUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApplicationUI().setVisible(true);
            }
        });
    }

    /**
     * Creates new form ApplicationUI
     */
    public ApplicationUI() {
        initComponents();
        groupButtons();

        // creates a new blank order
        order = new Order();

        // initialise order table
        tableOrder.setModel(model);

    }

    /**
     * Attempts to create a Box object with the specified parameters
     *
     * @param quantity
     * @param reinforcedBottom
     * @param reinforcedCorners
     * @param numberOfColours
     * @param gradeOfCard
     * @param width
     * @param height
     * @param length
     * @param sealableTop
     */
    private void createBox(int quantity, boolean reinforcedBottom, boolean reinforcedCorners, int numberOfColours, int gradeOfCard, double width, double height, double length, boolean sealableTop) {

        if (numberOfColours == 0) {
            // Grade I Box
            if (!reinforcedBottom && !reinforcedCorners && validGrade(gradeOfCard, 1, 3)) {
                Box box = new GeneralBox(gradeOfCard, sealableTop, width, height, length);
                order.addOrderLine(new OrderLine(quantity, box));
            } else {
                JOptionPane.showMessageDialog(null, "FlexBox cannot supply a box with these attributes.");
            }
        }

        if (numberOfColours == 1) {
            // Grade II Box
            if (!reinforcedBottom && !reinforcedCorners && validGrade(gradeOfCard, 2, 4)) {
                ColouredBox colouredBox = new ColouredBox(numberOfColours, gradeOfCard, sealableTop, width, height, length);
                order.addOrderLine(new OrderLine(quantity, colouredBox));

            } else {

                JOptionPane.showMessageDialog(null, "FlexBox cannot supply a box with these attributes.");
            }

        }

        if (numberOfColours == 2) {
            // Grade III Box
            if (!reinforcedBottom && !reinforcedCorners && validGrade(gradeOfCard, 2, 5)) {
                ColouredBox colouredBox = new ColouredBox(numberOfColours, gradeOfCard, sealableTop, width, height, length);
                order.addOrderLine(new OrderLine(quantity, colouredBox));

                // Grade IV Box
            } else if (reinforcedBottom && !reinforcedCorners && validGrade(gradeOfCard, 2, 5)) {
                ReinforcedBox reinforcedBox = new ReinforcedBox(reinforcedCorners, reinforcedBottom, numberOfColours, gradeOfCard, sealableTop, width, height, length);
                order.addOrderLine(new OrderLine(quantity, reinforcedBox));

                // Grade V Box
            } else if (validGrade(gradeOfCard, 3, 5) && reinforcedBottom && reinforcedCorners) {
                ReinforcedBox reinforcedBox = new ReinforcedBox(reinforcedCorners, reinforcedBottom, numberOfColours, gradeOfCard, sealableTop, width, height, length);
                order.addOrderLine(new OrderLine(quantity, reinforcedBox));

            } else {
                JOptionPane.showMessageDialog(null, "FlexBox cannot supply a box with these attributes.");
            }
        }

    }

    /**
     * Updates the Order and OrderTable
     */
    private void updateOrder() {
        // clear the table
        model = new DefaultTableModel(columnNames, 0);

        // loop through the OrderLine's and populate the table
        for (OrderLine orderLine : order.getOrder()) {
            if (orderLine.getBox() instanceof ReinforcedBox) {
                ReinforcedBox reinforcedBox = (ReinforcedBox) orderLine.getBox();
                model.addRow(new Object[]{orderLine.getQuantity(), reinforcedBox.getGradeOfCard(), reinforcedBox.getHeight(), reinforcedBox.getWidth(),
                    reinforcedBox.getLength(), getBooleanString(reinforcedBox.isSealableTop()), reinforcedBox.getNumberOfColours(), getBooleanString(reinforcedBox.isReinforcedBottom()), getBooleanString(reinforcedBox.isReinforcedCorners()), formatCurrency(orderLine.getCost())});
            } else if (orderLine.getBox() instanceof ColouredBox) {
                ColouredBox colouredBox = (ColouredBox) orderLine.getBox();
                model.addRow(new Object[]{orderLine.getQuantity(), colouredBox.getGradeOfCard(), colouredBox.getHeight(), colouredBox.getWidth(),
                    colouredBox.getLength(), getBooleanString(colouredBox.isSealableTop()), colouredBox.getNumberOfColours(), "-", "-", formatCurrency(orderLine.getCost())});
            } else if (orderLine.getBox() instanceof Box) {
                Box box = orderLine.getBox();
                model.addRow(new Object[]{orderLine.getQuantity(), box.getGradeOfCard(), box.getHeight(), box.getWidth(),
                    box.getLength(), getBooleanString(box.isSealableTop()), "-", "-", "-", formatCurrency(orderLine.getCost())});
            }
        }
        tableOrder.setModel(model);
        // update the Order total
        txtSubtotal.setText(formatCurrency(order.getOrderTotal()));

    }

    /**
     * Changes the boolean to a user readable answer - yes/no
     *
     * @param input
     * @return String
     */
    protected String getBooleanString(boolean input) {
        if (input) {
            return "Yes";
        } else {
            return "No";
        }
    }

    /**
     * Used to validate dimensions
     *
     * @param input
     * @return true/false
     */
    private boolean dimensionValidation(String input) {
        double doubleValue;
        try {
            // convert string to double
            doubleValue = Double.valueOf(input);

        } catch (NumberFormatException e) {
            // output error message
            JOptionPane.showMessageDialog(null, "All dimensions must be a valid double between 0.25m and 2.00m.");
            return false;
        }

        // validates dimension according to the set boundaries
        if (doubleValue >= 0.25 && doubleValue <= 2.00) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "All dimensions must be a valid double between 0.25m and 2.00m.");
            return false;
        }

    }

    /**
     * Tests whether the input is within a specified range
     *
     * @param input
     * @param min
     * @param max
     * @return
     */
    public boolean validGrade(int input, int min, int max) {
        return input >= min && input <= max;
    }

    /**
     * Converts the value to a String currency
     *
     * @param cost
     * @return String
     */
    public String formatCurrency(double cost) {
        String costString = String.format("£%.2f", cost);
        return costString;
    }

    /**
     * Returns which colour option is selected
     *
     * @return int numberOfColours
     */
    private int getColours() {

        if (rdoColour1.isSelected()) {
            return 1;
        } else if (rdoColour2.isSelected()) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Groups the radio buttons to ensure only one can be selected at a time
     */
    public void groupButtons() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(rdoColour0);
        bg.add(rdoColour1);
        bg.add(rdoColour2);
        // set default selection
        rdoColour0.setSelected(rootPaneCheckingEnabled);

    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCheckout = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        panelBox = new javax.swing.JPanel();
        panAttributes = new javax.swing.JPanel();
        lblHeight = new javax.swing.JLabel();
        lblWidth = new javax.swing.JLabel();
        lblLength = new javax.swing.JLabel();
        txtHeight = new javax.swing.JTextField();
        txtWidth = new javax.swing.JTextField();
        txtDepth = new javax.swing.JTextField();
        lblCard = new javax.swing.JLabel();
        cboCard = new javax.swing.JComboBox();
        chkSealable = new javax.swing.JCheckBox();
        panColour = new javax.swing.JPanel();
        rdoColour1 = new javax.swing.JRadioButton();
        rdoColour2 = new javax.swing.JRadioButton();
        rdoColour0 = new javax.swing.JRadioButton();
        panReinforced = new javax.swing.JPanel();
        chkBottom = new javax.swing.JCheckBox();
        chkCorners = new javax.swing.JCheckBox();
        spnQuantity = new javax.swing.JSpinner();
        btnAdd = new javax.swing.JButton();
        panelOrder = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        tableOrder = new javax.swing.JTable();
        txtSubtotal = new javax.swing.JTextField();
        lblLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));
        setForeground(new java.awt.Color(204, 204, 255));

        btnCheckout.setText("Checkout");
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        lblTotal.setText("Subtotal:");

        panelBox.setBorder(javax.swing.BorderFactory.createTitledBorder("Box"));

        panAttributes.setBorder(javax.swing.BorderFactory.createTitledBorder("Attributes"));

        lblHeight.setText("Height (m)");

        lblWidth.setText("Width (m)");

        lblLength.setText("Length (m)");

        txtHeight.setText("0.00");

        txtWidth.setText("0.00");
        txtWidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWidthActionPerformed(evt);
            }
        });

        txtDepth.setText("0.00");
        txtDepth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepthActionPerformed(evt);
            }
        });

        lblCard.setText("Grade of Card");

        cboCard.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));

        chkSealable.setText("Sealable Top");
        chkSealable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSealableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panAttributesLayout = new javax.swing.GroupLayout(panAttributes);
        panAttributes.setLayout(panAttributesLayout);
        panAttributesLayout.setHorizontalGroup(
            panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAttributesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAttributesLayout.createSequentialGroup()
                        .addComponent(lblCard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panAttributesLayout.createSequentialGroup()
                        .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panAttributesLayout.createSequentialGroup()
                                .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblWidth)
                                    .addComponent(lblHeight))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panAttributesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblLength)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHeight)
                            .addComponent(txtWidth)
                            .addComponent(txtDepth)
                            .addComponent(chkSealable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panAttributesLayout.setVerticalGroup(
            panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAttributesLayout.createSequentialGroup()
                .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCard)
                    .addComponent(cboCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeight)
                    .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWidth)
                    .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLength)
                    .addComponent(txtDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(chkSealable)
                .addContainerGap())
        );

        panColour.setBorder(javax.swing.BorderFactory.createTitledBorder("Colour"));

        rdoColour1.setText("1 Colour");
        rdoColour1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoColour1ActionPerformed(evt);
            }
        });

        rdoColour2.setText("2 Colour");

        rdoColour0.setText("None");
        rdoColour0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoColour0ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panColourLayout = new javax.swing.GroupLayout(panColour);
        panColour.setLayout(panColourLayout);
        panColourLayout.setHorizontalGroup(
            panColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panColourLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoColour1)
                    .addComponent(rdoColour0)
                    .addComponent(rdoColour2))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        panColourLayout.setVerticalGroup(
            panColourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panColourLayout.createSequentialGroup()
                .addComponent(rdoColour1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoColour2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoColour0))
        );

        panReinforced.setBorder(javax.swing.BorderFactory.createTitledBorder("Reinforced"));

        chkBottom.setText("Bottom");
        chkBottom.setToolTipText("");

        chkCorners.setText("Corners");

        javax.swing.GroupLayout panReinforcedLayout = new javax.swing.GroupLayout(panReinforced);
        panReinforced.setLayout(panReinforcedLayout);
        panReinforcedLayout.setHorizontalGroup(
            panReinforcedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chkBottom)
            .addComponent(chkCorners)
        );
        panReinforcedLayout.setVerticalGroup(
            panReinforcedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panReinforcedLayout.createSequentialGroup()
                .addComponent(chkBottom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkCorners)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        spnQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBoxLayout = new javax.swing.GroupLayout(panelBox);
        panelBox.setLayout(panelBoxLayout);
        panelBoxLayout.setHorizontalGroup(
            panelBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBoxLayout.createSequentialGroup()
                .addGroup(panelBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBoxLayout.createSequentialGroup()
                        .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBoxLayout.createSequentialGroup()
                            .addComponent(panColour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panReinforced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(panAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelBoxLayout.setVerticalGroup(
            panelBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBoxLayout.createSequentialGroup()
                .addComponent(panAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panColour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panReinforced, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(spnQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelOrder.setBorder(javax.swing.BorderFactory.createTitledBorder("Order"));
        panelOrder.setToolTipText("");

        scrollPane.setForeground(new java.awt.Color(204, 204, 255));

        tableOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPane.setViewportView(tableOrder);

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtSubtotal.setText("£0.00");

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FlexBoxApplication/UI/FlexBoxLogo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTotal)
                            .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCheckout)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotal)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRemove)
                            .addComponent(btnCheckout)))
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        // validates the dimensions 
        if (dimensionValidation(txtWidth.getText()) && dimensionValidation(txtHeight.getText()) && dimensionValidation(txtDepth.getText())) {
            // attempts to create a box
            createBox(Integer.valueOf(spnQuantity.getValue().toString()), chkBottom.isSelected(), chkCorners.isSelected(),
                    getColours(), Integer.valueOf(cboCard.getSelectedItem().toString()), Double.valueOf(txtWidth.getText()),
                    Double.valueOf(txtHeight.getText()), Double.valueOf(txtDepth.getText()), chkSealable.isSelected());
            // updates the order
            updateOrder();
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void rdoColour1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoColour1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoColour1ActionPerformed

    private void rdoColour0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoColour0ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoColour0ActionPerformed

    private void chkSealableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSealableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSealableActionPerformed

    private void txtDepthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepthActionPerformed

    private void txtWidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWidthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWidthActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // Ensures only one line is selected
        if (tableOrder.getSelectedRowCount() == 1) {
            order.removeOrderLine(tableOrder.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(null, "Please select a single row to remove.");
        }
        updateOrder();

    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        if (!order.getOrder().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have successfully completed the order, the total cost is " + formatCurrency(order.getOrderTotal()));
            // clear the order and update
            order = new Order();
            updateOrder();
        } else {
            JOptionPane.showMessageDialog(null, "Your order is empty.");
        }
    }//GEN-LAST:event_btnCheckoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnRemove;
    private javax.swing.JComboBox cboCard;
    private javax.swing.JCheckBox chkBottom;
    private javax.swing.JCheckBox chkCorners;
    private javax.swing.JCheckBox chkSealable;
    private javax.swing.JLabel lblCard;
    private javax.swing.JLabel lblHeight;
    private javax.swing.JLabel lblLength;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblWidth;
    private javax.swing.JPanel panAttributes;
    private javax.swing.JPanel panColour;
    private javax.swing.JPanel panReinforced;
    private javax.swing.JPanel panelBox;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JRadioButton rdoColour0;
    private javax.swing.JRadioButton rdoColour1;
    private javax.swing.JRadioButton rdoColour2;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JTable tableOrder;
    private javax.swing.JTextField txtDepth;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtWidth;
    // End of variables declaration//GEN-END:variables
}
