package com.example.ldap.infrastructure;

import com.example.ldap.user.CustomUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import java.util.Collection;

@Configuration
public class CustomUserDetailsContextMapper extends LdapUserDetailsMapper implements UserDetailsContextMapper {

    @Override
    public LdapUserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {

        // Standard user's properties. I'll add 3 custom attributes => CN, SN, L
        LdapUserDetailsImpl details = (LdapUserDetailsImpl) super.mapUserFromContext(ctx, username, authorities);

        String userCN = null;
        String userSN = null;
        String qrSecret = null;

        try {
            userCN = String.valueOf(ctx.getAttributes().get("cn").get());
            userSN = String.valueOf(ctx.getAttributes().get("sn").get());
            qrSecret = String.valueOf(ctx.getAttributes().get("l").get());
        }
        catch (Exception ex) {
            // TODO: Handle the exception!
        }

        return new CustomUserDetails(details, userCN, userSN, qrSecret);
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
    }
}
