public class City{

    private Integer id;
    private Integer latitude;
    private Integer longitude;
    private boolean visited;


    public City(Integer latitude,Integer longitude,Integer id){
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.visited = false;
    }

    public City(){ }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLatitude() { return latitude; }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
