package models;

import enums.AnimalCondition;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class AnimalShelter {
    private String shelterName;
    private List<Animal> animalList;
    private int maxCapacity;

    public AnimalShelter(String shelterName, int maxCapacity) {
        this.shelterName = shelterName;
        this.maxCapacity = maxCapacity;
        this.animalList = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        if (animalList.size() >= maxCapacity) {
            System.err.println("Maximum capacity reached!");
        } else if (animalList.contains(animal)) {
            System.out.println("Animal already exists in the shelter.");
        } else {
            animalList.add(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        animalList.remove(animal);
    }

    public void changeCondition(Animal animal, AnimalCondition condition) {
        if (animalList.contains(animal)) {
            animal.setCondition(condition);
        }
    }

    public void changeAge(Animal animal, int age) {
        if (animalList.contains(animal)) {
            animal.setAge(age);
        }
    }

    public int countByCondition(AnimalCondition condition) {
        int count = 0;
        for (Animal animal : animalList) {
            if (animal.getCondition() == condition) {
                count++;
            }
        }
        return count;
    }

    public List<Animal> sortByName() {
        List<Animal> sortedList = new ArrayList<>(animalList);
        sortedList.sort(Comparator.comparing(Animal::getName));
        return sortedList;
    }

    public List<Animal> sortByPrice() {
        List<Animal> sortedList = new ArrayList<>(animalList);
        sortedList.sort(Comparator.comparingDouble(Animal::getPrice));
        return sortedList;
    }

    public Animal search(String name) {
        for (Animal animal : animalList) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }

    public List<Animal> searchPartial(String fragment) {
        List<Animal> resultList = new ArrayList<>();
        for (Animal animal : animalList) {
            if (animal.getName().contains(fragment) || animal.getSpecies().contains(fragment)) {
                resultList.add(animal);
            }
        }
        return resultList;
    }

    public void summary() {
        for (Animal animal : animalList) {
            animal.print();
        }
    }

    public String getShelterName() {
        return shelterName;
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
