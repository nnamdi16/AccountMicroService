//package com.nnamdi.account.service;
//
//import com.nnamdi.account.exceptions.CustomException;
//import com.nnamdi.account.repository.AccountRepository;
//import com.nnamdi.account.security.jwt.JwtTokenProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    public String signin(String username, String password) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            return jwtTokenProvider.createToken(username, accountRepository.findByUsername(username));
//        } catch (AuthenticationException e) {
//            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//    }
//
////    public String signup( user) {
////        if (!userRepository.existsByUsername(user.getUsername())) {
////            user.setPassword(passwordEncoder.encode(user.getPassword()));
////            userRepository.save(user);
////            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
////        } else {
////            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
////        }
////    }
//
////    public void delete(String username) {
////        userRepository.deleteByUsername(username);
////    }
////
////    public User search(String username) {
////        User user = userRepository.findByUsername(username);
////        if (user == null) {
////            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
////        }
////        return user;
////    }
////
////    public User whoami(HttpServletRequest req) {
////        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
////    }
////
////    public String refresh(String username) {
////        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
////    }
//}
