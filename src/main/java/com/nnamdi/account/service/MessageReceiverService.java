package com.nnamdi.account.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageReceiverService {
    String ACCOUNTID = "sendAccountId";

    @Input(ACCOUNTID)
    SubscribableChannel sendAccountId();
}
