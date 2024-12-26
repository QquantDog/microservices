package com.orlov.kcprovider.controller;

import com.orlov.kcprovider.dto.UserAfterKCDto;
import com.orlov.kcprovider.dto.UserDto;
import com.orlov.kcprovider.dto.UserUpdateDto;
import com.orlov.kcprovider.dto.UserWithDetailsRegisterDto;
import com.orlov.kcprovider.model.User;
import com.orlov.kcprovider.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/custom-user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

//    admin
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users.stream().map(u -> modelMapper.map(u, UserDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserByUUID(@PathVariable UUID uuid) {
        User user = userService.getUserByUUID(uuid);
        return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.OK);
    }

    @GetMapping("/exists/{uuid}")
    public ResponseEntity<?> existsUserByUUID(@PathVariable UUID uuid) {
        userService.getUserByUUID(uuid);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDto> updateUserDetails(@RequestBody @Valid UserUpdateDto userUpdateDto, @PathVariable UUID uuid) {
        User user = userService.updateUserByUUID(uuid, userUpdateDto);
        return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.OK);
    }


    @PostMapping("/create-with-details")
    public ResponseEntity<UserDto> createUserWithDetails(@RequestBody @Valid UserWithDetailsRegisterDto dto) {
        User user = userService.createUserWithDetails(dto);
        return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/post-keycloak-registration")
    public ResponseEntity<UserDto> createUserAfterKC(@RequestBody @Valid UserAfterKCDto dto) {
        User user = userService.createUserAfterKC(dto);
        return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.CREATED);
    }
}
