import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class TspReader {
    private String filename;

    private double bestKnown;

    public long getSeed() {
        return seed;
    }

    private long seed;

    public double getBestKnown() {
        return bestKnown;
    }

    public TspReader(String filename) {
        this.filename = filename;
        switch (filename){
            case "ch130.tsp":{
                bestKnown = 6110;
                seed = 1553612458630L; //0.2782
            }
                break;
            case "d198.tsp":{
                bestKnown = 15780;
                seed = 1553633314766L;
            }
                break;
            case "eil76.tsp":{
                bestKnown = 538;
                seed = 1553612534651L; //0.1858
            }
                break;
            case "fl1577.tsp":{
                bestKnown = 22249;
                seed = 1553633423429L;
            }
                break;
            case "kroA100.tsp":{
                bestKnown = 21282;
                seed = 1553521360686L; //0.06108
            }
                break;
            case "lin318.tsp":{
                bestKnown = 42029;
                seed = 1553633633775L;
            }
                break;
            case "pcb442.tsp":{
                bestKnown = 50778;
                seed = 1553633675199L;
            }
                break;
            case "pr439.tsp":{
                bestKnown = 107217;
                seed = 1553633675199L;
            }
                break;
            case "rat783.tsp":{
                bestKnown = 8806;
                seed = 1553633675199L;
            }
                break;
            case "u1060.tsp":{
                bestKnown = 224094;
                seed = 1553633675199L;
            }
                break;
        }
    }

    public ArrayList<City> getCities(){
        ArrayList<City> cities = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/files/" + filename));
            String line = reader.readLine();
            int lineCounter = 0;
            while (line != null) {
                lineCounter++;
                if(lineCounter>7){
                    City city = readNumbers(line);
                    cities.add(city);
                }
                line = reader.readLine();
                if(line.charAt(0)=='E'&& line.charAt(1)=='O' && line.charAt(2)=='F')
                    break;
            }
            reader.close();
            return cities;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private City readNumbers(String line) {
        if(line.charAt(0)==' '){
            line = line.substring(1,line.length());
        }
        City city = new City();
        String[] informations = line.split(" ");
        city.setId(Integer.parseInt(informations[0])-1);
        city.setLatitude( Double.parseDouble(informations[1]));
        city.setLongitude( Double.parseDouble(informations[2]));
        return city;
    }
}
