package rmiBerkeley;
import java.rmi.RemoteException;

public class Processo implements RMIInterface {
    Integer identificador;
    Boolean disponibilidade; 
    Boolean coordenador; 
    RMIInterface sucessor; 

    public Integer getIdentificador() throws RemoteException {
    	return this.identificador;
    }
    
    public void setIdentificador(Integer identificador) throws RemoteException {
        this.identificador = identificador;
    }

    public Boolean getDisponibilidade() throws RemoteException {
        return this.disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) throws RemoteException {
        this.disponibilidade = disponibilidade;
    }

    public void setCoordenador(Boolean coordenador) throws RemoteException {
        this.coordenador = coordenador;
    }

    public Boolean getCoordenador() throws RemoteException {
        return this.coordenador;
    }

    public void setSucessor(RMIInterface sucessor) throws RemoteException {
        this.sucessor = sucessor;
    }

    public RMIInterface getSucessor() throws RemoteException{
        return this.sucessor;
    }

}
