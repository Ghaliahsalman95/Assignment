package com.example.movietwebapplication.Service;

import com.example.movietwebapplication.APIRespons.ApiException;
import com.example.movietwebapplication.DTO.UserDTO;
import com.example.movietwebapplication.Model.User;
import com.example.movietwebapplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    //DI(Dependency Injection )
    private final UserRepository userRepository;


    public UserDTO getMyUser(Integer userId) {
        UserDTO userDTO = new UserDTO(userRepository.findUserById(userId).getId(),
                userRepository.findUserById(userId).getUsername(),
                userRepository.findUserById(userId).getEmail(),
                userRepository.findUserById(userId).getPassword());


        return userDTO;
    }

    public void register(UserDTO userDTO) {
        if (userRepository.findUserByUsernameAndEmail(userDTO.getUsername(), userDTO.getEmail()) != null) {
            throw new ApiException("Username already exists");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    public void update(Integer userId, UserDTO userDTO) {
        User user = userRepository.findUserById(userId);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepository.save(user);
    }


    public void delete(Integer userId) {
        User user = userRepository.findUserById(userId);
        userRepository.delete(user);
    }

}
