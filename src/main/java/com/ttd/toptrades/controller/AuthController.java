//package com.example.famz.controller;
//
//import com.example.famz.entity.User;
//import com.example.famz.repository.UserRepository;
//import com.example.famz.services.UserService;
//import com.example.famz.utils.JwtTokenProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private JwtTokenProvider jwtUtil;
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public User register(@RequestBody User user) {
//
//        return userService.save(user);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//
//            // Retrieve the logged-in user details
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            User userEntity = userRepository.findByUsername(user.getUsername());
//            String token = jwtUtil.createToken(userDetails);
//            // Prepare response including user details and roles
//            if (userEntity != null) {
//                Set<String> roles = new HashSet<>();
//                userEntity.getRoles().forEach(role -> roles.add(role.getName())); // Assuming Role has a getName method
//
//                return ResponseEntity.ok(new LoginResponse(userEntity.getUsername(), roles,token));
//            } else {
//                return ResponseEntity.badRequest().body("User not found");
//            }
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
//    }
//
//}
