package demo.services.domain.shared;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyAmount {
    private BigDecimal amount;
    private Currency currency;

    MoneyAmount() {
    }

    public MoneyAmount(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

}
