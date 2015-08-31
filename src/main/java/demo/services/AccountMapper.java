package demo.services;

import demo.representations.AccountRepresentation;
import demo.representations.OwnerRepresentation;
import demo.services.domain.accounts.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    public List<AccountRepresentation> mapAccounts(List<Account> accounts) {
        return accounts.stream()
                .map(account -> {
                    final AccountRepresentation accountRepresentation = new AccountRepresentation();
                    accountRepresentation.setId(account.getId());
                    accountRepresentation.setType(account.getType());
                    accountRepresentation.setBalance(account.getBalance());
                    accountRepresentation.setIban(account.getIban());
                    accountRepresentation.setLabel(account.getLabel());
                    accountRepresentation.setOwners(account.getOwnerIds());
                    return accountRepresentation;
                })
                .collect(Collectors.toList());
    }
}
