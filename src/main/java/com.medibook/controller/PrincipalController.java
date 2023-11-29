package com.medibook.controller;


import com.medibook.controller.request.CreateUserDTO;
import com.medibook.entities.Role;
import com.medibook.entities.Room;
import com.medibook.entities.UserEntity;
import com.medibook.repository.UserRepository;
import com.medibook.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    private UserEntityService userEntityService;
    @Autowired
    public PrincipalController(UserEntityService userEntityService){

        this.userEntityService =  userEntityService;
    }



    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
    //Creo el usuario
        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .name(createUserDTO.getName())
                .lastname(createUserDTO.getLastname())
                .role(Role.USER)
                .build();
        ;


        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserEntity> userEntities = userEntityList.stream().filter(it -> userEntity.getUsername().equals(it.getUsername())).collect(Collectors.toList());

        if(!(userEntities.isEmpty())){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El username ya existe");
        }
        userRepository.save(userEntity);


        return ResponseEntity.ok(userEntity);
    }

}
