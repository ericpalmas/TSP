import java.util.ArrayList;
import java.lang.Math;

public class Controller {
    public static ArrayList<City> cities = new ArrayList<>();
    private static Integer[][] adjacencyMatrix;

    public static int distFrom(double lat1, double lng1, double lat2, double lng2) {
        int dist = (int) Math.sqrt(Math.pow(lat2-lat1,2)+Math.pow(lng2-lng1,2));
        return dist;
    }


    public static void main(String[] args){

        TspReader tspReader = new TspReader("src/files/" + args[0]);
        tspReader.getCitiesInfo();
        adjacencyMatrix = new Integer[cities.size()][cities.size()];
        calculateDistance();

    }


    private static void calculateDistance() {
        for(int i=0;i<cities.size();i++){
            for(int j=0;j<cities.size();j++){
                if(i==j){
                    adjacencyMatrix[i][j] = Integer.MAX_VALUE;
                }else
                    adjacencyMatrix[i][j] = distFrom(cities.get(i).getLatitude(),cities.get(i).getLongitude(),cities.get(j).getLatitude(),cities.get(j).getLongitude());
            }
        }

//        for(int i=0;i<cities.size();i++){
//            for(int j=0;j<cities.size();j++){
//                System.out.print(adjacencyMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
    }
}
