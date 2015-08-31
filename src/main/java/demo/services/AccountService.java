package demo.services;

import demo.representations.AccountRepresentation;
import demo.representations.CreateAccountCommand;
import demo.services.domain.accounts.Account;
import demo.services.domain.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    @Transactional(readOnly = true)
    public List<AccountRepresentation> getAccounts() {
        final List<Account> accounts = accountRepository.getAccounts();
        return accountMapper.mapAccounts(accounts);
    }

    @Transactional(readOnly = true)
    public List<AccountRepresentation> getAccountRepresentations() {
        return accountRepository.getAccountRepresentations();
    }

    public String create(CreateAccountCommand command) {
        final Account account = new Account(command.getType(), command.getOwnerIds());
        accountRepository.save(account);
        return account.getId();
    }
}
