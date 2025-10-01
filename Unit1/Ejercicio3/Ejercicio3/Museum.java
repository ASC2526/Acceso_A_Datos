public class Museum {
    private String _name;
    private String _direction;
    private String _city;
    private String _country;

    public Museum(String name, String direction, String city, String country) {
        this.name = _name;
        this.direction = _direction;
        this.city = _city;
        this.country = _country;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this.name = _name;
    }

    public String getDirection() {
        return _direction;
    }

    public void setDireccion(String direction) {
        this.direction = _direction;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        this.city = _city;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(String country) {
        this.country = _country;
    }
}
