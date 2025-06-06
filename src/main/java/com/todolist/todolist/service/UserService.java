package com.todolist.todolist.service;

import com.todolist.todolist.Dto.*;
import com.todolist.todolist.entity.Todo;
import com.todolist.todolist.entity.User;
import com.todolist.todolist.entity.status;
import com.todolist.todolist.repository.TodoRepository;
import com.todolist.todolist.repository.UserRepository;
import com.todolist.todolist.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(registerUser input){
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setUsername(input.getUsername());
        user.setProfilePictureURL(input.getProfilePictureURL());
        return userRepository.save(user);
    }

    //dummy to test registration for now
    public authResponse login(loginUser input){
        User user = userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new EntityNotFoundException("Invalid Email: " + input.getEmail()));

        if(!passwordEncoder.matches(input.getPassword(),user.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        String token = jwtUtil.generateToken(user.getId());

        return new authResponse(token, user.getId(),user.getUsername(),user.getEmail(),user.getProfilePictureURL() );
    }

    public User getUserById(Long UserId){
        User user = userRepository.findById(UserId).orElseThrow(() -> new EntityNotFoundException("Invalid UserId: " + UserId));
        return user;
    }

    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Invalid Email: " + email));
        return user;
    }

    public User editUser(editUser input, Long userId){
        User user = getUserById(userId);
        user.setEmail(input.getEmail());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setProfilePictureURL(input.getProfilePictureURL());
        return userRepository.save(user);
    }

    public authResponse dummyLogin(){

        User user = userRepository.findByEmail("test@example.com").orElse(null);

        if(user == null){
            user = new User();
            user.setEmail("test@example.com");
            user.setUsername("testUser");
            user.setProfilePictureURL("https://example.com/default-avatar.png");
        }

        user.setPassword(passwordEncoder.encode("12345"));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId());

        return new authResponse(token, user.getId(),user.getUsername(),user.getEmail(),user.getProfilePictureURL() );
    }
}
