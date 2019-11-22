package fr.apa.compteur;

import fr.apa.compteur.utils.Mot;

/**
 * Algorithm with normal tab in Java, sorted by the ASCII position of the first letter
 * letter = str[0] - 'a': 
 * >= 0 && <= 26 --> at the start
 * < 0 && > 26 --> at the end
 * Execution on Ryzen 5 2500U : ~30s
 * @author dylan @Killax-D
 *
 */
public class CompteurTableau extends CompteurAbstract {

	public final int TAILLE_INITIALE = 100;
	private Mot[] elements;
	private int nbElements;

	public CompteurTableau(String file) {
		super(file);
		this.elements = new Mot[TAILLE_INITIALE];
		nbElements = 0;
	}

	/**
	 * Herited method, add occurrence at each call
	 */
	@Override
	public void addOccurrence(String word) {
		if(word.length() > 4) { // stock only word with size > 4
			nbWords5++;
			Mot w = get(word);
			if (w == null) {
				// word not in list, add a different word for size
				nbElements++;
	
				if (nbElements == elements.length) // if tab is full
					increaseTabSize(); // then multiply size by 2
	
				add(word); // and add the word to the requested place
			} else
				w.addOccurrence();
		}
		
		nbWords++;
	}

	private void add(String word) {
		if (nbElements <= 3) { // important to get at least 3 items to swap for the sorting algorithm
			elements[nbElements-1] = new Mot(word);
			return;
		}
		int charId = word.charAt(0) - 'a';
		// it's between 'a' and 'z', then add in correct place
		if (charId >= 0 && charId <= 26) {
			for (int i = 0; i < nbElements; i++) {
				if (elements[i] == null) {
					elements[i] = new Mot(word);
					break;
				}
				if (charId <= elements[i].getWord().charAt(0) - 'a') {
					Mot pivot = elements[i];
					for (int j = i + 1; j <= nbElements; j++) {
						Mot tmp = elements[j];
						elements[j] = pivot;
						pivot = tmp;
					}
					elements[i] = new Mot(word);
					return;
				}
			}
		}
		// not between 'a' and 'z' then add to the end
		else
			elements[nbElements - 1] = new Mot(word);
	}

	/**
	 * check if word exist in the current tab
	 * 
	 * @param word
	 * @return Mot
	 */
	private Mot get(String word) {
		int charId = word.charAt(0) - 'a';
		if (charId > 0 && charId < 13) {
			// if charId is between 0 and 13, then search at the start
			for (int i = 0; i < nbElements; i++)
				if (elements[i] != null && elements[i].getWord().equals(word))
					return elements[i];
		} else {
			// else then search at the end
			for (int j = nbElements; j >= 0; j--)
				if (elements[j] != null && elements[j].getWord().equals(word))
					return elements[j];
		}
		// not in tab
		return null;
	}

	/**
	 * increase the tab size by 2
	 */
	private void increaseTabSize() {
		Mot[] dst = new Mot[elements.length * 2];
		System.arraycopy(elements, 0, dst, 0, dst.length);
		elements = dst;
		
		Mot[] tab = new Mot[elements.length * 2]; // create new tab with length * 2

		// put old value
		for (int i = 0; i < elements.length; i++) {
			tab[i] = elements[i];
		}

		// set the new tab for algorithm
		elements = tab;
	}

	/**
	 * Sort algorithm
	 * @param low position to start
	 * @param high position to end
	 * @return tab of elements
	 */
	public Mot[] sort(int low, int high) {
		int i = low;
		int j = high;
		Mot tmp;
		int middle = elements[(low + high) / 2].getOccurrence();

		while (i < j) {
			while (elements[i].getOccurrence() > middle) {
				i++;
			}
			while (elements[j].getOccurrence() < middle) {
				j--;
			}
			if (j >= i) {
				tmp = elements[i];
				elements[i] = elements[j];
				elements[j] = tmp;
				i++;
				j--;
			}
		}

		if (low < j) {
			sort(low, j);
		}
		if (i < high) {
			sort(i, high);
		}
		return elements;
	}

	/**
	 * retrieve words
	 * @return tab with each words (Mot object)
	 */
	public Mot[] getTab() {
		return elements;
	}
	
	/**
	 * Get the 10 most occurrences
	 */
	@Override
	public Mot[] getTop() {
		sort(0, nbElements-1);
		Mot[] mots = new Mot[10];
		for (int i = 0; i < 10; i++) {
			mots[i] = elements[i];
		}
		return mots;
	}
}
