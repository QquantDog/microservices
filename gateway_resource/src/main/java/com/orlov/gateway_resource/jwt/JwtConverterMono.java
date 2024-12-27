package com.orlov.gateway_resource.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtConverterMono implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

//    AbstractAuthenticationToken

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        System.out.println("--- PARSING JWT ---\n" + jwt.toString());
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractRealmRoles(jwt).stream()).collect(Collectors.toSet());
        return Mono.just(new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("preferred_username")));
    }

    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Collection<String> roles;
        if (resourceAccess == null) {
            return Set.of();
        }
//        || (roles = (Collection<String>) resourceAccess.get("OAO.roles")) == null
        try{
            Map<String, Object> _resource_tree = (Map<String, Object>) resourceAccess.get("OAO");
            roles = (Collection<String>) _resource_tree.get("roles");

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toSet());
        } catch (Exception e){
            return Set.of();
        }
    }

}
