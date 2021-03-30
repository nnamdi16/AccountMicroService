package com.nnamdi.account.service;

import com.nnamdi.account.exceptions.CustomException;
import com.nnamdi.account.model.Account;
import com.nnamdi.account.model.Roles;
import com.nnamdi.account.model.RolesEnum;
import com.nnamdi.account.payload.request.SignUpRequest;
import com.nnamdi.account.payload.response.JwtResponse;
import com.nnamdi.account.repository.RoleRepository;
import com.nnamdi.account.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    public JwtResponse signin(String username, String password) {
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item->item.getAuthority())
                    .collect(Collectors.toList());
            JwtResponse jwtResponse = new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),roles);
            return jwtResponse;

        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Account signup(SignUpRequest signUpRequest) {
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
        return registerAccount;

    }


}
