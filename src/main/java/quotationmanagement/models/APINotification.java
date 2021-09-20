package quotationmanagement.models;

import javax.validation.constraints.NotEmpty;

public class APINotification {
	
	private String host;
	
	private String port;
	
	public APINotification(String host, String port) {
		this.host = host;
		this.port = port;
	}

	public APINotification() {
		
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
