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

	private Map<String, Double> quotes;
	
	public StockQuoteForm() {
	}

	public StockQuoteForm(String stockId, Map<String, Double> quotes) {
		this.stockId = stockId;
		this.quotes = quotes;
	}
	
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<String, Double> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, Double> quotes) {
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
