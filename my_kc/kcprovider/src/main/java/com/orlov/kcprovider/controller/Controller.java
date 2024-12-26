//package com.orlov.kcprovider.controller;
//
//import com.orlov.kcprovider.dto.UserRegisterDto;
//import com.orlov.kcprovider.service.KCService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/user")
//public class Controller {
//
//    @Autowired
//    private KCService keycloakService;
//
//    @PostMapping("/create")
//    public ResponseEntity<?> createUser(@RequestBody UserRegisterDto userRegisterDto) {
//        keycloakService.addUser(userRegisterDto);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/create-with-details")
//    public ResponseEntity<?> createUserWithDetails(@RequestBody UserRegisterDto userRegisterDto) {
//        keycloakService.addUser(userRegisterDto);
//        return ResponseEntity.ok().build();
//    }
//}
