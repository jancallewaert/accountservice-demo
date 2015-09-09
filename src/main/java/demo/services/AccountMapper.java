package demo.services;

import demo.rest.resources.v2.AccountResource;
import demo.services.domain.accounts.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    public List<AccountResource> mapAccounts(List<Account> accounts) {
        return accounts.stream()
                .map(account -> {
                    final AccountResource accountResource = new AccountResource();
                    accountResource.setId(account.getId());
                    accountResource.setType(account.getType());
                    accountResource.setBalance(account.getBalance());
                    accountResource.setIban(account.getIban());
                    accountResource.setLabel(account.getLabel());
                    accountResource.setOwners(account.getOwnerIds());
                    return accountResource;
                })
                .collect(Collectors.toList());
    }
}
