package com.orlov.kcprovider.filter;

import com.orlov.kcprovider.dto.UserUUIDDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class HeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String userUUID = request.getHeader("uuid");
            String rolesText = request.getHeader("roles");
            if(userUUID == null || rolesText == null) throw new RuntimeException("No auth header(s) from gateway");
            List<String> roles = Arrays.stream(rolesText.split(" ")).toList();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    new UserUUIDDto(userUUID),
                    null,
                    roles.stream().map(r -> {
                        return new SimpleGrantedAuthority(r.toUpperCase());
                    }).toList()
            );
//            authToken.setDetails(
//                    new WebAuthenticationDetailsSource().buildDetails(request)
//            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (RuntimeException e){
            logger.error(e.getMessage(), e);
        } finally {
//            System.out.println("Error parsing auth headers");
            filterChain.doFilter(request, response);
        }

    }
}
