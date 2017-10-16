package com.benrevo.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

@SpringBootApplication
public class InventoryManagementApplication {
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<String> passwords = new ArrayList<String>();
	private static final int PASSWORD_INDEX = 1;
	private static final int USERNAME_INDEX = 0;
	private static boolean isAUser = false;
	private static boolean goBack = false;
	private static String user = "";
	private static String currentDir = "";
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApplication.class, args);
		InventoryManagementApplication runner = new InventoryManagementApplication();
		System.out.println("Initializing...");
		if(!runner.loadUserInformation()) System.exit(1);
		do {
			isAUser = goBack = false;
			runner.welcome();
			if(isAUser) runner.logIn();
			else runner.signUp();
		} while(goBack == true);
		currentDir = System.getProperty("user.dir") + "/" + user;
		(new File(currentDir)).mkdir();
		runner.applications();
		System.out.println("\n\nThank you " + user + "! Have a nice day!");
	}  

	public boolean loadUserInformation() {
		System.out.println("Loading Users...");
		File file = new File("users_information");
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				//read two words - 
				//one for username and the next for the associated password
				String information[] = scanner.nextLine().split(" ");
				
				//lower case
				information[USERNAME_INDEX] = 
						information[USERNAME_INDEX].toLowerCase();
				for(int i = 0; i < information.length; i++) {
					users.add(information[USERNAME_INDEX]);
					passwords.add(information[PASSWORD_INDEX]);
				}
				
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			System.out.println("\nError Locating Information");
			return false;
		}
		System.out.println("Finished Loading Users...");
		return true;
	}
	
	public void welcome() {
		// local variable used to read user input
		Scanner reader = new Scanner(System.in);
		// inputted character
		String input = "";
		// input counter
		int counter = 0;
		// welcome the user
		System.out.println("\n\nHi! Welcome to the Inventory Management System!");
		System.out.print("\n\nDo you already have an account? (y/n) ");
		do {
			if(counter > 0) System.out.print("I'm sorry, I don't recognize your response." + 
					" Please try again. ");
			input = reader.nextLine();
			counter++;
		} while (input.length() != 1 || (input.charAt(0) != 'y' && input.charAt(0) != 'n') );
		// check to see if wants to sign up
		if(input.charAt(0) == 'y') isAUser = true;
	}
	
	public void logIn() {
		// local variable used to read user input
		Scanner reader = new Scanner(System.in);
		// inputted character
		String input = "";
		// input counter
		int counter = 0;
		// welcome the user back
		System.out.println("\n\nWelcome back! Please sign in. ('b' to go back)\n\n");
		System.out.print("Username: ");
		do {
			if(counter > 0) System.out.print("Username does not exist. Username: ");
			input = reader.nextLine();
			if(input.length() == 1 && input.charAt(0) == 'b') {
				goBack = true;
				return;
			}
			counter++;
		} while (!users.contains(input));
		// index of user
		int index = users.indexOf(input);
		user = input;
		// reset counter
		counter = 0;
		System.out.print("Password: ");
		do {
			if(counter > 0) System.out.print("Incorrect password. Password: ");
			input = reader.nextLine();
			if(input.length() == 1 && input.charAt(0) == 'b') {
				user = "";
				goBack = true;
				return;
			}
			counter++;
		} while (!passwords.get(index).equals(input)); 
		// if pass, prompt password
	}
	
	public void signUp() {
		// local variable used to read user input
		Scanner reader = new Scanner(System.in);
		// inputted character
		String username = "";
		String password = "";
		// input counter
		int counter = 0;
		// welcome the user back
		System.out.println("\n\nWelcome! Please create a username. ('b' to go back)\n\n");
		System.out.print("Username: ");
		do {
			if(counter > 0) System.out.print("Username already taken. Username: ");
			username = reader.nextLine();
			if(username.length() == 1 && username.charAt(0) == 'b') {
				goBack = true;
				return;
			}
			counter++;
		} while (users.contains(username));
		// index of user
		System.out.println("\n\nPlease create a password. ('b' to go back)\n\n");
		counter = 0;
		System.out.print("Password: ");
		do {
			if(counter > 0) System.out.print("Please input a password. Password: ");
			password = reader.nextLine();
			if(password.length() == 1 && password.charAt(0) == 'b') {
				goBack = true;
				return;
			}
			counter++;
		} while (password.length() == 0); 
		// if pass, prompt password
		
		try {
			BufferedWriter writer = 
					new BufferedWriter(new FileWriter("users_information", true));
			writer.append(username + " " + password + "\n");
			writer.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(1);
		}
		catch (IOException e) {
			System.out.println("Error Creating User.");
			System.exit(1);
		}
		user = username;
	}
	
	public void applications() {
		// local variable used to read user input
		Scanner reader = new Scanner(System.in);
		// inputted character
		String input = "";
		// input counter
		int counter = 0;
		// welcome the user back
		do {
			System.out.printf("\n\nLogged into %s.\n\n", user);
			System.out.print("Exit (e), Create Directory (dir), Create Document (doc): ");
			do {
				if(counter > 0) System.out.print("I'm sorry, I don't recognize your response." + 
						" Please try again. ");
				input = reader.nextLine();
				counter++;
			} while(!input.equals("e") && !input.equals("dir") && !input.equals("doc"));
			if(input.equals("e"))
				return;
			if(input.equals("dir")) {
				System.out.print("What would you like to name your directory? ");
				input = reader.nextLine();
				boolean exists = (new File(currentDir + "/" + input)).mkdir();
				if(!exists) 
					System.out.println("Directory already exists!");
				else
					System.out.println("Successfully created " + input + "!");
			}
			if(input.equals("doc")) {
				System.out.print("What would you like to name your document? ");
				input = reader.nextLine();
				boolean exists = false;
				try {
					exists = (new File(currentDir + "/" + input)).createNewFile();
				}
				catch (IOException e) {
					System.out.println("Error creating file.");
				}
				if(!exists) 
					System.out.println("File already exists!");
				else
					System.out.println("Successfully created " + input + "!");
			}
			counter = 0;
		} while(true);
	}
}

