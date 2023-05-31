package com.coding.assignment.controllers;

import com.coding.assignment.common.Constant;
import com.coding.assignment.models.Role;
import com.coding.assignment.payload.request.RoleRequest;
import com.coding.assignment.payload.request.SignupRequest;
import com.coding.assignment.payload.response.UserResponse;
import com.coding.assignment.repository.RoleRepository;
import com.coding.assignment.security.services.UserDetailsImpl;
import com.coding.assignment.models.ERole;
import com.coding.assignment.models.User;
import com.coding.assignment.payload.request.LoginRequest;
import com.coding.assignment.payload.response.MessageResponse;
import com.coding.assignment.repository.UserRepository;
import com.coding.assignment.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

           // ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
//
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(item -> item.getAuthority())
//                    .collect(Collectors.toList());

            String accessToken = jwtUtils.generateAccessToken(userDetails);
            UserResponse response = new UserResponse(Constant.LOGIN_SUCCESS, accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse(Constant.USER_ALREADY_TAKEN));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse(Constant.EMAIL_ALREADY_TAKEN));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse(Constant.REGISTER_SUCCESS));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
    @PostMapping("/add-role")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleRequest roleRequest) {
        Role role = new Role();
        if(roleRequest.getName().equals(ERole.USER.name())){
            role.setName(ERole.USER);

        }else if(roleRequest.getName().equals(ERole.ADMIN.name())){
            role.setName(ERole.ADMIN);
        }
        if (roleRepository.existsRoleByName(role.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse(Constant.ROLE_ALREADY_EXISTS));
        }
        roleRepository.save(role);
        return ResponseEntity.ok(new MessageResponse(Constant.SAVE_ROLE_SUCCESS));
    }

}
