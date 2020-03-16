package db.interfaces;

import java.sql.Date;
import java.util.List;

import pojos.*;

public interface DogManager {

	public void admit(Dog dog);
	public void releaseDate(Date date);
	public void update(Dog dog);
	public List<Dog> searchByName(String name);
	public List<Dog> searchByBreed(String breed);

}
