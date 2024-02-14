import java.util.Random;
import java.util.ArrayList;
/**
 * Les données d'une instances du problème du sac à dos
 */
public class Probleme
{
    static Random genr = new Random();
    int dimension; 
    int nbMotifs;  
    ArrayList<Integer> board;    

    public Probleme(int dim, int motifs, ArrayList<Integer> puzzle)
    {
        this.dimension = dim; 
        this.nbMotifs = motifs;
        this.board = puzzle;
    }

    public String toString()
    {
        StringBuilder out = new StringBuilder();
        return out.toString();
    }

    int getDimension()
    {
        return dimension;
    }
    int getNbMotifs()
    {
        return nbMotifs;
    }
    ArrayList<Integer> getBoard(){
        return board;
    }
}