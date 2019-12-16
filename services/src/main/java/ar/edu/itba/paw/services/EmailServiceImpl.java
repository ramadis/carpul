package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Service
public class EmailServiceImpl implements EmailService {
	private final String from = "hi@carpul.com";
    private final static Logger console = LoggerFactory.getLogger(EmailServiceImpl.class);
    
    @Autowired
    JavaMailSender sender;
    
	public void sendRegistrationEmail(User user) {
		console.info("Trying to send registration email to {}", user.getUsername());
		String searchURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/";
		String driveURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/#/trips/add";
		String subject = "🚗 " + user.getFirst_name() + ", welcome to carpul";
		String content = loadFromTemplate("/mails/registration.html");
		String plaintextContent = "Hi there {USERNAME}," + System.lineSeparator() + "Welcome to a new life of adventures. Are you ready to ride? 🚙" + System.lineSeparator() + System.lineSeparator() + "Find somewhere to go by going to {SEARCH_URL}" + System.lineSeparator() + "Or get paid for driving by going to {DRIVE_URL}";
		
		// replace local variables
		content = content.replace("{USERNAME}", user.getFirst_name());
		content = content.replace("{SEARCH_URL}", searchURL);
		content = content.replace("{DRIVE_URL}", driveURL);
		plaintextContent = plaintextContent.replace("{USERNAME}", user.getFirst_name());
		plaintextContent = plaintextContent.replace("{SEARCH_URL}", searchURL);
		plaintextContent = plaintextContent.replace("{DRIVE_URL}", driveURL);
		
		MimeMessagePreparator email = createEmail(from, subject, user.getUsername(), content, plaintextContent);
		sendEmail(email);
	}
	
	public void sendReservationEmail(User user, Trip trip) {
		console.info("Trying to send reservation email to {}", user.getUsername());
		String subject = "🙋 Hey " + trip.getDriver().getFirst_name() + ", you have a new reservation!";
		String content = loadFromTemplate("/mails/reservation.html");
		String carpulURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/#/users/" + user.getId();
		String plaintextContent = "Hi there {USERNAME}," + System.lineSeparator() + "Great news! {PASSENGER} just reserved your trip to {DESTINATION}. Someone else to share your adventure!" + System.lineSeparator() + System.lineSeparator() + "Check the details by going to {URL}";
		
		// replace local variables
		content = content.replace("{USERNAME}", trip.getDriver().getFirst_name());
		content = content.replace("{DESTINATION}", trip.getTo_city());
		content = content.replace("{PASSENGER}", user.getFirst_name());
		content = content.replace("{URL}", carpulURL);
		plaintextContent = plaintextContent.replace("{USERNAME}", trip.getDriver().getFirst_name());
		plaintextContent = plaintextContent.replace("{DESTINATION}", trip.getTo_city());
		plaintextContent = plaintextContent.replace("{PASSENGER}", user.getFirst_name());
		plaintextContent = plaintextContent.replace("{URL}", carpulURL);
		
		MimeMessagePreparator email = createEmail(from, subject, trip.getDriver().getUsername(), content, plaintextContent);
		sendEmail(email);
	}
	
	public void sendUnreservationEmail(User user, Trip trip) {
		console.info("Trying to send unreserve email to {}", user.getUsername());
		String subject = "😞 Hey " + trip.getDriver().getFirst_name() + ", someone just dropped their reservation";
		String content = loadFromTemplate("/mails/unreservation.html");
		String plaintextContent = "Hi there {USERNAME}," + System.lineSeparator() + "We have some bad news. {PASSENGER} just dropped their reservation for your trip to {DESTINATION}. Don't worry though, there's still time and room for more people to join!";
				
		// replace local variables
		content = content.replace("{USERNAME}", trip.getDriver().getFirst_name());
		content = content.replace("{DESTINATION}", trip.getTo_city());
		content = content.replace("{PASSENGER}", user.getFirst_name());
		plaintextContent = plaintextContent.replace("{USERNAME}", trip.getDriver().getFirst_name());
		plaintextContent = plaintextContent.replace("{DESTINATION}", trip.getTo_city());
		plaintextContent = plaintextContent.replace("{PASSENGER}", user.getFirst_name());
		
		MimeMessagePreparator email = createEmail(from, subject, trip.getDriver().getUsername(), content, plaintextContent);
		sendEmail(email);
	}
	
	public void sendDeletionEmail(User user, Trip trip) {
		console.info("Trying to send trip deleted email to {}", user.getUsername());
		User u = user;
		String carpulURL = "http://pawserver.it.itba.edu.ar/paw-2017b-6/#/search?from=" + trip.getFrom_city() + " &to=" + trip.getTo_city() + "&arrLat=" + trip.getArrival_lat() + "&arrLon=" + trip.getArrival_lon() + "&when=" + trip.getEtd();
		String subject = "🚨 Alert " + u.getFirst_name() + ": Your trip to " + trip.getTo_city() + " was cancelled";
	    String content = loadFromTemplate("/mails/deletion.html");
	    String plaintextContent = "Hi there {USERNAME}," + System.lineSeparator() + "We hate this, but we have bad news about your trip to {DESTINATION}: {DRIVER}'s trip is cancelled." + System.lineSeparator() + "We know this sucks, but why don't you try finding a new trip to {DESTINATION}." + System.lineSeparator() + System.lineSeparator() + "Find more trips to {DESTINATION} by going to {URL}";  
	    
		// replace local variables
		content = content.replace("{DRIVER}", trip.getDriver().getFirst_name());
		content = content.replace("{DESTINATION}", trip.getTo_city());
		content = content.replace("{USERNAME}", user.getFirst_name());
		content = content.replace("{URL}", carpulURL);
		plaintextContent = plaintextContent.replace("{DRIVER}", trip.getDriver().getFirst_name());
		plaintextContent = plaintextContent.replace("{DESTINATION}", trip.getTo_city());
		plaintextContent = plaintextContent.replace("{USERNAME}", user.getFirst_name());
		plaintextContent = plaintextContent.replace("{URL}", carpulURL);
		
		
		MimeMessagePreparator email = createEmail(from, subject, u.getUsername(), content, plaintextContent);
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
	
	private MimeMessagePreparator createEmail(String from, String subject, String to, String content, String plaintextContent) {
		if (!from.contains("@")) return null;
		
		MimeMessagePreparator email = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(plaintextContent, content);
        };
        
        return email;
	}
	
	private void sendEmail(MimeMessagePreparator email) {
		console.info("Sending email...");
		if (email == null) return;
		
	    try {
	      sender.send(email);
	    } catch (MailException ex) {
	    	console.error(ex.getMessage());
	    }
	}
}
