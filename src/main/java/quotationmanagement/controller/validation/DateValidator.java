package quotationmanagement.controller.validation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateValidator {
	
	private List<String> formatDates;
	
	public DateValidator(List<String> formatDates) {
		this.formatDates = formatDates;
	}
	
	public Date dateParse(String date) {
		
		for (String formatDate : this.formatDates) {
			
			try {
				return new SimpleDateFormat(formatDate).parse(date);
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
