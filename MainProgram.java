package windowsLogin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainProgram {

    public static void main(String[] args) {
        try {
            // Create a thread pool with 3 threads
            ExecutorService executor = Executors.newFixedThreadPool(3);
            // Create a Computer object with test data
            Computer computer = new Computer("David", "1A23", "davidsan@rrsystems.co.il");

            // Submit a login attempt to the executor service
            Future<Boolean> future = executor.submit(() -> computer.tryLogin());
            // Get the result of the login attempt
            boolean loginSuccess = future.get();

            if (loginSuccess) {
                System.out.println("Proceeding with user " + computer.getOwner());
            } else {
                System.out.println("Login process ended. Please try again later.");
            }

            // Shut down the executor service
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred. Please try again later.");
        }
    }
}
