import java.util.Collections;

public class TwoOpt {
    private Tour path;

    public TwoOpt(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    private DistanceMatrix distanceMatrix;

    public TwoOpt(Tour path, DistanceMatrix distanceMatrix) {
        this.path = path;
        this.distanceMatrix = distanceMatrix;
    }

    public Tour optimizePath() {
        int maxGain, gain, maxI = 0, maxJ = 0;
        int size = path.getTourSize();
        do{
            maxGain = 0;
            for (int i = 0; i < size - 2; i++) {
                for (int j = i + 1; j < size - 2; j++) {
                    gain = calculateGain(path.getPath().get(i),path.getPath().get(i + 1),path.getPath().get(j),path.getPath().get(j+1));
                    if (gain < 0 && gain < maxGain) {
                        maxGain = gain;
                        maxI = i;
                        maxJ = j;
                    }
                }
            }
            swap(path, maxI, maxJ);
        }while(maxGain < 0);
        return path;
    }

    private int calculateGain(City a, City b, City c, City d) {
        int ab = distanceMatrix.getDistanceMatrix()[a.getId()][b.getId()];
        int cd = distanceMatrix.getDistanceMatrix()[c.getId()][d.getId()];
        int ac = distanceMatrix.getDistanceMatrix()[a.getId()][c.getId()];
        int bd = distanceMatrix.getDistanceMatrix()[b.getId()][d.getId()];
        return (ac + bd) - (ab + cd);
    }

    private void swap(Tour path, int from, int to) {
        for (int i = from + 1, j = to; i < j; i++, j--) {
            Collections.swap(path.getPath(), i, j);
        }
    }

    public int[] optimize(int[] path){
        int maxGain , gain, maxI = 0, maxJ = 0;
        int size = path.length;
        boolean improved;
        do{
            maxGain = Integer.MAX_VALUE;
            improved = false;
            for(int i=0;i<size - 2; i++){
                for(int j = i+1; j< size - 2; j++){
                    gain = distanceMatrix.getDistanceMatrix()[path[i]][path[j]] + distanceMatrix.getDistanceMatrix()[path[i+1]][path[j+1]]
                            - distanceMatrix.getDistanceMatrix()[path[i]][path[i+1]] - distanceMatrix.getDistanceMatrix()[path[j]][path[j+1]];
                    if (gain < 0 && gain < maxGain) {
                        maxGain = gain;
                        maxI = i;
                        maxJ = j;

                    }
                }
            }
            if (maxGain != Integer.MAX_VALUE) {
                reverse(path,maxI,maxJ);
                improved = true;
            }

        }while(improved);
        return path;
    }

    private void reverse(int[] route, int from, int to) {
        for (int i = from + 1, j = to; i < j; i++, j--) {
            final int tmp = route[i];
            route[i] = route[j];
            route[j] = tmp;
        }
    }
}

//public class TwoOpt {
//    private int[] path;
//
//    private DistanceMatrix distanceMatrix;
//
//    public TwoOpt(int[] path, DistanceMatrix distanceMatrix) {
//        this.path = path;
//        this.distanceMatrix = distanceMatrix;
//    }
//    public int[] optimize() {
//        int maxGain, gain, maxI = 0, maxJ = 0;
//        int size = path.length;
//        do {
//            maxGain = Integer.MAX_VALUE;
//            for (int i = 0; i < size - 1; i++) {
//                for (int j = i + 1; j < size - 1; j++) {
//                    gain = calculateGain(path[i],path[i+1],path[j],path[j+1]);
//                    if (maxGain>gain) {
//                        maxGain = gain;
//                        maxI = i;
//                        maxJ = j;
//                    }
//                }
//            }
//            swap(path, maxI, maxJ);
//        } while (maxGain<0);
//        return path;
//    }
//
//    private int calculateGain(int a, int b, int c, int d) {
//        int ab = distanceMatrix.getDistanceMatrix()[a][b];
//        int cd = distanceMatrix.getDistanceMatrix()[c][d];
//        int ac = distanceMatrix.getDistanceMatrix()[a][c];
//        int bd = distanceMatrix.getDistanceMatrix()[b][d];
//        return (ac + bd) - (ab + cd);
//    }
//
//    private void swap(int[] route, int from, int to) {
//        for (int i = from + 1, j = to; i < j; i++, j--) {
//            final int tmp = route[i];
//            route[i] = route[j];
//            route[j] = tmp;
//        }
//    }



//    public int optimize(int cost) {
//        int maxGain, gain, maxI = 0, maxJ = 0;
//        int size = path.length;
//        do {
//            maxGain = Integer.MAX_VALUE;
//            for (int i = 0; i < size - 1; i++) {
//                for (int j = i + 1; j < size - 1; j++) {
//                    gain = calculateGain(path[i],path[i+1],path[j],path[j+1]);
//                    if (maxGain>gain) {
//                        maxGain = gain;
//                        maxI = i;
//                        maxJ = j;
//                    }
//                }
//            }
//            swap(path, maxI, maxJ);
//        } while (maxGain<0);
//        return cost + maxGain;
//    }
}
