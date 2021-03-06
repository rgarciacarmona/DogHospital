package db.interfaces;

public interface DBManager {

	public void connect();
	public void disconnect();
	public void createTables();
	
	public DogManager getDogManager();
	public MedicineManager getMedicineManager();
	
	public int getLastId();
	
}
