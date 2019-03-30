import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {


    public static int getTourDistance(int[] path, DistanceMatrix distanceMatrix) {

        int distance = 0;
        for (int i = 0; i < path.length - 1; i++) {
            distance += distanceMatrix.getDistanceMatrix()[path[i]][path[i + 1]];
        }
        return distance;
    }

    public static void printCoordinates(int[] path, ArrayList<City> cities){
        System.out.println("COORDINATE\n");
        System.out.println("Latitudine:");


        for(int i=0;i<path.length;i++){
            System.out.println(cities.get(path[i]).getLatitude().toString().replace('.',','));
        }
        System.out.println("Longitudine: \n\n");
        for (int j=0;j<path.length;j++){
            System.out.println(cities.get(path[j]).getLongitude().toString().replace('.',','));
        }
        System.out.println("\n\n\n");
    }

    public static long[] seeds = {1553612458630L,1553723993577L,1553723997181L,1553724163894L,
            1553521360686L,1553724172912L,1553724210825L,1553724229277L,1553724306305L,1553724443917L};

    public static double[] costs = {6310,16980,548,23249,22282,43029,51778,108217,8906,234094};

    public static int[] bestKnowns = {6110,15780,538,22249,21282,42029,50778,107217,8806,224094};

    public static   String[] files = {"ch130.tsp", "d198.tsp", "eil76.tsp", "fl1577.tsp"
                    , "kroA100.tsp", "lin318.tsp", "pcb442.tsp"
                , "pr439.tsp", "rat783.tsp", "u1060.tsp"};
    public static boolean migliorato = false;
    public static void main(String[] args) {

        while(true){
            for (int i=0;i<10;i++){
                System.out.println(files[i]+"\n");
                TspReader tspReader = new TspReader(files[i]);
                ArrayList<City> cities = tspReader.getCities();
                DistanceMatrix distanceMatrix = new DistanceMatrix(cities);
                long startTime = System.nanoTime();

                //questo metodo è solo per la ricerca dei seeds
                Random random = new Random();
                //random.setSeed(System.currentTimeMillis());
                random.setSeed(200L);

                //Algoritmo costruttivo
                NearestNeighbor neirestNeighbor = new NearestNeighbor(cities, distanceMatrix,random);
                int[] path = neirestNeighbor.calculate();
                System.out.println("\nNN -> Errore relativo = " + ((getTourDistance(path,distanceMatrix)  - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");
                System.out.println("dis: " + getTourDistance(path,distanceMatrix));

                //Algoritmo euristico
                TwoOpt twoOpt = new TwoOpt(path, distanceMatrix);
                int[] newTour = twoOpt.optimize();
                System.out.println("\nTO -> Errore relativo = " + (( getTourDistance(path,distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");
                System.out.println("dis: " + getTourDistance(newTour,distanceMatrix));

                //Algoritmo meta-euristico
                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(newTour, distanceMatrix, startTime, random);
                int[] finalTour = simulatedAnnealing.compute();
                int dist = simulatedAnnealing.getTourDistance(finalTour);
                System.out.println("\nSA -> Errore relativo = " + (( getTourDistance(finalTour,distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");
                System.out.println("dist final " + getTourDistance(finalTour,distanceMatrix));

                if(dist<costs[i]){
                    costs[i] = dist;
                    seeds[i] = tspReader.getSeed();
                    System.out.println(files[i] + "migliorato\n");
                    migliorato = true;
                }

                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println("\nExecution time: " + totalTime * 1.6667e-11 + " minutes");

            }
            if(migliorato)
               writeSeeds();
        }
    }

    private static void writeSeeds() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("seeds.txt",true));
            for (int i=0;i<seeds.length;i++) {
                out.write(files[i] + ", SEED: " + seeds[i] + ", COST: "+ costs[i] + ", ERROR: " + (((costs[i]-(double)bestKnowns[i])/(double)bestKnowns[i])*100+ " %") + "\n");
                seeds[i]= System.currentTimeMillis();
            }
            out.write("\n\n\n");
            out.flush();
            out.close();
            migliorato = false;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
