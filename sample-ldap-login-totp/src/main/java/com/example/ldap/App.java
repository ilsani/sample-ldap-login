package com.example.ldap;

import com.example.ldap.totp.TOTPVerificationService;
import com.google.zxing.client.j2se.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /*@Component
    public class DataLoader implements org.springframework.boot.CommandLineRunner {

        @Autowired private TOTPVerificationService totpVerificationService;

        @Override
        public void run(String... strings) throws Exception {
            try {
                String secret = "NNK6EJYQ7GL4X4DZPOVUZIBCR5S2UQS462N7Q5KRATSP2ZQV";
                System.out.println(totpVerificationService.validate(secret, "39244003"));
            }
            catch (Exception ex) {
                System.out.println("ERROR");
            }
        }
    }*/

}
