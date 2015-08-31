package demo.services.domain.accounts;

import demo.services.domain.shared.MoneyAmount;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String label;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private String iban;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "BALANCE_EUR"))
    private MoneyAmount balance;
    @ElementCollection
    private List<String> ownerIds;

    Account() {
    }

    public Account(AccountType type, List<String> ownerIds) {
//        this.id = UUID.randomUUID().toString();
        this.label = generateLabel(type);
        this.type = type;
        this.iban = generateIban();
        this.balance = new MoneyAmount(BigDecimal.ZERO, Currency.getInstance("EUR"));
        this.ownerIds = ownerIds;
    }

    private String generateLabel(AccountType type) {
        switch (type) {
            case CHECKING:
                return "mijn zichtrekening";
            case SAVINGS:
                return "mijn spaarrekening";
            default:
                return "mijn rekening";
        }
    }

    private String generateIban() {
        return "new iban generated";
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public AccountType getType() {
        return type;
    }

    public String getIban() {
        return iban;
    }

    public MoneyAmount getBalance() {
        return balance;
    }

    public List<String> getOwnerIds() {
        return ownerIds;
    }
}
