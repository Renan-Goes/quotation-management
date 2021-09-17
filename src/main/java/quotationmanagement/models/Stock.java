package quotationmanagement.models;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public class Stock {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	private String description;
	
	public Stock() {
	}
	
	public Stock(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
