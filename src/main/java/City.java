public class City{

    private Integer id;
    private Double latitude;
    private Double longitude;


    public City(Double latitude, Double longitude, Integer id){
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }

    public City(){ }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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
