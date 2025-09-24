public abstract class Artwork {
    private String _title;
    private String _ubication;
    private Autor _author;
    private Room _room;

    public Artwork(String title, String ubication, Author author, Room room) {
        this.title = _title;
        this.ubication = _ubication;
        this.author = _author;
        this.room = _room;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this.title = _title;
    }

    public String getUbication() {
        return _ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = _ubication;
    }

    public Author getAuthor() {
        return _author;
    }

    public void setAuthor(Author author) {
        this.author = _author;
    }

    public Room getRoom() {
        return _room;
    }

    public void setRoom(Room room) {
        this.room = _room;
    }
}
