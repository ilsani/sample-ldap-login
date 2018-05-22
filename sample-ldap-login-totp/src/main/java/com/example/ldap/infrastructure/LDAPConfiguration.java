package com.example.ldap.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="ldap")
@Data
public class LDAPConfiguration {

    private String managerDn;
    private String managerPassword;
    private String url;

}
