package quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import quotationmanagement.controller.dto.StockQuoteDto;
import quotationmanagement.controller.form.StockQuoteForm;
import quotationmanagement.models.Stock;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.StockQuoteRepository;
import quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stockQuote")
public class StockQuoteController {

	StockQuoteRepository stockQuoteRepository;
	
	public StockQuoteController(StockQuoteRepository stockQuoteRepository) {
		this.stockQuoteRepository = stockQuoteRepository;
	}

	@PostMapping
	@Transactional
	@CacheEvict(value="listOfStocks", allEntries=true)
	public ResponseEntity<StockQuote> createStockQuote(@RequestBody @Valid StockQuoteForm form, UriComponentsBuilder uriBuilder) {
		StockQuote stockQuote = form.convert();

		if (stockQuote == null) {
			System.out.println("Null entity");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(stockQuote);
		}
		
		List<StockQuote> foundStockQuotes = stockQuoteRepository.findByStockId(stockQuote.getId());
		System.out.println("This here: " + foundStockQuotes);
		if (!foundStockQuotes.isEmpty()) {
			System.out.println("Entrei");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(stockQuote);
		}
			
		stockQuoteRepository.save(stockQuote);
		return ResponseEntity.ok(stockQuote);
	}

	@GetMapping(path="/{stockId}")
	public ResponseEntity<StockQuoteDto> findStock(@PathVariable String stockId) {
		List<StockQuote> foundStockQuotes = stockQuoteRepository.findByStockId(stockId);
		
		if (!foundStockQuotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.notFound().build();	
	}
}
 