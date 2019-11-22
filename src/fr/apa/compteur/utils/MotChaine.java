package fr.apa.compteur.utils;

/**
 * MotChaine, It's the word object but for linked list
 * @author dylan @Killax-D
 *
 */
public class MotChaine {
	
	private Mot word;
	private MotChaine next;

	public MotChaine(String word, MotChaine next) {
		this.word = new Mot(word);
		this.next = next;
	}

	public MotChaine(String word) {
		this(word, null);
	}

	/**
	 * Retrieve classic word object
	 * @return
	 */
	public Mot getInfo() {
		return word;
	}

	/**
	 * Get next element of the linked list
	 */
	public MotChaine getNext() {
		return next;
	}

	/**
	 * Set the next element
	 * @param word
	 */
	public void setNext(String word) {
		this.next = new MotChaine(word);
	}
	
	/**
	 * Check if it's the end of the linked list
	 * @return if the next element is not null
	 */
	public boolean hasNext() {
		return this.next != null;
	}
}
