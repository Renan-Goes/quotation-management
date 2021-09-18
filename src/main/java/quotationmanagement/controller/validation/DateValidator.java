package quotationmanagement.controller.validation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

public class DateValidator {
	
	private List<String> formatDates;
	
	public DateValidator(List<String> formatDates) {
		this.formatDates = formatDates;
	}
	
	public boolean dateParse(String date) {
		
		for (String formatDate : this.formatDates) {
			
			try {
				return GenericValidator.isDate(date, formatDate, true);
			}
			catch(Exception e) {
			}
		}
		
		return null;
	}

	public List<String> getFormatDates() {
		return formatDates;
	}

	public void setFormatDates(List<String> formatDates) {
		this.formatDates = formatDates;
	}
}
