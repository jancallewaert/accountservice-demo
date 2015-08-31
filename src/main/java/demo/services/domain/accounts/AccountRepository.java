package demo.services.domain.accounts;

import demo.representations.AccountRepresentation;
import demo.services.domain.shared.MoneyAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Repository
public class AccountRepository {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Account> getAccounts() {
        return em.createQuery("from Account", Account.class).getResultList();
    }

    public List<Account> getAccountsEagerFetch() {
        return em.createQuery("from Account acc join fetch acc.ownerIds", Account.class).getResultList();
    }

    public void save(Account account) {
        em.persist(account);
    }

    public List<AccountRepresentation> getAccountRepresentations() {
        final List<AccountRepresentation> representations = new ArrayList<>();
        jdbcTemplate.query(
                "select * from account a inner join account_owner_ids i on a.id = i.account_id order by a.id asc",
                rs -> {
                    final String id = rs.getString("id");
                    AccountRepresentation current;
                    if (representations.isEmpty() || !representations.get(0).getId().equals(id)) {
                        current = new AccountRepresentation();
                        current.setId(id);
                        current.setLabel(rs.getString("label"));
                        current.setIban(rs.getString("iban"));
                        current.setBalance(new MoneyAmount(rs.getBigDecimal("balance_eur"), Currency.getInstance("EUR")));
                        current.setType(AccountType.valueOf(rs.getString("type")));

                        representations.add(0, current);
                    } else {
                        current = representations.get(0);
                    }
                    current.getOwners().add(rs.getString("owner_ids"));
                });
        return representations;
    }
}
