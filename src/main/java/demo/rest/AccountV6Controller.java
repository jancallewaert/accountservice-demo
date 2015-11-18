package demo.rest;

import demo.rest.resources.CreateAccountCommand;
import demo.rest.resources.v6.AccountResource;
import demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/v6/accounts")
@ExposesResourceFor(AccountResource.class)
public class AccountV6Controller {

    @Autowired
    private AccountService accountService;
    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountResource>> list() {
        final List<AccountResource> accounts = accountService.getAccountResourcesHypermedia();
        return ResponseEntity.ok(accounts);
    }

    @RequestMapping(value = "/{id}")
    public ResponseEntity<AccountResource> get(@PathVariable String id) {
        final AccountResource accountResource = accountService.get(id);
        return ResponseEntity.ok(accountResource);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateAccountCommand createAccountCommand) throws URISyntaxException {
        final String id = accountService.create(createAccountCommand);
        return ResponseEntity.created(entityLinks.linkForSingleResource(AccountResource.class, id).toUri()).build();
//        throw new TechnicalException(ErrorCode.SERVICE_UNAVAILABLE);
//        throw new NullPointerException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
