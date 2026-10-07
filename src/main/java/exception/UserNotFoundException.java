package exception;

public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException(int userId) {
		super("User not found( user id:"+ userId+" )");
	}
	
	public UserNotFoundException(String userEmail, String password) {
		super("User not found( email: " + userEmail+ " | password: " + password+" )");
	}

}
