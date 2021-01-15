package com.example.demo.sercurity;

import com.example.demo.Validate.InvalidLoginRespone;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        InvalidLoginRespone loginRespone = new InvalidLoginRespone();
        String jsonlogin = new Gson().toJson(loginRespone);
// dinh kem len header de lay message khi găp loi 401
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonlogin);
    }
}
