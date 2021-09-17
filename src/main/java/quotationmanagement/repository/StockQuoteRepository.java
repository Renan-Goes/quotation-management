package quotationmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import quotationmanagement.models.StockQuote;

public interface StockQuoteRepository extends JpaRepository<StockQuote, String> {
	
	List<StockQuote> findByStockId(String stockId);
}
