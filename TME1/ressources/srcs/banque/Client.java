package srcs.banque;

import srcs.persistance.Sauvegardable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Client implements Sauvegardable {

	
	private final String nom;
	private final Compte compte;

	
	public Client(String nom, Compte compte) {
		this.nom=nom;
		this.compte=compte;

	}
		
	public String getNom() {
		return nom;
	}


	public Compte getCompte() {
		return compte;
	}

	@Override
	public boolean equals(Object o) {
		if(o==this) return true;
		if(o==null) return false;
		if(!(o instanceof Compte)) return false;
		Client other= (Client) o;
		return other.nom.equals(nom);
	}

	@Override
	public void save(OutputStream out) throws IOException {

		DataOutputStream dos = new DataOutputStream(out);

		dos.writeUTF(nom);
		compte.save(out);
//		dos.writeUTF(compte.getId());
//		dos.writeDouble(compte.getSolde());
	}
}
