package com.example.demoFriends.service;

import com.example.demoFriends.model.User;
import com.example.demoFriends.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User login(String username, String password) throws UserNotFoundException {
        Optional<User> result = Optional.ofNullable(repo.findByUsernameAndPassword(username, password));
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Incorrect data !!!");
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Couldn't find any users with ID " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count != null || count == 0) {
            throw new UserNotFoundException("Couldn't find any users with ID " + id);

        }
        repo.deleteById(id);
    }
}
