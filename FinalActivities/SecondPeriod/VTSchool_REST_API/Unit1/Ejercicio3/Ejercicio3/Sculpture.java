public class Sculpture extends Artwork {
    private Materials _material;
    private Styles _style;

    public Sculpture(String title, String ubication, Author author, Room room, Materials material, Styles style) {
        super(title, ubication, author, room);
        this.material = _material;
        this.style = _style;
    }

    public Materiales getMaterial() {
        return _material;
    }

    public void setMaterial(Materiales material) {
        this.material = _material;
    }

    public Styles getStyle() {
        return _style;
    }

    public void setSitio(Styles style) {
        this.style = _style;
    }
}
