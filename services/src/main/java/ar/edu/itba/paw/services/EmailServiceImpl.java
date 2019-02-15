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
		String carpulUrl = "#";
		String subject = user.getFirst_name() + ", welcome to carpul 🚗";
	    String content = "<!doctype html><html> <head> <meta name=\"viewport\" content=\"width=device-width\"> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <title>Carpul email</title> <style>/* ------------------------------------- INLINED WITH htmlemail.io/inline ------------------------------------- */ /* ------------------------------------- RESPONSIVE AND MOBILE FRIENDLY STYLES ------------------------------------- */ @media only screen and (max-width: 620px){table[class=body] h1{font-size: 28px !important; margin-bottom: 10px !important;}table[class=body] p, table[class=body] ul, table[class=body] ol, table[class=body] td, table[class=body] span, table[class=body] a{font-size: 16px !important;}table[class=body] .wrapper, table[class=body] .article{padding: 10px !important;}table[class=body] .content{padding: 0 !important;}table[class=body] .container{padding: 0 !important; width: 100% !important;}table[class=body] .main{border-left-width: 0 !important; border-radius: 0 !important; border-right-width: 0 !important;}table[class=body] .btn table{width: 100% !important;}table[class=body] .btn a{width: 100% !important;}table[class=body] .img-responsive{height: auto !important; max-width: 100% !important; width: auto !important;}}/* ------------------------------------- PRESERVE THESE STYLES IN THE HEAD ------------------------------------- */ @media all{.ExternalClass{width: 100%;}.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{line-height: 100%;}.apple-link a{color: inherit !important; font-family: inherit !important; font-size: inherit !important; font-weight: inherit !important; line-height: inherit !important; text-decoration: none !important;}.btn-primary table td:hover{background-color: #34495e !important;}.btn-primary a:hover{background-color: #34495e !important; border-color: #34495e !important;}}</style> </head> <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\"> <tr> <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td><td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\"> <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\"> <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\">This is preheader text. Some clients will show this text as a preview.</span> <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\"> <tr> <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\"> <tr> <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\"> <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Hi there" + user.getFirst_name() +",</p><p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Welcome to a life of adventures. Are you ready to ride 🚙?</p><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\"> <tbody> <tr> <td align=\"left\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\"> <tbody> <tr> <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a href=\""+carpulUrl+"\" target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">Start going places</a> </td></tr></tbody> </table> </td></tr></tbody> </table> </td></tr></table> </td></tr></table> <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\"> <tr> <td class=\"content-block\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\"> <span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">Carpul LTA, a divison of ITBA Inc.</span> <br>Don't like these emails? <a href=\"#\" style=\"text-decoration: underline; color: #999999; font-size: 12px; text-align: center;\">Unsubscribe</a>. </td></tr><tr> <td class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\"> Powered by <a href=\"#\" style=\"color: #999999; font-size: 12px; text-align: center; text-decoration: none;\">Ourselves</a>. </td></tr></table> </div></div></td><td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td></tr></table> </body></html>";
		
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
		Content contentEmail = new Content("text/html", content);
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
