package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.MedicineManager;
import pojos.Dog;
import pojos.Medicine;

public class SQLiteMedicineManager implements MedicineManager {

	private Connection c;

	public SQLiteMedicineManager(Connection c) {
		this.c = c;
	}

	@Override
	public void give(int dogId, int medicineId) {
		// Link a dog with a medicine
		try {
			String sql = "INSERT INTO dogsMedicines (dogId, medicineId) "
					+ "VALUES (?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, dogId);
			prep.setInt(2, medicineId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Medicine> showMedicines() {
		// Create an empty list of medicines
		List<Medicine> medicinesList = new ArrayList<Medicine>();
		// Get all the medicines
		try {
			String sql = "SELECT * FROM medicines";
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			// For each result...
			while (rs.next()) {
				int id = rs.getInt("id");
				String medicineName = rs.getString("name");
				// Create a new medicine and...
				Medicine newMedicine = new Medicine(id, medicineName);
				// Add it to the list
				medicinesList.add(newMedicine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the list
		return medicinesList;
	}

}
