import java.util.*;


public class DoubleBridge {

    Tour path;
    DistanceMatrix distanceMatrix;

    public DoubleBridge(Tour path, DistanceMatrix distanceMatrix) {
        this.path = path;
        this.distanceMatrix = distanceMatrix;
    }

    public int[] executeDoubleBridge(ArrayList<City> path, Integer[][] distanceMatrix, int num_cities, double tourDistance) {


        Integer[] intArray = path.stream().map(City::getId).toArray(Integer[]::new);
        int[] array = Arrays.stream(intArray).mapToInt(Integer::intValue).toArray();

        Random random = new Random();
        int pos1, pos2, pos3, pos4;
        do {
            pos1 = random.nextInt(num_cities);
            pos2 = random.nextInt(num_cities);
            pos3 = random.nextInt(num_cities);
            pos4 = random.nextInt(num_cities);
        } while (pos1 < 0 || pos2 < pos1 || pos3 < pos2 || pos4 < pos3);


        final int incPos1 = pos1 + 1;
        final int incPos2 = pos2 + 1;
        final int incPos3 = pos3 + 1;
        final int incPos4 = pos4 + 1;

        final int sub = -distanceMatrix[array[pos1]][array[incPos1]]
                - distanceMatrix[array[pos2]][array[incPos2]]
                - distanceMatrix[array[pos3]][array[incPos3]]
                - distanceMatrix[array[pos4]][array[incPos4]];

        final int add = +distanceMatrix[array[pos1]][array[incPos3]]
                + distanceMatrix[array[pos4]][array[incPos2]]
                + distanceMatrix[array[pos3]][array[incPos1]]
                + distanceMatrix[array[pos2]][array[incPos4]];

        SimulatedAnnealing.bestValue += (add + sub);

        //List<City> cities = new ArrayList<>(path);
        int[] temp_solution = array.clone();
        System.arraycopy(array, 0, temp_solution, 0, num_cities);

        int cnt = incPos1;

        for (int j = incPos3; j <= pos4; j++, cnt++)
            array[cnt] = temp_solution[j];
        for (int j = incPos2; j <= pos3; j++, cnt++)
            array[cnt] = temp_solution[j];
        for (int j = incPos1; j <= pos2; j++, cnt++)
            array[cnt] = temp_solution[j];

        return array;

    }

//    public Tour executeDoubleBridge() {
//        int num_cities = path.getTourSize() - 1;
//        Random random = new Random();
//        int pos1,pos2,pos3,pos4;
//        do {
//            pos1 = random.nextInt(num_cities);
//            pos2 = random.nextInt(num_cities);
//            pos3 = random.nextInt(num_cities);
//            pos4 = random.nextInt(num_cities);
//        }while(pos1<0 || pos2<pos1 || pos3<pos2 ||pos4<pos3);
//
//        final int incPos1 = pos1 + 1;
//        final int incPos2 = pos2 + 1;
//        final int incPos3 = pos3 + 1;
//
//        ArrayList<City> temp_solution = new ArrayList<>(path.getPath());
//        path.getPath().clear();
//        path.getPath().addAll(temp_solution.subList(0,pos1));
//
//        path.getPath().addAll(temp_solution.subList(incPos3,pos4));
//        path.getPath().addAll(temp_solution.subList(incPos2,pos3));
//        path.getPath().addAll(temp_solution.subList(incPos1,pos2));
//
//
//        return path;
//    }
}
