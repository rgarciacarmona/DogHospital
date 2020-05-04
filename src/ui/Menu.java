package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import pojos.*;
import pojos.users.*;
import xml.utils.CustomErrorHandler;
import db.interfaces.*;
import db.jpa.JPAUserManager;
import db.sqlite.*;

public class Menu {

	// DB Managers
	private static DBManager dbManager;
	private static DogManager dogManager;
	private static MedicineManager medicineManager;
	private static UserManager userManager;

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
		// We need to create the tables with JDBC before using JPA
		dbManager.createTables();
		userManager = new JPAUserManager();
		userManager.connect();
		// Initialize BufferedReader
		reader = new BufferedReader(new InputStreamReader(System.in));
		// Print welcome screen
		System.out.println("Welcome!");
		// Ask the user his or her role
		while (true) {
			System.out.println("What do you want to do?");
			System.out.println("1. Create a new role");
			System.out.println("2. Create a new user");
			System.out.println("3. Login");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				// Create a new role
				newRole();
				break;
			case 2:
				// Create a new user
				newUser();
				break;
			case 3:
				// Login
				login();
				break;
			case 0:
				dbManager.disconnect();
				userManager.disconnect();
				return;
			default:
				break;
			}
		}
	}

	private static void newRole() throws Exception {
		System.out.println("Please type the new role information:");
		System.out.print("Role name:");
		String roleName = reader.readLine();
		Role role = new Role(roleName);
		userManager.createRole(role);
	}

	private static void newUser() throws Exception {
		System.out.println("Please type the new user information:");
		System.out.print("Username:");
		String username = reader.readLine();
		System.out.print("Password:");
		String password = reader.readLine();
		// Create the password's hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		// Show all the roles and let the user choose one
		List<Role> roles = userManager.getRoles();
		for (Role role : roles) {
			System.out.println(role);
		}
		System.out.print("Type the chosen role id:");
		int roleId = Integer.parseInt(reader.readLine());
		// Get the chosen role from the database
		Role chosenRole = userManager.getRole(roleId);
		// Create the user and store it
		User user = new User(username, hash, chosenRole);
		userManager.createUser(user);
	}

	private static void login() throws Exception {
		System.out.println("Please input your credentials");
		System.out.print("Username:");
		String username = reader.readLine();
		System.out.print("Password:");
		String password = reader.readLine();
		User user = userManager.checkPassword(username, password);
		// We check if the user/password combination was OK
		if (user == null) {
			System.out.println("Wrong credentials, please try again!");
		}
		// We check the role
		else if (user.getRole().getRole().equalsIgnoreCase("doctor")) {
			System.out.println("Welcome doctor " + username + "!");
			doctorMenu();
		} else if (user.getRole().getRole().equalsIgnoreCase("owner")) {
			System.out.println("Welcome owner " + username + "!");
			ownerMenu();
		} else {
			System.out.println("Invalid role.");
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
			System.out.println("4. Admit dog through XML");
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
			case 4:
				admitDogXML();
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
			System.out.println("5. Generate XML");
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
				break;
			case 4:
				checkMedicines(dogId);
				break;
			case 5:
				generateXML(dogId);
				break;
			case 0:
				return;
			default:
				break;
			}
		}
	}

	private static void admitDogXML() throws Exception {
		// Create a JAXBContext
		JAXBContext context = JAXBContext.newInstance(Dog.class);
		// Get the unmarshaller
		Unmarshaller unmarshal = context.createUnmarshaller();
		// Open the file
		File file = null;
		boolean incorrectDog = false;
		do {
			System.out.println("Type the filename for the XML document (expected in the xmls folder):");
			String fileName = reader.readLine();
			file = new File("./xmls/" + fileName);
			try {
				// Create a DocumentBuilderFactory
				DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
				// Set it up so it validates XML documents
				dBF.setValidating(true);
				// Create a DocumentBuilder and an ErrorHandler (to check validity)
				DocumentBuilder builder = dBF.newDocumentBuilder();
				CustomErrorHandler customErrorHandler = new xml.utils.CustomErrorHandler();
				builder.setErrorHandler(customErrorHandler);
				// Parse the XML file and print out the result
				Document doc = builder.parse(file);
				if (!customErrorHandler.isValid()) {
					incorrectDog = true;
				}
			} catch (ParserConfigurationException ex) {
				System.out.println(file + " error while parsing!");
				incorrectDog = true;
			} catch (SAXException ex) {
				System.out.println(file + " was not well-formed!");
				incorrectDog = true;
			} catch (IOException ex) {
				System.out.println(file + " was not accesible!");
				incorrectDog = true;
			}
			
		} while (incorrectDog);
		// Unmarshall the dog from a file
		Dog dog = (Dog) unmarshal.unmarshal(file);
		// Print the dog
		System.out.println("Added to the database: " + dog);
		dogManager.admit(dog);
		// Get the dogId from the database because the XML file doesn't have it
		int dogId = dbManager.getLastId();
		// For each medicine of the dog
		List<Medicine> medicines = dog.getMedicines();
		for (Medicine medicine : medicines) {
			// Give the medicine to the dog
			medicineManager.give(dogId, medicine.getId());
		}
	}

	private static void generateXML(int dogId) throws Exception {
		Dog dog = dogManager.getDog(dogId);
		// Create a JAXBContext
		JAXBContext context = JAXBContext.newInstance(Dog.class);
		// Get the marshaller
		Marshaller marshal = context.createMarshaller();
		// Pretty formatting
		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// Marshall the dog to a file
		File file = new File("./xmls/Output-Dog.xml");
		marshal.marshal(dog, file);
		// Marshall the dog to the screen
		marshal.marshal(dog, System.out);
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
