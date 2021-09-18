package quotationmanagement.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import quotationmanagement.models.StockQuote;

public interface StockQuoteRepository extends JpaRepository<StockQuote, String> {

	boolean existsByStockId(String stockId);
	
	Page<StockQuote> findByStockId(String stockId, Pageable paging);

	StockQuote findStockQuoteByStockId(String stockId);
}
