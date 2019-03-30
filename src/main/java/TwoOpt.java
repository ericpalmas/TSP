public class TwoOpt {
    private int[] path;

    private DistanceMatrix distanceMatrix;

    public TwoOpt(int[] path, DistanceMatrix distanceMatrix) {
        this.path = path;
        this.distanceMatrix = distanceMatrix;
    }
    public int[] optimize() {
        int maxGain, gain, maxI = 0, maxJ = 0;
        int size = path.length;
        do {
            maxGain = Integer.MAX_VALUE;
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size - 1; j++) {
                    gain = calculateGain(path[i],path[i+1],path[j],path[j+1]);
                    if (maxGain>gain) {
                        maxGain = gain;
                        maxI = i;
                        maxJ = j;
                    }
                }
            }
            swap(path, maxI, maxJ);
        } while (maxGain<0);
        return path;
    }

    private int calculateGain(int a, int b, int c, int d) {
        int ab = distanceMatrix.getDistanceMatrix()[a][b];
        int cd = distanceMatrix.getDistanceMatrix()[c][d];
        int ac = distanceMatrix.getDistanceMatrix()[a][c];
        int bd = distanceMatrix.getDistanceMatrix()[b][d];
        return (ac + bd) - (ab + cd);
    }

    private void swap(int[] route, int source, int dest) {
        for (int i = source + 1, j = dest; i < j; i++, j--) {
            int tmp = route[i];
            route[i] = route[j];
            route[j] = tmp;
        }
    }
}
