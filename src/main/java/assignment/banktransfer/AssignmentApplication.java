package assignment.banktransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"assignment.banktransfer"})
public class AssignmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}
}
