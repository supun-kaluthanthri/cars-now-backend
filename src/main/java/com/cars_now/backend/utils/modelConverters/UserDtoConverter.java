package com.cars_now.backend.utils.modelConverters;

import com.cars_now.backend.dto.Role;
import com.cars_now.backend.dto.Users;
import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.UserCreate;
import com.cars_now.backend.repository.RoleRepository;
import com.cars_now.backend.utils.ValidationConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDtoConverter {

    @Autowired
    RoleRepository roleRepository;

    @Bean
    PasswordEncoder getPasswordEncoder(){

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return new BCryptPasswordEncoder();
    }


    public Users userCreateRequestToDto(final UserCreate user){
        Users repoUser = new Users();
        List<Role> roles =  new ArrayList<>();

        repoUser.setUsername(user.getEmail());
        repoUser.setEmail(user.getEmail());
        repoUser.setPassword(getPasswordEncoder().encode(user.getPassword()));

        //getting roles for role ids in the list
        user.getRoleIds().forEach(roleId -> {
            Optional<Role> role =  roleRepository.findById(roleId);

            //if role is not found
            if(!role.isPresent()) {
                try {
                    throw new NotFoundException(ValidationConst.ROLE_NOT_FOUND, ValidationConst.ROLE_NOT_FOUND.message());
                } catch (NotFoundException e) {

                }
            }

            roles.add(role.get());
        });

        repoUser.setRoles(roles);
        repoUser.setEnabled(true);
        repoUser.setAccountNonExpired(true);
        repoUser.setAccountNonLocked(true);
        repoUser.setCredentialsNonExpired(true);

        return repoUser;

    }




}
