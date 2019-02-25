import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TspReader {
    private String filename;
    public TspReader(String filename) {
        this.filename = filename;
    }

    public void getCitiesInfo(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            int lineCounter = 0;
            while (line != null) {
                lineCounter++;
                if(lineCounter>7){
                    City city = readNumbers(line);
                    Controller.cities.add(city);
                }
                line = reader.readLine();
                if(line.charAt(0)=='E'&& line.charAt(1)=='O' && line.charAt(2)=='F')
                    break;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private City readNumbers(String line) {
        City city = new City();
        String[] informations = line.split(" ");
        city.setId(Integer.parseInt(informations[0]));
        city.setLatitude(Integer.parseInt(informations[1]));
        city.setLongitude(Integer.parseInt(informations[2]));
        return city;
    }
}
