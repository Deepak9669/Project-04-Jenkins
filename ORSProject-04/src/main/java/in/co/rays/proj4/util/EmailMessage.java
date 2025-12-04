package in.co.rays.proj4.util;

/**
 * EmailMessage is a DTO class used to carry email information
 * like receiver, subject, content and message type (HTML / TEXT).
 * 
 * It is used by MailSender to send emails.
 * 
 * @author Deepak Verma
 * @version 1.0
 */
public class EmailMessage {

	private String to;
	private String subject;
	private String message;

	// Message type constants
	public static final int HTML_MSG = 1;
	public static final int TEXT_MSG = 2;

	// Default: Text message
	private int messageType = TEXT_MSG;

	/**
	 * Default Constructor
	 */
	public EmailMessage() {
	}

	/**
	 * Parameterized Constructor
	 */
	public EmailMessage(String to, String subject, String message) {
		this.to = to;
		this.subject = subject;
		this.message = message;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		if (messageType == HTML_MSG || messageType == TEXT_MSG) {
			this.messageType = messageType;
		} else {
			this.messageType = TEXT_MSG; // default safety
		}
	}

	@Override
	public String toString() {
		return "EmailMessage [to=" + to + ", subject=" + subject + ", messageType="
				+ (messageType == HTML_MSG ? "HTML" : "TEXT") + "]";
	}
}
