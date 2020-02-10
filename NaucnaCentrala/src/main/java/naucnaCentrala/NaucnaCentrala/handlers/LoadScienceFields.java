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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;

@Service
public class LoadScienceFields implements TaskListener {
	@Autowired
	IdentityService identityService;

	@Override
	public void notify(DelegateTask task) {
		System.out.println("LoadScienceFields");
		TaskFormData taskFormData = task.getExecution().getProcessEngineServices().getFormService()
				.getTaskFormData(task.getId());
		List<FormField> formFields = taskFormData.getFormFields();
		if (formFields != null) {
			System.out.println("formFields != null");
			for (FormField field : formFields) {
				if (field != null && "naucne_oblasti".equals(field.getId())) {
					System.out.println("field != null && \"naucne_oblasti\".equals(field.getId())");
					FormType formType = field.getType();
					if (formType != null) {
						System.out.println("formType != null");
						Map<String, String> values = (LinkedHashMap<String, String>) formType.getInformation("values");
						if (values == null) {
							System.out.println("values == null");
							values = new LinkedHashMap<String, String>();
						}
						System.out.println("values != null");
						for (ScienceField scienceField : ScienceField.values()) {
							values.put(scienceField.name(), scienceField.name());
						}
					}
				}
			}
		}
	}
}
