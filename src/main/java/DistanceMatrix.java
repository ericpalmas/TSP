import java.util.ArrayList;

public class DistanceMatrix {
    private Integer[][] distanceMatrix;

    public DistanceMatrix(final ArrayList<City> cities){
        distanceMatrix = new Integer[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                distanceMatrix[i][j] = distFrom(cities.get(i).getLatitude(), cities.get(i).getLongitude(), cities.get(j).getLatitude(), cities.get(j).getLongitude());
            }
        }
    }

    public Integer[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    private int distFrom(double lat1, double lng1, double lat2, double lng2) {
        int dist = (int) (Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lng2 - lng1, 2)) + 0.5);
        return dist;
    }
}
