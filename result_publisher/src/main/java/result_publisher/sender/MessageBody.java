/**
 * 
 * @author : M.N Aslam
 * @Batch : Aspire
 * @description : Creates the message body for the email as well as the SMS
 * @dependancies : Student.java
 * 
 */

package result_publisher.sender;

import result_publisher.models.Student;

class MessageBody {

/**
 *  Returns the message to be written on the email 
 */
	public static String getEmailBody(Student s) {
		String result = s.getPassed().equals("YES") ? "PASSED" : "FAILED";
		String message = "Dear Mr/Mrs."+s.getParentName()+",\n"
				+ "\tThe results have been pulished for the academic year 2020-2021 for all the students. "
				+ "your son/daughter "+s.getStudentName()+" has scored "+s.getTotalMarks()+" and have "+result+" their secondary school. "
				+ "Report card and further details have been attached as an excel file below"
				
				+ "\n\n\nThanks & Regards"
				+ "\nController of Examination,"
				+ "\nWebsite : https://www.dge.tn.nic.in/"
				+ "\nPhone: +91 98765 4321";
		
		return message;
	}

/**
 *  Returns the message to be written on the SMS which will contain OTP 
 */
	public static String getSMSBody(Student s) {
		String message = "Dear Mr/Mrs."+s.getParentName()+","
				+ "\nResults Published. "
				+ "Kindly Check your email and use the following OTP to open the attachment"
				+ "\n"+ s.getOTP();
		
		return message;
	}
}
