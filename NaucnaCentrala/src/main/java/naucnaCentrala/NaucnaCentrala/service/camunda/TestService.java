package naucnaCentrala.NaucnaCentrala.service.camunda;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;

@Service
public class TestService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String var = "Pera";
		var = var.toUpperCase();
		execution.setVariable("input", var);
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
		System.out.println(registration);
		User user = identityService.newUser("");
		for (FormSubmissionDto formField : registration) {
			if (formField.getFieldId().equals("username")) {
				user.setId((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("password")) {
				user.setPassword((String) formField.getFieldValue());
			}
		}
		identityService.saveUser(user);
	}
}
