package quotationmanagement.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import quotationmanagement.controller.dto.StockQuoteDto;
import quotationmanagement.controller.form.StockQuoteForm;
import quotationmanagement.models.Stock;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.StockQuoteRepository;

//@RestController
//@RequestMapping("/stockQuote")
//public class QuoteController {
//	
//	@Autowired
//	StockQuoteRepository stockQuoteRepository;
//
//	@PostMapping
//	@Transactional
//	@CacheEvict(value="listOfStocks", allEntries=true)
//	public ResponseEntity<StockQuoteDto> createStockQuote(@RequestBody @Valid StockQuoteForm form, UriComponentsBuilder uriBuilder) {
//		StockQuote stockQuote = form.convert();
//		stockQuoteRepository.save(stockQuote);
//		
//		URI uri = uriBuilder.path("/stockQuote/{id}").buildAndExpand(form.getStockId()).toUri();
//		return ResponseEntity.created(uri).body(new StockQuoteDto(stockQuote));
//	}
//}
