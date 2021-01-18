package com.example.demo.service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.exception.ProjectIdException;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repository.Backlogrepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private ProjectTaskRepository taskRepositor;
    @Autowired
    private Backlogrepository backlogrepository;
    @Autowired
    private ProjectRepository projectRepository;

    public Project check(String id,String name){
        Project pr = projectRepository.findByProjectIdentifier(id);
        if(pr==null){
            throw  new ProjectNotFoundException("Project witd ID: "+id+" does not exist");
        }
        if(!pr.getProjectLeader().equals(name)){
            throw new ProjectNotFoundException("Tài khoản không tồn tại !");
        }
        return pr;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask,String name) {
        // Exception : project not foud
Project pr = check(projectIdentifier,name);
        try {
            // PTs to be added to a specific project , project !=null, BL exists
            Backlog backlog = pr.getBacklog();
            //set the BL to pt
            projectTask.setBacklog(backlog);
            // we want out project sequence to be like this : IDPRO-1, IDPRO-2 ....
            Integer backlogSequence = backlog.getPTSequece();
            backlogSequence++;
            backlog.setPTSequece(backlogSequence);
// add sequence to project task
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            // INTITAL priority when priority null
            if (projectTask.getPriority() == null || projectTask.getPriority()==0) { // in the future we need projectTask.getProority() ==0 to handle the form
                projectTask.setPriority(3);
            }
            // INTITAL status when status null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }
            return taskRepositor.save(projectTask);
        }catch (Exception e){
            throw  new ProjectNotFoundException("Project not found");
        }
    }

   public  Iterable<ProjectTask> findProjectTaskById(String id,String name) {
       Project project = check(id,name);
        return taskRepositor.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findByProjectSequence(String backlog_id,String pt_id,String name){
        // make sure we are searching on the right backlog
        Backlog backlog = check(backlog_id,name).getBacklog();
        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }
        // make sure that our task exists
        ProjectTask projectTask =taskRepositor.findByProjectSequence(pt_id);
        if(projectTask==null){
            throw new ProjectNotFoundException("Project Task : "+pt_id+" Not Found");
        }
        // make sure that the backlog/project id in the path coresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("ProjectTask : '"+pt_id+"' does not exist in project: '"+backlog_id);
        }
        return  projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatetask,String backlog_id,String pt_id,String name){
        ProjectTask projectTask = findByProjectSequence(backlog_id, pt_id,name);

        projectTask = updatetask;

        return taskRepositor.save(projectTask);
    }
    // update project task

    public void deleteByProjectSequence(String backlog_id,String pt_id,String name){
        ProjectTask projectTask = findByProjectSequence(backlog_id, pt_id,name);
       // delete Backlog in ProjectTask
//       Backlog backlog =  projectTask.getBacklog();  // get backlog in projecttask
//        List<ProjectTask> list  = backlog.getProjectTasks(); // get list projectTask in backlog
//        list.remove(projectTask);
//        backlogrepository.save(backlog);

        taskRepositor.delete(projectTask);

    }
}
