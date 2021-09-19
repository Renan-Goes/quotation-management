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
import quotationmanagement.mapper.StockQuoteMapper;
import quotationmanagement.models.Quote;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.QuoteRepository;
import quotationmanagement.repository.StockQuoteRepository;

@Service
public class StockQuoteService {

	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private QuoteRepository quoteRepository;
	
	public ResponseEntity sendQuote(StockQuote stockQuote) {

		if (stockQuote == null) {
			System.out.println("Null entity");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Quote is not in stock-manager");
		}
		
		if (stockQuote.getQuotes() != null) {
			DateValidator validator = new DateValidator();
			Map<String, Double> sentQuotes = stockQuote.getQuotes();

			for (Map.Entry<String, Double> quote : sentQuotes.entrySet()) {
				boolean quoteExists = quoteRepository.existsByDate(quote.getKey());
				if (quoteExists) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("There is alreade a quote with this date");
				}
				
				boolean validOrNot = validator.dateParse(quote.getKey());

				if (!validOrNot) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Date is not valid");
				}
			}
		}
		
		boolean stockQuoteExists = stockQuoteRepository.existsByStockId(stockQuote.getStockId());
		if (stockQuoteExists) {
			return updateQuotes(stockQuote);
		}
			
		stockQuoteRepository.save(stockQuote);
		return ResponseEntity.ok(stockQuote.getStockId() + " was inserted in the repository");
	}

	public List<StockQuote> listStockQuotes(String stockId, Pageable paging) {
		
		if (stockId == null) {
			Page<StockQuote> stockQuotes = stockQuoteRepository.findAll(paging);
			return stockQuotes.getContent();
		} else {
			Page<StockQuote> stockQuote = stockQuoteRepository.findByStockId(
					stockId, paging);
			return stockQuote.getContent();
		}		
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