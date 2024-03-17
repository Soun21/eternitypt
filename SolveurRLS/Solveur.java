import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Solveur
{   // Une instance de test
    static ArrayList<Integer> puzzle = new ArrayList<Integer>();
    static int dim;
    static int motifs;

    public static void lire() {
        String cheminFichier = "./eternitypt-main/PROJET_TUTORE/export/export.txt";
        try (BufferedReader lecteur = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            // Lire ligne par ligne jusqu'à la fin du fichier
            while ((ligne = lecteur.readLine()) != null) {
                if (ligne.charAt(0) == '!') {
                    ligne = ligne.substring(1);
                    String[] parties = ligne.split(" ");
                    dim = Integer.parseInt(parties[0]);
                    motifs = Integer.parseInt(parties[1]);
                } else {
                    String[] parties = ligne.split(" ");
                    for (String p : parties) {
                        puzzle.add(Integer.parseInt(p));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Random rand = new Random();
    static double t = 0.20;

    /**
     * Une implémentation de solveur basé sur la montée stochastique.
     * @param maxTrans nombre maximum de transitions autorisée.
     * @return la meilleure configuration trouvée.
     */
    public static Configuration run(int maxTrans, Probleme instance)
    {
        Configuration cur = new ConfigEternity(instance);
        Configuration solution = new ConfigEternity(instance);
        int actualScore = cur.score();
        double score = actualScore;
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
                if (newScore < actualScore) {
                    if (newScore < score) {
                        score = newScore;
                        solution = cur;
                    }
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
        return solution;
    }
    
    public static void main(String[] args)
    {
        lire();
        Probleme instance = new Probleme(dim, motifs, puzzle);
        Configuration resultat = run(50000000, instance);
        ArrayList<Integer> board = instance.getBoard();
        if(resultat.solution())
            System.out.println("Solution trouvée");
        else
            System.out.println("Solution non trouvée");
        System.out.println(resultat);

        try {
			// Création d'un fileWriter pour écrire dans un fichier
			FileWriter fileWriter = new FileWriter("./eternitypt-main/PROJET_TUTORE/export/export_rls.txt", false);

			// Création d'un bufferedWriter qui utilise le fileWriter
			BufferedWriter writer = new BufferedWriter(fileWriter);

			// ajout d'un texte à notre fichier
			writer.write("!" + dim + " " + motifs);
			// Retour à la ligne
			writer.newLine();
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    for (int m = 0; m < 4; m++) {
                        writer.write(board.get(i * dim * 4 + j * 4 + m) + " ");
                    }
                    writer.write("\n");
                }
            }
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}