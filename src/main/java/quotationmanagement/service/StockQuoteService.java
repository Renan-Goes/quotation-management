package quotationmanagement.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import quotationmanagement.controller.validation.DateValidator;
import quotationmanagement.models.Quote;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.QuoteRepository;
import quotationmanagement.repository.StockQuoteRepository;

@Service
public class StockQuoteService {

	private StockQuoteRepository stockQuoteRepository;
	
	private QuoteRepository quoteRepository;
	
	public StockQuoteService(StockQuoteRepository stockQuoteRepository, 
			QuoteRepository quoteRepository) {
		this.stockQuoteRepository = stockQuoteRepository;
		this.quoteRepository = quoteRepository;
	}
	
	public ResponseEntity<StockQuote> sendQuote(StockQuote stockQuote) {

		if (stockQuote == null) {
			System.out.println("Null entity");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		if (stockQuote.getQuotes() != null) {
			DateValidator validator = new DateValidator();
			Map<String, Double> sentQuotes = stockQuote.getQuotes();

			for (Map.Entry<String, Double> quote : sentQuotes.entrySet()) {
//				Optional<Quote> foundQuote = quoteRepository.existsByDate(quote.getKey());
//				if (foundQuote.isPresent() && foundQuote.get) {
//					return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a quote with this date");
//				}
				
				boolean validOrNot = validator.dateParse(quote.getKey());

				if (!validOrNot) {
					return ResponseEntity.status(HttpStatus.CONFLICT).build();
				}
			}
		}
		
		boolean stockQuoteExists = stockQuoteRepository.existsByStockId(stockQuote.getStockId());
		if (stockQuoteExists) {
			return updateQuotes(stockQuote);
		}
			
		stockQuoteRepository.save(stockQuote);
		return ResponseEntity.ok().body(stockQuote);
	}

	public List<StockQuote> listStockQuotes() {

		List<StockQuote> stockQuotes = stockQuoteRepository.findAll();
		return stockQuotes;
	}
	
	public StockQuote getStockQuote(String stockId) {
		Optional<StockQuote> stockQuote = stockQuoteRepository.findByStockId(stockId);
		if (stockQuote.isPresent()) {
			return stockQuote.get();
		}
		
		return null;		
	}

	public ResponseEntity updateQuotes(StockQuote stockQuote) {

		Optional<StockQuote> foundStockQuoteOptional = stockQuoteRepository.findByStockId(stockQuote.getStockId());
		StockQuote foundStockQuote = foundStockQuoteOptional.get();
		stockQuote.getQuotes().forEach((date, value) -> {
				foundStockQuote.addQuote(date, value);				
		});
		
		stockQuoteRepository.save(foundStockQuote);
		
		return ResponseEntity.ok().body("Stock Quote was updated");
		
	}
}