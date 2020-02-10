package naucnaCentrala.NaucnaCentrala.service.camunda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
public class CreateNewMagazineService implements JavaDelegate {

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
		log.info("Create new magazine execute");

		List<FormSubmissionDto> newMagazineFormSubmission = (List<FormSubmissionDto>) execution
				.getVariable("create_new_magazine");
		HashMap<String, Object> newMagazineMap = Utils.mapListToDto(newMagazineFormSubmission);
		List<FormSubmissionDto> rolesFormSubmission = (List<FormSubmissionDto>) execution
				.getVariable("asign_roles_to_magazine");
		HashMap<String, Object> rolesMap = Utils.mapListToDto(rolesFormSubmission);

		Account account = accountRepository.findByUsername(Utils.getCurrentUserLogin().get());

		List<Account> fieldEditors = new ArrayList<>();
		try {
			Arrays.asList(((String) rolesMap.get("recezenti")).split(",")).forEach(x -> {
				Account acc = accountRepository.findByUsername(x);
				if (acc != null) {
					fieldEditors.add(acc);
				}
			});
		} catch (Exception e) {
			log.error("failed to get recezenti");
		}
		List<Account> reviewers = new ArrayList<>();
		try {
			Arrays.asList(((String) rolesMap.get("urednici")).split(",")).forEach(x -> {
				Account acc = accountRepository.findByUsername(x);
				if (acc != null) {
					reviewers.add(acc);
				}
			});
		} catch (Exception e) {
			log.error("failed to get urednici");
		}

		ScientificCommittee scientificCommittee = new ScientificCommittee();
		scientificCommittee.setChiefEditor(account);
		scientificCommittee.setFieldEditors(fieldEditors);
		scientificCommittee = scientificCommitteeRepository.save(scientificCommittee);

		Magazine magazine = new Magazine();
		magazine.setActive(false);
		UUID uuid = UUID.randomUUID();
		magazine.setIssn(uuid.toString());
		magazine.setBillingType(BillingType.valueOf((String) newMagazineMap.get("nacin_placanja")));
		magazine.setTitle((String) newMagazineMap.get("title"));
		magazine.setScienceFieldList(Arrays.asList(((String) newMagazineMap.get("naucne_oblasti")).split(",")).stream()
				.map(ScienceField::valueOf).collect(Collectors.toList()));
		magazine.setScientificCommittee(scientificCommittee);
		magazine.setReviewers(reviewers);
		magazine = magazineRepository.save(magazine);

		log.info("magazine save success, id: {}", magazine.getId());
		execution.setVariable("magazine_id", magazine.getId());
	}
}
