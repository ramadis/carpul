package ar.edu.itba.paw.services;

import com.sendgrid.*;

import ar.edu.itba.paw.models.User;

import java.io.IOException;

public enum EmailService {
	INSTANCE;
	private final String from = "hi@carpul.com";
	private final SendGrid sg = new SendGrid("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");
	
	public void sendRegistrationEmail(String to) {
		String subject = "Hey, Welcome to Carpul!";
	    String content = "Welcome to a life of adventures. Your account has already been created.";
		
		Mail email = createEmail(from, subject, to, content);
		sendEmail(email);
		
	}
	
	public void sendRegistrationEmail(User user) {
		String subject = "Hey, Welcome to Carpul!";
	    String content = "Welcome to a life of adventures";
		
		Mail email = createEmail(from, subject, user.getUsername(), content);
		sendEmail(email);
	}
	
	private Mail createEmail(String from, String subject, String to, String content) {
		Email toEmail = new Email(to);
		Email fromEmail = new Email(from);
		Content contentEmail = new Content("text/plain", content);
		
		return new Mail(fromEmail, subject, toEmail, contentEmail);
	}
	
	private void sendEmail(Mail email) {
		Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(email.build());
	      Response response = sg.api(request);
	    } catch (IOException ex) {}
	}
}
