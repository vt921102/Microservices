package io.toanlv.springsecurityjpa.services;

import io.toanlv.springsecurityjpa.models.MyUserDetails;
import io.toanlv.springsecurityjpa.models.User;
import io.toanlv.springsecurityjpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
       user.orElseThrow(()->new UsernameNotFoundException("Not found: "+ username));

        return user.map(MyUserDetails::new).get();
    }
}
