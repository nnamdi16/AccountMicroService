package com.nnamdi.account.model;

import com.nnamdi.account.controller.AccountController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {
    @Override
    public EntityModel<Account> toModel(Account account) {
        return EntityModel.of(account,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountController.class).getAccount(account.getAccountId())).withSelfRel()
                ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountController.class).all()).withRel("accounts"));
    }
}
