package naucnaCentrala.NaucnaCentrala.service.camunda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.Authority;
import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.model.Magazine;
import naucnaCentrala.NaucnaCentrala.model.ScientificCommittee;
import naucnaCentrala.NaucnaCentrala.model.enumeration.BillingType;
import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;
import naucnaCentrala.NaucnaCentrala.repository.AccountRepository;
import naucnaCentrala.NaucnaCentrala.repository.MagazineRepository;
import naucnaCentrala.NaucnaCentrala.repository.ScientificCommitteeRepository;
import naucnaCentrala.NaucnaCentrala.utils.Utils;

@Service
public class SelectChiefEditorService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	MagazineRepository magazineRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ScientificCommitteeRepository scientificCommitteeRepository;

	Logger log = LoggerFactory.getLogger(this.getClass());

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("SelectChiefEditorService execute");

		Long magazineId = (Long) execution.getVariable("magazine_id");
		Magazine magazine = magazineRepository.findOneById(magazineId);
		if (magazine == null) {
			throw new Exception("magazine not found");
		}
		Account chiefEditor = magazine.getScientificCommittee().getChiefEditor();
		// execution.setVariable("glavni_urednik", chiefEditor);
		execution.setVariable("glavni_urednik_email", chiefEditor.getEmail());
		execution.setVariable("glavni_urednik_login", chiefEditor.getUsername());

		log.info("SelectChiefEditorService selected: {}, email: {}", chiefEditor.getUsername(), chiefEditor.getEmail());
	}
}
