package quotationmanagement.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="QUOTES")
public class Quote {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="QUOTE_DATE")
	private String date;
	
	@Column(name="QUOTE_VALUE")
	private Double value;
	
	public Quote() {
	}
	
	public Quote(Double value, String date) {
		this.value = value;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

//	public String getId() {
//		return id;
//	}


}
