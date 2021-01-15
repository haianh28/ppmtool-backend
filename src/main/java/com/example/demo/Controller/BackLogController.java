package com.example.demo.Controller;

import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.repository.MapValidationService;
import com.example.demo.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/backlog")
@CrossOrigin
public class BackLogController {

    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private MapValidationService mapValidationService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBackLog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id){
        ResponseEntity<?> erro = mapValidationService.mapValidation(result);
        if(erro!=null){
            return  erro;
        }
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id,projectTask);

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> findById(@PathVariable String backlog_id){
        return  projectTaskService.findProjectTaskById(backlog_id);
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id ){
        ProjectTask projectTask = projectTaskService.findByProjectSequence(backlog_id,pt_id);
        return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,BindingResult result,
                                               @PathVariable String backlog_id,@PathVariable String pt_id ){
        ResponseEntity<?> erro = mapValidationService.mapValidation(result);
        if(erro!=null){
            return erro;
        }
        ProjectTask update = projectTaskService.updateByProjectSequence(projectTask,backlog_id,pt_id);

        return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id ){
       projectTaskService.deleteByProjectSequence(backlog_id,pt_id);
        return new ResponseEntity<String>("Project Task "+pt_id+" was delete successfuly",HttpStatus.OK);
    }
}
