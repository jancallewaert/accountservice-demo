package demo.representations;

public class ErrorRepresentation {
    private String id;
    private String message;

    public ErrorRepresentation(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
