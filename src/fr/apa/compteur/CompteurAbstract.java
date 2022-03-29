package fr.apa.compteur;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.apa.compteur.utils.Mot;

/**
 * Super class for each algorithm,
 * 
 * @author dylan @Killax-D
 *
 */
public abstract class CompteurAbstract {

	private String fileName; // filePath for reading state
	protected int nbWords; // total words with double
	protected int nbWords5; // different words count

	/**
	 * used to estimate execution time
	 */
	private long startTime;
	private long endTime;

	/**
	 * abstract method, add occurrence for each word
	 * 
	 * @param word the word you want to increment the occurence
	 */
	public abstract void addOccurrence(String word);

	/**
	 * Get 10 most occurrences
	 */
	public abstract Mot[] getTop();

	/**
	 * Initialize file and read file
	 * 
	 * @param file1 file you want to count the number of word
	 */
	public CompteurAbstract(String file) {
		nbWords = 0;
		nbWords5 = 0;
		this.fileName = file;
		this.startTime = 0;
		this.endTime = 0;
	}

	public void run() {
		try {
			System.out.println(String.format("Running %s...", getClass().getSimpleName()));
			// capture time before run
			startTime = System.nanoTime();

			read(); // start algorithm

			// capture time after run
			endTime = System.nanoTime();

			// show execution time
			showExecution();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show execution time
	 */
	private void showExecution() {
		// print execution time with different words count (nbWords5)
		System.out.println(
				String.format("Execution %s ended with %d words in \t %.2f s : %.2f ms", getClass().getSimpleName(),
						nbWords5, (float) (endTime - startTime) / 1000000000, (float) (endTime - startTime) / 1000000));
	}

	/**
	 * Read file and add occurence
	 * 
	 * @throws FileNotFoundException wanted file not found
	 */
	public void read() throws FileNotFoundException {
		File file = new File(fileName); // open new file

		/**
		 * UTF-8 encoding is important if we compile runnable jar
		 */
		Scanner scanner = new Scanner(file, "UTF-8"); // open a new scanner with UTF-8 encoding
		scanner.useDelimiter("\\n+|\\s+|\\.|\\,|\\)|\\(|\\[|\\]"); // set delimiter with space and line return (and all punctuation)

		/**
		 * Pattern for Word-Break :
		 * [a-z\\-'éàèîïôöêëœç]+|[a-zéàèîïôöêëœç]+\\-\\s\\n[a-zéàèîïôöêëœç]+ String
		 * fixer : replaceAll("\\-\\s\\n|\\-\\n", ""); Removed in subject Updated at :
		 * 25 December 2019
		 */
		// create a match pattern to get all word (word with numeric are not technically
		// "word", so escape any number in pattern)
		Pattern pattern = Pattern.compile("[a-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœ'-]+", // more special characters
				Pattern.CASE_INSENSITIVE);

		String word = null;
		
		while (scanner.hasNext()) {
			if (scanner.hasNext(pattern)) {
				word = scanner.next(pattern).toLowerCase();
				
				if (word.contains("-") || word.contains("'")) {
					String[] words = word.split("(')|(-)");
					for (String w : words)
						addOccurrence(w);
				} else
					addOccurrence(word);

			} else
				scanner.next(); // not matching, skip this word (numeric in word issue mostly)
		}

		scanner.close(); // close scanner
	}

	/**
	 * Show the result
	 */
	public void show() {
		System.out
				.println(String.format("Fichier : %s\n" + "Nombre de mots : %d\n" + "Nombre de mots de taille > 4 : %d",
						fileName, nbWords, nbWords5));
		System.out.println("----------\n" + "Mot les plus fréquents :");
		for (Mot m : getTop()) {
			if (m != null)
				System.out.println(m.getWord() + "\t" + m.getOccurrence());
		}

	}
}
