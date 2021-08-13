package ifc.sd.trabalhofinal.exception;

@SuppressWarnings("serial")
public class FailException extends RuntimeException {
	public FailException () {
		super("Mensagem 2PC: fail");
	}
}
