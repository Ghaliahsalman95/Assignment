package com.example.movietwebapplication.Service;

import com.example.movietwebapplication.APIRespons.ApiException;
import com.example.movietwebapplication.Model.User;
import com.example.movietwebapplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersDetailsService implements UserDetailsService {
    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws ApiException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) throw new ApiException("Wrong username or password.");
        return user;
    }
}
