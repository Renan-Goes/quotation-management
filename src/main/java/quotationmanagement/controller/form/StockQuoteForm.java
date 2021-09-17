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

	private Map<String, Long> quotes;
	
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<String, Long> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, Long> quotes) {
		this.quotes = quotes;
	}

	public StockQuote convert() {
		
		StockService stockService = new StockService();
//		System.out.println(stockQuote.toString());
		StockDto stock = stockService.findStock(stockId);
		
		if (stock == null) {
			return null;
		}

		StockQuote stockQuote = new StockQuote(stockId);
		
		quotes.forEach((date, value) -> {
			System.out.println("Values: " + date + ", " + value);
		});
		
		stockQuote.setQuotes(quotes);
		
		return stockQuote;		
	
	}
}
