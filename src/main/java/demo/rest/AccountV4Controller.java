package demo.rest;

import demo.rest.resources.AccountResource;
import demo.rest.resources.CreateAccountCommand;
import demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/v4/accounts")
public class AccountV4Controller {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountResource>> list() {
        final List<AccountResource> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateAccountCommand createAccountCommand) throws URISyntaxException {
        final String id = accountService.create(createAccountCommand);
        return ResponseEntity.created(new URI("http://localhost:8080/accounts/" + id)).build();
    }

}
