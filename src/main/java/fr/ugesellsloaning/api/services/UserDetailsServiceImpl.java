package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //Optional<User> user = userRepository.findByEmail(email);
        Optional<User> user = userRepository.loginQuery(login);

        user.orElseThrow(() -> new UsernameNotFoundException(login + " not found."));

        return user.map(UserDetailsImpl::new).get();

    }
}
