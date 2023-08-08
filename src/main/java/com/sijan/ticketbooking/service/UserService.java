package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.entity.Role;
import com.sijan.ticketbooking.entity.User;
import com.sijan.ticketbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getCurrentUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail( userDetails.getUsername());
    }

    public boolean isAdmin() {
        Optional<User> optionalUser = getCurrentUser();
        if(optionalUser.isPresent()) {
            Set<Role> roleList = optionalUser.get().getRoles();
            List<String> userRoles = roleList.stream().map(Role::getRole).toList();
            return userRoles.contains("ADMIN");
        }
        return false;
    }
}
