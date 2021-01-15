package com.example.demo.service;

import com.example.demo.domain.Users;
import com.example.demo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = usersRepo.findByUsername(s);
        if(user==null){
            new UsernameNotFoundException("username không tồn tại !");
        }
        return user;
    }
    @Transactional // dùng để rollback ngăn xẩy ra lỗi mà vẫn thực hiện 1 hành động
    public Users findById(Long id){
    Users us = usersRepo.getById(id);
    if(us==null){
        new UsernameNotFoundException("Tài khoản không tồn tại !");
    }
        return us;
    }
}
