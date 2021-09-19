package quotationmanagement.controller.dto;

import java.time.LocalDateTime;

import quotationmanagement.models.Quote;

public class QuoteDto {
	
	private String date;
	private Double value;
	
	public QuoteDto(Quote quote) {
		this.date = quote.getDate();
		this.value = quote.getValue();
	}

	public String getdate() {
		return date;
	}
	public Double getValue() {
		return value;
	}
	
	
}
