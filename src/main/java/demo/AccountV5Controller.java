package demo;

import demo.representations.AccountRepresentation;
import demo.representations.CreateAccountCommand;
import demo.services.AccountService;
import demo.services.domain.accounts.AccountType;
import demo.services.domain.shared.MoneyAmount;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/v5/accounts")
public class AccountV5Controller {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountRepresentation>> list() {
        final List<AccountRepresentation> accounts = accountService.getAccountRepresentations();
        return ResponseEntity.ok(accounts);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateAccountCommand createAccountCommand) throws URISyntaxException {
        final String id = accountService.create(createAccountCommand);
        return ResponseEntity.created(new URI("http://localhost:8080/accounts/" + id)).build();
    }

}
