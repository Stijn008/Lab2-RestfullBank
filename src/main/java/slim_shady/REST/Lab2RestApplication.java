package slim_shady.REST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import slim_shady.REST.Client;
import slim_shady.REST.Client2;

@SpringBootApplication
public class Lab2RestApplication {
	public static void main(String[] args) throws InterruptedException {
		// Instantiate Client thread
		Client client = new Client("Client 1");
		Thread clientThread = new Thread(client);

		// Run Server and Client
		sleep(5000); 		// Wait 5 seconds (until the server is up and running)
		clientThread.start();	// Start client thread
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time); // Wait 5 seconds for server to start up
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}