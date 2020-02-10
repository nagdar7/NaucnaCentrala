package naucnaCentrala.NaucnaCentrala.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.repository.AccountRepository;
import naucnaCentrala.NaucnaCentrala.security.JWTUtils;
import naucnaCentrala.NaucnaCentrala.service.AccountService;
import naucnaCentrala.NaucnaCentrala.service.MagazineService;
import naucnaCentrala.NaucnaCentrala.utils.Utils;
import naucnaCentrala.NaucnaCentrala.model.FormFieldsDto;
import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.model.LoginDTO;
import naucnaCentrala.NaucnaCentrala.model.Magazine;
import naucnaCentrala.NaucnaCentrala.model.TaskDto;
import naucnaCentrala.NaucnaCentrala.model.TokenDTO;
import naucnaCentrala.NaucnaCentrala.model.enumeration.BillingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/task")
public class TaskController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    // @Autowired
    // private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    // @GetMapping(path = "/get", produces = "application/json")
    // public @ResponseBody FormFieldsDto get() {
    // // provera da li korisnik sa id-jem pera postoji
    // // List<User> users =
    // identityService.createUserQuery().userId("pera").list();
    // ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_1");

    // Task task =
    // taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

    // TaskFormData tfd = formService.getTaskFormData(task.getId());
    // List<FormField> properties = tfd.getFormFields();
    // for (FormField fp : properties) {
    // System.out.println(fp.getId() + fp.getType());
    // }

    // return new FormFieldsDto(task.getId(), pi.getId(), properties);
    // }

    @GetMapping(path = "", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> getAllUsersTasks() {
        log.info("getAllUsersTasks");
        String user = Utils.getCurrentUserLogin().get();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(user).list();
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
            dtos.add(t);
        }

        return new ResponseEntity(dtos, HttpStatus.OK);
    }

    @GetMapping(path = "/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getOneUsersTasks(@PathVariable String taskId) {
        log.info("getOneUsersTasks, taskId: {}", taskId);

        String user = Utils.getCurrentUserLogin().get();
        Task task = taskService.createTaskQuery().taskAssignee(user).taskId(taskId).singleResult();

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), task.getProcessInstanceId(), properties);
    }

    @PostMapping(path = "/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = Utils.mapListToDto(dto);

        String user = Utils.getCurrentUserLogin().get();
        Task task = taskService.createTaskQuery().taskAssignee(user).taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        // runtimeService.setVariable(processInstanceId, "registration", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // @PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
    // public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
    // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    // String processInstanceId = task.getProcessInstanceId();
    // String user = (String) runtimeService.getVariable(processInstanceId,
    // "username");
    // taskService.claim(taskId, user);
    // return new ResponseEntity<>(HttpStatus.OK);
    // }

    // @PostMapping(path = "/tasks/complete/{taskId}", produces =
    // "application/json")
    // public @ResponseBody ResponseEntity<List<TaskDto>> complete(@PathVariable
    // String taskId) {
    // Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
    // taskService.complete(taskId);
    // List<Task> tasks =
    // taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
    // List<TaskDto> dtos = new ArrayList<TaskDto>();
    // for (Task task : tasks) {
    // TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
    // dtos.add(t);
    // }
    // return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
    // }

    // private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // for (FormSubmissionDto temp : list) {
    // map.put(temp.getFieldId(), temp.getFieldValue());
    // }

    // return map;
    // }

}
