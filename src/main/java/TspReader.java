import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class TspReader {
    private String filename;

    private double bestKnown;

    private String name;

    private String comment;

    private int dimension;

    private String split;

    private String commentLine;

    private String dimensionLine;

    public long getSeed() {
        return seed;
    }

    private long seed;

    public double getBestKnown() {
        return bestKnown;
    }

    public TspReader(String filename, String commentLine, String dimensionLine, String split) {
        this.filename = filename;
        this.split = split;
        this.commentLine = commentLine;
        this.dimensionLine = dimensionLine;

        switch (filename){
            case "ch130.tsp":{
                bestKnown = 6110;
                seed = 1554104699057L;
            }
                break;
            case "d198.tsp":{
                bestKnown = 15780;
                seed = 1554404587246L;
            }
                break;
            case "eil76.tsp":{
                bestKnown = 538;
                seed = 1554106242827L;
            }
                break;
            case "fl1577.tsp":{
                bestKnown = 22249;
                seed = 1555458570325L;
            }
                break;
            case "kroA100.tsp":{
                bestKnown = 21282;
                seed = 1554104882992L;
            }
                break;
            case "lin318.tsp":{
                bestKnown = 42029;
                seed = 1556347251330L;
            }
                break;
            case "pcb442.tsp":{
                bestKnown = 50778;
                seed = 1555228717892L;
            }
                break;
            case "pr439.tsp":{
                bestKnown = 107217;
                seed = 1555104967032L;
            }
                break;
            case "rat783.tsp":{
                bestKnown = 8806;
                seed = 1554371112973L;
            }
                break;
            case "u1060.tsp":{
                bestKnown = 224094;
                seed = 1554351921574L;
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
                if(lineCounter==1){
                    name = line.split(split)[1];
                }
                if(lineCounter==Integer.parseInt(commentLine)){
                    comment = line.split(split)[1];
                }
                if(lineCounter==Integer.parseInt(dimensionLine)){
                    dimension = Integer.parseInt(line.split(split)[1]);
                }
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

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getDimension() {
        return dimension;
    }
}
