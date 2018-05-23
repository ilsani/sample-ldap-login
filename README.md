# sample-ldap-login

This repository contains 2 projects:
- sample-ldap-login-base
- sample-ldap-login-totp

_sample-ldap-login-base_ implements base requirements (login form to authenticate users through a LDAP server).

_sample-ldap-login-totp_ implements bonus feature (OTP authentication).

## Dev environment

- Microsoft Windows 10 Pro 32bit
- java version "1.8.0_171" (build 1.8.0_171-b11)
- ApacheDS - v. apacheds-2.0.0-M24 (http://directory.apache.org/apacheds/downloads.html)
- Spring Framework (Spring boot 2.0.0-RELEASE)
- FreeOTP Authenticator version 1.5 (17)

ApacheDS has been configured to use the provided LDIF:
1. New Context Entry -> dc=myorg,dc=test
2. Import LDIF file (_test.ldif_)

## Usage

By default programs use configuration settings defined in the _application.yml_ file embedded into the project (jar file).
```
# "C:\Program Files\Java\jre1.8.0_171\bin\java" -jar sample-ldap-login-1.0-SNAPSHOT.jar
```

If you would like use another configuration file, you could specify its path with _--spring.config.location_ parameter.
```
# "C:\Program Files\Java\jre1.8.0_171\bin\java" -jar sample-ldap-login-1.0-SNAPSHOT.jar --spring.config.location="C:\tmp\sample-ldap-login\application.yml"
```

If you would like overwrite embedded/default configuration file with a file in the same folder of the JAR program you could place a file named _application.yml_ in its folder.
```
# dir
  ... sample-ldap-login-1.0-SNAPSHOT.jar
  ... application.yml
  
# "C:\Program Files\Java\jre1.8.0_171\bin\java" -jar sample-ldap-login-1.0-SNAPSHOT.jar
```

Programs use Spring Framework, so more combination are allowed. (e.g it is also possible to overwrite only a single configuration parameter). See references for more details.

## Default settings

By default programs use settings defined in the _application.yml_ file embedded into the project (jar file).

```
# app name
app:
  name: sample-ldap-login

# web server's port
server:
  port: 8080

# LDAP settings
# managerDn and managerPassword are the username/password used by the application to LDAP connections, as _system_ user.
# if you use anonymous LDAP connection comment managerDn and managerPassword settings.
ldap:
  #managerDn: uid=test1,ou=People,dc=myorg,dc=test
  #managerPassword: Password123!
  url: ldap://localhost:10389/dc=myorg,dc=test
```

# sample-ldap-login-totp

This project implements login form to authenticate users through a LDAP server and the OTP authentication. In order to simply tests and implementation I created a simple POC that uses 3 web pages:
1. _/_: Login form with username/password
2. _/qrcode_: QR code display and TOTP code verification
3. _/home_: Protected resource

## References
- https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html