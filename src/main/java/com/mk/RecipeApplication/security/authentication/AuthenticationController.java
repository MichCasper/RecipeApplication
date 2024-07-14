package com.mk.RecipeApplication.security.authentication;

import com.mk.RecipeApplication.security.JwtGenerator;
import com.mk.RecipeApplication.security.role.Role;
import com.mk.RecipeApplication.security.role.RoleRepository;
import com.mk.RecipeApplication.security.user.UserEntity;
import com.mk.RecipeApplication.security.user.UserRepository;
import com.mk.RecipeApplication.security.userRoles.UserRole;
import com.mk.RecipeApplication.security.userRoles.UserRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    UserRoleRepository userRoleRepository,
                                    PasswordEncoder passwordEncoder,
                                    JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginObject loginObject) {
        try {
            Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(loginObject.getUsername(), loginObject.getPassword());
            Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            String token = jwtGenerator.generateToken(authenticationResult);
            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterObject registerObject) {
        if (userRepository.findByUsername(registerObject.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is taken.", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerObject.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registerObject.getPassword()));

        Role role = roleRepository.findByName("USER");
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserRole userRole = new UserRole();
        userRole.setUserId(savedUserEntity.getId());
        userRole.setRoleId(role.getId());
        userRoleRepository.save(userRole);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}