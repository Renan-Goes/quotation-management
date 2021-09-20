package quotationmanagement.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import quotationmanagement.models.StockQuote;

@Repository
public interface StockQuoteRepository extends JpaRepository<StockQuote, String> {

	boolean existsByStockId(String stockId);

	Optional<StockQuote> findByStockId(String stockId);
	
	Optional<StockQuote> findById(String id);
}
