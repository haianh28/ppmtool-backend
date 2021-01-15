package com.example.demo.service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.exception.ProjectIdException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.Backlogrepository;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;
    @Autowired
    private Backlogrepository backlogrepository;

    public Project saveOrUpdateProject(Project project) {
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

            return projectRepo.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }

    public Iterable<Project> findAll(){
       return projectRepo.findAll();
    }

    public Project findById(Long id){
        return projectRepo.findByProjectId(id);
    }
    public void delete(Long id){
        try {
            Project project = findById(id);
            projectRepo.delete(project);
        }catch (Exception e){
            throw new ProjectNotFoundException("Not Found: "+id+" in Project");
        }
    }
}
