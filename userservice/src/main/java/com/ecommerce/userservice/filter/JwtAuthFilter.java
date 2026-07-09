package com.ecommerce.userservice.filter;

import com.ecommerce.userservice.entities.User;
import com.ecommerce.userservice.service.UserService;
import com.ecommerce.userservice.service.impl.UserServiceImpl;
import com.ecommerce.userservice.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         try {
             final String requestTokenHeader=request.getHeader("Authorization");
             if(requestTokenHeader == null && !requestTokenHeader.startsWith("Bearer "))
             {
                 filterChain.doFilter(request,response);
                 return;
             }
             String token=requestTokenHeader.split("Bearer ")[1];
             Long userId=jwtUtil.getUserIdFromToken(token);
             if(userId !=null && SecurityContextHolder.getContext().getAuthentication()!=null){
                 UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userId,null,null);
                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
             }


         }catch (Exception e){
            SecurityContextHolder.clearContext();
         }
             filterChain.doFilter(request,response);
    }
}
