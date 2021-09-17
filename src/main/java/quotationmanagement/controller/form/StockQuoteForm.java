package quotationmanagement.controller.form;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.istack.NotNull;

import quotationmanagement.controller.dto.QuoteDto;
import quotationmanagement.controller.dto.StockDto;
import quotationmanagement.models.Quote;
import quotationmanagement.models.Stock;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.StockQuoteRepository;
import quotationmanagement.service.StockService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component 	
public class StockQuoteForm {

	@NotEmpty
	private String stockId;

	private Map<String, BigDecimal> quotes;
	
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<String, BigDecimal> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, BigDecimal> quotes) {
		this.quotes = quotes;
	}

	public StockQuote convert() {
		
		StockService stockService = new StockService();
		StockDto stock = stockService.findStock(stockId);
		
		if (stock == null) {
			return null;
		}

		StockQuote stockQuote = new StockQuote(stockId);
		
		stockQuote.setQuotes(quotes);
		
		return stockQuote;		
	
	}
}
