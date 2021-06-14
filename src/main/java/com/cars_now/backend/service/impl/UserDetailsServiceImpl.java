package com.cars_now.backend.service.impl;

import com.cars_now.backend.dto.AuthUserDetails;
import com.cars_now.backend.dto.Users;
import com.cars_now.backend.exception.NotFoundException;
import com.cars_now.backend.model.Role;
import com.cars_now.backend.model.User;
import com.cars_now.backend.model.UserCreate;
import com.cars_now.backend.repository.UsersDetailRepository;
import com.cars_now.backend.service.UserService;
import com.cars_now.backend.utils.DtoToResponseConverter;
import com.cars_now.backend.utils.RequestValidator;
import com.cars_now.backend.utils.ValidationConst;
import com.cars_now.backend.utils.modelConverters.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    @Autowired
    UsersDetailRepository userDetailRepository;

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    DtoToResponseConverter dtoToResponseConverter;

    @Autowired
    UserDtoConverter userDtoConverter;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Users> optionalUser = userDetailRepository.findByUsername(name);
        optionalUser.orElseThrow(()-> new UsernameNotFoundException("Username or password wrong"));

        UserDetails userDetails =  new AuthUserDetails(optionalUser.get());
        //check whether token is expired or not
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;
    }

    @Override
    public User createUser(UserCreate user) throws Exception {
        requestValidator.validateUserCreateRequest(user);

        final Users createdUser = userDetailRepository.save(userDtoConverter.userCreateRequestToDto(user));

        return dtoToResponseConverter.userDtoToUserResponse(createdUser);


    }

    @Override
    public User getUserById(Integer userId) throws Exception {
        Optional<Users> user = userDetailRepository.findById(userId);

        if(!user.isPresent()){
            throw new NotFoundException(ValidationConst.USER_NOT_FOUND,ValidationConst.USER_NOT_FOUND.message() +
                    ValidationConst.ATTRIBUTE_ID.message() + userId);
        }

        return dtoToResponseConverter.userDtoToUserResponse(user.get());
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        Optional<Users> user = userDetailRepository.findByEmail(email);

        requestValidator.isValidEmailAddress(email);

        if(!user.isPresent()){
            throw new NotFoundException(ValidationConst.USER_NOT_FOUND,ValidationConst.USER_NOT_FOUND.message() +
                    ValidationConst.EMAIL_ID.message() + email);
        }

        return dtoToResponseConverter.userDtoToUserResponse(user.get());
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        Optional<Users> user = userDetailRepository.findByUsername(username);

        if(!user.isPresent()){
            throw new NotFoundException(ValidationConst.USER_NOT_FOUND,ValidationConst.USER_NOT_FOUND.message() +
                    ValidationConst.USERNAME_ID.message() + username);
        }

        return dtoToResponseConverter.userDtoToUserResponse(user.get());
    }

    @Override
    public List<Role> getRolesByUserId(String username) throws Exception{
        Optional<Users> user = userDetailRepository.findByUsername(username);

        if(!user.isPresent()){
            throw new NotFoundException(ValidationConst.USER_NOT_FOUND,ValidationConst.USER_NOT_FOUND.message() +
                    ValidationConst.USERNAME_ID.message() + username);
        }

        List<Role> roleList = new ArrayList<>();
        user.get().getRoles().forEach(repoRole -> {
            Role role = new Role();
            role.setId(repoRole.getId());
            role.setName(repoRole.getName());
            roleList.add(role);
        });

        return roleList;
    }


}
