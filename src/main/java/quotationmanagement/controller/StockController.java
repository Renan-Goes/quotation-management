package quotationmanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Cacheable;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
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
import quotationmanagement.controller.dto.StockDto;
import quotationmanagement.controller.form.StockForm;
import quotationmanagement.models.Stock;
import quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
	
	private StockService stockService;
	
	@GetMapping
	public ResponseEntity<Stock[]> getStocks() {
		
			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<Stock[]> response = restTemplate.getForEntity(
					"http://localhost:8080/stock", Stock[].class);
	
			Stock[] stocks = response.getBody();
			
			if (stocks == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			return ResponseEntity.ok(stocks);
		
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Stock> findStock(@PathVariable String id) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Stock> response = restTemplate.getForEntity(
				"http://localhost:8080/stock/" + id, Stock.class);
		
		Stock stock = response.getBody();
		
		if (stock == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.ok(stock);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Stock> createStock(@RequestBody @Valid StockForm form) {
		Stock stock = form.convert();
		
		StockService stockService = new StockService();
		StockDto foundStock = stockService.findStock(stock.getid());
		
		if (foundStock != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(stock);
		}

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Stock> response = restTemplate.postForEntity("http://localhost:8080/stock", stock, Stock.class);
		Stock newStock = response.getBody();
		
		 	
		return ResponseEntity.ok(stock);
	}
	
}
