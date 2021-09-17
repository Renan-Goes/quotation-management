package quotationmanagement.controller.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import quotationmanagement.controller.dto.StockDto;
import quotationmanagement.models.Stock;
import quotationmanagement.models.StockQuote;
import quotationmanagement.service.StockService;

public class StockForm {

	@NotNull @NotEmpty
	private String id;
	private String description;	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Stock convert() {
		return new Stock(id, description);	
	}

}
