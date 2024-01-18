package srcs.persistance;

import srcs.banque.Compte;

import java.io.*;

public class PersistanceSauvegardable {

    public static void save(String fichier, Sauvegardable s) throws IOException {

        FileOutputStream fos = new FileOutputStream(fichier);
            s.save(fos);

    }

    public static Sauvegardable load(String fichier) throws IOException {
        FileInputStream fis = new FileInputStream(fichier);
        return new Compte(fis);

    }
}
