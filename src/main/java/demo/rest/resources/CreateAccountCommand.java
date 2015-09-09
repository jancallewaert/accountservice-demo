package demo.rest.resources;

import demo.services.domain.accounts.AccountType;

import java.util.List;

public class CreateAccountCommand {
    private AccountType type;
    private List<String> ownerIds;

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public List<String> getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(List<String> ownerIds) {
        this.ownerIds = ownerIds;
    }
}
