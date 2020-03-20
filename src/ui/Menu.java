package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pojos.*;
import db.interfaces.*;
import db.sqlite.*;

public class Menu {

	// DB Managers
	private static DBManager dbManager;
	private static DogManager dogManager;
	private static MedicineManager medicineManager;

	// Used for parsing dates
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	// BufferedReader for my whole code
	private static BufferedReader reader;

	public static void main(String[] args) throws Exception {
		// Connect with the database
		dbManager = new SQLiteManager();
		dbManager.connect();
		dogManager = dbManager.getDogManager();
		medicineManager = dbManager.getMedicineManager();
		// Initialize BufferedReader
		reader = new BufferedReader(new InputStreamReader(System.in));
		// Print welcome screen
		System.out.println("Welcome!");
		dbManager.createTables();
		// Ask the user his or her role
		while (true) {
			System.out.println("Who are you?");
			System.out.println("1. Doctor");
			System.out.println("2. Owner");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				doctorMenu();
				break;
			case 2:
				ownerMenu();
				break;
			case 0:
				dbManager.disconnect();
				return;
			default:
				break;
			}
		}
	}

	private static void doctorMenu() throws Exception {
		while (true) {
			System.out.println("Search dogs by...");
			System.out.println("1. Name");
			System.out.println("2. Breed");
			System.out.println("0. Back");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				searchDogByName();
				System.out.println("Type the selected dog's id");
				int dogIdName = Integer.parseInt(reader.readLine());
				doctorSubMenu(dogIdName);
				break;
			case 2:
				searchDogByBreed();
				System.out.println("Type the selected dog's id");
				int dogIdBreed = Integer.parseInt(reader.readLine());
				doctorSubMenu(dogIdBreed);
				break;
			case 0:
				return;
			default:
				break;
			}
		}
	}

	private static void ownerMenu() throws Exception {
		while (true) {
			System.out.println("What do you wanna do, yo?");
			System.out.println("1. Admit dog");
			System.out.println("2. Search dog by Name");
			System.out.println("3. Search dog by Breed");
			System.out.println("0. Back");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				admitDog();
				break;
			case 2:
				searchDogByName();
				break;
			case 3:
				searchDogByBreed();
				break;
			case 0:
				return;
			default:
				break;
			}
		}
	}

	private static void doctorSubMenu(int dogId) throws Exception {
		while (true) {
			System.out.println("What do you wanna do, doc?");
			System.out.println("1. Change release date");
			System.out.println("2. Update dog data");
			System.out.println("3. Give medicine");
			System.out.println("4. Check medicines");
			System.out.println("0. Back");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				changeDogReleaseDate(dogId);
				break;
			case 2:
				updateDog(dogId);
				break;
			case 3:
				giveMedicine(dogId);
			case 4:
				checkMedicines(dogId);
			case 0:
				return;
			default:
				break;
			}
		}
	}
	
	private static void checkMedicines(int dogId) throws Exception {
		Dog dog = dogManager.getDog(dogId);
		System.out.println(dog);
	}
	
	private static void giveMedicine(int dogId) throws Exception {
		// Show all the available medicines
		List<Medicine> medicinesList = medicineManager.showMedicines();
		for (Medicine medicine : medicinesList) {
			System.out.println(medicine);
		}
		// Ask the doctor the medicine ID
		System.out.println("Please, type the medicine ID:");
		int medicineId = Integer.parseInt(reader.readLine());
		// Give the medicine to the dog
		medicineManager.give(dogId, medicineId);
		
	}

	private static void updateDog(int dogId) throws Exception {
		// First I get the dog
		Dog toBeModified = dogManager.getDog(dogId);
		System.out.println("Actual name: " + toBeModified.getName());
		// If the user doesn't type anything, the name is not changed
		System.out.println("Type the new name or press enter to leave it as is:");
		String newName = reader.readLine();
		if (newName.equals("")) {
			newName = toBeModified.getName();
		}
		System.out.println("Actual breed: " + toBeModified.getBreed());
		// If the user doesn't type anything, the breed is not changed
		System.out.println("Type the new breed or press enter to leave it as is:");
		String newBreed = reader.readLine();
		if (newBreed.equals("")) {
			newBreed = toBeModified.getBreed();
		}
		System.out.println("Actual weight: " + toBeModified.getWeight());
		// If the user doesn't type anything, the weight is not changed
		System.out.println("Type the new weight or press enter to leave it as is:");
		String newWeight = reader.readLine();
		Float floatNewWeight;
		if (newWeight.equals("")) {
			floatNewWeight = toBeModified.getWeight();
		} else {
			floatNewWeight = Float.parseFloat(newWeight);
		}
		System.out.println("Actual admissionDate: " + toBeModified.getAdmissionDate());
		// If the user doesn't type anything, the date is not changed
		System.out.println("Type the new admission date or press enter to leave it as is (yyyy-MM-dd):");
		String newAdmissionDate = reader.readLine();
		Date dateNewAdmissionDate;
		if (newAdmissionDate.equals("")) {
			dateNewAdmissionDate = toBeModified.getAdmissionDate();
		} else {
			dateNewAdmissionDate = Date.valueOf(LocalDate.parse(newAdmissionDate, formatter));
		}
		Dog updatedDog = new Dog(dogId, newName, newBreed, floatNewWeight, dateNewAdmissionDate);
		// AT the very end...
		dogManager.update(updatedDog);
	}

	private static void changeDogReleaseDate(int dogId) throws Exception {
		// First I get the dog
		Dog toBeModified = dogManager.getDog(dogId);
		System.out.println("Actual releaseDate: " + toBeModified.getReleaseDate());
		// If the user doesn't type anything, the date is not changed
		System.out.println("Type the new release date or press enter to leave it as is (yyyy-MM-dd):");
		String newReleaseDate = reader.readLine();
		Date dateNewReleaseDate;
		if (newReleaseDate.equals("")) {
			dateNewReleaseDate = toBeModified.getReleaseDate();
		} else {
			dateNewReleaseDate = Date.valueOf(LocalDate.parse(newReleaseDate, formatter));
		}
		// AT the very end...
		dogManager.releaseDate(dogId, dateNewReleaseDate);
	}

	private static void admitDog() throws Exception {
		System.out.println("Type!");
		System.out.print("Name: ");
		String name = reader.readLine();
		System.out.print("Breed: ");
		String breed = reader.readLine();
		// Only allow for floats
		System.out.print("Weight: ");
		Float weight = new Float(0.0);
		boolean wrongtext = false;
		do {
			try {
				weight = Float.parseFloat(reader.readLine());
				wrongtext = false;
			} catch (Exception e) {
				wrongtext = true;
				System.out.println("Please, enter a float, idiot!");
			}
		} while (wrongtext);
		//
		System.out.print("Admission Date (yyyy-MM-dd): ");
		String adm = reader.readLine();
		LocalDate admissionDate = LocalDate.parse(adm, formatter);
		Dog dog = new Dog(name, breed, weight, Date.valueOf(admissionDate));
		dogManager.admit(dog);
		// Optional: Add medicine
		System.out.println("Do you want to add a medicine right now? (Y/N)");
		String answer = reader.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			// USE AN ALREADY EXISTING METHOD
			// But the dog OBJECT doesn't have an ID yet!
			// So we get it
			giveMedicine(dbManager.getLastId());
		}
	}

	private static void searchDogByName() throws Exception {
		System.out.println("Type!");
		System.out.print("Name: ");
		String name = reader.readLine();
		List<Dog> dogs = dogManager.searchByName(name);
		for (Dog dog : dogs) {
			System.out.println(dog);
		}
	}

	private static void searchDogByBreed() throws Exception {
		System.out.println("Type!");
		System.out.print("Breed: ");
		String breed = reader.readLine();
		List<Dog> dogs = dogManager.searchByBreed(breed);
		for (Dog dog : dogs) {
			System.out.println(dog);
		}
	}

}
