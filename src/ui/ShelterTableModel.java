package ui;

import models.AnimalShelter;
import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.util.List;


public class ShelterTableModel extends AbstractTableModel {
    private List<AnimalShelter> shelters;
    private String[] columnNames = {"Shelter Name", "Capacity", "Current Animals"};

    public ShelterTableModel(List<AnimalShelter> shelters) {
        this.shelters = shelters;
    }

    @Override
    public int getRowCount() {
        return shelters.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AnimalShelter shelter = shelters.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return shelter.getShelterName();
            case 1:
                return shelter.getMaxCapacity();
            case 2:
                return shelter.getAnimalList().size();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
