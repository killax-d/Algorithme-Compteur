package fr.apa.compteur;

import fr.apa.compteur.utils.Mot;
import fr.apa.compteur.utils.MotChaine;

/**
 * LinkedList algorithm, not the fasted one,
 * But it's a good one implemented like that,
 * Execution on Ryzen 5 2500U : ~30s
 * @author dylan @Killax-D
 *
 */
public class CompteurChaine extends CompteurAbstract {

	private MotChaine first;
	
    public CompteurChaine(String file) {
        super(file);
    }

	/**
	 * Herited method, add occurrence at each call
	 */
    @Override
    public void addOccurrence(String word) {
    	if (word.length() > 4) { // stock only if length > 4
	    	if (first == null) {
	    		first = new MotChaine(word);
	    	} else {
		    	MotChaine element = first;
		    	// while the word is not the current or not null
		    	while(element.hasNext() && !element.getInfo().getWord().equals(word))
		    		element = element.getNext();
		    	
		    	if (element.getInfo().getWord().equals(word))
		    		element.getInfo().addOccurrence();
		    	else
		    		element.setNext(word);
	    	}
    		nbWords5++;
    	}
    	nbWords++;
    		
    }

	/**
	 * Get the 10 most occurrences
	 * Sort  a tiny tab with a length of 10
	 * By descending order
	 */
	@Override
	public Mot[] getTop() {
		Mot[] mots = new Mot[10];
		mots[0] = first.getInfo();
		
		MotChaine current = first;
		while((current = current.getNext()) != null) {
			for (int j = 0; j < mots.length; j++) {
				if (mots[j] == null) {
					mots[j] = current.getInfo();
					break;
				}
				else {
					if (current.getInfo().getOccurrence() > mots[j].getOccurrence()) {
						for (int x = mots.length-1; x > j; x--)
							mots[x] = mots[x-1];
						
						mots[j] = current.getInfo();
						break;
					}
				}
			}
		}
		return mots;
	}

}
