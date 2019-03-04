import java.util.ArrayList;

public class NeirestNeighbor {
    private  ArrayList<City> cities;
    private  DistanceMatrix distanceMatrix;

    public NeirestNeighbor(ArrayList<City> cities, DistanceMatrix distanceMatrix){
        this.cities = cities;
        this.distanceMatrix = distanceMatrix;
    }

    public Tour calculatePath() {
        Tour minPath = new Tour();
        int min, count, i, minPos = 0, distance = 0;
        minPath.addCity(cities.get(0));
        cities.get(0).setVisited(true);
        for (count = 0; count < cities.size(); count++) {
            min = Integer.MAX_VALUE;
            i = minPos;
            for (int j = 0; j < cities.size(); j++) {
                if (distanceMatrix.getDistanceMatrix()[i][j] != 0 && distanceMatrix.getDistanceMatrix()[i][j] < min && !cities.get(j).isVisited()) {
                    min = distanceMatrix.getDistanceMatrix()[i][j];
                    minPos = j;
                }
            }
            if (min == Integer.MAX_VALUE)
                break;
            else
                distance += min;
            cities.get(minPos).setVisited(true);
            minPath.addCity(cities.get(minPos));
        }
        minPath.addCity(cities.get(0));
        distance += distanceMatrix.getDistanceMatrix()[minPos][0];
        minPath.addCity(minPath.getCity(0));
        //return distance;
        return minPath;
    }
}
