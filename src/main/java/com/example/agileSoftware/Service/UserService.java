package com.example.agileSoftware.Service;

import com.example.agileSoftware.Model.User;
import com.example.agileSoftware.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public User signup(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUserPartial(Long id, Map<String, Object> updates) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            applyPartialUpdates(user, updates);
            return userRepository.save(user);
        }
        return null;
    }

    private void applyPartialUpdates(User user, Map<String, Object> updates) {
        // Parcourez les mises à jour et appliquez-les aux champs appropriés de l'utilisateur
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            if (fieldName.equals("username")) {
                user.setUsername((String) value);
            } else if (fieldName.equals("email")) {
                user.setEmail((String) value);
            } // Ajoutez d'autres champs à mettre à jour si nécessaire
            else if (fieldName.equals("role")) {
                user.setRole((String) value);
            }
        }
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
