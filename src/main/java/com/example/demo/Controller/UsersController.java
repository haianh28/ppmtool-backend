package com.example.demo.Controller;

import com.example.demo.Validate.LoginRequest;
import com.example.demo.Validate.UserValidate;
import com.example.demo.domain.Users;
import com.example.demo.payload.JWTLoginSucessReponse;
import com.example.demo.repository.MapValidationService;
import com.example.demo.sercurity.JwtTokenProvider;
import com.example.demo.sercurity.SecurityConstants;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private MapValidationService mapValidationService;
    @Autowired
    private UserValidate userValidate;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,BindingResult result){
        ResponseEntity<?> errors = mapValidationService.mapValidation(result);
        if(errors!=null){
            return errors;
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTLoginSucessReponse(true,jwt));
    }

    @PostMapping("/register")
    public ResponseEntity saveUser(@Valid @RequestBody Users users, BindingResult result){
        // validate password match
        userValidate.validate(users,result);
        ResponseEntity<?> error = mapValidationService.mapValidation(result);
        if(error!=null) {
            return error;
        }
        Users user = usersService.saveUsers(users);
        return new ResponseEntity<Users>(user,HttpStatus.CREATED);
    }
}
