package quotationmanagement.service;

import java.net.URI;

import org.apache.http.impl.client.HttpClients;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
	
	public boolean createStock(Stock stock) {

		StockDto foundStock = findStock(stock.getid());
		
		if (foundStock != null) {
			System.out.println("ja tem no repositorio...");
			return false;
		}
		else {
//			ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
			RestTemplate restTemplate = new RestTemplate();
			Stock newStock = restTemplate.postForObject("http://localhost:8080/stock", stock, Stock.class);
			
			return true;			
		}
	     
	}
}
