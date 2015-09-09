package demo.rest.resources.v2;

import demo.services.domain.accounts.AccountType;
import demo.services.domain.shared.MoneyAmount;

import java.util.ArrayList;
import java.util.List;

public class AccountResource {
    private String id;
    private String label;
    private AccountType type;
    private List<String> owners = new ArrayList<>();
    private String iban;
    private MoneyAmount balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public MoneyAmount getBalance() {
        return balance;
    }

    public void setBalance(MoneyAmount balance) {
        this.balance = balance;
    }
}
