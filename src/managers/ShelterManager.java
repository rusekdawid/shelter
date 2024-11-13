package managers;

import models.AnimalShelter;
import enums.AnimalCondition;
import java.util.*;

public class ShelterManager {
    private Map<String, AnimalShelter> shelters;

    public ShelterManager() {
        shelters = new HashMap<>();
    }

    public void addShelter(String name, int capacity) {
        shelters.put(name, new AnimalShelter(name, capacity));
    }

    public void removeShelter(String name) {
        shelters.remove(name);
    }

    public List<String> findEmpty() {
        List<String> emptyShelters = new ArrayList<>();
        for (Map.Entry<String, AnimalShelter> entry : shelters.entrySet()) {
            if (entry.getValue().countByCondition(AnimalCondition.ZDROWE) == 0) {
                emptyShelters.add(entry.getKey());
            }
        }
        return emptyShelters;
    }

    public void summary() {
        for (Map.Entry<String, AnimalShelter> entry : shelters.entrySet()) {
            String name = entry.getKey();
            AnimalShelter shelter = entry.getValue();
            int totalAnimals = shelter.countByCondition(AnimalCondition.ZDROWE) + shelter.countByCondition(AnimalCondition.CHORE) + shelter.countByCondition(AnimalCondition.W_TRAKCIE_ADOPCJI) + shelter.countByCondition(AnimalCondition.KWARANTANNA);
            System.out.println("Shelter: " + name + ", Occupancy: " + (totalAnimals * 100.0 / shelter.getMaxCapacity()) + "%");
        }
    }

    public Map<String, AnimalShelter> getShelters() {
        return shelters;
    }
}
