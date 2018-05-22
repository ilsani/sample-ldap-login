package com.example.ldap.home;

import com.example.ldap.user.User;
import com.example.ldap.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired private UserService userService;

    @GetMapping("/")
    public String index() {

        User user = userService.getCurrentUser();
        return String.format("Welcome %s %s!", user.getDetails().getCN(), user.getDetails().getSN());
    }

}