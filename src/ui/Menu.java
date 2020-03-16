package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import pojos.*;

public class Menu {

	// Used for parsing dates
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	// BufferedReader for my whole code
	private static BufferedReader reader;
	
	public static void main(String[] args) throws Exception {
		reader = new BufferedReader(new InputStreamReader(System.in));
		// Print welcome screen
		System.out.println("Welcome!");
		System.out.println("Who are you?");
		System.out.println("1. Doctor");
		System.out.println("2. Owner");
		int choice = Integer.parseInt(reader.readLine());
		switch (choice) {
		case 1:
			// TODO
			break;
		case 2:
			ownerMenu();
			break;
		default:
			break;
		}
	}
	
	private static void ownerMenu() throws Exception {
		System.out.println("What do you wanna do, yo?");
		System.out.println("1. Admit dog");
		System.out.println("2. Search dog by Name");
		System.out.println("3. Search dog by Breed");
		int choice = Integer.parseInt(reader.readLine());
		switch (choice) {
		case 1:
			admitDog();
			break;
		case 2:
			searchDogByName();
			break;
		case 3:
			// TODO
			break;
		default:
			break;
		}
	}
	
	private static void admitDog() throws Exception {
		System.out.println("Type!");
		System.out.print("Name: ");
		String name = reader.readLine();
		System.out.print("Breed: ");
		String breed = reader.readLine();
		System.out.print("Weight: ");
		Float weight = Float.parseFloat(reader.readLine());
		System.out.print("Admission Date (yyyy-MM-dd): ");
		String adm = reader.readLine();
		LocalDate admissionDate = LocalDate.parse(adm, formatter);
		Dog dog = new Dog(name, breed, weight, Date.valueOf(admissionDate));
		// TODO insert the poor dog
	}
	
	private static void searchDogByName() throws Exception {
		// TODO
	}
	
}
