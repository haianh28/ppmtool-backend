package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Vui lòng nhập tên project")

    private String projectName;
    @NotBlank(message = "Vui long không để trống Identifier")
    @Size(min = 4 , max = 10, message = "Vui lòng nhập 4 đến 10 ký tự")
    @Column(updatable = false,unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Vui lòng không để trống description")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date star_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_At;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date update_At;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;
// bên kia dùng mappedBy thì bên này phải dùng @joinColumn de bieu thi moi quan he 2 chieu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private Users users;

    private  String projectLeader;

    @PrePersist
    public void onCreate(){
        this.create_At=new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.update_At=new Date();
    }

    public Project() {

    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStar_date() {
        return star_date;
    }

    public void setStar_date(Date star_date) {
        this.star_date = star_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
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
