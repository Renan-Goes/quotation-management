package quotationmanagement.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import quotationmanagement.controller.dto.StockQuoteDto;
import quotationmanagement.controller.form.StockQuoteForm;
import quotationmanagement.controller.validation.DateValidator;
import quotationmanagement.models.Stock;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.StockQuoteRepository;
import quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stockQuote")
public class StockQuoteController {

	@Autowired
	private StockQuoteRepository stockQuoteRepository;

	@GetMapping	
	@Cacheable(value="listOfStocks")
	public List<StockQuote> list(@RequestParam(required=false) String stockId,
			Pageable paging) {
		
		if (stockId == null) {
			Page<StockQuote> stockQuotes = stockQuoteRepository.findAll(paging);
			return stockQuotes.getContent();
		} else {
			Page<StockQuote> stockQuote = stockQuoteRepository.findByStockId(
					stockId, paging);
			return stockQuote.getContent();
		}
	}

	@PostMapping
	@Transactional
	@CacheEvict(value="listOfStocks", allEntries=true)
	public ResponseEntity<StockQuote> createStockQuote(
			@RequestBody @Valid StockQuoteForm form, UriComponentsBuilder uriBuilder) {
		StockQuote stockQuote = form.convert();
 
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
			
			sentQuotes.forEach((date, value) -> {
				Date validOrNot = validator.dateParse(date);
				if (validOrNot == null) {
					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(stockQuote);
				}
			});
		}
			
		stockQuoteRepository.save(stockQuote);
		return ResponseEntity.ok(stockQuote);
	}
}
