package com.example.ldap.qrcode;

import com.example.ldap.totp.TOTPVerificationService;
import com.example.ldap.user.User;
import com.example.ldap.user.UserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class QRCodeController {

    @Autowired private UserService userService;

    @Autowired private TOTPVerificationService totpVerificationService;

    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public String show() {
        return "qrcode";
    }

    @RequestMapping(value = "/qrcode", method = RequestMethod.POST)
    public String submit(String code) throws Exception {

        String view = "redirect:/qrcode?error";

        User user = userService.getCurrentUser();

        if (totpVerificationService.validate(user.getDetails().getQrSecret(), code)) {
            grantAuthority();
            view = "redirect:/home";
        }

        return view;
    }

    @RequestMapping("/qrcode/image")
    public void qrcodeImage(HttpServletResponse response) throws Exception {

        User user = userService.getCurrentUser();

        response.setContentType("image/png");

        // TODO: &algorithm=SHA256&digits=8&period=20
        // current: digits=6&algorithm=SHA1

        String data = String.format("otpauth://totp/%s?secret=%s&issuer=ExampleApp",
                user.getDetails().getUsername(), user.getDetails().getQrSecret());

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToStream(matrix, "PNG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    private void grantAuthority() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

}
