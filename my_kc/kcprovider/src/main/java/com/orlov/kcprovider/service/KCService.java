//package com.orlov.kcprovider.service;
//
//import java.util.*;
//
//import com.orlov.kcprovider.dto.UserRegisterDto;
//import lombok.RequiredArgsConstructor;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.resource.*;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RoleRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KCService {
//
//    private final Keycloak keycloak;
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    public void addUser(UserRegisterDto dto) {
//        String username = dto.getUsername();
//        CredentialRepresentation credential = createPasswordCredentials(dto.getPassword());
//        UserRepresentation user = new UserRepresentation();
//        user.setUsername(username);
//        user.setCredentials(Collections.singletonList(credential));
//        user.setEnabled(true);
//        user.setEmail(dto.getEmail());
//        user.setEmailVerified(true);
//
//        user.setRealmRoles(Collections.singletonList("RRR"));
//
//        UsersResource usersResource = getUsersResource();
//        usersResource.create(user);
//        addClientRoleToUser(username);
//
//        String uuid = keycloak.realm(realm).users().search(username).getFirst().getId();
//        System.out.println("uuid: " + uuid);
//
//
////        сохраняем по uuid
//
//    }
//
//    private void addClientRoleToUser(String userName) {
////        UserResource userResource = keycloak.realm(realm).users().get(userName);
//
////        RolesResource rolesResource = keycloak.realm(realm).roles();
//        List<UserRepresentation> users = keycloak.realm(realm).users().search(userName);
//        RoleRepresentation role = keycloak.realm(realm).rolesById().getRole("75abce12-c590-47ff-9e87-a5b8d8b4858f");
////        RoleRepresentation representation = rolesResource.get("CUSTOMER").toRepresentation();
////        userResource.roles().realmLevel().add(Collections.singletonList(role));
//        UserRepresentation r = keycloak.realm(realm).users().search(userName).getFirst();
////        r.getId();
////        System.out.println("R GET UD: " + r.getId());
////        UserRepresentation user = userResource.toRepresentation();
////        user.setGroups(Collections.singletonList("OAO_customers"));
//        keycloak.realm(realm).users().get(r.getId()).roles().clientLevel("a35a1264-62bf-4600-bb18-28e7f8b4c82a").add(Collections.singletonList(role));
//
//    }
//
//    private UsersResource getUsersResource() {
//        return keycloak.realm(realm).users();
//    }
//
//    private static CredentialRepresentation createPasswordCredentials(String password) {
//        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
//        passwordCredentials.setTemporary(false);
//        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
//        passwordCredentials.setValue(password);
//        return passwordCredentials;
//    }
//}
