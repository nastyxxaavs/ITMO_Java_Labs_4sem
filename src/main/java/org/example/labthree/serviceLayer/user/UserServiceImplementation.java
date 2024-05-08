package org.example.labthree.serviceLayer.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.labthree.dataAccessLayer.dao.RoleDao;
import org.example.labthree.dataAccessLayer.dao.UserDao;
import org.example.labthree.dataAccessLayer.entities.role.RoleBase;
import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImplementation implements UserService {

    private final UserDao userRepository;
    @Autowired
    public UserServiceImplementation(UserDao userRepo) {
        this.userRepository = userRepo;
    }

    @Override
    public boolean isCurrentUserEquals(String username) {
        String actualUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return actualUser.equals(username);
    }

    @Override
    public void addUser(UserBase user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserBase user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UserBase user) {
        userRepository.delete(user);
    }

    @Override
    public UserBase getUserById(UUID userEntityId) {
        return userRepository.findById(userEntityId).orElseThrow(() -> new UsernameNotFoundException("UseEntity with current id does not exists"));
    }

    @Override
    public UserBase getUserByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("UseEntity with current username does not exists"));
    }
}
/*@Service
@Slf4j
//@RequiredArgsConstructor
public class UserServiceImplementation implements UserService{
    private final UserDao userRepository;
    private final RoleDao roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImplementation(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.passwordEncoder = bCryptPasswordEncoder;
        this.userRepository = userDao;
        this.roleRepository = roleDao;
    }
    @Override
    public UserBase register(UserBase user){
        RoleBase roleUser = roleRepository.findByName("ROLE_USER");
        List<RoleBase> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        UserBase registerUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registerUser);
        return  registerUser;
    }
    @Override
    public List<UserBase> getAll(){
        List<UserBase> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }
    @Override
    public Optional<UserBase> findByUserName(String userName){
        Optional<UserBase> result = userRepository.findByUserName(userName);
        log.info("IN findByUserName - user: {} found by username: {}", result, userName);
        return result;
    }
    public UserBase findById(UUID id){
        UserBase result = userRepository.findById(id).orElse(null);
        if (result == null){
            log.warn("IN findById - no user found by id: {}", id);
        }
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }
    @Override
    public void delete(UUID id){
        userRepository.deleteById(id);
    }

}*/
