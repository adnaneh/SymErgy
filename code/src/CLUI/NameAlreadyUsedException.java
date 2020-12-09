package CLUI;

@SuppressWarnings("serial")
public class NameAlreadyUsedException extends Exception {
	public NameAlreadyUsedException(String message){
		super(message);
	}
}
