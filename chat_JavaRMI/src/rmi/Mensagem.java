package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Mensagem implements MensagemDistribuido{
	 private ArrayList<String> mensagem = new ArrayList<String>();
	    
	 public void enviar(String message) throws RemoteException {
    	this.mensagem.add(message);
    }
    
    public int contarMensagens() throws RemoteException {
    	return this.mensagem.size();
    }

    public String ler() throws RemoteException {
    	return this.mensagem.get(this.mensagem.size() - 1);
    }
}