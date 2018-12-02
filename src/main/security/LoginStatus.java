package security;

public class LoginStatus {
	private boolean success;
	private String message;

	public LoginStatus(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
