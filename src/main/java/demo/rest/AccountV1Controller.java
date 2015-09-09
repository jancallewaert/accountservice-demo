package demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/v1/accounts")
public class AccountV1Controller {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create() {
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
