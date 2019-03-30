import java.util.Random;

public class SimulatedAnnealing {
    private int[] path;
    private DistanceMatrix distanceMatrix;
    private long startTime;
    private Random random;


    public SimulatedAnnealing(int[] path, DistanceMatrix distanceMatrix, long startTime, Random random) {
        this.path = path;
        this.distanceMatrix = distanceMatrix;
        this.startTime = startTime;
        this.random = random;
    }

    public int getTourDistance(int[] path) {

        int distance = 0;
        for (int i = 0; i < path.length - 1; i++) {
            distance += distanceMatrix.getDistanceMatrix()[path[i]][path[i + 1]];
        }
        return distance;
    }

//    public int[] computeSolution() {
//        double temperature = 100, alpha = 0.95;
//        int[] candidate, next, current = path.clone(), best = current.clone();
//        long end;
//
//        do {
//            for (int i = 0; i < 100; i++) {
//
//                next = executeDoubleBridge(current);
//
//                TwoOpt twoOpt = new TwoOpt(next, distanceMatrix);
//                candidate = twoOpt.optimize().clone();
//
//                if (getTourDistance(candidate) < getTourDistance(current)) {
//                    current = candidate.clone();
//                    if (getTourDistance(current) < getTourDistance(best)) {
//                        best = current.clone();
//                        //System.out.println("SA: " + getTourDistance(best) + " seed: " + System.currentTimeMillis());
//                    }
//                } else if (random.nextDouble() < Math.exp(-(getTourDistance(candidate) - getTourDistance(current)) / temperature)) {
//                    current = candidate.clone();
//                }
//                end = System.nanoTime();
//                if (end - startTime > 1.71e+11)
//                    return best;
//            }
//            temperature *= alpha;
//
//        } while (temperature > 0.00001);
//        return best;
//    }


//    public int[] computeSolution() {
//        double temperature = 100, alpha = 0.95;
//        int[] candidate, current = path.clone(), best = current.clone();
//        long end;
//
//
//        do {
//            for (int i = 0; i < 100; i++) {
//
//                currentDistance = executeDoubleBridge(current);
//
//                TwoOpt twoOpt = new TwoOpt(path, distanceMatrix);
//                candidate = twoOpt.optimize().clone();
//
//                candidateDistance = getTourDistance(candidate);
//                if (candidateDistance < currentDistance) {
//                    current = candidate.clone();
//                    if (candidateDistance < getTourDistance(best)) {
//                        best = current.clone();
//                        currentDistance = getTourDistance(best);
//                        System.out.println("SA: " + getTourDistance(best) + " seed: " + System.currentTimeMillis());
//                    }
//                } else if (random.nextDouble() < Math.exp(-((double) candidateDistance - currentDistance) / temperature)) {
//                    current = candidate.clone();
//                }
//                end = System.nanoTime();
//                if (end - startTime > 1.71e+11)
//                    return best;
//            }
//            temperature *= alpha;
//
//        } while (temperature > 0.0001);
//        return best;
//    }

//    private int executeDoubleBridge(int[] path) {
//        int tourDistance = getTourDistance(path);
//        int pos1, pos2, pos3, pos4;
//        do {
//            pos1 = random.nextInt(path.length - 1);
//            pos2 = random.nextInt(path.length - 1);
//            pos3 = random.nextInt(path.length - 1);
//            pos4 = random.nextInt(path.length - 1);
//        } while (pos1 < 0 || pos2 < pos1 || pos3 < pos2 || pos4 < pos3);
//
//        int gain = calculateGain(pos1,pos2,pos3,pos4);
//        int[] temp_solution = path.clone();
//
//        int k = pos1 + 1;
//
//        for (int j = pos3 + 1; j <= pos4; j++, k++)
//            path[k] = temp_solution[j];
//        for (int j = pos2 + 1; j <= pos3; j++, k++)
//            path[k] = temp_solution[j];
//        for (int j = pos1 + 1; j <= pos2; j++, k++)
//            path[k] = temp_solution[j];
//
//        return tourDistance + gain;
//    }


//    private int[] executeDoubleBridge(int[] path) {
//        int pos1, pos2, pos3, pos4;
//        do {
//            pos1 = random.nextInt(path.length - 1);
//            pos2 = random.nextInt(path.length - 1);
//            pos3 = random.nextInt(path.length - 1);
//            pos4 = random.nextInt(path.length - 1);
//        } while (pos1 < 0 || pos2 < pos1 || pos3 < pos2 || pos4 < pos3);
//
//        int[] temp_solution = path.clone();
//
//        int k = pos1 + 1;
//
//        for (int j = pos3 + 1; j <= pos4; j++, k++)
//            path[k] = temp_solution[j];
//        for (int j = pos2 + 1; j <= pos3; j++, k++)
//            path[k] = temp_solution[j];
//        for (int j = pos1 + 1; j <= pos2; j++, k++)
//            path[k] = temp_solution[j];
//
//        return path;
//    }

    private int calculateGain(int pos1, int pos2, int pos3, int pos4) {
        return distanceMatrix.getDistanceMatrix()[path[pos1]][path[pos3 + 1]]
                + distanceMatrix.getDistanceMatrix()[path[pos4]][path[pos2 + 1]]
                + distanceMatrix.getDistanceMatrix()[path[pos3]][path[pos1 + 1]]
                + distanceMatrix.getDistanceMatrix()[path[pos2]][path[pos4 + 1]]
                -distanceMatrix.getDistanceMatrix()[path[pos1]][path[pos1 + 1]]
                - distanceMatrix.getDistanceMatrix()[path[pos2]][path[pos2 + 1]]
                - distanceMatrix.getDistanceMatrix()[path[pos3]][path[pos3 + 1]]
                - distanceMatrix.getDistanceMatrix()[path[pos4]][path[pos4 + 1]];
    }

    public int[] compute() {
        double temperature = 100, alpha = 0.95;
        int[] next,candidate,current = path.clone();
        int[] best = current.clone();
        long end;
        int currentDistance , candidateDistance;
        do {
            for (int i = 0; i < 100; i++) {
                next = executeDoubleBridge(current).clone();
                TwoOpt twoOpt = new TwoOpt(next, distanceMatrix);
                candidate = twoOpt.optimize().clone();
                currentDistance = getTourDistance(current);
                candidateDistance = getTourDistance(candidate);
                if (candidateDistance < currentDistance) {
                    current = candidate.clone();
                    if (candidateDistance < getTourDistance(best)) {
                        best = current.clone();
                    }
                } else if (random.nextDouble() < Math.exp(-(candidateDistance - currentDistance) / temperature)) {
                    current = candidate.clone();
                }
                end = System.nanoTime();
                if (end - startTime > 1.62e+11)
                    return best;
            }
            temperature *= alpha;

        } while (temperature > 0.0001);
        return best;
    }


    public int[] executeDoubleBridge(int[] array) {
        int num_cities = array.length - 1;

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
//        final int incPos4 = pos4 + 1;

//        final int sub = -distanceMatrix.getDistanceMatrix()[array[pos1]][array[incPos1]]
//                - distanceMatrix.getDistanceMatrix()[array[pos2]][array[incPos2]]
//                - distanceMatrix.getDistanceMatrix()[array[pos3]][array[incPos3]]
//                - distanceMatrix.getDistanceMatrix()[array[pos4]][array[incPos4]];
//
//        final int add = +distanceMatrix.getDistanceMatrix()[array[pos1]][array[incPos3]]
//                + distanceMatrix.getDistanceMatrix()[array[pos4]][array[incPos2]]
//                + distanceMatrix.getDistanceMatrix()[array[pos3]][array[incPos1]]
//                + distanceMatrix.getDistanceMatrix()[array[pos2]][array[incPos4]];
//        SimulatedAnnealing.bestValue += (add + sub);

        int[] temp_solution = array.clone();
        int k = incPos1;


//        for (int j = incPos3; j <= pos4; j++, k++)
//            array[k] = temp_solution[j];
//        for (int j = incPos2; j <= pos3; j++, k++)
//            array[k] = temp_solution[j];
//        for (int j = incPos1; j <= pos2; j++, k++)
//            array[k] = temp_solution[j];

        //System.arraycopy(source_arr,sourcePos,dest_arr,destPos,len);

        System.arraycopy(temp_solution,k,array,incPos3,pos4+1-incPos3);
        k+=pos4+1-incPos3;
        System.arraycopy(temp_solution,k,array,incPos2,pos3+1-incPos2);
        k+=pos3+1-incPos2;
        System.arraycopy(temp_solution,k,array,incPos1,pos2+1-incPos1);
        return array;
    }
}
