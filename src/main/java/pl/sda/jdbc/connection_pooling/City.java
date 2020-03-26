package pl.sda.jdbc.connection_pooling;

public class City {

    private String name;
    private int population;

    City(String name, int population) {
        this.name = name;
        this.population = population;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
