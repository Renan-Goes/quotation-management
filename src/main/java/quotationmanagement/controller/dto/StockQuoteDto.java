package quotationmanagement.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Page;

import quotationmanagement.models.StockQuote;

public class StockQuoteDto {
		
	private String id;
	private String stockId;
	private Map<String, BigDecimal> quotes;
	
	public StockQuoteDto(StockQuote stockQuote) {
		this.id = stockQuote.getId(); 
		this.stockId = stockQuote.getStockId();
	}

	public String getId() {
		return id;
	}

	public static Page<StockQuoteDto> convert(Page<StockQuote> stockQuotes) {
		return stockQuotes.map(StockQuoteDto::new);
	}

}
