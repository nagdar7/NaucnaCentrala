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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;
import naucnaCentrala.NaucnaCentrala.service.AccountService;

@Service
public class LoadEditorsFields implements TaskListener {
	@Autowired
	IdentityService identityService;
	@Autowired
	AccountService accountService;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void notify(DelegateTask task) {
		log.info("notify");
		TaskFormData taskFormData = task.getExecution().getProcessEngineServices().getFormService()
				.getTaskFormData(task.getId());
		List<FormField> formFields = taskFormData.getFormFields();
		if (formFields != null) {
			log.info("formFields != null");
			for (FormField field : formFields) {
				if (field != null && "urednici".equals(field.getId())) {
					FormType formType = field.getType();
					if (formType != null) {
						log.info("formType != null");
						Map<String, String> values = (LinkedHashMap<String, String>) formType.getInformation("values");
						if (values == null) {
							log.info("values == null");
							values = new LinkedHashMap<String, String>();
						}
						log.info("values != null");
						for (Account account : accountService.getAllEditors()) {
							values.put(account.getUsername(), account.getFirstName() + " " + account.getLastName());
						}
					}
				}
			}
		}
	}
}
