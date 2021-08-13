package ifc.sd.replica.controller;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ifc.sd.replica.exception.NoException;
import ifc.sd.replica.model.Acao;
import ifc.sd.replica.model.Conta;

@RestController
@RequestMapping("/contas")
public class ContaController {
	private ArrayList<Conta> contas = new ArrayList<Conta>();
	private ArrayList<Acao> waLog = new ArrayList<Acao>();

	public ContaController() {
		contas.add(new Conta(1234, 100.00));
		contas.add(new Conta(4345, 50.00));
		contas.add(new Conta(5678, 250.00));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Acao enviarAcao(Acao acao) {
		this.waLog.add(acao);
		Random r = new Random();
		int erro = r.nextInt(10);
		if (erro > 6) {
			throw new NoException();
		}
		for (Conta conta : this.contas) {
			if (conta.getNumero() == acao.getConta()) {
				switch (acao.getOperacao()) {
				case "debito":
					conta.setSaldo(conta.getSaldo() - acao.getValor());
					break;
				case "credito":
					conta.setSaldo(conta.getSaldo() + acao.getValor());
					break;
				}
			}
		}
		return acao;
	}

	@ControllerAdvice
	class Fail {
		@ResponseBody
		@ExceptionHandler(NoException.class)
		@ResponseStatus(HttpStatus.FORBIDDEN)
		String no(NoException n) {
			return n.getMessage();
		}
	}
}
