package fr.apa.compteur;

import fr.apa.compteur.utils.ABR;
import fr.apa.compteur.utils.Mot;

import java.util.ArrayList;

/**
 * 
 * @author fabien
 *
 */
public class CompteurArbre extends CompteurAbstract {

    public ABR arbre;

    /**
     * Initialize file and read file
     *
     * @param file1 file you want to count the number of word
     */
    public CompteurArbre(String file) {
        super(file);
    }

    /**
     * it uses a stack
     *
     * @param word the word you want to increment the occurence
     */
    @Override
    public void addOccurrence(String word) {
        ArrayList<ABR> stack = new ArrayList<>();
        ABR current;

        if (arbre == null) {
            arbre = new ABR(word);
            nbWords5++;
            return;
        } else getPath(word, stack, arbre);

        // while we have to look for something
        while (!stack.isEmpty()) {

            // get the first element of stack
            current = stack.remove(0);

            // if it is our word
            if (current.getWord().equals(word)) {
                current.setOccurence(current.getOccurence() + 1); // increment it
                return; // stop everything
            }

            getPath(word, stack, current);
        }

        // we didn't find it
        arbre.insert(word);
        nbWords5++;
    }

    private void getPath(String word, ArrayList<ABR> stack, ABR current) {
        if (current.getGauche() != null && arbre.getWord().charAt(0) <= word.charAt(0)) {
            stack.add(current.getGauche());
        } else if (current.getDroite() != null && arbre.getWord().charAt(0) > word.charAt(0)) {
            stack.add(current.getDroite());
        }
    }

	@Override
	public Mot[] getTop() {
		// TO-DO
		return null;
	}

}
