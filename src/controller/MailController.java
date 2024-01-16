package controller;

import dto.MailDto;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class MailController {

	MailDto mailDto;
	public MailController(String from, String password, String to){
		mailDto = new MailDto();
		mailDto.setFrom(from);
		mailDto.setTo(to);
		mailDto.setUsername(from);
		mailDto.setPassword(password);
		sendMail(mailDto);
	}

	public void sendMail(MailDto mailDto){
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailDto.getUsername(),mailDto.getPassword());
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailDto.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailDto.getTo()));
			message.setSubject("Website Status Notification.");
	         MimeMultipart multipart = new MimeMultipart("related");

	         // first part (the html)
	         BodyPart messageBodyPart = new MimeBodyPart();
	         String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
	         messageBodyPart.setContent(htmlText, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);

	         // second part (the image)
	         messageBodyPart = new MimeBodyPart();
	         File file = new File("src/capture/test.png");
	         DataSource fds = new FileDataSource(file);

	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         messageBodyPart.setHeader("Content-ID", "<image>");

	         // add image to the multipart
	         multipart.addBodyPart(messageBodyPart);

	         // put everything together
	         message.setContent(multipart);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println("Error sending email: "+e.getMessage());
		}
	}
//	public static void main(String[] args){
		/*first email would not be sent.
		 * as you would have to allow less secure apps from sender email explicitly to allow this program
		 * to send e-mails using sender email account.
		 * */
		//Mail mail = new Mail("badarshahzad54@gmail.com","badarkhan0766786","badarshahzad54@gmail.com");
	//}
}