package com.example.demo.Validate;

import com.example.demo.domain.Users;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidate implements Validator {  // validator thuộc org.springframework.validation.Validator
    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Users user = (Users) object;

        if(user.getPassword().length() <6){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            // giá trị bị từ chối
            errors.rejectValue("confirmPassword","Match", "password nhập không trùng nhau !");

        }

        //confirmPassword



    }
}
