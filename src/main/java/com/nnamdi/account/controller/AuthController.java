package com.nnamdi.account.controller;

import com.nnamdi.account.model.*;
import com.nnamdi.account.payload.request.LoginRequest;
import com.nnamdi.account.payload.request.SignUpRequest;
import com.nnamdi.account.payload.response.JwtResponse;
import com.nnamdi.account.payload.response.MessageResponse;
import com.nnamdi.account.repository.AccountRepository;
import com.nnamdi.account.service.AccountRequestProvider;
import com.nnamdi.account.service.MessageSenderService;
//import com.nnamdi.account.service.RabbitMqSender;
import com.nnamdi.account.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api(tags = "accounts", value = "Account Microservice")
public class AuthController {

    @Autowired
    UserService userService;
//    private RabbitMqSender rabbitMqSender;
    Logger logger = LoggerFactory.getLogger(AuthController.class);

//    private MessageChannel messageChannel;

    private final AccountRepository accountRepository;

    private final AccountModelAssembler accountModelAssembler;

    private final AccountRequestProvider accountRequestProvider;


    public AuthController(AccountRepository accountRepository, AccountModelAssembler accountModelAssembler, AccountRequestProvider accountRequestProvider) {
        this.accountRepository = accountRepository;
        this.accountModelAssembler = accountModelAssembler;
//        this.rabbitMqSender = rabbitMqSender;
//        messageChannel = messageSenderService.notification();
        this.accountRequestProvider = accountRequestProvider;
    }


    @ApiOperation(value = "Create an account")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong "),
            @ApiResponse(code = 422, message = "Username or email already exist")
    })
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
        List<String> accountNotification = new ArrayList<>();
        String accountId = String.valueOf(userAccount.getAccountId());
        accountNotification.add(accountId);
        accountNotification.add(userAccount.getEmail());
        accountRequestProvider.requestApproval(accountNotification);
//        Message<List<String>> msg = MessageBuilder.withPayload(accountNotification).build();
//        this.messageChannel.send(msg);
//        logger.info(String.valueOf(msg));
//        rabbitMqSender.send(userAccount.getEmail());
        EntityModel<Account> entityModel = accountModelAssembler.toModel(accountRepository.save(userAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @ApiOperation(value ="Authenticates user and returns its JWT token." )
    @ApiResponses(value = {
            @ApiResponse(code = 400,message = "Something went wrong")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.signin(loginRequest.getUsername(),loginRequest.getPassword());
        return ResponseEntity.ok(jwtResponse);

    }


}
