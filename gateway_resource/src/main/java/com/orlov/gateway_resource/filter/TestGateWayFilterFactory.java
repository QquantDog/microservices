package com.orlov.gateway_resource.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestGateWayFilterFactory extends AbstractGatewayFilterFactory<TestGateWayFilterFactory.Config> {

    public TestGateWayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> exchange.getPrincipal()
                .flatMap(principal -> {

                    var auth = ((JwtAuthenticationToken) principal);
                    List<String> rolesToPass = new ArrayList<>();
                    auth.getAuthorities().forEach(role -> rolesToPass.add(role.getAuthority()));
                    String uuid = ((Jwt) auth.getCredentials()).getClaim("sub");

                    var request = exchange
                            .getRequest()
                            .mutate()
                            .header("uuid", uuid)
                            .header("roles", String.join(" ", rolesToPass))
                            .build();

                    return chain.filter(exchange.mutate().request(request).build());
                });
    }

    public static class Config {
//        private String customValue;
//
//        public String getCustomValue() {
//            return customValue;
//        }
//
//        public void setCustomValue(String customValue) {
//            this.customValue = customValue;
//        }
    }
}

//@Override
//public GatewayFilter apply(Config config) {
//    return (exchange, chain) -> {
//        ServerHttpRequest request = exchange.getRequest().mutate()
//                .header("Meow-Custom-Header", "123").header("ROLES", "scope_1", "scope_2")
//                .build();
//
//        return chain.filter(exchange.mutate().request(request).build());
//    };
//}







//return ReactiveSecurityContextHolder.getContext()
//                    .map(SecurityContext::getAuthentication) // Extract the Authentication object
//                    .mapNotNull(authentication -> {
//                        if (authentication != null) {
//                            // You can extract details from the Authentication object
//                            String username = authentication.getName();
//                            System.out.println("Authenticated user: " + username);
//
//                            List<String> rolesToPass = new ArrayList<>();
//                            authentication.getAuthorities().forEach(role -> rolesToPass.add(role.getAuthority()));
//                            String uuid = ((Jwt) authentication.getPrincipal()).getClaim("sub");
////                            exchange.getAttributes().put("uuid", uuid);
//                            // Optionally add user info to request headers or attributes
//                            ServerHttpRequest mutateRequest = exchange.getRequest().mutate()
//                                    .header("uuid", uuid).header("roles", rolesToPass.toArray(new String[0])).build();
//                            return exchange.mutate().request(mutateRequest).build();
//                        }
//                        return null;
//                    })
//                    .doOnNext((e) -> {
//                        chain.filter(e);
//
//                        var q = 5;
//                        return;
//                    }).then();