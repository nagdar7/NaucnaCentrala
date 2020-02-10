package naucnaCentrala.NaucnaCentrala.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormType;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.Magazine;
import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;
import naucnaCentrala.NaucnaCentrala.repository.MagazineRepository;

@Service
public class CreateMagazineAdminCheck implements TaskListener {
	@Autowired
	IdentityService identityService;

	@Autowired
	MagazineRepository magazineRepository;

	@Override
	public void notify(DelegateTask task) {
		System.out.println("CreateMagazineAdminCheck");
		DelegateExecution execution = task.getExecution();

		Long magazineId = (Long) execution.getVariable("magazine_id");
		Magazine magazine = magazineRepository.findOneById(magazineId);

		execution.setVariable("check_data", magazine.toString());
	}
}
