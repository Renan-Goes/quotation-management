package quotationmanagement.service;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import quotationmanagement.controller.dto.StockDto;
import quotationmanagement.models.Stock;

public class StockService {

	public StockDto findStock(String id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Stock> response = restTemplate.getForEntity(
				"http://localhost:8080/stock/" + id, Stock.class);
		
		Stock stock = response.getBody();
		
		if (stock == null) {
			return null;
		}
		
		return new StockDto(stock);
	}
}
