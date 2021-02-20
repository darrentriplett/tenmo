package com.techelevator.tenmo;
import java.text.MessageFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import com.google.common.util.concurrent.Service;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransfersService;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {

				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}

		}

	}

	private void viewCurrentBalance() {
		AccountService test = new AccountService(API_BASE_URL, currentUser);

		try {

			System.out.println("Your current balance is: $" + test.getBalance());
		} catch (Exception e) {

			System.out.println(
					"We are unable to display your balance at this time.  We do apologize for the inconvenience.");
		}

	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		TransfersService service = new TransfersService(API_BASE_URL, currentUser);
		int userId = currentUser.getUser().getId();
		List<Transfers> transfers = service.getTransfers(userId);
		System.out.println(currentUser.getUser().getUsername() +", here is a list of all of your transactions:");
		
		for(Transfers transfer: transfers)
		{
			System.out.println("--------------------------------");
			System.out.println("Id: " + transfer.getId());
			System.out.println("From: " + transfer.getTransferFrom());
			System.out.println("To: " + transfer.getTransferTo());
			System.out.println("Type: " + transfer.getTransferType());
			System.out.println("Status: " + transfer.getTransferStatus());
			System.out.println("Amount: " + transfer.getAmount());
		}
		
		System.out.println("--------------------------------");
		System.out.println("--------------------------------");


	}

	private void viewPendingRequests() {
		TransfersService transferService = new TransfersService(API_BASE_URL, currentUser);
		Scanner scanner = new Scanner(System.in);
		UserService test = new UserService(API_BASE_URL, currentUser);
		AccountService accountService = new AccountService(API_BASE_URL, currentUser);

		int userId = currentUser.getUser().getId();
		String userName = currentUser.getUser().getUsername();
		
		
		List<Transfers> transfers = transferService.getTransfers(userId);
				
		int requestSize = transferService.getRequests(userId).size();
		if (requestSize != 0) 
		{
			System.out.println("Here are your pending requests:");
			System.out.println("-------------------------------");
			for (Transfers transfer : transfers)
			{
			
				boolean isPending = transfer.getTransferStatus().equals("Pending");	
				boolean fromMe = transfer.getTransferFrom().equals(userName);
			
				if(isPending && fromMe)
				{
					System.out.println("--------------------------------");
					System.out.println("Id: " + transfer.getId());
					System.out.println("From: " + transfer.getTransferFrom());
					System.out.println("To: " + transfer.getTransferTo());
					System.out.println("Type: " + transfer.getTransferType());
					System.out.println("Status: " + transfer.getTransferStatus());
					System.out.println("Amount: " + transfer.getAmount());
				}
			
			
	
			
			}
		}
		else 
		{
			System.out.println("You have no pending requests");
			mainMenu();
		}

		System.out.println("Please enter an ID to answer the request or enter 0 to exit");
		String id = scanner.nextLine();

		if (Integer.parseInt(id) == 0) 
		{
			mainMenu();
		}
		System.out.println("");
		System.out.println("1: Approve\r\n" + "2: Reject\r\n" + "0: Don't approve or reject\r\n" + "---------\r\n"
				+ "Please choose an option:");
		String option = scanner.nextLine();

		if (Integer.parseInt(option) == 0) 
		{
			mainMenu();
		} 
		else if (Integer.parseInt(option) == 1) 
		{
			for (Transfers transfer : transferService.getRequests(userId)) 
			{
				if (transfer.getId() == Integer.parseInt(id)) 
				{
					transferService.confirmTransfer(Integer.parseInt(id), 2);
					accountService.withdraw(currentUser.getUser().getId(), transfer.getAmount());
					accountService.deposit(Integer.parseInt(transfer.getTransferTo()), transfer.getAmount());
				}

			}

		} 
		else if (Integer.parseInt(option) == 2) 
		{
			transferService.confirmTransfer(Integer.parseInt(id), 3);
		}
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		UserService userService = new UserService(API_BASE_URL, currentUser);
		AccountService accountService = new AccountService(API_BASE_URL, currentUser);
		TransfersService transferService = new TransfersService(API_BASE_URL, currentUser);
		List<User> users = userService.findAllUsers();
		
		System.out.println("Please select a person to send money to:");
		System.out.println();
		System.out.println();
		
		// Write a method in console service to prompt the user for an id and store it
		for(User user:users)
		{
			System.out.println(user.getId() + ")  " + "Name: " + user.getUsername());
		}
			
		// Go get the receivers balance\
		System.out.println("");
		System.out.println("Enter ID of user you are sending to (0 to cancel):");
		String input = scanner.nextLine();
		if (Integer.parseInt(input) == 0) {
			mainMenu();
		}
		System.out.println("Please enter an amount to send:");
		System.out.println();
		
		BigDecimal amount = scanner.nextBigDecimal();
		if (accountService.getBalance().compareTo(amount) >= 0) {

			try {

				accountService.deposit(Integer.parseInt(input), amount);
				accountService.withdraw(currentUser.getUser().getId(), amount);
				transferService.createTransfers(Integer.parseInt(input), currentUser.getUser().getId(), amount);

			} catch (Exception e) {
				System.out.println(
						"We are unable to send Tenmo bucks at this time.  We do apologize for the inconvenience.");
			}
		} else {
			System.out.println("");
			System.out.println("");
			System.out.println("You do not have enough money in your account to fullfil this request.");
			System.out.println("Please choose another option.");
			System.out.println("");
			System.out.println("");
		}

	}

	private void requestBucks() {
		UserService userService = new UserService(API_BASE_URL, currentUser);
		AccountService accountService = new AccountService(API_BASE_URL, currentUser);
		TransfersService transferService = new TransfersService(API_BASE_URL, currentUser);

		Scanner scanner = new Scanner(System.in);
		///Create a list of users
		List<User> users = userService.findAllUsers();
		for(User user:users)
		{
			System.out.println("User ID: " + user.getId() + " Name: " + user.getUsername());
		}
				

		System.out.println("Enter ID of user you are requesting from (0 to cancel):");

		String input = scanner.nextLine();
		if (Integer.parseInt(input) == 0) {
			mainMenu();
		}

		System.out.println("Please enter an amount");

		BigDecimal amount = scanner.nextBigDecimal();
		try {
			transferService.createRequests(currentUser.getUser().getId(), Integer.parseInt(input), amount);

		} catch (Exception e) {
			System.out.println("Error Requesting Money");
		}

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

}
