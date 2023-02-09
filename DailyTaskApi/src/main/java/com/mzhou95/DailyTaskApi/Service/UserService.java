package com.mzhou95.DailyTaskApi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzhou95.DailyTaskApi.Model.User;
import com.mzhou95.DailyTaskApi.Persistence.UserEntity;
import com.mzhou95.DailyTaskApi.Persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserEntity getUserById(Integer id){
        return userRepository.getUserEntityByUserId(id);
    }

    public Optional<UserEntity> insertNewUser(HttpEntity<String> user){
        Optional<UserEntity> insertedUser = Optional.empty();

        Optional<User> userFromHttpBody = jsonToUserModel(user.getBody());

        if(userFromHttpBody.isPresent()) {
            UserEntity newUser = new UserEntity(userFromHttpBody.get().getUsername(), userFromHttpBody.get().getPassword());
            UserEntity returnedUser = userRepository.save(newUser);
            insertedUser = Optional.of(returnedUser);
        }
        return insertedUser;
    }

    public Optional<UserEntity> changePassword(Integer id, HttpEntity<String> user){
        Optional<UserEntity> updatedUser = Optional.empty();
        Optional<UserEntity> oldUser = Optional.ofNullable(userRepository.getUserEntityByUserId(id));

        if(oldUser.isEmpty()){
            return updatedUser;
        }

        Optional<User> userFromHttpBody = jsonToUserModel(user.getBody());
        if(userFromHttpBody.isPresent()){
            UserEntity userToUpdate = oldUser.get();
            userToUpdate.setPassword(userFromHttpBody.get().getPassword());
            UserEntity update = userRepository.save(userToUpdate);
            updatedUser = Optional.of(update);
        }
        return updatedUser;
    }

    public Optional<UserEntity> deleteUser(Integer id, String password) {
        Optional<UserEntity> userToDelete = Optional.ofNullable(userRepository.getUserEntityByUserId(id));
        if(userToDelete.isPresent()){
            if(userToDelete.get().getPassword() == password)
            userRepository.deleteById(id);
        }
        return userToDelete;
    }
    private Optional<User> jsonToUserModel(String jsonUser){
        ObjectMapper mapper = new ObjectMapper();
        Optional<User> user = Optional.empty();
        try{
            User mappedUser = mapper.readValue(jsonUser, User.class);
            user = Optional.of(mappedUser);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
