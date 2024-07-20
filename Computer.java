package windowsLogin;

import java.util.Random;
import java.util.Scanner;

public class Computer {
    private int attempts = 5; // Number of login attempts allowed
    private String owner; // Name of the computer owner
    private String correctPassword; // Correct password for the login
    private String myEmail; // Email for password reset verification
    private Scanner scanner = new Scanner(System.in); // Scanner for user input
    private String verificationCode; // Code for verifying password reset requests
    
    // Constructor to initialize the Computer object
    public Computer(String owner, String correctPassword, String myEmail) {
        this.owner = owner;
        this.correctPassword = correctPassword;
        this.myEmail = myEmail;
    }

    // Synchronized method to handle login attempts
    public synchronized boolean tryLogin() {
        while (attempts > 0) {
            System.out.print("Enter your password: ");
            String userInput = scanner.nextLine();

            // Check password validity
            if (!isValidPassword(userInput)) {
                attempts--;
                System.out.println("Invalid password format. Password must be at least 4 characters long.");
                continue;
            }

            // Check if the entered password is correct
            if (correctPassword.equals(userInput)) {
                System.out.println("Success! Welcome " + owner);
                return true;
            } else {
                attempts--;
                if (attempts == 0) {
                    System.out.println("You have tried to login too many times. Your account has been blocked. Please reset your password.");
                    break;
                } else {
                    System.out.println("Incorrect password. Please try again. You have " + attempts + " attempts remaining.");
                    System.out.println("Would you like to try logging in again? Enter 'yes' to try again, 'no' to abort, or anything else to reset password.");
                    String retryLogin = scanner.nextLine();

                    if (retryLogin.equalsIgnoreCase("no")) {
                        break; // Exit the loop if the user chooses to abort
                    } else if (retryLogin.equalsIgnoreCase("yes")) {
                        continue; // Try logging in again
                    } else {
                        String resetResult = resetPassword();
                        System.out.println(resetResult);
                        if (resetResult.equals("Password reset successful.")) {
                            attempts = 5; // Reset the attempts after password reset
                        } else {
                            System.out.println("Password reset failed. Please try again later.");
                            break; // Exit the loop if password reset failed
                        }
                    }
                }
            }
        }
        return false;
    }

    // Method to handle password reset functionality
    public synchronized String resetPassword() {
        System.out.println("Hello,\nBefore we start with the password reset,\nI have one short question to make sure it's really you, " + owner);
        System.out.println("Enter your email:");
        String emailInput = scanner.nextLine();

        // Check if the email matches
        if (!emailInput.equals(myEmail)) {
            return "Email verification failed. Password reset aborted.";
        }

        // Generate and display verification code
        verificationCode = generateVerificationCode();
        System.out.println("Your verification code is: " + verificationCode);
        System.out.println("Please enter the code:");
        String codeInput = scanner.nextLine();

        // Verify the entered code
        if (!codeInput.equals(verificationCode)) {
            return "Verification failed. Password reset aborted.";
        }

        // Allow user to choose a new password
        System.out.println("Please choose a new password for your computer:");
        String passwordInput = scanner.nextLine();

        // Check new password validity
        if (!isValidPassword(passwordInput)) {
            return "Invalid password format. Password must be at least 4 characters long.";
        }

        // Set the new password
        setCorrectPassword(passwordInput);
        return "Password reset successful.";
    }
    
    // Generate a 4-digit verification code
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Generate a 4-digit code
        return String.valueOf(code);
    }

    // Check if the password is valid (at least 4 characters long)
    private boolean isValidPassword(String password) {
        return password.length() >= 4; // Example password length check
    }

    // Getters and setters for the class fields
    public String getCorrectPassword() {
        return correctPassword;
    }

    public void setCorrectPassword(String correctPassword) {
        this.correctPassword = correctPassword;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}