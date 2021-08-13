package ifc.sd.trabalhofinal.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ifc.sd.trabalhofinal.exception.ContaNaoEncontradaException;
import ifc.sd.trabalhofinal.exception.FailException;
import ifc.sd.trabalhofinal.model.Acao;
import ifc.sd.trabalhofinal.model.Conta;
import ifc.sd.trabalhofinal.model.Replica;

@RestController
public class ContaController {
	private AtomicInteger contador = new AtomicInteger();
	private ArrayList<Conta> contas = new ArrayList<Conta>();
	private ArrayList<Acao> waLog = new ArrayList<Acao>();
	private ArrayList<Replica> replicas = new ArrayList<Replica>();
	HttpClient client = HttpClient.newHttpClient();

	public ContaController() {
		this.contas.add(new Conta(1234, 100.00));
		this.contas.add(new Conta(4345, 50.00));
		this.contas.add(new Conta(5678, 250.00));

		this.replicas.add(new Replica(1, "http://localhost:8081"));
		this.replicas.add(new Replica(2, "http://localhost:8082"));
	}

	@GetMapping("/contas")
	public ArrayList<Conta> obterContas() {
		return this.contas;
	}

	@GetMapping("/replicas")
	public ArrayList<Replica> obterReplicas() {
		return this.replicas;
	}

	@PostMapping("/contas")
	@ResponseStatus(HttpStatus.CREATED)
	public Acao enviarAcao(@RequestBody Acao acao) throws IOException, InterruptedException {
		for (Conta conta : this.contas) {
			if (conta.getNumero() == acao.getConta()) {
				acao.setId(contador.getAndIncrement());
				waLog.add(acao);

				Map<Object, Object> data = new HashMap<>();

				data.put("id", acao.getId());
				data.put("operacao", acao.getOperacao());
				data.put("conta", acao.getConta());
				data.put("valor", acao.getValor());

				for (Replica replica : this.replicas) {
					HttpRequest request = HttpRequest.newBuilder().POST(buildFormDataFromMap(data))
							.uri(URI.create(replica.getEndpoint())).build();
					HttpResponse<String> response = this.client.send(request, BodyHandlers.ofString());
					if (response.statusCode() == 403) {
						throw new FailException();
					}
				}

				waLog.remove(acao);

				switch (acao.getOperacao()) {
				case "debito":
					conta.setSaldo(conta.getSaldo() - acao.getValor());
					break;
				case "credito":
					conta.setSaldo(conta.getSaldo() + acao.getValor());
					break;
				}
				return acao;
			}
		}
		throw new ContaNaoEncontradaException(acao.getConta());
	}

	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
		var builder = new StringBuilder();
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
			builder.append("=");
			builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}
		System.out.println(builder.toString());
		return HttpRequest.BodyPublishers.ofString(builder.toString());
	}

	@ControllerAdvice
	class Fail {
		@ResponseBody
		@ExceptionHandler(FailException.class)
		@ResponseStatus(HttpStatus.FORBIDDEN)
		String fail(FailException f) {
			return f.getMessage();
		}
	}

	@ControllerAdvice
	class ContaNaoEncontrada {
		@ResponseBody
		@ExceptionHandler(ContaNaoEncontradaException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		String fail(ContaNaoEncontradaException c) {
			return c.getMessage();
		}
	}
}
