import java.util.Random;
/**
 * Méthodes à implémenter dans toute classe représentant le paysage
 * de recherche d'un problème particulier.
 */
public interface Configuration
{
    static Random genrand = new Random();
    /**
     * Les voisins d'une configuration sont identifiés par des entiers
     * compris entre 0 et le nombre de voisins moins un.
     * @return le nombre de voisins de la configutation courante
    */
    @SuppressWarnings("unused")
    int nbrVoisins();

    /**
     * @return Le score du voisin numéro i de la configuration courante
     */
    int scoreVoisin(int i);

    /**
     * @return Le score de la configuration courantet.
     */
    int score();

    String toString();

    /**
     * Transforme la configuration courante en une configuration aléatoire
     */

    /**
     * Transforme la configuration courante en un de ses voisins
     * @param i le numéro du voisin choisi
     */
    void selectVoisin(int i);

    /**
     * @return Le numéro d'un voisin choisi aléatoirement, avec une
     * distribution de probabilité qui devra être documentée pour chaque
     * implémentation.
     */
    int randomVoisin();

    /**
     * @return true si et seulement si la configuration courante est
     * une solution
     */
    boolean solution();
}