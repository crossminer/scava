package m3;

public class BuildException extends Exception {
	private static final long serialVersionUID = 1L;

	public BuildException(String message) {
		super(message);
	}
	
	public BuildException(String message, Throwable cause) {
		super(message, cause);
	}
}
