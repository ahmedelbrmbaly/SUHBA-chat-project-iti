public interface UserDAO {

	// Client Operations:
	User getUserById (int id);
	User getUserByPhone (String phone);
	String getUserStatusById (int id);
	String getUserDisplayNameById (int id);

	boolean addNewUser(User user);                 //also in server

	boolean updateUserProfileById(int id);
	boolean updateUserPasswordById(int id);

	// Server Operations
	boolean deleteUserById(int id);
	List<User> getAllUsers ();
	List<User> getUsersByCountry(String country);
	List<User> getUsersByStatus (String status);
	List<User> getUsersByGender (String gender);

}