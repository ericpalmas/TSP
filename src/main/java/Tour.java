import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    private  ArrayList<City> path;

    public Tour(){
        path = new ArrayList<>();
    }



    public void addCity(City newCity){
        path.add(newCity);
    }

    public ArrayList<City> getPath() {
        return path;
    }

    public Tour(Tour anotherTour){
        path = new ArrayList<>(anotherTour.getPath());
    }

    public Tour(ArrayList<City> cities){
        path = new ArrayList<>(cities);
    }

    public void setPath(ArrayList<City> path) {
        this.path = path;
    }

    public void setCity(int i,City city){
        if(i>=path.size()){
            path.add(city);
        }else
            path.set(i,city);
    }
    public City getCity(int i){
        return path.get(i);
    }

    public double getTourDistance(DistanceMatrix distanceMatrix){
        int distance = 0;
        for(int i=0;i<path.size()-1;i++){
            distance += distanceMatrix.getDistanceMatrix()[path.get(i).getId()][path.get(i+1).getId()];
        }
        return distance;
    }

    public int getTourSize(){
        return path.size();
    }


    public void printCoordinates(){
        System.out.println("Latitudine:");
        for(int i=0;i<path.size();i++){
            System.out.println(path.get(i).getLatitude());
        }
        System.out.println("Longitudine:");
        for (int j=0;j<path.size();j++){
            System.out.println(path.get(j).getLongitude());
        }
    }
    //public void check
}
