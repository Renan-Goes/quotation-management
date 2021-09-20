package quotationmanagement.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import quotationmanagement.models.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String> {
	Quote findQuoteByDate(String date);

	Optional<Quote> existsByDate(String key);
}
