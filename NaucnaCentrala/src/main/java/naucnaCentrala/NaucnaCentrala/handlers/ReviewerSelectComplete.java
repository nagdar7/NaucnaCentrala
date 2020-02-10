package naucnaCentrala.NaucnaCentrala.handlers;

import java.util.ArrayList;
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
public class ReviewerSelectComplete implements TaskListener {
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

		List<Account> accountants = (List<Account>) task.getExecution().getVariable("casopis_recezenti");

		ArrayList<String> listaRecezenata = (ArrayList<String>) task.getExecution().getVariable("listaRecezenata");

		if (listaRecezenata == null) {
			listaRecezenata = new java.util.ArrayList();
		}

		List<FormField> formFields = taskFormData.getFormFields();
		if (formFields != null) {
			for (FormField field : formFields) {
				if (field != null && "recezent".equals(field.getId())) {
					// ovo je nase select polje
					if (!field.getValue().getValue().equals("none")) {
						listaRecezenata.add((String) field.getValue().getValue());
					}
					// FormType formType = field.getType();
					// if (formType != null) {
					// log.info("formType != null");
					// Map<String, String> values = (LinkedHashMap<String, String>)
					// formType.getInformation("values");
					// if (values == null) {
					// log.info("values == null");
					// values = new LinkedHashMap<String, String>();
					// }
					// log.info("values != null");
					// for (Account account : accountants) {
					// values.put(account.getUsername(), account.getFirstName() + " " +
					// account.getLastName());
					// }
					// }
				}
			}
		}
		task.getExecution().setVariable("listaRecezenata", listaRecezenata);
		Boolean atLeastTwo = listaRecezenata.size() >= 2;
		task.getExecution().setVariable("atLeastTwo", atLeastTwo);
	}

}
