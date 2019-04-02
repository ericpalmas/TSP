import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

//    public static int getTourDistance(int[] path, DistanceMatrix distanceMatrix) {
//        int distance = 0;
//        for (int i = 0; i < path.length - 1; i++) {
//            distance += distanceMatrix.getDistanceMatrix()[path[i]][path[i + 1]];
//        }
//        return distance;
//    }

    public static long[] seeds;

    public static Integer[] costs;

    public static int[] bestKnowns = {6110,15780,538,22249,21282,42029,50778,107217,8806,224094};

    public static   String[] files = {"ch130.tsp", "d198.tsp", "eil76.tsp", "fl1577.tsp"
                    , "kroA100.tsp", "lin318.tsp", "pcb442.tsp"
                , "pr439.tsp", "rat783.tsp", "u1060.tsp"};

    public static boolean improve = false;

    public static void main(String[] args) {
    costs = new Integer[10];
    seeds = new long[10];

        while(true){
            readSeeds();
            for (int i=8;i<10;i++){
                long startTime = System.nanoTime();

                System.out.println(files[i]+"\n\n");
                TspReader tspReader = new TspReader(files[i]);
                ArrayList<City> cities = tspReader.getCities();
                DistanceMatrix distanceMatrix = new DistanceMatrix(cities);

                Random random = new Random();
                long seed = System.currentTimeMillis();
                random.setSeed(seed);
                //random.setSeed(tspReader.getSeed());

                //Algoritmo costruttivo
                NearestNeighbor neirestNeighbor = new NearestNeighbor(cities, distanceMatrix,random);
                int[] path = neirestNeighbor.calculate();

                //Algoritmo euristico
                TwoOpt twoOpt = new TwoOpt(path, distanceMatrix);
                int[] path2 = twoOpt.optimize();

                //Algoritmo meta-euristico
                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(path2, distanceMatrix, startTime, random);
                int[] finalTour = simulatedAnnealing.compute();
                int dist = simulatedAnnealing.getTourDistance(finalTour);
                System.out.println("dist " + dist);
                System.out.println("\nSA -> Errore relativo = " + ((dist - (double)bestKnowns[i]) / (double)bestKnowns[i]) * 100 + " %");


                if(dist<costs[i]){
                    costs[i] = dist;
                    seeds[i] = seed;
                    improve = true;
                }

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println("\nExecution time: " + totalTime * 1.6667e-11 + " minutes\n");
            }
            if(improve)
               writeSeeds();
        }
    }

    private static void writeSeeds() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("seeds.txt"));
            for (int i=0;i<seeds.length;i++) {
                out.write(files[i] + "," + seeds[i] + ","+ costs[i] + "," + (((costs[i]-(double)bestKnowns[i])/(double)bestKnowns[i])*100+ " %") + "\n");
            }
            out.write("\n\n\n");
            out.flush();
            out.close();
            improve = false;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void readSeeds(){
        BufferedReader reader;
        String[] informations;
        try {
            reader = new BufferedReader(new FileReader("seeds.txt"));
            String line;
             for(int i=0;i<10;i++) {
                 line = reader.readLine();
                 informations = line.split(",");
                 seeds[i] = Long.parseLong(informations[1]);
                 costs[i] = Integer.parseInt(informations[2]);
             }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
