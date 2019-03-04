import java.util.ArrayList;

public class Tour {
    private  ArrayList<City> path = new ArrayList<>();

    public void addCity(City newCity){
        path.add(newCity);
    }

    public ArrayList<City> getPath() {
        return path;
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
            distance += distanceMatrix.getDistanceMatrix()[path.get(i).getId()-1][path.get(i+1).getId()-1];
        }
        return distance;
    }
    public int getTourSize(){
        return path.size();
    }
}
