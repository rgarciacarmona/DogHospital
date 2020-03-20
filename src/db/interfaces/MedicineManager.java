package db.interfaces;

import java.util.List;
import pojos.*;

public interface MedicineManager {

	public void give(int dogId, int medicineId);
	public List<Medicine> showMedicines();
}
