import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Main {


    /*
    Tutte queste variabili le utilizzavo per la ricerca dei seed,cosi come le funzioni infondo
     */
    public static long[] seeds;

    public static Integer[] costs;

    public static int[] bestKnowns = {6110,15780,538,22249,21282,42029,50778,107217,8806,224094};

    public static   String[] files = {"ch130.tsp", "d198.tsp", "eil76.tsp", "fl1577.tsp"
                    , "kroA100.tsp", "lin318.tsp", "pcb442.tsp"
                , "pr439.tsp", "rat783.tsp", "u1060.tsp"};

    public static boolean improve = false;

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        TspReader tspReader = new TspReader(args[0],args[1],args[2],args[3]);
        ArrayList<City> cities = tspReader.getCities();
        DistanceMatrix distanceMatrix = new DistanceMatrix(cities);

        Random random = new Random();
        random.setSeed(tspReader.getSeed());


        NearestNeighbor neirestNeighbor = new NearestNeighbor(cities, distanceMatrix, random);
        int[] path = neirestNeighbor.calculate();


        TwoOpt twoOpt = new TwoOpt(path, distanceMatrix);
        int[] path2 = twoOpt.optimize();


        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(path2, distanceMatrix, startTime, random);
        int[] finalTour = simulatedAnnealing.compute();
        int dist = simulatedAnnealing.getTourDistance(finalTour);


        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Name = " + args[0] + ", Distance = " + dist + " ,Error = " + ((dist - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " % " + ", Execution time = " + totalTime * 1.6667e-11 + " minutes\n");
        writeResult(args[0], tspReader, finalTour);
    }


    private static void writeResult(String out, TspReader tspReader, int[] finalTour){

        File f = new File(tspReader.getName() + ".opt.tour");
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);
            fw.write("NAME : " + tspReader.getName()+ ".opt.tour" + "\n");
            fw.write("COMMENT : " + tspReader.getComment() + "\n");
            fw.write("TYPE : TOUR\n");
            fw.write("DIMENSION : " + tspReader.getDimension() + "\n");
            fw.write("TOUR_SECTION\n");
            for(int i=0;i<finalTour.length-1;i++){
                fw.write((finalTour[i]+1) + "\n");
            }
            fw.write("-1\nEOF\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
    private static boolean checkPath(int size, int[] path){
        boolean[] control = new boolean[size];
        for(int i=0;i<size;i++){
            if(!control[path[i]]){
                control[path[i]] = true;
            }else{
                return false;
            }
        }
        return true;
    }
}
