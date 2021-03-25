package com.nnamdi.account.controller;

import com.nnamdi.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//    @Bean
//    CommandLineRunner initDatabase(AccountRepository repository) {
//        return args -> {
//            log.info("Preloading "  + repository.save(new Account("Badebo12345", "Biblo Baggins", "Angel Street", "+2347038276158","valiantnnamdi@yahoo.ca","12345")));
//            log.info("Preloading "  + repository.save(new Account("Salami12345", "Froddo Baggins ", "Heaven Street", "+2347038200000","famola@yahoo.ca","12345")));
//        };
//    }
}
