package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.DogManager;
import pojos.Dog;

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
	public void releaseDate(Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Dog dog) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Dog> searchByName(String name) {
		// Create an empty list of dogs
		List<Dog> dogsList = new ArrayList<Dog>();
		// Search for all dogs that fit the name
		try {
			String sql = "SELECT * FROM dogs WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%" + name + "%");
			ResultSet rs = prep.executeQuery();
			// For each result...
			while (rs.next()) {
				int id = rs.getInt("id");
				String dogName = rs.getString("name");
				String breed = rs.getString("breed");
				float weight = rs.getFloat("weight");
				Date admissionDate = rs.getDate("admissionDate");
				// Create a new dog and...
				Dog newDog = new Dog(id, dogName, breed, weight, admissionDate);
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
		// TODO Auto-generated method stub
		return null;
	}

}
