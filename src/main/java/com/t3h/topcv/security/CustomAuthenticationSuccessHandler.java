package com.t3h.topcv.security;


import com.t3h.topcv.entity.Account;
import com.t3h.topcv.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService accountService;

    public CustomAuthenticationSuccessHandler(UserService theUserService) {
        accountService = theUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("In customAuthenticationSuccessHandler");

        String userName = authentication.getName();

        System.out.println("userName=" + userName);

        Account theAccount = accountService.findByUserName(userName);

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("account", theAccount);

        // forward to home page
        response.sendRedirect(request.getContextPath() + "/");
    }

}
