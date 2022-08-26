package com.techelevator;

import com.techelevator.model.*;
import com.techelevator.services.AccountService;
import com.techelevator.services.AuthenticationService;
import com.techelevator.services.ConsoleService;
import com.techelevator.services.TransferService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {

        BigDecimal balance = accountService.getBalance(currentUser);
        System.out.println("\n Your current account balance is: $" + balance + "\n");
	}

	private void viewTransferHistory() {

        Transfer[]transfers =  transferService.getAllTransfers(accountService.getAccountId(currentUser.getUser().getId()));
		User[]users = transferService.getAllUsers();
        String username = null;

        System.out.println("-------------------------------------------\n" +
                "Transfers\n" +
                "ID          From/To                 Amount\n" +
                "-------------------------------------------");
        for (Transfer transfer : transfers) {
            if (users != null) {
                for (User user: users) {
                    if (accountService.getAccountId(user.getId()).equals(transfer.getAccountTo()) && !currentUser.getUser().getId().equals(user.getId()));
                    username = user.getUsername();
                }
            }
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		User[]users = transferService.getAllUsers();
        Transfer transfer = new Transfer();
        Account sender = accountService.getByUserId(currentUser.getUser().getId());         // For handling updated sender balance
        System.out.println(currentUser.getUser().getId());                                  // 1001
        Account receiver = null;                                                            // For handling updated receiver balance

        if (users != null) {
            System.out.println("-------------------------------------------\n" +
                    "Users\n" +
                    "ID          Name\n" +
                    "-------------------------------------------");
            for (User user : users) {
//                System.out.println(accountService.getByUserId(currentUser.getUser().getId()) + "   " + accountService.getUsername(user.getId()));
                System.out.println(user.getId() + "    " + user.getUsername());
            }
            System.out.println("-------------------------------------------");
        }
        //using ConsoleService over Scanner tool
        ConsoleService console = new ConsoleService();
        // need Integer over int to use equals method in User class
        Integer userTo = console.promptForInt("Enter ID of user you are sending to (0 to cancel):");
        Integer accountFrom =  accountService.getAccountId(currentUser.getUser().getId());

        while (accountFrom.equals(userTo)) {
//             System.out.println("cannot send money to self");
             userTo = console.promptForInt("Cannot send money to self");

        }
            receiver = accountService.getByAccountId(userTo);                           // should be userTo, not accountFrom... XD
            BigDecimal amountToSend = console.promptForBigDecimal("Enter amount:");

        //  if (amountToSend.intValue() <= accountService.getBalance(currentUser).intValue())
            if (amountToSend.compareTo(accountService.getBalance(currentUser)) <= 0 && amountToSend.intValue() > 0) {
                // -1, 0, 1 <-- Less than, equal to, greater than
                //at this point, everything is valid, now we need to create transfer to store in table
                transfer.setTransferTypeId(2); //referred to tenmo.sql for numerical value
                transfer.setTransferStatusId(2);
                transfer.setAccountFrom(accountFrom);
                transfer.setAccountTo(userTo);
                transfer.setAmount(amountToSend);

                transferService.addTransfer(transfer);
//                BigDecimal remainingBalance = accountService.getBalance(currentUser);
                BigDecimal remainingBalance = accountService.getBalance(currentUser).subtract(amountToSend);
                sender.setBalance(accountService.getBalance(currentUser).subtract(amountToSend));        // sender's new balance
                receiver.setBalance(accountService.getBalance(currentUser).add(amountToSend));           // receiver's new balance
                System.out.println("Object's balance: " + sender.getBalance() + "  Database's balance: " + accountService.getBalance(currentUser));
                accountService.update(sender);                                                           // write to database
                System.out.println("Object's balance: " + sender.getBalance() + "  Database's balance: " + accountService.getBalance(currentUser));
                accountService.update(receiver);                                                         // write to database

                System.out.println("Remaining Balance: " + accountService.getBalance(currentUser));

            } else { // can clean this up
                System.out.println("Not enough money in account or money entered must be greater than 0");
            }

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
