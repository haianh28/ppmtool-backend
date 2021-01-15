package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true) //không phải khóa chính nhưng là duy nhất
    private String username;
    @NotBlank(message = "Please enter your full name")
    private String fullName;
    @NotBlank(message = "Password field is required")
    private String password;
    @Transient  // tạo 1 trường ảo không lưu vao database
    private String confirmPassword;
    private Date create_At;
    private Date update_At;

    //OneToMany with Project
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "users", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public Users() {
    }

    public Users(Long id, @Email(message = "Username needs to be an email") @NotBlank(message = "username is required") String username, @NotBlank(message = "Please enter your full name") String fullName, @NotBlank(message = "Password field is required") String password, String confirmPassword, Date create_At, Date update_At, List<Project> projects) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.create_At = create_At;
        this.update_At = update_At;
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { //trả về true nếu tài khoản của người dùng chưa hết hạn
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {//trả về true nếu người dùng chưa bị khóa
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {//trả về true nếu chứng thực (mật khẩu) của người dùng chưa hết hạn
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() { //trả về true nếu người dùng đã được kích hoạt
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //trả về danh sách các quyền của người dùng
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

}
