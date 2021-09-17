package quotationmanagement.controller.dto;

import java.time.LocalDateTime;

import quotationmanagement.models.Quote;

public class QuoteDto {
	
	private String date;
	private Long value;
	
	public QuoteDto(Quote quote) {
		this.date = quote.getDate();
		this.value = quote.getValue();
	}

	public String getdate() {
		return date;
	}
	public Long getValue() {
		return value;
	}
	
	
}
