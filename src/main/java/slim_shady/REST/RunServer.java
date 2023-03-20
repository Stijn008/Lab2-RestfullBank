package slim_shady.REST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunServer {
	public static void main(String[] args)  {
		Thread serverThread = new Thread(() -> SpringApplication.run(RunServer.class, args));

		// Run Server and Client
		serverThread.start();	// Start server thread
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time); // Wait 5 seconds for server to start up
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}