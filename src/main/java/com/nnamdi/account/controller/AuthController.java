package com.nnamdi.account.controller;

import com.nnamdi.account.model.*;
import com.nnamdi.account.payload.request.LoginRequest;
import com.nnamdi.account.payload.request.SignUpRequest;
import com.nnamdi.account.payload.response.JwtResponse;
import com.nnamdi.account.payload.response.MessageResponse;
import com.nnamdi.account.repository.AccountRepository;
import com.nnamdi.account.repository.RoleRepository;
import com.nnamdi.account.repository.UserRoleRepository;
import com.nnamdi.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    private final AccountRepository accountRepository;

    private final AccountModelAssembler accountModelAssembler;


    public AuthController(AccountRepository accountRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, AccountModelAssembler accountModelAssembler, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.accountModelAssembler = accountModelAssembler;

    }



    @PostMapping("/signup")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already exists"));
        }

        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error:email is already in use"));
        }

        Account userAccount = userService.signup(signUpRequest);
        accountRepository.save(userAccount);
        EntityModel<Account> entityModel = accountModelAssembler.toModel(accountRepository.save(userAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.signin(loginRequest.getUsername(),loginRequest.getPassword());
        return ResponseEntity.ok(jwtResponse);

    }


}
