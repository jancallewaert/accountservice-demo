package demo.services;

import demo.rest.resources.CreateAccountCommand;
import demo.rest.resources.v2.AccountResource;
import demo.services.domain.accounts.Account;
import demo.services.domain.accounts.AccountRepository;
import demo.services.domain.shared.exceptions.BusinessException;
import demo.services.domain.shared.exceptions.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    @Transactional(readOnly = true)
    public List<AccountResource> getAccounts() {
        final List<Account> accounts = accountRepository.getAccounts();
        return accountMapper.mapAccounts(accounts);
    }

    @Transactional(readOnly = true)
    public List<AccountResource> getAccountResources() {
        return accountRepository.getAccountRepresentations();
    }

    @Transactional(readOnly = true)
    public List<demo.rest.resources.v6.AccountResource> getAccountResourcesHypermedia() {
        return accountRepository.getAccountResourcesHypermedia();
    }

    public String create(CreateAccountCommand command) {
        if (!isValidCreateAccountCommand(command)) {
            throw new BusinessException(ErrorCode.MISSING_CREATE_ACCOUNT_INFORMATION);
        }
        final Account account = new Account(command.getType(), command.getOwnerIds());
        accountRepository.save(account);
        return account.getId();
    }

    private boolean isValidCreateAccountCommand(@RequestBody CreateAccountCommand createAccountCommand) {
        return createAccountCommand.getType() != null && createAccountCommand.getOwnerIds() != null && !createAccountCommand.getOwnerIds().isEmpty();
    }

    public demo.rest.resources.v6.AccountResource get(String id) {
        return accountRepository.getResourceById(id);
    }

    public void delete(String id) {
        Account account = accountRepository.getById(id);
        accountRepository.delete(account);
    }
}
