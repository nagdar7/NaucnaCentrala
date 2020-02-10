package naucnaCentrala.NaucnaCentrala.controller;

import org.camunda.bpm.engine.FormService;
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
import naucnaCentrala.NaucnaCentrala.model.TokenDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/magazine")
public class MagazineController {

    @Autowired
    FormService formService;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MagazineService magazineService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Value("${client.url}")
    private String clientUrl;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(path = "", produces = "application/json")
    public @ResponseBody FormFieldsDto startProcess() {
        log.info("start create new magazine process and get form fields");
        // start process
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("proces_kreiranja_casopisa");
        // get first task
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        // taskService.addCandidateUser(task.getId(),
        // Utils.getCurrentUserLogin().get());

        // get task form
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto createNew(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId)
            throws Exception {
        log.info("createNew magazine, dto: {}", dto);
        HashMap<String, Object> map = Utils.mapListToDto(dto);

        List<String> naucneOblasti = Arrays.asList(((String) map.get("naucne_oblasti")).split(","));
        if (naucneOblasti.size() <= 0) {
            throw new Exception("Invalid value submitted for form field 'naucne_oblasti': minlength set to '1'");
        }
        map.remove("naucne_oblasti");

        // list all running/unsuspended instances of the process
        // ProcessInstance processInstance =
        // runtimeService.createProcessInstanceQuery()
        // .processDefinitionKey("Process_1")
        // .active() // we only want the unsuspended process instances
        // .list().get(0);

        // Task task =
        // taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return null; // new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String processInstanceId = task.getProcessInstanceId();
        if (processInstanceId == null) {
            return null; // new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        runtimeService.setVariable(processInstanceId, "create_new_magazine", dto);
        formService.submitTaskForm(taskId, map);

        // get next task
        task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        // get task form
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }

    @PostMapping(path = "/roles/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity asignRoles(@RequestBody List<FormSubmissionDto> dto,
            @PathVariable String taskId) throws Exception {
        log.info("asign roles to magazine, dto: {}", dto);
        HashMap<String, Object> map = Utils.mapListToDto(dto);

        // List<String> naucneOblasti = Arrays.asList(((String)
        // map.get("naucne_oblasti")).split(","));
        // if (naucneOblasti.size() <= 1) {
        // throw new Exception("Invalid value submitted for form field 'naucne_oblasti':
        // minlength set to '1'");
        // }
        // map.remove("naucne_oblasti");

        // list all running/unsuspended instances of the process
        // ProcessInstance processInstance =
        // runtimeService.createProcessInstanceQuery()
        // .processDefinitionKey("Process_1")
        // .active() // we only want the unsuspended process instances
        // .list().get(0);

        // Task task =
        // taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return null; // new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String processInstanceId = task.getProcessInstanceId();
        if (processInstanceId == null) {
            return null; // new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        runtimeService.setVariable(processInstanceId, "asign_roles_to_magazine", dto);
        formService.submitTaskForm(taskId, map);

        return new ResponseEntity(HttpStatus.OK);
        // get next task
        // task =
        // taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        // // get task form
        // TaskFormData tfd = formService.getTaskFormData(task.getId());
        // List<FormField> properties = tfd.getFormFields();

        // return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public @ResponseBody ResponseEntity getAllMagazines() {
        log.info("request to get all magazines");
        List<Magazine> magazines = magazineService.findAll();
        return new ResponseEntity(magazines, HttpStatus.OK);
    }

    // @PostMapping(path = "/login/{taskId}", produces = "application/json")
    // public ResponseEntity login(@RequestBody List<FormSubmissionDto> dto,
    // @PathVariable String taskId) {
    // HashMap<String, Object> map = Utils.mapListToDto(dto);
    // log.info("login, dto: {}", map.toString());
    // try {
    // formService.submitTaskForm(taskId, map);
    // } catch (Error e) {
    // e.printStackTrace();
    // }

    // LoginDTO loginDTO = new LoginDTO((String) map.get("username"), (String)
    // map.get("password"));
    // try {
    // UsernamePasswordAuthenticationToken token = new
    // UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
    // loginDTO.getPassword());
    // authenticationManager.authenticate(token);
    // Account account = this.accountService.findByUsername(loginDTO.getUsername());

    // UserDetails details =
    // userDetailsService.loadUserByUsername(account.getUsername());

    // TokenDTO userToken = new TokenDTO(jwtUtils.generateToken(details));

    // return new ResponseEntity<>(userToken, HttpStatus.OK);
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // return new ResponseEntity(HttpStatus.NOT_FOUND);
    // }
    // }

    // @PostMapping(path = "/process/{processInstanceId}", produces =
    // "application/json")
    // public @ResponseBody ResponseEntity saveData(@RequestBody
    // List<FormSubmissionDto> dto,
    // @PathVariable String processInstanceId) {
    // RestTemplate restTemplate = new RestTemplate();

    // // get task
    // String taskUrl = path + "task?processInstanceId=" + processInstanceId;
    // ResponseEntity<TaskDto[]> resultTask = restTemplate.getForEntity(taskUrl,
    // TaskDto[].class);
    // TaskDto task = resultTask.getBody()[0];

    // // submit task form
    // String submitUrl = path + "/task/" + task.getId() + "/submit-form";
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // HttpEntity request = new HttpEntity<>(JsonFromList(dto), headers);
    // restTemplate.postForEntity(submitUrl, request, String.class);

    // // get all tasks
    // resultTask = restTemplate.getForEntity(taskUrl, TaskDto[].class);
    // if (resultTask.getBody().length == 0)
    // return new ResponseEntity<>(HttpStatus.OK);
    // else {
    // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }
    // }

    // @GetMapping(value = "/activate/{processId}", produces = "application/json")
    // public @ResponseBody RedirectView mailConfirmation(@PathVariable String
    // processId) {
    // log.info("mailConfirmation, pid: {}", processId);
    // try {
    // Task task =
    // taskService.createTaskQuery().processInstanceId(processId).list().get(0);
    // taskService.complete(task.getId());
    // return new RedirectView(clientUrl + "?success=true");
    // } catch (Exception e) {
    // return new RedirectView(clientUrl + "?success=false");
    // }
    // }

    // public static String JsonFromList(List<FormSubmissionDto> list) {
    // String json = "{ \"variables\" : {";
    // for (FormSubmissionDto dto : list) {
    // json += "\"" + dto.getFieldId() + "\": { \"value\":\"" + dto.getFieldValue()
    // + "\"},";
    // }
    // json = json.substring(0, json.length() - 2);
    // json += "}}}";
    // return json;
    // }

}
