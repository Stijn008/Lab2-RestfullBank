package slim_shady.REST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import slim_shady.REST.Client;
import slim_shady.REST.Client2;

@SpringBootApplication
public class Lab2RestApplication {
	public static void main(String[] args) throws InterruptedException {
		// -------------------------------------------------------------------------------------------------------------
		// UNCOMMENT THE DESIRED EXERCISE
		// -------------------------------------------------------------------------------------------------------------

		//runExercise1(args);
		runExercise2(args);
	}

	public static void runExercise1(String[] args) {
		// Instantiate Server thread
		Thread serverThread = new Thread(() -> SpringApplication.run(Lab2RestApplication.class, args));

		// Instantiate Client thread
		Client client = new Client("Client 1");
		Thread clientThread = new Thread(client);

		// Run Server and Client
		serverThread.start();	// Start server thread
		sleep(5000); 		// Wait 5 seconds (until the server is up and running)
		clientThread.start();	// Start client thread
	}

	public static void runExercise2(String[] args) {
		// -------------------------------------------------------------------------------------------------------------
		// The problem was that both clients were writing to the balance at the same time, which caused the balance
		// to deviate from the correct value. This was easily solved by making the methods in BankService SYNCHRONISED
		// -------------------------------------------------------------------------------------------------------------

		// Instantiate Server thread
		Thread serverThread = new Thread(() -> SpringApplication.run(Lab2RestApplication.class, args));

		// Instantiate Client thread
		Client2 client1 = new Client2("Client 1");
		Client2 client2 = new Client2("Client 2");
		Thread clientThread1 = new Thread(client1);
		Thread clientThread2 = new Thread(client2);

		// Run Server and Client
		serverThread.start();	// Start server thread
		sleep(5000); 		// Wait 5 seconds (until the server is up and running)
		clientThread1.start();	// Start client 1
		clientThread2.start();	// Start client 2
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time); // Wait 5 seconds for server to start up
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}