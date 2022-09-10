package result_publisher.sender;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import result_publisher.models.Student;

public class TwilioSMS {
	Properties props;

	public TwilioSMS() throws IOException {
		props = new Properties();
		props.load(new FileInputStream("config.properties"));
	}

//	public static void main(String[] args) throws IOException {
//		new TwilioSMS().sendSMS();
//	}

	public void sendSMS(Set<Student> studentEntries) {

		Twilio.init(props.getProperty("TWILIO_ACCOUNT_SID"), props.getProperty("TWILIO_AUTH_TOKEN"));
		System.out.println("Processing . . .");

		// loop through the student records and populate the values
		Iterator<Student> iter = studentEntries.iterator();
		while (iter.hasNext()) {
			// get the students one by one
			Student s = iter.next();

			String msg = MessageBody.getSMSBody(s);
			String num = "+" + s.getParentMobile();

			Message message = Message
					.creator(new PhoneNumber(num), new PhoneNumber(props.getProperty("TWILIO_NUMBER")), msg).create();

			if (message.getSid() != null || message.getSid() != "") {
				System.out.printf("OTP successfully sent to parent: %s (%s)\n", s.getParentName(),
						s.getParentMobile());
//				System.out.println("Message to send: " + message.getBody());
//				System.out.println("Message successfully sent to: " + message.getTo());
			}
		}

	}
}