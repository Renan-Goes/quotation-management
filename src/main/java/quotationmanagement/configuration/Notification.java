package quotationmanagement.configuration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import quotationmanagement.models.APINotification;

public class Notification {

	public void notifyHost() {
		RestTemplate restTemplate = new RestTemplate();
		APINotification notification = new APINotification("localhost", "8081"); 
		restTemplate.postForEntity(
				"http://localhost:8080/notification/", notification, Void.class);

		System.out.println("API notified");
	}
}
