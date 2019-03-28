import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomGenerator {
    ArrayList<City> cities;

    RandomGenerator(ArrayList<City> cities){
        this.cities = cities;
    }

    public Tour generateTour(){
        Collections.shuffle(cities);
        cities.add(cities.get(0));
        Tour path = new Tour(cities);
        return path;
    }
}
