package com.example.movietwebapplication.Repository;

import com.example.movietwebapplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);
    User findUserByUsernameAndEmail(String username,String email);
    User findUserByUsername(String username);

}
