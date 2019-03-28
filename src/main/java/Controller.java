import java.util.ArrayList;


public class Controller {
    public static void printCoordinates(Tour path, ArrayList<City> cities){
        System.out.println("COORDINATE\n");
        System.out.println("Latitudine:");
        for(int i=0;i<path.getTourSize();i++){
            System.out.println(cities.get(path.getCity(i).getId()).getLatitude());
        }
        System.out.println("Longitudine:");
        for (int j=0;j<path.getTourSize();j++){
            System.out.println(cities.get(path.getCity(j).getId()).getLongitude());
        }
        System.out.println("\n\n\n");
    }
    public static void main(String[] args) {
        TspReader tspReader = new TspReader(args[0]);
        ArrayList<City> cities = tspReader.getCities();
        DistanceMatrix distanceMatrix = new DistanceMatrix(cities);
        long startTime = System.nanoTime();


        //Algoritmo costruttivo
        NeirestNeighbor neirestNeighbor = new NeirestNeighbor(cities,distanceMatrix);
        Tour path  = neirestNeighbor.calculatePath();
        System.out.println("\nNearestNeighbor -> Errore relativo = " + ((path.getTourDistance(distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");

        System.out.println("SIZE: " + path.getTourSize());
        //printCoordinates(path,cities);
        path.printCoordinates();




        //Algoritmo euristico
        TwoOpt twoOpt = new TwoOpt(path,distanceMatrix);
        Tour newTour = twoOpt.optimizePath();
        System.out.println((newTour.getTourDistance(distanceMatrix)));
        System.out.println("\nTwoOpt -> Errore relativo = " + ((newTour.getTourDistance(distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");
        //printCoordinates(newTour,cities);


        //Algoritmo meta-euristico
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(newTour,distanceMatrix,cities,startTime);
        Tour finalTour = simulatedAnnealing.computeSolution();
        System.out.println("\nSA -> Errore relativo = " + ((finalTour.getTourDistance(distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");


        //meta-euristico secondo approccio
//        RandomApproach randomApproach = new RandomApproach(distanceMatrix,newTour,cities,startTime);
//        Tour finalT = randomApproach.computeSolution();
//        System.out.println("\nRANDOM -> Errore relativo = " + ((finalT.getTourDistance(distanceMatrix) - tspReader.getBestKnown()) / tspReader.getBestKnown()) * 100 + " %");


        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("\nExecution time: " + totalTime * 1.6667e-11 + " minutes");
    }
}
