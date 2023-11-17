package com.medibook.service;



import com.medibook.entities.UserEntity;
import com.medibook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       UserEntity userEntity = userRepository.findByUsername(username);

       if (userEntity!=null) {
           List<GrantedAuthority> permisos = new ArrayList();
           GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().toString());
           permisos.add(p);
          return new User(userEntity.getUsername(),userEntity.getPassword(),permisos);
       }else{
       return null;
       }
    }
}

