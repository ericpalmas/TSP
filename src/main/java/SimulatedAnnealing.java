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

    public int[] compute() {
        double temperature = 100, alpha = 0.95;
        int[] next, candidate, current = path.clone();
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
        int pos1, pos2, pos3, pos4;
        int[] temp = array.clone();
        do {
            pos1 = random.nextInt(array.length - 1);
            pos2 = random.nextInt(array.length - 1);
            pos3 = random.nextInt(array.length - 1);
            pos4 = random.nextInt(array.length - 1);
        } while (pos1 < 0 || pos2 < pos1 || pos3 < pos2 || pos4 < pos3);

        int k = pos1 + 1;
        System.arraycopy(temp,k,array,pos3 + 1,pos4-pos3);
        k+=pos4-pos3;
        System.arraycopy(temp,k,array,pos2 + 1,pos3-pos2);
        k+=pos3-pos2;
        System.arraycopy(temp,k,array,pos1 + 1,pos2-pos1);
        return array;
    }
}
