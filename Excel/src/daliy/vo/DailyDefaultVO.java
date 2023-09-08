package daliy.vo;

import java.sql.Date;

public class DailyDefaultVO {
	private Date date;
	private String content;

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "DailyDefaultVO [date=" + date + "\n content=" + content + "]";
	}
}
