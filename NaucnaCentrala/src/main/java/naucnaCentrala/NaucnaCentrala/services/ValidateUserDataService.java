package naucnaCentrala.NaucnaCentrala.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.model.User;
import naucnaCentrala.NaucnaCentrala.repository.UserRepository;

@Service
public class ValidateUserDataService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	UserRepository userRepository;

	Logger log = LoggerFactory.getLogger(this.getClass());

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("execute");

		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");

		User user = new User();
		for (FormSubmissionDto formField : registration) {
			if (formField.getFieldId().equals("ime")) {
				user.setIme(formField.getFieldValue());
			}
			if (formField.getFieldId().equals("prezime")) {
				user.setPrezime(formField.getFieldValue());
			}
			if (formField.getFieldId().equals("grad")) {
				user.setGrad(formField.getFieldValue());
			}
			if (formField.getFieldId().equals("drzava")) {
				user.setDrzava(formField.getFieldValue());
			}
			if (formField.getFieldId().equals("titula")) {
				user.setTitula(formField.getFieldValue());
			}
			if (formField.getFieldId().equals("email")) {
				user.setEmail(formField.getFieldValue());
			}
			// if (formField.getFieldId().equals("naucne_oblasti")) {
			// user.setNaucneOblasti(formField.getFieldValue().split(","));
			// }
			if (formField.getFieldId().equals("username")) {
				user.setUsername(formField.getFieldValue());
			}
			if (formField.getFieldId().equals("password")) {
				user.setPassword(passwordEncoder.encode(formField.getFieldValue()));
			}
			if (formField.getFieldId().equals("recezent")) {
				user.setRecezent(formField.getFieldValue().equals("true"));
			}
		}
		if (isEmailValid(user.getEmail())) {
			log.info("email valid");
			execution.setVariable("podaci_ok", true);
			try {
				user = userRepository.save(user);
				execution.setVariable("user_id", user.getId());
				log.info("user save success, id: {}", user.getId());
			} catch (Exception e) {
				log.info("data not valid");
				execution.setVariable("podaci_ok", false);
			}
		} else {
			log.info("email not valid");
			execution.setVariable("podaci_ok", false);
		}
	}

	private boolean isEmailValid(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
