package com.example.ldap.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getCurrentUser() {

        // I retrieve current authenticated user from session (SecurityContext).
        // See com.example.ldap.infrastructure.CustomUserDetailsContextMapper

        CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new User(userDetails);
    }



}
