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
public class ActivateReviewerService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	UserRepository userRepository;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("set is recezent");
		Long id = (Long) execution.getVariable("user_id");
		User user = userRepository.getOne(id);
		user.setRecezent(true);
		userRepository.save(user);
	}
}
