package com.example.ldap.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.util.StringUtils;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private LDAPConfiguration ldapConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                    .antMatchers("/qrcode").hasRole("PRE_AUTH_USER")
                    .antMatchers("/home").hasRole("USER")
                    .anyRequest().fullyAuthenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/qrcode", true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        setupLDAP(auth);
    }

    private void setupLDAP(AuthenticationManagerBuilder auth) throws Exception {

        if (StringUtils.isEmpty(ldapConfiguration.getUrl())) {
            throw new Exception("Invalid settings. LDAP url not found.");
        }

        auth
                .ldapAuthentication()
                .userDetailsContextMapper(userDetailsContextMapper())
                .userDnPatterns("uid={0},ou=People")
                .userSearchBase("ou=People")
                .contextSource()
                .managerDn(ldapConfiguration.getManagerDn())
                .managerPassword(ldapConfiguration.getManagerPassword())
                .url(ldapConfiguration.getUrl());
    }

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new CustomUserDetailsContextMapper();
    }

}
