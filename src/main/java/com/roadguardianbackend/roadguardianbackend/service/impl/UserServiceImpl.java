package com.roadguardianbackend.roadguardianbackend.service.impl;

//import com.roadguardianbackend.roadguardianbackend.entity.Role;
//import com.roadguardianbackend.roadguardianbackend.entity.User;
//import com.roadguardianbackend.roadguardianbackend.repository.RoleRepository;
//import com.roadguardianbackend.roadguardianbackend.repository.UserRepository;

import com.roadguardianbackend.roadguardianbackend.domain.UserPrincipal;
import com.roadguardianbackend.roadguardianbackend.entity.User;
import com.roadguardianbackend.roadguardianbackend.enums.Role;
import com.roadguardianbackend.roadguardianbackend.exception.domain.EmailExistException;
import com.roadguardianbackend.roadguardianbackend.exception.domain.EmailNotFoundException;
import com.roadguardianbackend.roadguardianbackend.exception.domain.UserNotFoundException;
import com.roadguardianbackend.roadguardianbackend.exception.domain.UsernameExistException;
import com.roadguardianbackend.roadguardianbackend.repository.UserRepository;
import com.roadguardianbackend.roadguardianbackend.service.EmailService;
import com.roadguardianbackend.roadguardianbackend.service.LoginAttemptService;
import com.roadguardianbackend.roadguardianbackend.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.roadguardianbackend.roadguardianbackend.constant.FileConstant.*;
import static com.roadguardianbackend.roadguardianbackend.constant.UserImplConstant.*;
import static com.roadguardianbackend.roadguardianbackend.enums.Role.ROLE_USER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.logging.log4j.util.Strings.EMPTY;


@Service
@Transactional
@Qualifier("UserDetailsService")


@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private LoginAttemptService loginAttemptService;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()){
            if(loginAttemptService.hasExceedeMaxAttempts(user.getUsername())){
                user.setNotLocked(false);
            }else{
                user.setNotLocked(true);
            }
        }else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

//    @Override
//    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException, MessagingException {
//        validateNewUsernameAndEmail(StringUtils.EMPTY, username,email);
//        User user = new User();
//        user.setUserId(generateUserId());
//        String password = generatePassword();
//
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setJoinDate(new Date());
//        user.setPassword(encodedPassword(password));
//        user.setActive(true);
//        user.setNotLocked(true);
//        user.setRoles(ROLE_USER.name());
//        user.setAuthorities(ROLE_USER.getAuthorities());
//        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
//        userRepository.save(user);
////        LOGGER.info("New User Password :"+ password);
//        emailService.sendNewPasswordEmail(firstName, password, email);
//        return user;
//    }


    @Override
    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException, MessagingException, IOException {
        validateNewUsernameAndEmail(EMPTY, username,email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRoles(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(firstName, password, email);
        return user;
    }

    @Override
    public User addNewUser(String firstName, String lastName, String username, String email, String roles, boolean notLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        validateNewUsernameAndEmail(EMPTY,username,email);
        User user = new User();
        String password = generatePassword();
        user.setUserId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setJoinDate(new Date());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword(password));
        user.setActive(isActive);
        user.setNotLocked(notLocked);
        user.setRoles(getRoleEnumName(roles).name());
        user.setAuthorities(getRoleEnumName(roles).getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        saveProfileImage(user, profileImage);
        LOGGER.info("Add New User ");
        return user;
    }



    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
       User currentUser =  validateNewUsernameAndEmail(currentUsername , newUsername, newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonLocked);
        currentUser.setRoles(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepository.save(currentUser);
        saveProfileImage(currentUser, profileImage);
        return currentUser;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);

    }

    @Override
    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }

        String password = generatePassword();
        user.setPassword(encodedPassword(password));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());

    }

    @Override
    public User updateProfilImage(String username, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        User user = validateNewUsernameAndEmail(username, null, null);
        saveProfileImage(user, profileImage);
        return user;
    }
    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
        if(profileImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            userRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM +  profileImage.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH
        + username + DOT + JPG_EXTENSION).toUriString();
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }



    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);

        if(StringUtils.isNotBlank( currentUsername)){
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null){
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }

            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())){
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }


            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())){
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return  currentUser;
        }else {
            if(userByNewUsername != null){
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }

            if(userByNewEmail != null){
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }



}