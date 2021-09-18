package quotationmanagement.service;

import java.math.BigDecimal;
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
	
	public ResponseEntity<StockQuote> sendQuote(StockQuote stockQuote) {
		 
		if (stockQuote == null) {
			System.out.println("Null entity");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(stockQuote);
		}
		
		boolean stockQuoteExists = stockQuoteRepository.existsByStockId(stockQuote.getStockId());
		if (stockQuoteExists) {
			return updateQuotes(stockQuote);
		}
		
		if (stockQuote.getQuotes() != null) {
			List<String> formatDates = new ArrayList<>();
			formatDates.add("yyyy-MM-dd");
			formatDates.add("yyyy/MM/dd");
			DateValidator validator = new DateValidator(formatDates);
			Map<String, BigDecimal> sentQuotes = stockQuote.getQuotes();

			for (Map.Entry<String, BigDecimal> quote : sentQuotes.entrySet()) {
				boolean validOrNot = validator.dateParse(quote.getKey());
				if (validOrNot) {
					return ResponseEntity.status(HttpStatus.CONFLICT).build();
				}
			}
		}
			
		stockQuoteRepository.save(stockQuote);
		return ResponseEntity.ok(stockQuote);
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

	public ResponseEntity<StockQuote> updateQuotes(StockQuote stockQuote) {

		Optional<StockQuote> foundStockQuoteOptional = stockQuoteRepository.findByStockId(stockQuote.getStockId());
		StockQuote foundStockQuote = foundStockQuoteOptional.get();
		stockQuote.getQuotes().forEach((date, value) -> {
			foundStockQuote.addQuote(date, value);
		});
		
		stockQuoteRepository.save(foundStockQuote);
		
		return ResponseEntity.ok().body(stockQuote);
		
	}
}














//List<Quote> quotes = quoteRepository.findAll();
//
//quotes.forEach((quote) -> {
//	System.out.println("Date: " + quote.getDate() + ", value: " + quote.getValue());
//});
//stockQuoteRepository.findStockQuoteByStockId(stockId);

//System.out.println("Got into updateQuotes");
//StockQuote stockQuoteToAddQuotes = stockQuoteRepository.findStockQuoteByStockId(stockId);
//Map<String, BigDecimal> quotes = stockQuote.getQuotes();
//System.out.println("Got into repository");
//
//quotes.forEach((date, value) -> {
//	if (quoteRepository.findQuoteByDate(date) != null) {
//		stockQuoteToAddQuotes.addQuote(date, value);
//	}
//});
//
//System.out.println("Added quotes");
//
//stockQuoteRepository.save(stockQuoteToAddQuotes);
//
//System.out.println("Salvei o stockQuote");
//
//return ResponseEntity.ok(stockQuoteToAddQuotes);

