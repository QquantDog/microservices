package com.orlov.kcprovider.service;

import com.orlov.kcprovider.dto.UserAfterKCDto;
import com.orlov.kcprovider.dto.UserUpdateDto;
import com.orlov.kcprovider.dto.UserWithDetailsRegisterDto;
import com.orlov.kcprovider.model.User;
import com.orlov.kcprovider.repository.UserRepository;
import com.orlov.kcprovider.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUUID(UUID uuid){
        return userRepository.findByCustomerUUID(uuid)
                .orElseThrow(()->new RuntimeException("User not found by UUID"));
    }

    public User getMe(){
        var t = SecurityUtils.getContextUserUUID();
        return userRepository.findByCustomerUUID(SecurityUtils.getContextUserUUID())
                .orElseThrow(()->new RuntimeException("User not found by UUID"));
    }

    @Transactional
    public User updateUserByUUID(UUID uuid, UserUpdateDto userUpdateDto){
        User user = userRepository.findByCustomerUUID(uuid)
                .orElseThrow(()->new RuntimeException("User not found by UUID"));

        modelMapper.map(userUpdateDto, user);
        return userRepository.save(user);
    }

    @Transactional
    public User createUserAfterKC(UserAfterKCDto dto){
        String customerUUID = dto.getCustomerUUID().toString();
        UserRepresentation ur = keycloak.realm(realm).users().get(customerUUID).toRepresentation();
        if(ur == null) throw new RuntimeException("User not found in KC");

        addClientRoleByUUID(customerUUID);
        User userToSave = modelMapper.map(dto, User.class);
        return userRepository.save(userToSave);
    }

    @Transactional
    public User createUserWithDetails(UserWithDetailsRegisterDto dto) {
        String username = dto.getUsername();
        CredentialRepresentation credential = createPasswordCredentials(dto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        user.setEmail(dto.getEmail());
        user.setEmailVerified(true);

        UsersResource usersResource = getUsersResource();
        usersResource.create(user);
        addClientRoleToUser(username);

        String uuid = keycloak.realm(realm).users().search(username).getFirst().getId();
        System.out.println("uuid: " + uuid);

        User userToSave = modelMapper.map(dto, User.class);
        userToSave.setCustomerUUID(UUID.fromString(uuid));
        return userRepository.save(userToSave);


//        сохраняем по uuid

    }

    private void addClientRoleToUser(String userName) {
        List<UserRepresentation> users = keycloak.realm(realm).users().search(userName);
        RoleRepresentation role = keycloak.realm(realm).rolesById().getRole("75abce12-c590-47ff-9e87-a5b8d8b4858f");
        UserRepresentation r = keycloak.realm(realm).users().search(userName).getFirst();
        keycloak.realm(realm).users().get(r.getId()).roles().clientLevel("a35a1264-62bf-4600-bb18-28e7f8b4c82a").add(Collections.singletonList(role));
    }

    private void addClientRoleByUUID(String customerUUID) {
        RoleRepresentation role = keycloak.realm(realm).rolesById().getRole("75abce12-c590-47ff-9e87-a5b8d8b4858f");
        keycloak.realm(realm).users().get(customerUUID).roles().clientLevel("a35a1264-62bf-4600-bb18-28e7f8b4c82a").add(Collections.singletonList(role));
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
