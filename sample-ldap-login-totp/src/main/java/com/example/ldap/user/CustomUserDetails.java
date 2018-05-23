package com.example.ldap.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

import java.util.Collection;

public class CustomUserDetails implements LdapUserDetails {

    private LdapUserDetailsImpl attributes;
    private String cn;
    private String sn;
    private String qrSecret;

    public CustomUserDetails(LdapUserDetailsImpl attributes, String cn, String sn, String qrSecret) {
        this.attributes = attributes;
        this.cn = cn;
        this.sn = sn;
        this.qrSecret = qrSecret;
    }

    public String getCN() {
        return cn;
    }

    public String getSN() {
        return sn;
    }

    public String getQrSecret() {
        return qrSecret;
    }

    @Override
    public String getDn() {
        return attributes.getDn();
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: Preserve curren authorities
        return AuthorityUtils.createAuthorityList("ROLE_PRE_AUTH_USER");
        //return attributes.getAuthorities();
    }

    @Override
    public String getPassword() {
        return attributes.getPassword();
    }

    @Override
    public String getUsername() {
        return attributes.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return attributes.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return attributes.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return attributes.isEnabled();
    }
}
