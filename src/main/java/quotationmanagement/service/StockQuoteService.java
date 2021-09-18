package quotationmanagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.fge.jsonpatch.JsonPatch;

import quotationmanagement.controller.validation.DateValidator;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.StockQuoteRepository;

@Service
public class StockQuoteService {

	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	public ResponseEntity<StockQuote> sendQuote(StockQuote stockQuote) {
		 
		if (stockQuote == null) {
			System.out.println("Null entity");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(stockQuote);
		}
		
		boolean stockQuoteExists = stockQuoteRepository.existsByStockId(stockQuote.getStockId());
		if (stockQuoteExists) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(stockQuote);
		}
		
		if (stockQuote.getQuotes() != null) {
			List<String> formatDates = new ArrayList<>();
			formatDates.add("yyyy-MM-dd");
			formatDates.add("yyyy/MM/dd");
			DateValidator validator = new DateValidator(formatDates);
			Map<String, BigDecimal> sentQuotes = stockQuote.getQuotes();

			for (Map.Entry<String, BigDecimal> quote : sentQuotes.entrySet()) {
				Date validOrNot = validator.dateParse(quote.getKey());
				if (validOrNot == null) {
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

//	public ResponseEntity<StockQuote> updateQuotes(String stockId, JsonPatch patch) {
//
//	    try {
//	        StockQuote StockQuote = stockQuoteRepository.findStockQuoteByStockId(stockId).orElseThrow(StockQuoteNotFoundException::new);
//	        StockQuote StockQuotePatched = applyPatchToStockQuote(patch, StockQuote);
//	        stockQuoteRepository.updateStockQuote(StockQuotePatched);
//	        return ResponseEntity.ok(StockQuotePatched);
//	    } catch (JsonPatchException | JsonProcessingException e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    } catch (StockQuoteNotFoundException e) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	    }
//	}
}
