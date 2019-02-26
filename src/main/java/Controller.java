import java.util.ArrayList;
import java.lang.Math;

public class Controller {
    public static ArrayList<City> cities = new ArrayList<>();
    private static Integer[][] adjacencyMatrix;
    private static ArrayList<City> minPath = new ArrayList<>();

    public static void main(String[] args){

        TspReader tspReader = new TspReader("src/files/" + args[0]);
        tspReader.getCitiesInfo();
        adjacencyMatrix = new Integer[cities.size()][cities.size()];
        calculateDistance();
        nearestNeighbor();
        for(City c : minPath){
            System.out.println(c);
        }
    }

    private static void nearestNeighbor() {
        int min,count, i, minPos = 0;
        minPath.add(cities.get(0));
        cities.get(0).setVisited(true);
        for(count = 0;count<cities.size();count++){
            min = Integer.MAX_VALUE;
            i = minPos;
            for(int j=0;j<cities.size();j++){
                if(adjacencyMatrix[i][j]!=0 && adjacencyMatrix[i][j]<min && !cities.get(j).isVisited()){
                    min = adjacencyMatrix[i][j];
                    minPos = j;
                }
            }
            if(min==Integer.MAX_VALUE)
                break;
            cities.get(minPos).setVisited(true);
            minPath.add(cities.get(minPos));
        }
        minPath.add(cities.get(0));
    }

    public static int distFrom(double lat1, double lng1, double lat2, double lng2) {
        int dist = (int) (Math.sqrt(Math.pow(lat2-lat1,2)+Math.pow(lng2-lng1,2)) + 0.5);
        return dist;
    }

    private static void calculateDistance() {
        for(int i=0;i<cities.size();i++){
            for(int j=0;j<cities.size();j++){
                    adjacencyMatrix[i][j] = distFrom(cities.get(i).getLatitude(),cities.get(i).getLongitude(),cities.get(j).getLatitude(),cities.get(j).getLongitude());
            }
        }
    }
}
