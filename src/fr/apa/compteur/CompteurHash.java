package fr.apa.compteur;

import fr.apa.compteur.utils.Mot;

/**
 * Hash algorithm, Faster than the others,
 * Collision is not so common, but I make (index + 1) while case is not the word or null 
 * Execution on Ryzen 5 2500U : ~3s
 * @author dylan @Killax-D
 *
 */
public class CompteurHash extends CompteurAbstract {
	
	// use a prime number near the last ASCII lowercase char 'z' - 'a' = 26
    private static final int p = 31; // Prime number near 26
	public final int TAILLE_INITIALE = 100000;
	private int TAILLE_COURANTE;
	private Mot[] elements;
	
	public CompteurHash(String file) {
		super(file);
		TAILLE_COURANTE = TAILLE_INITIALE;
		this.elements = new Mot[TAILLE_INITIALE];
	}

	/**
	 * Herited method, add occurrence at each call
	 */
	@Override
	public void addOccurrence(String word) {
		if (word.length() > 4) { // stock only if length > 4
			nbWords5++;
			setValue(word);
		}
		nbWords++;
	}
	
	/**
	 * If Collision algorithm go out of the tab, need to increase the size.
	 * To be efficient, I multiply the length by 2
	 */
	public void incrementSize() {
		TAILLE_COURANTE = elements.length * 2;
		Mot[] tab = new Mot[TAILLE_COURANTE];
		System.out.println("Increase tab size to " + tab.length);
		for (int i = 0; i < elements.length; i++)
			if(elements[i] != null) {
				int key = computeHash(elements[i].getWord())-1; // recompute each word (different tab size)
				while (tab[++key] != null);
				tab[key] = elements[i];
			}
		elements = tab;
	}
	
	/**
	 * Get the word by it's String value
	 * @param word
	 * @return Mot object
	 */
	public Mot get(String word) {
		int key = computeHash(word)-1; // take the hash and minus 1 to start algorithm with ++value
		while (elements[++key] != null)
			if (elements[key].getWord().equals(word))
				return elements[key];
			else
				if(key == elements.length - 1) {
					incrementSize();
					break;
				}
		return null;
	}
	
	/**
	 * Compute a hash from a String, convert into int to use it has Tab Index
	 * @param str
	 * @return index (int)
	 */
	public int computeHash(String str) {
		long hash = 0;
        long p_pow = 1;
        for (char c : str.toCharArray()) {
            // 'a' - 'a' = 0 -> +1
            // so 'aa' = 1 + 1 = 2
            hash = (hash + (c - 'a' + 1) * p_pow) % TAILLE_COURANTE;
            p_pow = (p_pow * p) % TAILLE_COURANTE;
        }
        return (int) (hash < 0 ? hash * -1 : hash); // avoid negative hash and convert to int index
	}
	
	/**
	 * Put word into the tab with the Hash index, if the index is not empty and it's not the current word
	 * Add + 1, and do a while until it's not the current word or null
	 * @param word
	 */
	public void setValue(String word) {
		Mot m = get(word);
		int key = computeHash(word);
		if(m == null) {
			if(elements[key] != null)
				while (elements[++key] != null);
			elements[key] = new Mot(word);
		} else {
			if(elements[key].getWord().equals(word))
				elements[key].addOccurrence();
			else {
				boolean found = false;
				while (elements[++key] != null)
					if(elements[key].getWord().equals(word)) {
						found = true;
						elements[key].addOccurrence();
						break;
					}
				if(!found)
					elements[key] = new Mot(word);
			}
		}
		
	}

	/**
	 * Get the 10 most occurrences
	 * Sort  a tiny tab with a length of 10
	 * By descending order
	 */
	@Override
	public Mot[] getTop() {
		Mot[] mots = new Mot[10];
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null) {
				for (int j = 0; j < mots.length; j++) {
					if (mots[j] == null) {
						mots[j] = elements[i];
						break;
					}
					else {
						if (elements[i].getOccurrence() > mots[j].getOccurrence()) {
							for (int x = mots.length-1; x > j; x--)
								mots[x] = mots[x-1];
							
							mots[j] = elements[i];
							break;
						}
					}
				}
			}
		}
		return mots;
	}

}
