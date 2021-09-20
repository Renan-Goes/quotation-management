package quotationmanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import junit.framework.Assert;
import quotationmanagement.controller.StockQuoteController;
import quotationmanagement.controller.form.StockQuoteForm;
import quotationmanagement.models.StockQuote;
import quotationmanagement.repository.QuoteRepository;
import quotationmanagement.repository.StockQuoteRepository;

class StockQuoteServiceTest {

	@Mock
	private StockQuoteController stockQuoteController;
	
	@Mock
	private StockQuoteRepository stockQuoteRepository;
	
	@Mock
	private QuoteRepository quoteRepository;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.stockQuoteRepository = Mockito.mock(StockQuoteRepository.class);
		this.quoteRepository = Mockito.mock(QuoteRepository.class);
		this.stockQuoteController = new StockQuoteController(stockQuoteRepository, quoteRepository);
	}
	
	@Test
	public void shouldCreateStockQuote() {
		Map<String, Double> quotes = new HashMap<>();
		quotes.put("2020-05-19", 15.1);
		StockQuoteForm form = new StockQuoteForm("petr4", quotes);
		ResponseEntity<StockQuote> stockQuoteResponse = stockQuoteController.createStockQuote(form);		
		StockQuote stockQuote = form.convert();

		StockQuote foundStockQuote = new StockQuote("petr4", quotes);
		Mockito.when(stockQuoteRepository.findByStockId("petr4")).thenReturn(Optional.of(foundStockQuote));
		
		Assert.assertEquals(stockQuote.getStockId(), foundStockQuote.getStockId());
		Assert.assertEquals(stockQuote.getQuotes(), foundStockQuote.getQuotes());		
	}
	
	@Test
	public void shouldReturnStockQuote() {
		StockQuote stockQuote = new StockQuote("petr4");
		Mockito.when(stockQuoteRepository.findByStockId("petr4")).thenReturn(Optional.of(stockQuote));
		
		ResponseEntity<StockQuote> foundStockQuoteResponse = stockQuoteController.findStockQuote("petr4");
		StockQuote foundStockQuote = foundStockQuoteResponse.getBody();
		
		Assert.assertEquals(stockQuote, foundStockQuote);
	}
	
	@Test
	public void shouldNotReturnStockQuote() {
		StockQuote stockQuote = new StockQuote("resgerg");
		Mockito.when(stockQuoteRepository.findByStockId("resgerg")).thenReturn(Optional.ofNullable(stockQuote));
		
		ResponseEntity<StockQuote> foundStockQuoteResponse = stockQuoteController.findStockQuote("resgerg");
		StockQuote foundStockQuote = foundStockQuoteResponse.getBody();
		
		Assert.assertEquals(stockQuote, foundStockQuote);
	}
	
	@Test
	public void shouldNotCreateStockQuoteBecauseIsNotInStockManager() {
		Map<String, Double> quotes = new HashMap<>();
		quotes.put("2020-07-12", 15.1);
		StockQuoteForm form = new StockQuoteForm("negargw", quotes);
		ResponseEntity<StockQuote> stockQuoteResponse = stockQuoteController.createStockQuote(form);		
		StockQuote stockQuote = form.convert();

		Mockito.when(stockQuoteRepository.findByStockId("negargw")).thenReturn(Optional.ofNullable(stockQuote));

		Assert.assertEquals(stockQuote, null);
	}
	
	@Test
	public void shouldNotCreateStockQuoteBecauseDateIsInvalid() {
		Map<String, Double> quotes = new HashMap<>();
		quotes.put("2021-13-05", 14.7);
		StockQuoteForm form = new StockQuoteForm("petr4", quotes);
		ResponseEntity<StockQuote> stockQuoteResponse = stockQuoteController.createStockQuote(form);

		Assert.assertEquals(stockQuoteResponse, ResponseEntity.status(HttpStatus.CONFLICT).build());
		
	}
	
	@Test
	public void shouldGetListOfStockQuotes() {
		List<StockQuote> foundListOfStockQuotes = new ArrayList<>();
		Mockito.when(stockQuoteRepository.findAll()).thenReturn(foundListOfStockQuotes);
		
		List<StockQuote> listOfStockQuotes = stockQuoteController.listStockQuotes();

		Assert.assertEquals(foundListOfStockQuotes.size(), listOfStockQuotes.size());
		
		for (int i = 0; i < listOfStockQuotes.size(); i++) {
			Assert.assertEquals(foundListOfStockQuotes.get(i).getStockId(), 
					listOfStockQuotes.get(i).getStockId());

			Assert.assertEquals(foundListOfStockQuotes.get(i).getQuotes(), 
					listOfStockQuotes.get(i).getQuotes());
		}
	}
	
	@Test
	public void shouldCreateStockQuoteWithoutQuotes() {
		StockQuoteForm form = new StockQuoteForm("petr4", null);
		ResponseEntity<StockQuote> stockQuoteResponse = stockQuoteController.createStockQuote(form);		
		StockQuote stockQuote = form.convert();

		StockQuote foundStockQuote = new StockQuote("petr4");
		Mockito.when(stockQuoteRepository.findByStockId("petr4")).thenReturn(Optional.of(foundStockQuote));
		
		Assert.assertEquals(stockQuote.getStockId(), foundStockQuote.getStockId());
		Assert.assertEquals(stockQuote.getQuotes(), foundStockQuote.getQuotes());	
	}
	
	@Test
	public void shouldAddQuote() {
		Map<String, Double> quotes = new HashMap<>();
		quotes.put("2020-05-19", 15.1);
		StockQuoteForm form = new StockQuoteForm("petr4", quotes);
		ResponseEntity<StockQuote> stockQuoteResponse = stockQuoteController.createStockQuote(form);	
		StockQuote stockQuote = form.convert();
		
		quotes.put("2019-07-20", 18.4);
		StockQuoteForm newForm = new StockQuoteForm("petr4", quotes);
		ResponseEntity<StockQuote> newStockQuoteResponse = stockQuoteController.createStockQuote(form);		
		StockQuote updatedStockQuote = newForm.convert();

		StockQuote foundStockQuote = new StockQuote("petr4", quotes);
		Mockito.when(stockQuoteRepository.findByStockId("petr4")).thenReturn(Optional.of(foundStockQuote));
		
		Assert.assertEquals(updatedStockQuote.getStockId(), foundStockQuote.getStockId());
		Assert.assertEquals(updatedStockQuote.getQuotes(), foundStockQuote.getQuotes());	
	}

}
