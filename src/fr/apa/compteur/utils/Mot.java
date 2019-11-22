package fr.apa.compteur.utils;

/**
 * Mot object class, stock word and occurrences of each word
 * @author dylan @Killax-D
 *
 */
public class Mot {
	
	private String word;
	private int occurrence;
	
	public Mot(String word) {
		this.word = word;
		this.occurrence = 1;
	}
	
	public void addOccurrence() {
		occurrence++;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getOccurrence() {
		return occurrence;
	}
	
}
