package demo.rest.resources.v6;

import org.springframework.hateoas.ResourceSupport;

public class OwnerResource extends ResourceSupport {
    private String firstName;
    private String name;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
