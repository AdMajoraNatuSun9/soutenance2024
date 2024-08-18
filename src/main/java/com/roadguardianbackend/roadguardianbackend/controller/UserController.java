package com.roadguardianbackend.roadguardianbackend.controller;

import com.roadguardianbackend.roadguardianbackend.domain.HttpResponse;
import com.roadguardianbackend.roadguardianbackend.domain.UserPrincipal;
import com.roadguardianbackend.roadguardianbackend.entity.Role;
import com.roadguardianbackend.roadguardianbackend.exception.domain.*;
import com.roadguardianbackend.roadguardianbackend.service.UserService;
import com.roadguardianbackend.roadguardianbackend.entity.User;
import com.roadguardianbackend.roadguardianbackend.utility.JWTTokenProvider;
import jakarta.mail.MessagingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.roadguardianbackend.roadguardianbackend.constant.FileConstant.*;
import static com.roadguardianbackend.roadguardianbackend.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/","/user"})

public class UserController extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully ";

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;






    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<User> login(@RequestParam("username") String username,@RequestParam("password") String password) {
//        authenticate(username, password);
//        User loginUser = userService.findUserByUsername(username);
//        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
//        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
//        return new ResponseEntity<>(loginUser, jwtHeader, OK);
//    }


    // Méthode d'aide pour authentifier l'utilisateur
    private Authentication authenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    // Méthode d'aide pour obtenir l'entête JWT.


//    private HttpHeaders getJwtHeader(UserPrincipal user) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
//        return headers;
//    }



    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, EmailExistException, UsernameExistException, MessagingException, IOException {
      User newUser =  userService.register(user.getFirstName(), user.getLastName(),  user.getUsername(), user.getEmail());
      return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("roles") String roles,
                                           @RequestParam("isActive") String isActive,
                                           @RequestParam("notLocked") String notLocked,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
        User newUser = userService.addNewUser(firstName, lastName, username, email, roles,
                Boolean.parseBoolean(notLocked),  Boolean.parseBoolean(isActive), profileImage);

        return new ResponseEntity<>(newUser, OK);

    }

    @PutMapping("/update")
    public ResponseEntity<User> update(
                                            @RequestParam("firstName") String firstName,
                                            @RequestParam("currentUsername") String currentUsername,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("roles") String roles,
                                           @RequestParam("isActive") String isActive,
                                           @RequestParam("notLocked") String notLocked,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
        User updateUser = userService.updateUser(currentUsername,firstName, lastName, username, email, roles,
                Boolean.parseBoolean(notLocked),  Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(updateUser, OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, OK);
    }
    @GetMapping("/list")
    public ResponseEntity< List<User> > getAllUsers(){
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }
    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws EmailNotFoundException, MessagingException {
         userService.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return response(NO_CONTENT, USER_DELETED_SUCCESSFULLY);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username,
                                                   @RequestParam(value = "profileImage") MultipartFile profileImage)
            throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
        User user = userService.updateProfilImage(username, profileImage);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username,@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try(InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }

        }
        return byteArrayOutputStream.toByteArray();
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new  ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().
                toUpperCase(), message.toUpperCase()),httpStatus);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }
    
}

