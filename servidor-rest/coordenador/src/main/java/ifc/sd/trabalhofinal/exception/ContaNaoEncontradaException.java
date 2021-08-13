package ifc.sd.trabalhofinal.exception;

@SuppressWarnings("serial")
public class ContaNaoEncontradaException extends RuntimeException {
	public ContaNaoEncontradaException (int conta) {
		super("A conta de número: " + conta + " não foi encontrada");
	}
}
