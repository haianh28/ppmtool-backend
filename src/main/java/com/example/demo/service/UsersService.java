package com.example.demo.service;

import com.example.demo.domain.Users;
import com.example.demo.exception.UsernameAlreadyExitstsException;
import com.example.demo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Users saveUsers(Users users) {
        try {
            users.setPassword(bCryptPasswordEncoder.encode((users.getPassword())));
            users.setUsername(users.getUsername());
            // set cho nó rỗng để nó không hiển thị pass lên
            users.setConfirmPassword("");
            return usersRepo.save(users);
        } catch (Exception e) {
            e.printStackTrace();
            // ném ra ngoại lệ
            throw new UsernameAlreadyExitstsException("Username: " + users.getUsername() + " đã tồn tại");
        }
    }

}
