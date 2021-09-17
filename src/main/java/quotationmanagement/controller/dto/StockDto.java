package quotationmanagement.controller.dto;

import org.springframework.data.domain.Page;

import quotationmanagement.models.Stock;

public class StockDto {
	
	private String id;
	private String Description;
	
	public StockDto(Stock stock) {
		this.id = stock.getid();
		this.Description = stock.getDescription();
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return Description;
	}
	
//	public static Page<StockDto> convert(Page<Stock> stocks) {
//		return stocks.map(StockDto::new);
//	}
}
