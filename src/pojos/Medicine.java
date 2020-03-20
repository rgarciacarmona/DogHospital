package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Medicine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7683312764530492550L;
	private Integer id;
	private String name;
	private List<Dog> dogs;
	
	public Medicine() {
		super();
		dogs = new ArrayList<Dog>();
	}
	
	public Medicine(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
		dogs = new ArrayList<Dog>();
	}

	
	public Medicine(Integer id, String name, List<Dog> dogs) {
		super();
		this.id = id;
		this.name = name;
		this.dogs = dogs;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicine other = (Medicine) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + "]";
	}

	public List<Dog> getDogs() {
		return dogs;
	}

	public void setDogs(List<Dog> dogs) {
		this.dogs = dogs;
	}
	
	
}
