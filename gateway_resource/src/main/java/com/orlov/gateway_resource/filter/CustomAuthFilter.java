//package com.orlov.gateway_resource.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomAuthFilter implements GatewayFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        return ReactiveSecurityContextHolder.getContext()
//                .map(context -> context.getAuthentication()) // Extract the Authentication object
//                .doOnNext(authentication -> {
//                    if (authentication != null) {
//                        // You can extract details from the Authentication object
//                        String username = authentication.getName();
//                        System.out.println("Authenticated user: " + username);
//
//                        List<String> rolesToPass = new ArrayList<>();
//                        authentication.getAuthorities().forEach(role -> rolesToPass.add(role.getAuthority()));
//                        String uuid = ((Jwt) authentication.getPrincipal()).getClaim("sub");
//                        // Optionally add user info to request headers or attributes
//                        exchange.getRequest().mutate()
//                                .header("uuid", uuid).header("roles", rolesToPass.toArray(new String[0])).build();
//                    }
//                })
//                .then(chain.filter(exchange));
//    }
//}
