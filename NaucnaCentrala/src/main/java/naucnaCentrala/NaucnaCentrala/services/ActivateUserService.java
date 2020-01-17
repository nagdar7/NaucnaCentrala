package naucnaCentrala.NaucnaCentrala.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.model.User;
import naucnaCentrala.NaucnaCentrala.repository.UserRepository;

@Service
public class ActivateUserService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	UserRepository userRepository;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("activate user");
		Long id = (Long) execution.getVariable("user_id");
		User user = userRepository.getOne(id);
		user.setActivated(true);
		execution.setVariable("recezent", user.isRecezent());
		// set user not recezent for now
		user.setRecezent(false);
		userRepository.save(user);
		org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser("");
		camundaUser.setId(user.getUsername());
		camundaUser.setEmail(user.getEmail());
		camundaUser.setFirstName(user.getIme());
		camundaUser.setLastName(user.getPrezime());
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
		for (FormSubmissionDto formField : registration) {
			if (formField.getFieldId().equals("password")) {
				camundaUser.setPassword(formField.getFieldValue());
			}
		}
		identityService.saveUser(camundaUser);
	}
}
