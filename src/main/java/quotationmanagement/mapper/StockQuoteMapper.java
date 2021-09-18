package quotationmanagement.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import quotationmanagement.controller.dto.StockQuoteDto;
import quotationmanagement.models.StockQuote;

@Mapper(componentModel="spring")
public interface StockQuoteMapper {
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateStockQuoteFromDto(StockQuoteDto stockQuoteDto, 
			@MappingTarget StockQuote stockQuote);
}
