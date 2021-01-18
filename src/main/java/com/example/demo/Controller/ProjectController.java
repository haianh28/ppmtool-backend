package com.example.demo.Controller;

import com.example.demo.domain.Project;
import com.example.demo.repository.MapValidationService;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/project")
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationService mapValidationService;
    @PostMapping
    public HttpEntity<?> create (@Valid @RequestBody Project project, BindingResult result,Principal pc){
     ResponseEntity<?> erroMap = mapValidationService.mapValidation(result);
     if(erroMap!=null) {
         return erroMap;
     }
        Project project1 = projectService.saveOrUpdateProject(project,pc.getName());
        return  new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public HttpEntity<Iterable<Project>> findAll(Principal pc){
        return  new ResponseEntity<>(projectService.findAll(pc.getName()), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public HttpEntity<Project>findById(@PathVariable("id") Long Id,Principal pc){
        Project project  = projectService.findById(Id, pc.getName());
        return new ResponseEntity<Project>(project,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") Long Id,Principal pc){
        projectService.delete(Id, pc.getName());
        return  new ResponseEntity<String>("Project "+Id+" was delete successfuly",HttpStatus.OK);
    }
}
