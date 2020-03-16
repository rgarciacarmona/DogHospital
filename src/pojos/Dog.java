package pojos;

import java.io.Serializable;
import java.sql.Date;

public class Dog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6891296751142184360L;
	
	private Integer id;
	private String name;
	private String breed;
	private Float weight;
	private Date admissionDate;
	private Date releaseDate;
	
	
	public Dog(Integer id, String name, String breed, Float weight, Date admissionDate) {
		super();
		this.id = id;
		this.name = name;
		this.breed = breed;
		this.weight = weight;
		this.admissionDate = admissionDate;
	}
	
	

	public Dog(Integer id, String name, String breed, Float weight, Date admissionDate, Date releaseDate) {
		super();
		this.id = id;
		this.name = name;
		this.breed = breed;
		this.weight = weight;
		this.admissionDate = admissionDate;
		this.releaseDate = releaseDate;
	}



	public Dog(String name, String breed, Float weight, Date admissionDate) {
		super();
		this.name = name;
		this.breed = breed;
		this.weight = weight;
		this.admissionDate = admissionDate;
	}



	public Dog() {
		super();
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

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Dog [id=" + id + ", name=" + name + ", breed=" + breed + ", weight=" + weight + ", admissionDate="
				+ admissionDate + ", releaseDate=" + releaseDate + "]";
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
		Dog other = (Dog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
