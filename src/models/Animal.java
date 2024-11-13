package models;

import enums.AnimalCondition;

public class Animal implements Comparable<Animal> {
    private String name;
    private String species;
    private AnimalCondition condition;
    private int age;
    private double price;

    public Animal(String name, String species, AnimalCondition condition, int age, double price) {
        this.name = name;
        this.species = species;
        this.condition = condition;
        this.age = age;
        this.price = price;
    }

    public void print() {
        System.out.println("Name: " + name + ", Species: " + species + ", Condition: " + condition + ", Age: " + age + ", Price: " + price);
    }

    @Override
    public int compareTo(Animal other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Species: " + species + ", Condition: " + condition + ", Age: " + age + ", Price: " + price;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public AnimalCondition getCondition() {
        return condition;
    }

    public void setCondition(AnimalCondition condition) {
        this.condition = condition;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }
}