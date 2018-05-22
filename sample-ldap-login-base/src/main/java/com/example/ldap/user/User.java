package com.example.ldap.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private CustomUserDetails details;

}
