package com.nnamdi.account.service;

import com.nnamdi.account.model.Account;
import com.nnamdi.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private  final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account updateAccount(Account account, Long id) {
        Account updateAccount = accountRepository.findById(id)
                .map(userAccount ->{
                    userAccount.setAddress(account.getAddress());
                    userAccount.setEmail(account.getEmail());
                    userAccount.setName(account.getName());
                    userAccount.setPassword(account.getPassword());
                    userAccount.setPhoneNumber(account.getPhoneNumber());
                    return accountRepository.save(userAccount);
                })    .orElseGet(() -> {
                    account.setAccountId(id);
                    return accountRepository.save(account);
                });
        return  updateAccount;
    }
}
