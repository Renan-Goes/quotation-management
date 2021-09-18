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
import javax.validation.Validation;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import quotationmanagement.service.StockQuoteService;
import quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stockQuote")
public class StockQuoteController {

	@Autowired
	private StockQuoteService stockQuoteService = new StockQuoteService();

	@GetMapping	
	@Cacheable(value="listOfStockQuotes")
	public List<StockQuote> list(@RequestParam(required=false) String stockId,
			Pageable paging) {
		return stockQuoteService.listStockQuotes(stockId, paging);
	}

	@PostMapping
	@Transactional
	@CacheEvict(value="listOfStockQuotes", allEntries=true)
	public ResponseEntity<StockQuote> createStockQuote(
			@RequestBody @Valid StockQuoteForm form, UriComponentsBuilder uriBuilder) {
		StockQuote stockQuote = form.convert();
 
		return stockQuoteService.sendQuote(stockQuote);
	}
}
