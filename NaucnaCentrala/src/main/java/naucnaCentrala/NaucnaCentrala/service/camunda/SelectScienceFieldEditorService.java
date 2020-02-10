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
public class SelectScienceFieldEditorService implements JavaDelegate {

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
		log.info("execute");

		execution.setVariable("odgovor_na_komentare", "");

		Long magazineId = (Long) execution.getVariable("magazine_id");
		Magazine magazine = magazineRepository.findOneById(magazineId);
		if (magazine == null) {
			throw new Exception("magazine not found");
		}

		List<FormSubmissionDto> dto = (List<FormSubmissionDto>) execution.getVariable("submitted_paper");
		HashMap<String, Object> map = Utils.mapListToDto(dto);

		Optional<Account> scienceFieldEditor = magazine.getScientificCommittee().getFieldEditors().stream()
				.filter(x -> x.getScienceFieldList().contains(ScienceField.valueOf((String) map.get("naucna_oblast"))))
				.findFirst();

		// // sistem automatski bira: odabrani_urednik
		// // ako nema - postavi glavnog_urednika
		String odabraniUrednik = null, odabraniUrednikEmail = null;
		if (!scienceFieldEditor.isPresent()) {
			odabraniUrednik = (String) execution.getVariable("glavni_urednik_login");
			odabraniUrednikEmail = (String) execution.getVariable("glavni_urednik_email");
		} else {
			odabraniUrednik = scienceFieldEditor.get().getUsername();
			odabraniUrednikEmail = scienceFieldEditor.get().getEmail();
		}
		execution.setVariable("odabrani_urednik", odabraniUrednik);
		execution.setVariable("odabrani_urednik_email", odabraniUrednikEmail);

		log.info("selected: {}, email: {}", odabraniUrednik, odabraniUrednikEmail);
	}
}
