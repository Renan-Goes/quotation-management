package quotationmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import quotationmanagement.controller.StockController;
import quotationmanagement.controller.dto.StockDto;
import quotationmanagement.controller.form.StockForm;
import quotationmanagement.models.Stock;

class stockControllerTest {

	private StockController stockController;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		this.stockController = new StockController();
	}
	
	@Test
	public void shouldGetFromStockManager() {
		
		ResponseEntity<Stock> response = stockController.findStock("petr4");
		Stock stock = response.getBody();
		Assert.assertEquals(stock.getid(), "petr4");
		Assert.assertEquals(stock.getDescription(), "Petrobras PN");
	}
	
	@Test 
	public void shouldCreateStock() {
		
		StockForm form = new StockForm("asfabava", "aaaaa");
		ResponseEntity<Stock> createdStockResponse = stockController.createStock(form);
		Stock stock = createdStockResponse.getBody();
		
		Assert.assertNotEquals(stock, null);	
	}
	
	@Test 
	public void shouldNotCreateStock() {
		StockForm form = new StockForm("petr4", "Petrobrasss");
		ResponseEntity<Stock> createdStockResponse = stockController.createStock(form);
		Stock stock = createdStockResponse.getBody();
		System.out.println("stock: " + stock);	
		Assert.assertEquals(stock, null);
	}
	
	@Test 
	public void shouldNotGetFromStockManager() {

		ResponseEntity<Stock> stockResponse = stockController.findStock("petrasdfa4");
		Stock stock = stockResponse.getBody();
		Assert.assertEquals(stock, null);
	}

}
