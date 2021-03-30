package com.nnamdi.account.controller;

import com.nnamdi.account.model.*;
import com.nnamdi.account.payload.request.LoginRequest;
import com.nnamdi.account.payload.request.SignUpRequest;
import com.nnamdi.account.payload.response.JwtResponse;
import com.nnamdi.account.payload.response.MessageResponse;
import com.nnamdi.account.repository.AccountRepository;
import com.nnamdi.account.repository.RoleRepository;
import com.nnamdi.account.repository.UserRoleRepository;
import com.nnamdi.account.security.jwt.JwtUtils;
import com.nnamdi.account.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    private final AccountRepository accountRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final AccountModelAssembler accountModelAssembler;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(AccountRepository accountRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, AccountModelAssembler accountModelAssembler, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.accountModelAssembler = accountModelAssembler;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @PostMapping("/accounts")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already exists"));
        }

        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error:email is already in use"));
        }
        Account registerAccount = new Account(signUpRequest.getUsername(),signUpRequest.getName(), signUpRequest.getAddress(), signUpRequest.getPhoneNumber(), signUpRequest.getEmail(),bCryptPasswordEncoder.encode(signUpRequest.getPassword()) );
        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userUserRoles = roleRepository.findByName(RolesEnum.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error: Role does not exist"));
            roles.add(userUserRoles);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminUserRoles = roleRepository.findRolesByName(RolesEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found. "));
                        roles.add(adminUserRoles);
                        break;

                    default:
                        Roles userUserRoles = roleRepository.findRolesByName(RolesEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error:Role is not found"));
                        roles.add(userUserRoles);
                }
            });
        }
        registerAccount.setRoles(roles);
        accountRepository.save(registerAccount);
        EntityModel<Account> entityModel = accountModelAssembler.toModel(accountRepository.save(registerAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(roles);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),roles));

    }


}
