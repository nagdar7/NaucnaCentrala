package naucnaCentrala.NaucnaCentrala.service.camunda;

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

import naucnaCentrala.NaucnaCentrala.model.Account;
import naucnaCentrala.NaucnaCentrala.model.FormSubmissionDto;
import naucnaCentrala.NaucnaCentrala.service.AccountService;
import naucnaCentrala.NaucnaCentrala.utils.Utils;

@Service
public class SendEditorNotificationEmailService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	AccountService accountService;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("execute");

		String email = (String) execution.getVariable("odabrani_urednik_email");

		String message = "Paper needs reviewing.";

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, true);
		mail.setTo(email);
		mail.setFrom(senderEmail);
		mail.setSubject("Naucna centrala - new paper for review");
		mail.setText(message);
		javaMailSender.send(mimeMessage);

		log.info("Mail sent, content: {} , to {} , from {}", message, email, senderEmail);
	}
}
