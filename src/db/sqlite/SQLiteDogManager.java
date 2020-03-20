package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.DogManager;
import pojos.Dog;
import pojos.Medicine;

public class SQLiteDogManager implements DogManager {

	private Connection c;

	public SQLiteDogManager(Connection c) {
		this.c = c;
	}

	@Override
	public void admit(Dog dog) {
		// Insert the provided dog
		try {
			String sql = "INSERT INTO dogs (name, breed , weight , admissionDate, releaseDate) "
					+ "VALUES (?,?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, dog.getName());
			prep.setString(2, dog.getBreed());
			prep.setFloat(3, dog.getWeight());
			prep.setDate(4, dog.getAdmissionDate());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void releaseDate(int dogId, Date date) {
		try {
			// Set the release date for a dog
			String sql = "UPDATE dogs SET releaseDate=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setDate(1, date);
			s.setInt(2, dogId);
			s.executeUpdate();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Dog dog) {
		try {
			// Update every aspect of a particular dog
			String sql = "UPDATE dogs SET name=?, breed=?, weight=?, admissionDate=?, releaseDate=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setString(1, dog.getName());
			s.setString(2, dog.getBreed());
			s.setFloat(3, dog.getWeight());
			s.setDate(4, dog.getAdmissionDate());
			s.setDate(5, dog.getReleaseDate());
			s.setInt(6, dog.getId());
			s.executeUpdate();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Dog getDog(int dogId) {
		// Get the dog AND the medicines for the dog
		Dog newDog = null;
		try {
			String sql = "SELECT * FROM dogs AS d JOIN dogsMedicines AS dm ON d.id = dm.dogId"
					+ " JOIN medicines AS m ON dm.medicineId = m.id" + " WHERE d.id = ?";
			// Get the joining of three tables
			// id, name, breed, weight, admissionDate, releaseDate, dogId, medicineId, id,
			// name
			// EXAMPLE
			// 1, Lassie, Collie, 10, 2020-06-01, 2020-08-01, 1, 1, 1, Dalsi
			// 1, Lassie, Collie, 10, 2020-06-01, 2020-08-01, 1, 2, 2, Dogmotril
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, dogId);
			ResultSet rs = p.executeQuery();
			List<Medicine> medicinesList = new ArrayList<Medicine>();
			// WHEN YOU DO A JOIN WITH SQLITE YOU CANNOT USE COLUMN NAMES
			// YOU MUST USE NUMBERS INSTEAD
			// THIS IS BECAUSE SQLITE DOESN'T SUPPORT JOINS WITH ALIASES
			// To avoid creating the dog several times
			boolean dogCreated = false;
			while (rs.next()) {
				// If the dog has not been created
				if (!dogCreated) {
					// Get the dog information
					int newDogId = rs.getInt(1);
					String dogName = rs.getString(2);
					String breed = rs.getString(3);
					float weight = rs.getFloat(4);
					Date admissionDate = rs.getDate(5);
					Date releaseDate = rs.getDate(6);
					// Create a new dog
					newDog = new Dog(newDogId, dogName, breed, weight, admissionDate, releaseDate);
					dogCreated = true;
				}
				// Get the medicine information
				int medicineId = rs.getInt(9);
				String medicineName = rs.getString(10);
				Medicine newMedicine = new Medicine(medicineId, medicineName);
				medicinesList.add(newMedicine);
			}
			newDog.setMedicines(medicinesList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newDog;
	}

	@Override
	public List<Dog> searchByName(String name) {
		// Create an empty list of dogs
		List<Dog> dogsList = new ArrayList<Dog>();
		// Search for all dogs that fit the name
		try {
			String sql = "SELECT * FROM dogs WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			// ANY CHARACTERS + name + ANY CHARACTERS
			prep.setString(1, "%" + name + "%");
			ResultSet rs = prep.executeQuery();
			// For each result...
			while (rs.next()) {
				int id = rs.getInt("id");
				String dogName = rs.getString("name");
				String breed = rs.getString("breed");
				float weight = rs.getFloat("weight");
				Date admissionDate = rs.getDate("admissionDate");
				Date releaseDate = rs.getDate("releaseDate");
				// Create a new dog and...
				Dog newDog = new Dog(id, dogName, breed, weight, admissionDate, releaseDate);
				// Add it to the list
				dogsList.add(newDog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the list
		return dogsList;
	}

	@Override
	public List<Dog> searchByBreed(String breed) {
		// Create an empty list of dogs
		List<Dog> dogsList = new ArrayList<Dog>();
		// Search for all dogs that fit the name
		try {
			String sql = "SELECT * FROM dogs WHERE breed LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			// ANY CHARACTERS + name + ANY CHARACTERS
			prep.setString(1, "%" + breed + "%");
			ResultSet rs = prep.executeQuery();
			// For each result...
			while (rs.next()) {
				int id = rs.getInt("id");
				String dogName = rs.getString("name");
				String dogBreed = rs.getString("breed");
				float weight = rs.getFloat("weight");
				Date admissionDate = rs.getDate("admissionDate");
				Date releaseDate = rs.getDate("releaseDate");
				// Create a new dog and...
				Dog newDog = new Dog(id, dogName, dogBreed, weight, admissionDate, releaseDate);
				// Add it to the list
				dogsList.add(newDog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the list
		return dogsList;
	}

}
