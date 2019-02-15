package ar.edu.itba.paw.services;

import com.sendgrid.*;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	private final String from = "hi@carpul.com";
	private final SendGrid sg = new SendGrid("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");
    private final static Logger console = LoggerFactory.getLogger(EmailServiceImpl.class);

	public void sendRegistrationEmail(User user) {
		console.info("Trying to send registration email to {}", user.getUsername());
		String subject = "Hey, welcome to Carpul!";
	    String content = "Welcome to a life of adventures " + user.getFirst_name() + ". It's your time to shine. Welcome to carpul!" ;
		
		Mail email = createEmail(from, subject, user.getUsername(), content);
		sendEmail(email);
	}
	
	public void sendReservationEmail(User user, Trip trip) {
		console.info("Trying to send reservation email to {}", user.getUsername());
		String subject = "Hey " + trip.getDriver().getFirst_name() + ", you have a new reservation!";
	    String content = "Hey " + trip.getDriver().getFirst_name() + " Just FYI: " + user.getFirst_name() + " just reserved your trip to " + trip.getTo_city() + ". Check the details in your Carpul profile!";
		
		Mail email = createEmail(from, subject, trip.getDriver().getUsername(), content);
		sendEmail(email);
	}
	
	public void sendUnreservationEmail(User user, Trip trip) {
		console.info("Trying to send unreserve email to {}", user.getUsername());
		String subject = "Hey " + trip.getDriver().getFirst_name() + ", someone just dropped a reservation!";
	    String content = "Hey " + trip.getDriver().getFirst_name() + " Just FYI: " + user.getFirst_name() + " just dropped a reservation for your trip to " + trip.getTo_city() + ". Check the details in your Carpul profile!";
		
		Mail email = createEmail(from, subject, trip.getDriver().getUsername(), content);
		sendEmail(email);
	}
	
	public void sendDeletionEmail(User user, Trip trip) {
		console.info("Trying to send trip deleted email to {}", user.getUsername());
		User u = user;
		
		String subject = "Alert " + u.getFirst_name() + ", your trip to " + trip.getTo_city() + " was cancelled";
	    String content = "Hey " + u.getFirst_name() + " Just FYI: " + user.getFirst_name() + " just removed his trip to " + trip.getTo_city() + ". Try to fin another trip to the same place!";
		
		Mail email = createEmail(from, subject, u.getUsername(), content);
		sendEmail(email);
	}
	
	private Mail createEmail(String from, String subject, String to, String content) {
		if (!from.contains("@")) return null;
		
		Email toEmail = new Email(to);
		Email fromEmail = new Email(from);
		fromEmail.setName("Carpul");
		Content contentEmail = new Content("text/plain", content);
		return new Mail(fromEmail, subject, toEmail, contentEmail);
	}
	
	private void sendEmail(Mail email) {
		if (email == null) return;
		
	    try {
	    	  Request request = new Request();
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(email.build());
	      sg.api(request);

	    } catch (IOException ex) {
	    	console.error(ex.getMessage());
	    }
	}
}
