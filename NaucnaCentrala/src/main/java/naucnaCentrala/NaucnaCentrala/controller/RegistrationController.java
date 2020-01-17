package naucnaCentrala.NaucnaCentrala.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.cmd.GetDeploymentResourceNamesCmd;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import naucnaCentrala.NaucnaCentrala.model.FormFieldsDto;
import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.model.TaskDto;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	@Autowired
	IdentityService identityService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	TaskService taskService;

	@Autowired
	FormService formService;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping(produces = "application/json")
	public @ResponseBody FormFieldsDto get() {
		log.info("start process and get form fields");
		// provera da li korisnik sa id-jem pera postoji
		// List<User> users = identityService.createUserQuery().userId("pera").list();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("proces_registracije_korisnika");

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for (FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}

		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	}

	// @GetMapping(path = "/get/tasks/{processInstanceId}", produces =
	// "application/json")
	// public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String
	// processInstanceId) {

	// List<Task> tasks =
	// taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	// List<TaskDto> dtos = new ArrayList<TaskDto>();
	// for (Task task : tasks) {
	// TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
	// dtos.add(t);
	// }

	// return new ResponseEntity(dtos, HttpStatus.OK);
	// }

	@PostMapping(path = "/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity postFormSubmission(@RequestBody List<FormSubmissionDto> dto,
			@PathVariable String taskId) {
		log.info("postFormSubmission, dto: {}", dto);
		System.out.println("posted data:" + dto.toString());
		HashMap<String, Object> map = this.mapListToDto(dto);

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
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String processInstanceId = task.getProcessInstanceId();
		if (processInstanceId == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		runtimeService.setVariable(processInstanceId, "registration", dto);
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/activate/{processId}", produces = "application/json")
	public @ResponseBody ResponseEntity mailConfirmation(@PathVariable String processId) {
		log.info("mailConfirmation, pid: {}", processId);
		Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
		taskService.complete(task.getId());
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

	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (FormSubmissionDto temp : list) {
			map.put(temp.getFieldId(), temp.getFieldValue());
		}

		return map;
	}
}