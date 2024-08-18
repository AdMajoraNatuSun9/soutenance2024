package com.roadguardianbackend.roadguardianbackend.service;

import com.roadguardianbackend.roadguardianbackend.entity.Role;
import com.roadguardianbackend.roadguardianbackend.entity.User;
import com.roadguardianbackend.roadguardianbackend.exception.domain.EmailExistException;
import com.roadguardianbackend.roadguardianbackend.exception.domain.EmailNotFoundException;
import com.roadguardianbackend.roadguardianbackend.exception.domain.UserNotFoundException;
import com.roadguardianbackend.roadguardianbackend.exception.domain.UsernameExistException;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User register(String firstName, String lastName,  String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException, MessagingException, IOException;
        List<User> getUsers();
        User findUserByUsername(String username);
        User findUserByEmail(String email);

        User addNewUser(String firstName, String lastName, String username, String email,
                        String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

        User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail,
                    String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

        void deleteUser(long id);

        void resetPassword(String email) throws MessagingException, EmailNotFoundException;

        User updateProfilImage(String username, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;



}
