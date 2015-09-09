package demo.rest.resources;

public class ErrorResource {
    private String id;
    private String message;

    public ErrorResource(String id, String message) {
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
