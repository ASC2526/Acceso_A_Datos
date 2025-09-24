public class Paint extends Artwork {
    private PintureType _type;
    private String _format;

    public Paint(String title, String ubication, Author author, Room room, PintureType type, String format) {
        super(title, ubication, author, room);
        this.type = _type;
        this.format = _format;
    }

    public PintureType getType() {
        return _type;
    }

    public void setType(PintureType type) {
        this.type = _type;
    }

    public String getFormat() {
        return _format;
    }

    public void setFormat(String format) {
        this.format = _format;
    }
}
