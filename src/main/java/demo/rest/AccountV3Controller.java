package demo.rest;

import demo.rest.resources.CreateAccountCommand;
import demo.rest.resources.ErrorResource;
import demo.rest.resources.v2.AccountResource;
import demo.services.domain.accounts.AccountType;
import demo.services.domain.shared.MoneyAmount;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/v3/accounts")
public class AccountV3Controller {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountResource>> list() {
        final List<AccountResource> accounts = createAccounts();
        return ResponseEntity.ok(accounts);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateAccountCommand createAccountCommand) throws URISyntaxException {
        if (!isValidCreateAccountCommand(createAccountCommand)) {
            final ErrorResource error = new ErrorResource(UUID.randomUUID().toString(), "Information missing");
            return ResponseEntity.badRequest().body(error);
        }

        final String id = UUID.randomUUID().toString();
        return ResponseEntity.created(new URI("http://localhost:8080/accounts/" + id)).build();
    }

    private boolean isValidCreateAccountCommand(@RequestBody CreateAccountCommand createAccountCommand) {
        return createAccountCommand.getType() != null && createAccountCommand.getOwnerIds() != null && !createAccountCommand.getOwnerIds().isEmpty();
    }

    private List<AccountResource> createAccounts() {
        final List<AccountResource> accounts = new ArrayList<>();
        final AccountResource account = new AccountResource();
        account.setId(UUID.randomUUID().toString());
        account.setLabel("Jan's zichtrekening");
        account.setType(AccountType.CHECKING);
        account.setBalance(new MoneyAmount(BigDecimal.valueOf(1234.0), Currency.getInstance("EUR")));
        account.setIban("BE68539007547034");
        List<String> owners = Collections.singletonList(UUID.randomUUID().toString());
        account.setOwners(owners);
        accounts.add(account);
        return accounts;
    }

}
