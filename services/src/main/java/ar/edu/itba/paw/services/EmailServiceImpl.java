package ar.edu.itba.paw.services;

import com.sendgrid.*;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Service
public class EmailServiceImpl implements EmailService {
	private final String from = "hi@carpul.com";
	private final SendGrid sg = new SendGrid("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");
    private final static Logger console = LoggerFactory.getLogger(EmailServiceImpl.class);

	public void sendRegistrationEmail(User user) {
		console.info("Trying to send registration email to {}", user.getUsername());
		String searchURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/";
		String driveURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/trip";
		String subject = user.getFirst_name() + ", welcome to carpul 🚗";
		String content = loadFromTemplate("/mails/registration.html");
		
		// replace local variables
		content = content.replace("{USERNAME}", user.getFirst_name());
		content = content.replace("{SEARCH_URL}", searchURL);
		content = content.replace("{DRIVE_URL}", driveURL);
		
		Mail email = createEmail(from, subject, user.getUsername(), content);
		sendEmail(email);
	}
	
	public void sendReservationEmail(User user, Trip trip) {
		console.info("Trying to send reservation email to {}", user.getUsername());
		String subject = "Hey " + trip.getDriver().getFirst_name() + ", you have a new reservation!";
		String content = loadFromTemplate("/mails/reservation.html");
		String carpulURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/user/" + user.getId();
		
		// replace local variables
		content = content.replace("{USERNAME}", trip.getDriver().getFirst_name());
		content = content.replace("{DESTINATION}", trip.getTo_city());
		content = content.replace("{PASSENGER}", user.getFirst_name());
		content = content.replace("{URL}", carpulURL);
		
		Mail email = createEmail(from, subject, trip.getDriver().getUsername(), content);
		sendEmail(email);
	}
	
	public void sendUnreservationEmail(User user, Trip trip) {
		console.info("Trying to send unreserve email to {}", user.getUsername());
		String subject = "Hey " + trip.getDriver().getFirst_name() + ", someone just dropped their reservation 😞";
		String content = loadFromTemplate("/mails/unreservation.html");
		
		// replace local variables
		content = content.replace("{USERNAME}", trip.getDriver().getFirst_name());
		content = content.replace("{DESTINATION}", trip.getTo_city());
		content = content.replace("{PASSENGER}", user.getFirst_name());
		
		Mail email = createEmail(from, subject, trip.getDriver().getUsername(), content);
		sendEmail(email);
	}
	
	public void sendDeletionEmail(User user, Trip trip) {
		console.info("Trying to send trip deleted email to {}", user.getUsername());
		User u = user;
		String carpulURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/search?from=" + trip.getFrom_city() + " &to=" + trip.getTo_city() + "&when=" + trip.getEtd();
		String subject = "🚨 Alert " + u.getFirst_name() + ": Your trip to " + trip.getTo_city() + " was cancelled";
	    String content = loadFromTemplate("/mails/deletion.html");
	    
		// replace local variables
		content = content.replace("{DRIVER}", trip.getDriver().getFirst_name());
		content = content.replace("{DESTINATION}", trip.getTo_city());
		content = content.replace("{USERNAME}", user.getFirst_name());
		content = content.replace("{URL}", carpulURL);
		
		Mail email = createEmail(from, subject, u.getUsername(), content);
		sendEmail(email);
	}
	
	private String loadFromTemplate(String template) {
		File file;
		String content = "";
		// read mail file
		try {
			Resource resource = new ClassPathResource(template);
			file = resource.getFile();
        } catch (IOException e) {
        	console.error(e.toString());
            file = null;
        }

		// read HTML mail into a string
        if (file != null && file.exists()) {
            try {
                content = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException e) { console.error(e.toString()); }
        }
		
        return content;
	}
	
	private Mail createEmail(String from, String subject, String to, String content) {
		if (!from.contains("@")) return null;
		
		Email toEmail = new Email(to);
		Email fromEmail = new Email(from);
		fromEmail.setName("Carpul");
		Content contentEmail = new Content("text/html", content);
		return new Mail(fromEmail, subject, toEmail, contentEmail);
	}
	
	private void sendEmail(Mail email) {
		console.info("Sending email...");
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
