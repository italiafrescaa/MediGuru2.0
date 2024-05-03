package com.user_manager_v1.rest_controllers;

import com.user_manager_v1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RegisterApiController {

    @Autowired
    UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity registerNewUser(@Param("nome") String nome,
                                          @Param("cognome") String cognome,
                                          @Param("email") String email,
                                          @Param("password") String password){

        if(nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Completa tutti i campi", HttpStatus.BAD_REQUEST);
        }

        String hashed_password = BCrypt.hashpw(password, BCrypt.gensalt());

        //Registrazione
        int result = userService.registerNewUserServiceMethod(nome, cognome, email, hashed_password); //1 se è stato inserito

        if(result != 1){
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}