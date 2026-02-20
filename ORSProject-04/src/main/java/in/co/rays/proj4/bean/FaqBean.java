
package in.co.rays.proj4.bean;


public class FaqBean extends BaseBean {

    private Long faqId;
    private String faqCode;   // UNIQUE
    private String question;
    private String answer;
    private String faqStatus;

   

   

    // Getters and Setters
    public Long getFaqId() {
        return faqId;
    }

    public void setFaqId(Long faqId) {
        this.faqId = faqId;
    }

    public String getFaqCode() {
        return faqCode;
    }

    public void setFaqCode(String faqCode) {
        this.faqCode = faqCode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFaqStatus() {
        return faqStatus;
    }

    public void setFaqStatus(String faqStatus) {
        this.faqStatus = faqStatus;
    }

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id +"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return "faqId";
	}

  
    
}