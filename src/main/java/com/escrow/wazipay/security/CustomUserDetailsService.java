package com.escrow.wazipay.security;

import com.escrow.wazipay.user.dao.UserDao;
import com.escrow.wazipay.user.entity.UserAccount;
import com.escrow.wazipay.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user by phone number
        UserAccount user = userDao.getUserByPhoneNumber(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with phone number: " + username);
        }

        // Convert roles to Spring Security GrantedAuthority objects
        Collection<GrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        // Return the Spring Security UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPin(),
                authorities
        );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
    }
}
