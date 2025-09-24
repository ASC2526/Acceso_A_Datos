public class Author {
    private String _name;
    private String _nationality;

    public Author(String name, String nationality) {
        this.name = _name;
        this.nationality = _nationality;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this.name = _name;
    }

    public String getNationality() {
        return _nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = _nationality;
    }
}
