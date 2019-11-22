package fr.apa.compteur.utils;

import java.util.ArrayList;
/**
 * 
 * @author fabien
 *
 */
public class ABR {

    private String word;

    private int occurence;

    private ABR gauche, droite;

    private int size = 0;

    private int height = -1;

    private static boolean changed = true;

    public ABR(String word) {
        this.word = word;
        this.occurence = 1;
        size++;
    }

    public ABR(String word, int occurence) {
        this.word = word;
        this.occurence = occurence;
        size++;
    }

    public void insert(String word) {
        ABR current = this, parent = this;

        while (current != null) {
            parent = current;
            if (current.getWord().charAt(0) <= word.charAt(0)) {
                current = current.getGauche();
            } else {
                current = current.getDroite();
            }
        }
        if (parent.getWord().charAt(0) <= word.charAt(0)) {
            parent.setGauche(new ABR(word));
        } else {
            parent.setDroite(new ABR(word));
        }
    }

    public int hauteur() {
        if (changed) {
            ArrayList<ABR> stack = new ArrayList<>();
            stack.add(this);
            int hauteur = 0;
            while (!stack.isEmpty()) {
                ABR current = stack.remove(0);
                if (current.getGauche() != null || current.getDroite() != null) {
                    hauteur++;
                }
                if (current.getGauche() != null) {
                    stack.add(current.getGauche());
                }
                if (current.getDroite() != null) {
                    stack.add(current.getDroite());
                }
            }
            height = hauteur;
        }
        changed = false;
        return height;
    }

    /*
    public static ABR balance(ABR a) {
        int left = a.getGauche().hauteur(), right = a.getDroite().hauteur();
        if (left - right == 2) {
            if (a.getGauche().getGauche().hauteur() < a.getGauche().getDroite().hauteur()) {
                a.setGauche(rotationG(a.getGauche()));
            }
            return rotationD(a);
        }
        if (left - right == -2) {
            if (a.getGauche().getGauche().hauteur() < a.getGauche().getDroite().hauteur()) {
                a.setGauche(rotationG(a.getGauche()));
            }
            return rotationD(a);
        }
        return a;
    }
     */

    /*
    public static ABR rotationG(ABR a) {
        ABR droite = a.getDroite(); // le fils droit de l'arbre

        ABR temp = new ABR(a.getWord(), a.getOccurence());
        temp.setGauche(a.getGauche());
        temp.setDroite(droite.getGauche());

        ABR ret = new ABR(droite.getWord(), droite.getOccurence());
        ret.setGauche(temp);
        ret.setDroite(droite.getDroite());

        return ret;
    }
     */

    /*
    public static ABR rotationD(ABR a) {
        ABR gauche = a.getGauche(); // le fils droit de l'arbre

        ABR temp = new ABR(a.getWord(), a.getOccurence());
        temp.setDroite(a.getDroite());
        temp.setGauche(gauche.getDroite());

        ABR ret = new ABR(gauche.getWord(), gauche.getOccurence());
        ret.setDroite(temp);
        ret.setGauche(gauche.getGauche());

        return ret;
    }
     */

    public int size() {
        return size;
    }

    public String getWord() {
        return word;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    public ABR getGauche() {
        return gauche;
    }

    public ABR getDroite() {
        return droite;
    }

    public void setGauche(ABR gauche) {
        changed = true;
        this.gauche = gauche;
        size++;
    }

    public void setDroite(ABR droite) {
        changed = true;
        this.droite = droite;
        size++;
    }
}
