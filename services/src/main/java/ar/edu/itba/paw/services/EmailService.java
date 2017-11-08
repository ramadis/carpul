package ar.edu.itba.paw.services;

import com.sendgrid.*;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.io.IOException;

public enum EmailService {
	INSTANCE;
	private final String from = "hi@carpul.com";
	private final SendGrid sg = new SendGrid("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");
	
	public void sendRegistrationEmail(User user) {
		String subject = "Hey, welcome to Carpul!";
	    String content = "Welcome to a life of adventures " + user.getFirst_name() + ". It's your time to shine. Welcome to carpul!" ;
		
		Mail email = createEmail(from, subject, user.getUsername(), content);
		sendEmail(email);
	}
	
	public void sendReservationEmail(User user, Trip trip) {
		String subject = "Hey " + trip.getDriver().getFirst_name() + ", you have a new reservation!";
	    String content = "Hey " + trip.getDriver().getFirst_name() + " Just FYI: " + user.getFirst_name() + " just reserved your trip to " + trip.getTo_city() + ". Check the details in your Carpul profile!";
		
		Mail email = createEmail(from, subject, trip.getDriver().getUsername(), content);
		sendEmail(email);
	}
	
	public void sendUnreservationEmail(User user, Trip trip) {
		String subject = "Hey " + trip.getDriver().getFirst_name() + ", someone just dropped a reservation!";
	    String content = "Hey " + trip.getDriver().getFirst_name() + " Just FYI: " + user.getFirst_name() + " just dropped a reservation for your trip to " + trip.getTo_city() + ". Check the details in your Carpul profile!";
		
		Mail email = createEmail(from, subject, trip.getDriver().getUsername(), content);
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
