package naucnaCentrala.NaucnaCentrala.service.camunda;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.repository.AccountRepository;

@Service
public class ActivateUserService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	AccountRepository accountRepository;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("activate user");
		Long id = (Long) execution.getVariable("user_id");

		Account user = accountRepository.getOne(id);
		user.setActive(true);
		execution.setVariable("recezent", user.getReviewer());
		// set user not recezent for now
		user.setReviewer(false);
		user = accountRepository.save(user);

		org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser("");
		camundaUser.setId(user.getUsername());
		camundaUser.setEmail(user.getEmail());
		camundaUser.setFirstName(user.getFirstName());
		camundaUser.setLastName(user.getLastName());
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
		for (FormSubmissionDto formField : registration) {
			if (formField.getFieldId().equals("password")) {
				camundaUser.setPassword((String) formField.getFieldValue());
			}
		}
		identityService.saveUser(camundaUser);
	}
}
