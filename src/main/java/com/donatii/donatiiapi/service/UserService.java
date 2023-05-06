package com.donatii.donatiiapi.service;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.repository.UserRepository;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String parola) throws NotFoundException {
        Optional<User> user = userRepository.findUserByEmailAndParola(email, parola);
        if(user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    public void register(User user) throws MyException {
        if(userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new MyException("Email already exists");
        userRepository.save(user);
    }

    public void update(Long id, User user) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User userToUpdate = userOptional.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setParola(user.getParola());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFullName(user.getFullName());
        userToUpdate.setGender(user.getGender());
        userToUpdate.setInterese(user.getInterese());
        userRepository.save(userToUpdate);
    }

    public void delete(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        userRepository.deleteById(id);
    }

    public User findById(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        return userOptional.get();
    }

    public void like(Long userId, Long cauzaId) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        if(userRepository.existsSustinere(userId, cauzaId)) {//schimbam statusul aprecierii
            userRepository.deleteSustinere(userId, cauzaId);
            System.out.println("dislike");
        }
        else {//apreciere
            userRepository.addSustinere(userId, cauzaId);
            System.out.println("like");
        }
    }
}
