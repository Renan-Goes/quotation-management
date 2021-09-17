package quotationmanagement.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import quotationmanagement.models.StockQuote;

public class StockQuoteDto {
		
	private String id;
	private String stockId;
	
	public StockQuoteDto(StockQuote stockQuote) {
		this.id = stockQuote.getId(); 
		this.stockId = stockQuote.getStockId();
	}

	public String getId() {
		return id;
	}

}
