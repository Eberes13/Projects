package com.example.demoFriends.repo;

import com.example.demoFriends.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
   public Long countById(Integer id);
   public User findByUsername(String username);
   public User findByUsernameAndPassword(String username, String password);
}
