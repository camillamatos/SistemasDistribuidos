package rmiBerkeley;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
    public Integer getIdentificador() throws RemoteException;
    public void setIdentificador(Integer identificador) throws RemoteException;
    
    public Boolean getDisponibilidade() throws RemoteException;
    public void setDisponibilidade(Boolean disponibilidade) throws RemoteException;
    
    public Boolean getCoordenador() throws RemoteException;
    public void setCoordenador(Boolean coordenador) throws RemoteException;
    
    public RMIInterface getSucessor() throws RemoteException;
    public void setSucessor(RMIInterface sucessor) throws RemoteException;
}