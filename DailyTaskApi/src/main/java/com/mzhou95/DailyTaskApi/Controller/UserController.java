package com.mzhou95.DailyTaskApi.Controller;

import com.mzhou95.DailyTaskApi.Persistence.UserEntity;
import com.mzhou95.DailyTaskApi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/user")
    public UserEntity getUserById(@RequestParam Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("/v1/create-user")
    public ResponseEntity<?> addUser(HttpEntity<String> httpEntity){
        Optional<UserEntity> insertionSuccess = userService.insertNewUser(httpEntity);
        Integer userId = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if (insertionSuccess.isPresent()){
            userId = insertionSuccess.get().getUserId();
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(userId, status);
    }

    @PutMapping("/v1/change-password")
    public ResponseEntity<?> changePassword(@RequestParam Integer id, HttpEntity<String> httpEntity){
        Optional<UserEntity> insertionSuccess = userService.changePassword(id, httpEntity);
        Integer userId = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if (insertionSuccess.isPresent()){
            userId = insertionSuccess.get().getUserId();
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(userId, status);
    }

    @DeleteMapping("/v1/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Integer userId, @RequestParam String password){
        Optional<UserEntity> deletionSuccess = userService.deleteUser(userId, password);
        Integer userIdToDelete = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if(deletionSuccess.isPresent()){
            userIdToDelete = deletionSuccess.get().getUserId();
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(userIdToDelete, status);
    }
}
