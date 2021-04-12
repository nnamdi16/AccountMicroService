package com.nnamdi.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnamdi.account.model.Account;
import com.nnamdi.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@EnableBinding(MessageReceiverService.class)
public class AccountService {
    @Autowired
    private ObjectMapper objectMapper;
    private  final AccountRepository accountRepository;
    private MessageChannel messageChannel;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountService(AccountRepository accountRepository, MessageSenderService messageSenderService) {
        this.accountRepository = accountRepository;
        this.messageChannel = messageSenderService.accountDetails();

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

    @StreamListener(target = MessageReceiverService.ACCOUNTID)
    public void getAccountDetails(String id){
            logger.info(id);
            Long accountId = Long.parseLong(id);
            Optional<Account> account = accountRepository.findById(accountId);
            logger.info(String.valueOf(account));
//            String accountDetails = objectMapper.writeValueAsString(account);
            Message<String> message= MessageBuilder.withPayload(account.get().getEmail()).build();
            logger.info(String.valueOf(message));
            messageChannel.send(message);

//        HashMap<String,Object> accountDetails = new HashMap<>();
//        accountDetails.put("accountId",account.get().getAccountId());
//        accountDetails.put("a")

    }
}
