package demo.services.domain.accounts;

import demo.rest.resources.v2.AccountResource;
import demo.services.domain.shared.MoneyAmount;
import demo.services.domain.shared.exceptions.UnknownResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
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
    @Autowired
    private EntityLinks entityLinks;

    public List<Account> getAccounts() {
        return em.createQuery("from Account", Account.class).getResultList();
    }

    public List<Account> getAccountsEagerFetch() {
        return em.createQuery("from Account acc join fetch acc.ownerIds", Account.class).getResultList();
    }

    public void save(Account account) {
        em.persist(account);
    }

    public List<AccountResource> getAccountRepresentations() {
        final List<AccountResource> representations = new ArrayList<>();
        jdbcTemplate.query(
                "select * from account a inner join account_owner_ids i on a.id = i.account_id order by a.id asc",
                rs -> {
                    final String id = rs.getString("id");
                    AccountResource current;
                    if (representations.isEmpty() || !representations.get(0).getId().equals(id)) {
                        current = new AccountResource();
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

    public List<demo.rest.resources.v6.AccountResource> getAccountResourcesHypermedia() {
        final List<demo.rest.resources.v6.AccountResource> representations = new ArrayList<>();
        jdbcTemplate.query(
                "select * from account a inner join account_owner_ids i on a.id = i.account_id order by a.id asc",
                rs -> {
                    final String id = rs.getString("id");
                    demo.rest.resources.v6.AccountResource current;
                    if (representations.isEmpty() || !representations.get(0).getId().equals(id)) {
                        current = new demo.rest.resources.v6.AccountResource();
                        current.add(entityLinks.linkToSingleResource(current.getClass(), id));
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

    public demo.rest.resources.v6.AccountResource getResourceById(String id) {
        final Account account = getById(id);
        final demo.rest.resources.v6.AccountResource accountResource = new demo.rest.resources.v6.AccountResource();
        accountResource.add(entityLinks.linkToSingleResource(accountResource.getClass(), id));
        accountResource.setLabel(account.getLabel());
        accountResource.setBalance(account.getBalance());
        accountResource.setIban(account.getIban());
        accountResource.setOwners(account.getOwnerIds());
        accountResource.setType(account.getType());
        return accountResource;
    }

    public void delete(Account account) {
        em.remove(account);
    }

    public Account getById(String id) {
        final Account account = em.find(Account.class, id);
        if (account == null) {
            throw new UnknownResourceException();
        }
        return account;
    }
}
