package org.example.labthree.securityLayer;

import lombok.extern.slf4j.Slf4j;
import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.example.labthree.serviceLayer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Autowired
    public JwtUserDetailsService(UserService service){
        this.userService = service;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserBase user = userService.findByUserName(name);
        if (user == null){
           throw  new UsernameNotFoundException("User with name: " + name + "not found");
        }
        return null;
    }


}
