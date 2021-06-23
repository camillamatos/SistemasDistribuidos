package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MensagemDistribuido extends Remote{
	public void enviar(String message) throws RemoteException;
    public int contarMensagens() throws RemoteException;
    public String ler() throws RemoteException;
}