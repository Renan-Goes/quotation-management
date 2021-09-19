package quotationmanagement.controller.validation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

public class DateValidator {
	
	public boolean dateParse(String date) {
		LocalDateTime ldt = null;
		boolean validOrNot = false;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		try {
			ldt = LocalDateTime.parse(date, formatter);
			String result = ldt.format(formatter);
			validOrNot = result.equals(date);
			
		}
		catch(Exception e) {
			try {
				LocalDate ld = LocalDate.parse(date, formatter);
				String result = ld.format(formatter);
				validOrNot = result.equals(date);
			}
			catch(Exception f) {
				System.out.println("Bugou");
			}
		}
	
		return validOrNot;
	}
}
