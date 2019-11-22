package fr.apa;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import fr.apa.compteur.CompteurAbstract;
import fr.apa.compteur.CompteurChaine;
import fr.apa.compteur.CompteurHash;
import fr.apa.compteur.CompteurTableau;

/**
 * TestUnit class, main entry of the program
 * can be launched with different args to run each algorithm
 * Command : java -jar [file exported .jar] [algorithm] [file...] [file...]
 * ex : java -jar Compteur.jar tab fichier.txt test.txt
 * @author dylan @Killax-D
 *
 */
public class TestUnit {

	/**
	 * Static name of each algorithm
	 */
	public static final String TABLEAU = "CompteurTableau";
	public static final String HASH = "CompteurHash";
	public static final String ARBRE = "CompteurArbre";
	public static final String CHAINE = "CompteurChaine";

	/**
	 * Add different input, shortcut to type command faster
	 */
	private static Map<String, String[]> methods = Map.of(
			TABLEAU, new String[] {"tab", "tableau", "compteurtableau", "compteurtab", "cpttab"},
		    HASH, new String[] {"hash", "compteurhash", "cpthash"},
		    CHAINE, new String[] {"chaine", "compteurchaine", "cptchaine"}
		);

	/**
	 * Get raw algorithm name entry and return the static name
	 * @param method
	 * @return static name of method
	 */
	public static String getMethod(String method) {
		for (String key : methods.keySet()) {
			for (String arg : methods.get(key)) {
				if (method.equals(arg))
					return key;
			}
		}
		return "";
	}

	/**
	 * Core of the program, entry point
	 * @param args
	 */
	public static void main(String[] args) {
		// verify if program has enought args to run
		if (args.length >= 2) {
			// get static name of algorithm with raw arg
			String method = getMethod(args[0].toLowerCase());
			
			for (int i = 1; i < args.length; i++) {
				String file = args[i];
				
				CompteurAbstract cpt = null;
				
				if (method != "") { // if method is not undefined
					if (Files.exists(Paths.get(file))) { // if file exist
						switch (method) {
							case TABLEAU:
								cpt = new CompteurTableau(file);
								break;
							case HASH:
								cpt = new CompteurHash(file);
								break;
							case CHAINE:
								cpt = new CompteurChaine(file);
								break;
							default:
								System.err.println(String.format("[WARNING] Method '%s' not implemend yet.", method));
								break;
						}
					} else {
						System.err.println(String.format("[ERROR] File %s not found.", file));
						System.exit(1);
					}
				} else {
					System.err.println(String.format("[ERROR] Method %s not found.", method));
					System.exit(1);
				}
				
				if (cpt != null) { // if method not found cpt is null, so handle this possibility
					cpt.run();
					cpt.show();
				}
			}
		}
		
		System.exit(0); // exit
	}

}
