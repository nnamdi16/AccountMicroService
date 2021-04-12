package com.nnamdi.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(MessageSenderService.class)
public class AccountRequestProvider {
    Logger logger = LoggerFactory.getLogger(AccountRequestProvider.class);
    private MessageChannel messageChannel;


    public AccountRequestProvider(MessageSenderService messageSenderService) {
        messageChannel = messageSenderService.notification();
    }

    public void requestApproval(List<String> msg){
        Message<List<String>> message = MessageBuilder.withPayload(msg).build();
        this.messageChannel.send(message);
        logger.info(String.valueOf(message));
    }
}
