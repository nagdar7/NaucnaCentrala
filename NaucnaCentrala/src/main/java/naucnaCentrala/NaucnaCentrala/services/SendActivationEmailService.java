package naucnaCentrala.NaucnaCentrala.services;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;

@Service
public class SendActivationEmailService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	JavaMailSender javaMailSender;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("execute");
		String piid = execution.getProcessInstanceId();
		String email = (String) execution.getVariable("email");
		String message = "To activate Your account click on: http://localhost:8080/registration/activate/" + piid;

		// MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		// MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, true);
		// mail.setTo(email);
		// mail.setFrom(senderEmail);
		// mail.setSubject("Naucna centrala account confirmation");
		// mail.setText(message);
		// javaMailSender.send(mimeMessage);

		log.info("Mail sent, content: {} , to {} , from {}", message, email, senderEmail);
	}
}
