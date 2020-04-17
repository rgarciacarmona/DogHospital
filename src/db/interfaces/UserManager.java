package db.interfaces;

import java.util.List;

import pojos.users.*;

public interface UserManager {

	public void connect();
	public void disconnect();
	public void createUser(User user);
	public void createRole(Role role);
	public Role getRole(int id);
	public List<Role> getRoles();
	public User checkPassword(String username, String password);
	
}
