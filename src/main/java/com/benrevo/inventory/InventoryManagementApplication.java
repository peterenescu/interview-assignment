package com.benrevo.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApplication.class, args);
		System.out.println("Hi! Welcome to the Inventory Management System!");
		System.out.println("Do you already have an account? (y/n)");
	}
}
