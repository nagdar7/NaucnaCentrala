package naucnaCentrala.NaucnaCentrala.service.camunda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.Authority;
import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;
import naucnaCentrala.NaucnaCentrala.repository.AccountRepository;

@Service
public class RegisterUserService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	AccountRepository accountRepository;

	Logger log = LoggerFactory.getLogger(this.getClass());

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Register user service execute");

		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");

		Account user = new Account();
		user.setActive(false);
		user.setReviewer(false);
		Set<Authority> authorities = new HashSet();
		authorities.add(new Authority("AUTHOR"));
		user.setAuthorities(authorities);
		for (FormSubmissionDto formField : registration) {
			if (formField.getFieldId().equals("ime")) {
				user.setFirstName((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("prezime")) {
				user.setLastName((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("grad")) {
				user.setCity((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("drzava")) {
				user.setCountry((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("titula")) {
				user.setTitle((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("email")) {
				user.setEmail((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("naucne_oblasti")) {
				String scienceFieldList = (String) formField.getFieldValue();
				user.setScienceFieldList(Arrays.asList(scienceFieldList.split(",")).stream().map(ScienceField::valueOf)
						.collect(Collectors.toList()));
				// scienceFieldList.stream().map(ScienceField::valueOf).collect(Collectors.toList()));
			}
			if (formField.getFieldId().equals("username")) {
				user.setUsername((String) formField.getFieldValue());
			}
			if (formField.getFieldId().equals("password")) {
				user.setPassword(passwordEncoder.encode((String) formField.getFieldValue()));
			}
			if (formField.getFieldId().equals("recezent")) {
				user.setReviewer(formField.getFieldValue().equals("true"));
			}
		}
		if (isEmailValid(user.getEmail())) {
			log.info("email valid");
			execution.setVariable("podaci_ok", true);
			try {
				user = accountRepository.save(user);
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
