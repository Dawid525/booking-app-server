package com.dpap.bookingapp.auth.userdetails;

import com.dpap.bookingapp.users.UserDatabase;
import com.dpap.bookingapp.users.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserDatabase userDatabase;

    public UserDetailsServiceImpl(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDatabase.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return UserDetailsImpl.build(user);
    }
}
