package ui;

import managers.ShelterManager;
import models.Animal;
import models.AnimalShelter;
import enums.AnimalCondition;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
public class ShelterApp {
    private ShelterManager shelterManager;
    private boolean isAdmin;

    public ShelterApp(ShelterManager shelterManager, boolean isAdmin) {
        this.shelterManager = shelterManager;
        this.isAdmin = isAdmin;
    }

    public void initUI() {
        JFrame frame = new JFrame("Animal Shelter Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Shelter List Table
        JTable shelterTable = new JTable(new ShelterTableModel(new ArrayList<>(shelterManager.getShelters().values())));
        JScrollPane shelterScrollPane = new JScrollPane(shelterTable);
        mainPanel.add(shelterScrollPane, BorderLayout.WEST);

        // Animal List Table
        DefaultListModel<Animal> animalListModel = new DefaultListModel<>();
        JList<Animal> animalList = new JList<>(animalListModel);
        JScrollPane animalScrollPane = new JScrollPane(animalList);
        mainPanel.add(animalScrollPane, BorderLayout.CENTER);

        // Update animal list when a shelter is selected
        shelterTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = shelterTable.getSelectedRow();
            if (selectedRow != -1) {
                String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);
                AnimalShelter selectedShelter = shelterManager.getShelters().get(shelterName);
                if (selectedShelter != null) {
                    animalListModel.clear();
                    for (Animal animal : selectedShelter.getAnimalList()) {
                        animalListModel.addElement(animal);
                    }
                }
            }
        });

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        if (isAdmin) {
            JButton addShelterButton = new JButton("Add Shelter");
            addShelterButton.addActionListener(e -> {
                String name = JOptionPane.showInputDialog(frame, "Enter Shelter Name:");
                String capacityStr = JOptionPane.showInputDialog(frame, "Enter Shelter Capacity:");
                try {
                    int capacity = Integer.parseInt(capacityStr);
                    shelterManager.addShelter(name, capacity);
                    shelterTable.setModel(new ShelterTableModel(new ArrayList<>(shelterManager.getShelters().values())));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid capacity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonPanel.add(addShelterButton);

            JButton removeShelterButton = new JButton("Remove Shelter");
            removeShelterButton.addActionListener(e -> {
                int selectedRow = shelterTable.getSelectedRow();
                if (selectedRow != -1) {
                    String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);
                    shelterManager.removeShelter(shelterName);
                    shelterTable.setModel(new ShelterTableModel(new ArrayList<>(shelterManager.getShelters().values())));
                } else {
                    JOptionPane.showMessageDialog(frame, "No shelter selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonPanel.add(removeShelterButton);

            JButton addAnimalButton = new JButton("Add Animal");
            addAnimalButton.addActionListener(e -> {
                int selectedRow = shelterTable.getSelectedRow();
                if (selectedRow != -1) {
                    String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);
                    AnimalShelter selectedShelter = shelterManager.getShelters().get(shelterName);
                    if (selectedShelter != null) {
                        String animalName = JOptionPane.showInputDialog(frame, "Enter Animal Name:");
                        String species = JOptionPane.showInputDialog(frame, "Enter Species:");
                        String conditionStr = JOptionPane.showInputDialog(frame, "Enter Condition (ZDROWE, CHORE, W_TRAKCIE_ADOPCJI, KWARANTANNA):");
                        AnimalCondition condition;
                        try {
                            condition = AnimalCondition.valueOf(conditionStr.toUpperCase());
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid condition.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String ageStr = JOptionPane.showInputDialog(frame, "Enter Age:");
                        String priceStr = JOptionPane.showInputDialog(frame, "Enter Price:");
                        try {
                            int age = Integer.parseInt(ageStr);
                            double price = Double.parseDouble(priceStr);
                            Animal animal = new Animal(animalName, species, condition, age, price);
                            selectedShelter.addAnimal(animal);
                            animalListModel.addElement(animal);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid age or price.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No shelter selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonPanel.add(addAnimalButton);

            JButton removeAnimalButton = new JButton("Remove Animal");
            removeAnimalButton.addActionListener(e -> {
                Animal selectedAnimal = animalList.getSelectedValue();
                if (selectedAnimal != null) {
                    int selectedRow = shelterTable.getSelectedRow();
                    String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);
                    AnimalShelter selectedShelter = shelterManager.getShelters().get(shelterName);
                    if (selectedShelter != null) {
                        selectedShelter.removeAnimal(selectedAnimal);
                        animalListModel.removeElement(selectedAnimal);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No animal selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            buttonPanel.add(removeAnimalButton);
        }

        JButton sortByNameButton = new JButton("Sort Animals by Name");
        sortByNameButton.addActionListener(e -> {
            int selectedRow = shelterTable.getSelectedRow();
            if (selectedRow != -1) {
                String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);
                AnimalShelter selectedShelter = shelterManager.getShelters().get(shelterName);
                if (selectedShelter != null) {
                    List<Animal> sortedAnimals = selectedShelter.sortByName();
                    animalListModel.clear();
                    for (Animal animal : sortedAnimals) {
                        animalListModel.addElement(animal);
                    }
                }
            }
        });
        buttonPanel.add(sortByNameButton);

        JButton sortByPriceButton = new JButton("Sort Animals by Price");
        sortByPriceButton.addActionListener(e -> {
            int selectedRow = shelterTable.getSelectedRow();
            if (selectedRow != -1) {
                String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);
                AnimalShelter selectedShelter = shelterManager.getShelters().get(shelterName);
                if (selectedShelter != null) {
                    List<Animal> sortedAnimals = selectedShelter.sortByPrice();
                    animalListModel.clear();
                    for (Animal animal : sortedAnimals) {
                        animalListModel.addElement(animal);
                    }
                }
            }
        });
        buttonPanel.add(sortByPriceButton);

        mainPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
