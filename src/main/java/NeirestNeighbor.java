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
        int min, count, i, minPos = 0;
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
            cities.get(minPos).setVisited(true);
            minPath.addCity(cities.get(minPos));
        }
        minPath.addCity(cities.get(0));
        //minPath.addCity(minPath.getCity(0));
        return minPath;
    }



    public int[] calculate(){
        int[] minPath = new int[cities.size()+1];
        int min, count, i, minPos = 0;
        minPath[0] = cities.get(0).getId();
        boolean[] visitedCities = new boolean[cities.size()];
        visitedCities[0] = true;
        for (count = 1; count <= cities.size(); count++) {
            min = Integer.MAX_VALUE;
            i = minPos;
            for(int j = 0; j < cities.size(); ++j){
                if(distanceMatrix.getDistanceMatrix()[i][j] != 0 && distanceMatrix.getDistanceMatrix()[i][j] < min && !visitedCities[j]){
                    min = distanceMatrix.getDistanceMatrix()[i][j];
                    minPos = j;
                }
            }
            if (min == Integer.MAX_VALUE)
                break;
            visitedCities[minPos] = true;
            minPath[count] = cities.get(minPos).getId();
        }
        minPath[cities.size()] = minPath[0];
        return minPath;
    }
}
