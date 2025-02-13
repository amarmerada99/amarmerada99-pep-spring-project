package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findAccountByUsername(username);
    }

    public Account getAccountById(int id){
        return accountRepository.findAccountByAccountId(id);
    }
}
