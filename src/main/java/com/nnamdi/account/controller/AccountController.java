package com.nnamdi.account.controller;

import com.nnamdi.account.exceptions.AccountNotFoundException;
import com.nnamdi.account.model.Account;
import com.nnamdi.account.model.AccountModelAssembler;
import com.nnamdi.account.repository.AccountRepository;
import com.nnamdi.account.repository.RoleRepository;
import com.nnamdi.account.repository.UserRoleRepository;
import com.nnamdi.account.security.jwt.JwtUtils;
import com.nnamdi.account.service.RabbitMqSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    private RabbitMqSender rabbitMqSender;
    private RabbitTemplate rabbitTemplate;

    private final AccountRepository accountRepository;
    private final UserRoleRepository userRoleRepository;
    private final AccountModelAssembler accountModelAssembler;
    private final RoleRepository roleRepository;

    public AccountController(AccountRepository accountRepository, UserRoleRepository userRoleRepository, AccountModelAssembler accountModelAssembler, BCryptPasswordEncoder bCryptPasswordEncoder, RabbitMqSender rabbitMqSender, RabbitTemplate rabbitTemplate, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.userRoleRepository = userRoleRepository;
        this.accountModelAssembler = accountModelAssembler;
        this.rabbitMqSender = rabbitMqSender;
        this.rabbitTemplate = rabbitTemplate;
        this.roleRepository = roleRepository;
    }




    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
    public CollectionModel<EntityModel<Account>> all() {
        List<EntityModel<Account>> accounts = accountRepository.findAll().stream().map(accountModelAssembler::toModel).collect(Collectors.toList());
        System.out.println(accounts);

        rabbitMqSender.send("accounts are for developers who like to work");
        return CollectionModel.of(accounts, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountController.class).all()).withSelfRel());
    }



    //Single item

    @GetMapping("/account/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public EntityModel<Account> getAccount(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
//        rabbitMqSender.send(account);
        return accountModelAssembler.toModel(account);
    }



    @PutMapping("/account/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
                    account.setAccountId(id);
                    return accountRepository.save(account);
                });
        EntityModel<Account> entityModel = accountModelAssembler.toModel(updateAccount);
        return  ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @DeleteMapping("/account/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
