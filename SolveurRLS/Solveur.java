import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Solveur
{   // Une instance de test
    static ArrayList<Integer> puzzle = new ArrayList<Integer>();
    static int dim;
    static int motifs;

    public static void lire() {
        String cheminFichier = "../eternitycplusplus/eternity/export/export.txt";
        try (BufferedReader lecteur = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            // Lire ligne par ligne jusqu'à la fin du fichier
            while ((ligne = lecteur.readLine()) != null) {
                if(ligne.charAt(0) == '!'){
                    ligne = ligne.substring(1);
                    String[] parties = ligne.split(" ");
                    dim = Integer.parseInt(parties[0]);
                    motifs = Integer.parseInt(parties[1]);
                }
                else{
                    String[] parties = ligne.split(" ");
                    for (String p : parties){
                        puzzle.add(Integer.parseInt(p));
                    }
                }
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    static Random rand = new Random();
    static double t = 0.5;





    /**
     * Une implémentation de solveur basé sur la montée stochastique.
     * @param maxTrans nombre maximum de transitions autorisée.
     * @return la meilleure configuration trouvée.
     */
    public static Configuration run(int maxTrans, Probleme instance)
    {
        Configuration cur = new ConfigEternity(instance);
        int actualScore = cur.score();
        System.out.println("Score initial : " + actualScore);
        for(int i=0; i<maxTrans; i++)
        {
            if(cur.solution()) return cur;
            int candidat = cur.randomVoisin();
            int newScore = cur.scoreVoisin(candidat);
            double r = rand.nextDouble();
            if(newScore <= actualScore)
            {
                cur.selectVoisin(candidat);
                if(newScore < actualScore){
                    actualScore = newScore;
                    System.out.println(i + " : " + cur.score());
                }
            }
            else if(r <= Math.exp( (actualScore - newScore)/t )){
                cur.selectVoisin(candidat);
                actualScore = newScore;
                System.out.println(i + " : " + cur.score());
                }
        }
        return cur;
    }
    
    public static void main(String[] args)
    {
        lire();
        Probleme instance = new Probleme(dim, motifs, puzzle);
        Configuration resultat = run(10000000, instance);
        if(resultat.solution())
            System.out.println("Solution trouvée");
        else
            System.out.println("Solution non trouvée");
        System.out.println(resultat);
    }
}