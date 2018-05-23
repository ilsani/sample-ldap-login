package com.example.ldap.home;

import com.example.ldap.user.User;
import com.example.ldap.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired private UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String show(Map<String, Object> model) {

        User user = userService.getCurrentUser();
        String message = String.format("Welcome %s %s!", user.getDetails().getCN(), user.getDetails().getSN());

        model.put("message", message);

        return "home";
    }

}