package ifc.sd.replica.exception;

@SuppressWarnings("serial")
public class NoException extends RuntimeException {
	public NoException() {
		super("Resposta 2PC: no");
	}
}
