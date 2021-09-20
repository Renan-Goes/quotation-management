package quotationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import quotationmanagement.configuration.Notification;
import quotationmanagement.models.APINotification;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class QuotationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotationManagementApplication.class, args);
	}
	
//	@Bean(initMethod="notifyHost")
//    public Notification getFunnyBean() {
//        return new Notification();
//	}
}
