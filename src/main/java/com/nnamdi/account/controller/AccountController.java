package com.nnamdi.account.controller;

import com.nnamdi.account.exceptions.AccountNotFoundException;
import com.nnamdi.account.model.Account;
import com.nnamdi.account.model.AccountModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountModelAssembler accountModelAssembler;

    public AccountController(AccountRepository accountRepository,AccountModelAssembler accountModelAssembler) {
        this.accountRepository = accountRepository;
        this.accountModelAssembler = accountModelAssembler;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/accounts")
    public CollectionModel<EntityModel<Account>> all() {
        List<EntityModel<Account>> accounts = accountRepository.findAll().stream().map(accountModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(accounts, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountController.class).all()).withSelfRel());
    }
//    List<Account>all() {
//        return accountRepository.findAll();
//    }
    //end:: get-aggregate-root[]

    @PostMapping("/accounts")
    public ResponseEntity<?> registerAccount(@RequestBody Account registerAccount) {
        EntityModel<Account> entityModel = accountModelAssembler.toModel(accountRepository.save(registerAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    //Single item

    @GetMapping("/accounts/{id}")
    public EntityModel<Account> getAccount(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        return accountModelAssembler.toModel(account);
    }



    @PutMapping("/accounts/{id}")
    public ResponseEntity<?> updateAccount(@RequestBody Account account, @PathVariable Long id) {
        Account updateAccount = accountRepository.findById(id)
                .map(userAccount ->{
                    userAccount.setAddress(account.getAddress());
                    userAccount.setEmail(account.getEmail());
                    userAccount.setName(account.getName());
                    userAccount.setPassword(account.getPassword());
                    userAccount.setPhoneNumber(account.getPhoneNumber());
                    return accountRepository.save(userAccount);
                })    .orElseGet(() -> {
                    account.setId(id);
                    return accountRepository.save(account);
                });
        EntityModel<Account> entityModel = accountModelAssembler.toModel(updateAccount);
        return  ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @DeleteMapping("/accounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
