package Pharmacy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacyManagementSystem {

    private List<Drug> drugs = new ArrayList<>();
    private int pharmacyCapacity = 0;
    private double totalSales = 0;

    private JFrame frame;
    private JPanel panel;
    private JLabel capacityLabel;
    private JTextField capacityField;
    private JButton addDrugButton;
    private JButton removeDrugButton;
    private JButton placeOrderButton;
    private JButton getTotalSalesButton;
    private JButton exitButton;
    private JButton setCapacityButton;

    public PharmacyManagementSystem() {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Pharmacy Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

        capacityLabel = new JLabel("Enter pharmacy capacity: ");
        capacityField = new JTextField(10);
        addDrugButton = new JButton("Add Drug");
        removeDrugButton = new JButton("Remove Drug");
        placeOrderButton = new JButton("Place Order");
        getTotalSalesButton = new JButton("Get Total Sales");
        exitButton = new JButton("Exit");
        setCapacityButton = new JButton("Set Capacity");

        panel.add(capacityLabel);
        panel.add(capacityField);
        panel.add(addDrugButton);
        panel.add(removeDrugButton);
        panel.add(placeOrderButton);
        panel.add(getTotalSalesButton);
        panel.add(exitButton);
        panel.add(setCapacityButton);

        addDrugButton.addActionListener(e -> addDrug());
        removeDrugButton.addActionListener(e -> removeDrug());
        placeOrderButton.addActionListener(e -> placeOrder());
        getTotalSalesButton.addActionListener(e -> getTotalSales());
        exitButton.addActionListener(e -> System.exit(0));
        setCapacityButton.addActionListener(e -> setPharmacyCapacity());

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        setPharmacyCapacity();
    }

    private void addDrug() {
        if (drugs.size() >= pharmacyCapacity) {
            JOptionPane.showMessageDialog(null, "Pharmacy has reached its capacity. Cannot add more drugs.");
            return;
        }

        JFrame addDrugFrame = new JFrame("Add Drug");
        addDrugFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField availableQuantityField = new JTextField();
        String[] categories = {"Cosmetics", "Prescription Drugs", "Other"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("ID: "));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Price: "));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Category: "));
        inputPanel.add(categoryComboBox);
        inputPanel.add(new JLabel("Available Quantity: "));
        inputPanel.add(availableQuantityField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String id = idField.getText();
                double price = Double.parseDouble(priceField.getText());
                String category = (String) categoryComboBox.getSelectedItem();
                int availableQuantity = Integer.parseInt(availableQuantityField.getText());
                
                Drug newDrug = new Drug(name, id, price, category, availableQuantity);
                drugs.add(newDrug);
                addDrugFrame.dispose();
                JOptionPane.showMessageDialog(null, "Drug added successfully: " + newDrug); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid values.");
            }
        });

        inputPanel.add(addButton);

        addDrugFrame.getContentPane().add(inputPanel);
        addDrugFrame.pack();
        addDrugFrame.setVisible(true);
    }

    private void removeDrug() {
        String idToRemove = JOptionPane.showInputDialog("Enter Drug ID to remove:");

        if (idToRemove != null && !idToRemove.isEmpty()) {
            boolean removed = drugs.removeIf(drug -> drug.getId().equals(idToRemove));

            if (removed) {
                JOptionPane.showMessageDialog(null, "Drug removed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Drug with ID '" + idToRemove + "' not found.");
            }
        }
    }

    private void placeOrder() {
        String idToOrder = JOptionPane.showInputDialog("Enter Drug ID to place order:");

        if (idToOrder != null && !idToOrder.isEmpty()) {
            Drug orderedDrug = drugs.stream()
                                   .filter(drug -> drug.getId().equals(idToOrder))
                                   .findFirst()
                                   .orElse(null);

            if (orderedDrug != null) {
                try {
                    int requestedQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity to order:"));
                    if (requestedQuantity <= 0) {
                        throw new NumberFormatException(); // Non-positive quantity
                    }

                    if (requestedQuantity <= orderedDrug.getAvailableQuantity()) {
                        totalSales += orderedDrug.getPrice() * requestedQuantity;
                        orderedDrug.setAvailableQuantity(orderedDrug.getAvailableQuantity() - requestedQuantity);
                        JOptionPane.showMessageDialog(null, "Order placed successfully for " + requestedQuantity + " " + orderedDrug.getName() + "(s).");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient stock. Only " + orderedDrug.getAvailableQuantity() + " available.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity entered.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Drug with ID '" + idToOrder + "' not found.");
            }
        }
    }

    private void getTotalSales() {
        JOptionPane.showMessageDialog(null, "Total sales for the day: $" + totalSales);
    }

    private void setPharmacyCapacity() {
        while (pharmacyCapacity == 0) {
            try {
                String capacityInput = JOptionPane.showInputDialog("Enter pharmacy capacity:");
                if (capacityInput != null) {
                    pharmacyCapacity = Integer.parseInt(capacityInput);
                    if (pharmacyCapacity <= 0) {
                        throw new NumberFormatException();
                    }
                } else {
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive integer for capacity.");
            }
        }
    }

    public static void main(String[] args) {
        new PharmacyManagementSystem(); 
    }
}

