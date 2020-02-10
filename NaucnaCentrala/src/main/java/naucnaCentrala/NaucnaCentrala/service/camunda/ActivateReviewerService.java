package naucnaCentrala.NaucnaCentrala.service.camunda;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.Authority;
import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.repository.AccountRepository;

@Service
public class ActivateReviewerService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	AccountRepository accountRepository;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("set is recezent");
		Long id = (Long) execution.getVariable("user_id");
		Account user = accountRepository.getOne(id);
		user.setReviewer(true);
		Set<Authority> authorities = user.getAuthorities();
		authorities.add(new Authority("REVIEWER"));
		user.setAuthorities(authorities);
		accountRepository.save(user);
	}
}
