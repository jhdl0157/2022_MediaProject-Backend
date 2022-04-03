package com.example.timecapsule.user.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtFilter implements Filter {
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
//        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
//        String tokenFromHeader = httpServletRequest.getHeader("X-AUTH-TOKEN");
//
//        //토큰 유효
//        if (tokenFromHeader != null && jwtTokenProvider.validateToken(tokenFromHeader)){
//            //토큰으로 정보 받기
//
//        }
//
//    }
//
//}
