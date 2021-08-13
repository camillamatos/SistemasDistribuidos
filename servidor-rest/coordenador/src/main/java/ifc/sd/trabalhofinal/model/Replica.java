package ifc.sd.trabalhofinal.model;

public class Replica {
	private int id;
	private String endpoint;

	public Replica(int id, String endpoint) {
		super();
		this.id = id;
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
