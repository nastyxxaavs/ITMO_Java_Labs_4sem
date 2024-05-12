package org.example.labthree.controllerLayer;

import org.example.labthree.dataAccessLayer.dao.RoleDao;
import org.example.labthree.dataAccessLayer.dao.UserDao;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.entities.role.RoleBase;
import org.example.labthree.dataAccessLayer.entities.user.LoginUserDto;
import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Collections;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userRepository;
    private final RoleDao roleRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                          UserDao userRepo, RoleDao roleRepo) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody LoginUserDto loginDto) {
        if (userRepository.existsByUsername(loginDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserBase user = new UserBase();
        user.setUsername(loginDto.getUsername());
        user.setPassword(passwordEncoder.encode((loginDto.getPassword())));

        RoleBase roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(roles));

        OwnerBase owner = new OwnerBase();
        owner.setId(user.getId());
        owner.setName(user.getUsername());
        //owner.setUser(user);

        user.setOwner(owner);

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}