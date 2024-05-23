package org.example.spring.controllers;

import org.example.spring.entities.UserDto;
import org.example.spring.models.auth.User;
import org.example.spring.requests.UserLoginRequest;
import org.example.spring.responses.UserLoginResponse;
import org.example.spring.services.UserService;
import org.example.spring.services.user.auth.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUserService authService;
    @Autowired
    public UserController(UserService userService, JwtUserService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
//        UserDto userDto = userService.getUserById(id);
//        if (userDto == null) {
//            return ResponseEntity.notFound().build();
//        }
        UserDto userDto = new UserDto();
        userDto.setUsername("Sonya");
        userDto.setEmail("sofapokl@gmail.com");
        userDto.setId(id);
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            System.out.println("hi:" + userDto.toString());
            User user = userService.registerNewUserAccount(userDto);
            UserLoginResponse userLoginResponse = authService.signUp(user);
            return ResponseEntity.ok(userLoginResponse);
        } catch(Exception ex)
        {
            return ResponseEntity.ok().body(ex.toString());
        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        try {
            System.out.println("hi:" + userDto.toString());
            userService.updateUser(userDto);

            return ResponseEntity.ok("Success");
        } catch(Exception ex)
        {
            return ResponseEntity.ok().body(ex.toString());
        }
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUserData(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        // Перевіряємо та розшифровуємо токен

        try {
            User user = authService.getUserData(token);
            if (user != null) {
                return ResponseEntity.ok(user); // Повертаємо дані про користувача
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Якщо користувач не знайдений, повертаємо статус 401
            }
        }catch(Exception ex)
        {
            return ResponseEntity.ok("token");
        }


    }
    @PostMapping("/signIn")
    public ResponseEntity<?> userSignIn(@RequestBody UserLoginRequest ulR) {
        try {
            UserLoginResponse userLoginResponse = authService.signIn(ulR);
            return ResponseEntity.ok(userLoginResponse);
        } catch(Exception ex)
        {
            return ResponseEntity.ok().body("Error");
        }
    }
}
