package com.nnamdi.account.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public interface MessageSenderService {
    @Output("notification")
    MessageChannel notification();

    @Output("accountDetails")
    MessageChannel accountDetails();
}
