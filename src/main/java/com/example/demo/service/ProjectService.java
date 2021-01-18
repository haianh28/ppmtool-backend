package com.example.demo.service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.Users;
import com.example.demo.exception.ProjectIdException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.Backlogrepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;
    @Autowired
    private Backlogrepository backlogrepository;

    @Autowired
    private UsersRepo usersRepo;

    public Project saveOrUpdateProject(Project project,String name) {
      if(project.getId()!=null){
          Project pj = projectRepo.findByProjectIdentifier(project.getProjectIdentifier());
          if(pj!=null && !pj.getProjectLeader().equals(name)){
              throw new ProjectNotFoundException("Tài khoản không tồn tại");
          }
          if(pj==null){
              throw  new ProjectNotFoundException("Không tìm thấy dữ liệu !");
          }
      }
        // logic
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if (project.getId() != null) {
                project.setBacklog(backlogrepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            Users us = usersRepo.findByUsername(name);
            project.setUsers(us);
            project.setProjectLeader(name);

            return projectRepo.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }

    public Iterable<Project> findAll(String name){
       return projectRepo.findAllByProjectLeader(name);
    }

    public Project findById(Long id,String name){
        Project pro = projectRepo.findByProjectId(id);
        if(pro==null){
            throw new ProjectNotFoundException("không tìm thấy dữ liệu !");
        }
        if(!pro.getProjectLeader().equals(name)){
            throw new ProjectNotFoundException("Không tìm thấy tài khoản !");
        }
        return pro;
    }
    public void delete(Long id,String name){
            Project project = findById(id,name);
            projectRepo.delete(project);
    }
}
