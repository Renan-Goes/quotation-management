package quotationmanagement.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Page;

import quotationmanagement.controller.dto.StockQuoteDto;

@Entity
@Table(name="STOCK_QUOTES")
public class StockQuote {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@Column(name="STOCK_QUOTE_ID")
	private String id;
	
	@Column
	private String stockId;

    @ElementCollection
    @CollectionTable(name="QUOTES", 
      joinColumns = {@JoinColumn(name = "STOCK_QUOTE_ID", referencedColumnName = "STOCK_QUOTE_ID")})
    @MapKeyColumn(name = "QUOTE_DATE")
    @Column(name = "QUOTE_VALUE")
    private Map<String, BigDecimal> quotes;
    
	public StockQuote() {
	}

	public StockQuote(String stockId) {
		this.stockId = stockId;
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<String, BigDecimal> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, BigDecimal> quotes) {
		this.quotes = quotes;
	}
	
	@Override
	public String toString() {
		return "StockQuote [id=" + id + ", stockId=" + stockId + ", quotes=" + quotes + "]";
	}
	
}
