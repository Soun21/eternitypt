import java.util.ArrayList;
import java.util.Random;

public class ConfigEternity implements Configuration
{
    static Random genrand = new Random();

    int dimensions;
    int nbMotifs;
    ArrayList<Integer> board;  
    int scoreCourant; // Score de la configuration courante
    Probleme inst;       // Instance de problème à résoudre


    public ConfigEternity(Probleme m)
    {
        inst = m;
        dimensions = inst.getDimension();
        nbMotifs = inst.getNbMotifs();
        board = inst.getBoard();
        scoreCourant = score(board);
    }

    @Override
    public int nbrVoisins()
    {
        return ((dimensions - 2)*(dimensions - 2)*3) + //nb rotation piece pleine possible
        ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2) + //nb d'échanges de pieces pleines possibles : n(n-1)/2
        (((dimensions - 2) * 4) * (((dimensions - 2) * 4) - 1) / 2) +  //nb d'échanges de bords
        6; //nb d'échanges de coins
    }


    public int score(ArrayList<Integer> puzzle)
    {
        int score = 0;
        int currentMotif; 
        int oppMotif;
        for(int i=0; i<dimensions; i++){
            for(int j=0; j<dimensions; j++){
                for(int m=0; m<4; m++){
                    currentMotif = puzzle.get(m + j * 4 + i * dimensions * 4);
                    if(currentMotif != 0){
                        if(m == 0){
                            oppMotif = puzzle.get(2 + j * 4 + (i-1) * dimensions*4);
                        }
                        else if(m == 1){
                            oppMotif = puzzle.get(3 + (j-1) * 4 + i * dimensions*4);
                        }
                        else if(m == 2){
                            oppMotif = puzzle.get(0 + j * 4 + (i+1) * dimensions*4);
                        }
                        else{
                            oppMotif = puzzle.get(1 + (j+1) * 4 + i * dimensions*4);
                        }
                        if(currentMotif != oppMotif){
                            score += 1;
                        }
                    }
                    
                }
            }
        }
        return score/2;
    }

    @Override
    public int score()
    {
        return scoreCourant;
    }

    @Override
    public int scoreVoisin(int i) //score du voisin avant changement
    {
        ArrayList<Integer> board2 = new ArrayList<Integer>(board); 
        ArrayList<Integer> piece1 = new ArrayList<Integer>();
        ArrayList<Integer> piece2 = new ArrayList<Integer>();
        int decal;
        if(i < (dimensions - 2)*(dimensions - 2)*3){ //rotation
            int ip = i/3;
            int ib = ((dimensions + 1) + ip + (ip/(dimensions-2)) * 2) * 4;
            if(i % 3 == 0){
                piece1.add(board2.get(ib + 3)); //Est
                piece1.add(board2.get(ib)); //Nord
                piece1.add(board2.get(ib + 1)); //Ouest
                piece1.add(board2.get(ib + 2)); //Sud
            }
            else if(i % 3 == 1){
                piece1.add(board2.get(ib + 2)); //Sud
                piece1.add(board2.get(ib + 3)); //Est
                piece1.add(board2.get(ib)); //Nord
                piece1.add(board2.get(ib + 1)); //Ouest
            }
            else if(i % 3 == 2){
                piece1.add(board2.get(ib + 1)); //Ouest
                piece1.add(board2.get(ib + 2)); //Sud
                piece1.add(board2.get(ib + 3)); //Est
                piece1.add(board2.get(ib)); //Nord
            }
            board2.set(ib, piece1.get(0));
            board2.set(ib + 1, piece1.get(1));
            board2.set(ib + 2, piece1.get(2));
            board2.set(ib + 3, piece1.get(3));
        }
        else if(i < ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2) && i >= (dimensions - 2)*(dimensions - 2)*3){ //échange pièce pleine
            decal = (dimensions - 2)*(dimensions - 2)*3;
            i -= decal;
            int nbp = (dimensions - 2)*(dimensions - 2);
            for(int n = nbp - 1; n >= 1; n--){
                if(i - n < 0){ 
                    int ip1 = nbp - n - 1;
                    int ip2 = ip1 + i + 1;
                    int ib1 = ((dimensions + 1) + ip1 + 2 * (ip1/(dimensions-2))) * 4;
                    int ib2 = ib1 + ((ip2 - ip1) + (ip2/(dimensions-2) - ip1/(dimensions-2))*2)*4;
                    piece1.add(board2.get(ib1)); //Nord
                    piece1.add(board2.get(ib1 + 1)); //Ouest
                    piece1.add(board2.get(ib1 + 2)); //Sud
                    piece1.add(board2.get(ib1 + 3)); //Est
                    piece2.add(board2.get(ib2)); //Nord
                    piece2.add(board2.get(ib2 + 1)); //Ouest
                    piece2.add(board2.get(ib2 + 2)); //Sud
                    piece2.add(board2.get(ib2 + 3)); //Est
                    board2.set(ib1, piece2.get(0));
                    board2.set(ib1 + 1, piece2.get(1));
                    board2.set(ib1 + 2, piece2.get(2));
                    board2.set(ib1 + 3, piece2.get(3));
                    board2.set(ib2, piece1.get(0));
                    board2.set(ib2 + 1, piece1.get(1));
                    board2.set(ib2 + 2, piece1.get(2));
                    board2.set(ib2 + 3, piece1.get(3));
                    break;
                }
                else{
                    i -= n;
                }
            } 

        }
        else if(i < ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2) + (((dimensions - 2) * 4) * (((dimensions - 2) * 4) - 1) / 2) && i >= ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2)){ //échange bords
            decal = ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2);
            i -= decal;
            int nbp = (dimensions - 2)*4;
            for(int n = nbp - 1; n >= 1; n--){
                if(i - n < 0){ 
                    int ip1 = nbp - n - 1;
                    int ip2 = ip1 + i + 1;
                    int ib1;
                    int ib2;
                    if(ip1 < dimensions-2){ //bord haut
                        ib1 = (1 + ip1) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                        }
                    }
                    else if(ip1 < 2*(dimensions-2) && ip1 >= dimensions-2){ //bord gauche
                        ib1 = (dimensions + dimensions * (ip1 - (dimensions - 2))) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                        }
                    }
                    else if(ip1 < 3*(dimensions-2) && ip1 >= 2*(dimensions-2)){ //bord bas
                        ib1 = (dimensions * (dimensions - 1) + 1 + ip1 - 2 * (dimensions - 2)) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                        }
                    }
                    else{ //bord droit
                        ib1 = (2 * dimensions - 1 + dimensions * (ip1 - 3 * (dimensions - 2))) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board2.get(ib2)); //Nord
                            piece1.add(board2.get(ib2 + 1)); //Ouest
                            piece1.add(board2.get(ib2 + 2)); //Sud
                            piece1.add(board2.get(ib2 + 3)); //Est
                            piece2.add(board2.get(ib1)); //Nord
                            piece2.add(board2.get(ib1 + 1)); //Ouest
                            piece2.add(board2.get(ib1 + 2)); //Sud
                            piece2.add(board2.get(ib1 + 3)); //Est
                        }
                    }
                    
                    board2.set(ib1, piece1.get(0));
                    board2.set(ib1 + 1, piece1.get(1));
                    board2.set(ib1 + 2, piece1.get(2));
                    board2.set(ib1 + 3, piece1.get(3));
                    board2.set(ib2, piece2.get(0));
                    board2.set(ib2 + 1, piece2.get(1));
                    board2.set(ib2 + 2, piece2.get(2));
                    board2.set(ib2 + 3, piece2.get(3));
                    break;
                }
                else{
                    i -= n;
                }
            }
        }
        else{ //échange coins
            decal = ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2) + (((dimensions - 2) * 4) * (((dimensions - 2) * 4) - 1) / 2);
            i -= decal;
            int ib1;
            int ib2;
            if(i == 0){
                ib1 = 0; //indice piece en haut a gauche
                ib2 = (dimensions-1) * 4; //indice piece en haut a droite
                piece1.add(board2.get(ib2 + 3)); //Est
                piece1.add(board2.get(ib2)); //Nord
                piece1.add(board2.get(ib2 + 1)); //Ouest
                piece1.add(board2.get(ib2 + 2)); //Sud
                piece2.add(board2.get(ib1 + 1)); //Ouest
                piece2.add(board2.get(ib1 + 2)); //Sud
                piece2.add(board2.get(ib1 + 3)); //Est
                piece2.add(board2.get(ib1)); //Nord
            }
            else if(i == 1){
                ib1 = 0; //indice piece en haut a gauche
                ib2 = dimensions * (dimensions-1) * 4; //indice piece en bas a gauche
                piece1.add(board2.get(ib2 + 1)); //Ouest
                piece1.add(board2.get(ib2 + 2)); //Sud
                piece1.add(board2.get(ib2 + 3)); //Est
                piece1.add(board2.get(ib2)); //Nord
                piece2.add(board2.get(ib1 + 3)); //Est
                piece2.add(board2.get(ib1)); //Nord
                piece2.add(board2.get(ib1 + 1)); //Ouest
                piece2.add(board2.get(ib1 + 2)); //Sud
            }
            else if(i == 2){
                ib1 = 0; //indice piece en haut a gauche
                ib2 = (dimensions * dimensions - 1) * 4; //indice piece en bas a droite
                piece1.add(board2.get(ib2 + 2)); //Sud
                piece1.add(board2.get(ib2 + 3)); //Est
                piece1.add(board2.get(ib2)); //Nord
                piece1.add(board2.get(ib2 + 1)); //Ouest
                piece2.add(board2.get(ib1 + 2)); //Sud
                piece2.add(board2.get(ib1 + 3)); //Est
                piece2.add(board2.get(ib1)); //Nord
                piece2.add(board2.get(ib1 + 1)); //Ouest
            }
            else if(i == 3){
                ib1 = (dimensions-1) * 4; //indice piece en haut a droite
                ib2 = dimensions * (dimensions-1) * 4; //indice piece en bas a gauche
                piece1.add(board2.get(ib2 + 2)); //Sud
                piece1.add(board2.get(ib2 + 3)); //Est
                piece1.add(board2.get(ib2)); //Nord
                piece1.add(board2.get(ib2 + 1)); //Ouest
                piece2.add(board2.get(ib1 + 2)); //Sud
                piece2.add(board2.get(ib1 + 3)); //Est
                piece2.add(board2.get(ib1)); //Nord
                piece2.add(board2.get(ib1 + 1)); //Ouest
            }
            else if(i == 4){
                ib1 = (dimensions-1) * 4; //indice piece en haut a droite
                ib2 = (dimensions * dimensions - 1) * 4; //indice piece en bas a droite
                piece1.add(board2.get(ib2 + 3)); //Est
                piece1.add(board2.get(ib2)); //Nord
                piece1.add(board2.get(ib2 + 1)); //Ouest
                piece1.add(board2.get(ib2 + 2)); //Sud
                piece2.add(board2.get(ib1 + 1)); //Ouest
                piece2.add(board2.get(ib1 + 2)); //Sud
                piece2.add(board2.get(ib1 + 3)); //Est
                piece2.add(board2.get(ib1)); //Nord
            }
            else{
                ib1 = dimensions * (dimensions-1) * 4; //indice piece en bas a gauche
                ib2 = (dimensions * dimensions - 1) * 4; //indice piece en bas a droite
                piece1.add(board2.get(ib2 + 1)); //Ouest
                piece1.add(board2.get(ib2 + 2)); //Sud
                piece1.add(board2.get(ib2 + 3)); //Est
                piece1.add(board2.get(ib2)); //Nord
                piece2.add(board2.get(ib1 + 3)); //Est
                piece2.add(board2.get(ib1)); //Nord
                piece2.add(board2.get(ib1 + 1)); //Ouest
                piece2.add(board2.get(ib1 + 2)); //Sud
            }
            board2.set(ib1, piece1.get(0));
            board2.set(ib1 + 1, piece1.get(1));
            board2.set(ib1 + 2, piece1.get(2));
            board2.set(ib1 + 3, piece1.get(3));
            board2.set(ib2, piece2.get(0));
            board2.set(ib2 + 1, piece2.get(1));
            board2.set(ib2 + 2, piece2.get(2));
            board2.set(ib2 + 3, piece2.get(3));
        }
        return score(board2);
    }
    @Override
    public void selectVoisin(int i) //changement de configuration
    {
        ArrayList<Integer> piece1 = new ArrayList<Integer>();
        ArrayList<Integer> piece2 = new ArrayList<Integer>();
        int decal;
        if(i < (dimensions - 2)*(dimensions - 2)*3){ //rotation
            int ip = i/3;
            int ib = ((dimensions + 1) + ip + (ip/(dimensions-2)) * 2) * 4;
            if(i % 3 == 0){
                piece1.add(board.get(ib + 3)); //Est
                piece1.add(board.get(ib)); //Nord
                piece1.add(board.get(ib + 1)); //Ouest
                piece1.add(board.get(ib + 2)); //Sud
            }
            else if(i % 3 == 1){
                piece1.add(board.get(ib + 2)); //Sud
                piece1.add(board.get(ib + 3)); //Est
                piece1.add(board.get(ib)); //Nord
                piece1.add(board.get(ib + 1)); //Ouest
            }
            else if(i % 3 == 2){
                piece1.add(board.get(ib + 1)); //Ouest
                piece1.add(board.get(ib + 2)); //Sud
                piece1.add(board.get(ib + 3)); //Est
                piece1.add(board.get(ib)); //Nord
            }
            board.set(ib, piece1.get(0));
            board.set(ib + 1, piece1.get(1));
            board.set(ib + 2, piece1.get(2));
            board.set(ib + 3, piece1.get(3));
        }
        else if(i < ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2) && i >= (dimensions - 2)*(dimensions - 2)*3){ //échange pièce pleine
            decal = (dimensions - 2)*(dimensions - 2)*3;
            i -= decal;
            int nbp = (dimensions - 2)*(dimensions - 2);
            for(int n = nbp - 1; n >= 1; n--){
                if(i - n < 0){ 
                    int ip1 = nbp - n - 1;
                    int ip2 = ip1 + i + 1;
                    int ib1 = ((dimensions + 1) + ip1 + 2 * (ip1/(dimensions-2))) * 4;
                    int ib2 = ib1 + ((ip2 - ip1) + (ip2/(dimensions-2) - ip1/(dimensions-2))*2)*4;
                    piece1.add(board.get(ib1)); //Nord
                    piece1.add(board.get(ib1 + 1)); //Ouest
                    piece1.add(board.get(ib1 + 2)); //Sud
                    piece1.add(board.get(ib1 + 3)); //Est
                    piece2.add(board.get(ib2)); //Nord
                    piece2.add(board.get(ib2 + 1)); //Ouest
                    piece2.add(board.get(ib2 + 2)); //Sud
                    piece2.add(board.get(ib2 + 3)); //Est
                    board.set(ib1, piece2.get(0));
                    board.set(ib1 + 1, piece2.get(1));
                    board.set(ib1 + 2, piece2.get(2));
                    board.set(ib1 + 3, piece2.get(3));
                    board.set(ib2, piece1.get(0));
                    board.set(ib2 + 1, piece1.get(1));
                    board.set(ib2 + 2, piece1.get(2));
                    board.set(ib2 + 3, piece1.get(3));
                    break;
                }
                else{
                    i -= n;
                }
            } 

        }
        else if(i < ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2) + (((dimensions - 2) * 4) * (((dimensions - 2) * 4) - 1) / 2) && i >= ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2)){ //échange bords
            decal = ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2);
            i -= decal;
            int nbp = (dimensions - 2)*4;
            for(int n = nbp - 1; n >= 1; n--){
                if(i - n < 0){ 
                    int ip1 = nbp - n - 1;
                    int ip2 = ip1 + i + 1;
                    int ib1;
                    int ib2;
                    if(ip1 < dimensions-2){ //bord haut
                        ib1 = (1 + ip1) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                        }
                    }
                    else if(ip1 < 2*(dimensions-2) && ip1 >= dimensions-2){ //bord gauche
                        ib1 = (dimensions + dimensions * (ip1 - (dimensions - 2))) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                        }
                    }
                    else if(ip1 < 3*(dimensions-2) && ip1 >= 2*(dimensions-2)){ //bord bas
                        ib1 = (dimensions * (dimensions - 1) + 1 + ip1 - 2 * (dimensions - 2)) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                        }
                    }
                    else{ //bord droit
                        ib1 = (2 * dimensions - 1 + dimensions * (ip1 - 3 * (dimensions - 2))) * 4;
                        if(ip2 < dimensions-2){ //bord haut
                            ib2 = (1 + ip2) * 4;
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                        }
                        else if(ip2 < 2*(dimensions-2) && ip2 >= dimensions-2){ //bord gauche
                            ib2 = (dimensions + dimensions * (ip2 - (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                        }
                        else if(ip2 < 3*(dimensions-2) && ip2 >= 2*(dimensions-2)){ //bord bas
                            ib2 = (dimensions * (dimensions - 1) + 1 + ip2 - 2 * (dimensions - 2)) * 4;
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                        }
                        else{ //bord droit
                            ib2 = (2 * dimensions - 1 + dimensions * (ip2 - 3 * (dimensions - 2))) * 4;
                            piece1.add(board.get(ib2)); //Nord
                            piece1.add(board.get(ib2 + 1)); //Ouest
                            piece1.add(board.get(ib2 + 2)); //Sud
                            piece1.add(board.get(ib2 + 3)); //Est
                            piece2.add(board.get(ib1)); //Nord
                            piece2.add(board.get(ib1 + 1)); //Ouest
                            piece2.add(board.get(ib1 + 2)); //Sud
                            piece2.add(board.get(ib1 + 3)); //Est
                        }
                    }
                    
                    board.set(ib1, piece1.get(0));
                    board.set(ib1 + 1, piece1.get(1));
                    board.set(ib1 + 2, piece1.get(2));
                    board.set(ib1 + 3, piece1.get(3));
                    board.set(ib2, piece2.get(0));
                    board.set(ib2 + 1, piece2.get(1));
                    board.set(ib2 + 2, piece2.get(2));
                    board.set(ib2 + 3, piece2.get(3));
                    break;
                }
                else{
                    i -= n;
                }
            }
        }
        else{ //échange coins
            decal = ((dimensions - 2)*(dimensions - 2)*3) + ((dimensions - 2)*(dimensions - 2) * ((dimensions - 2)*(dimensions - 2) - 1) / 2) + (((dimensions - 2) * 4) * (((dimensions - 2) * 4) - 1) / 2);
            i -= decal;
            int ib1;
            int ib2;
            if(i == 0){
                ib1 = 0; //indice piece en haut a gauche
                ib2 = (dimensions-1) * 4; //indice piece en haut a droite
                piece1.add(board.get(ib2 + 3)); //Est
                piece1.add(board.get(ib2)); //Nord
                piece1.add(board.get(ib2 + 1)); //Ouest
                piece1.add(board.get(ib2 + 2)); //Sud
                piece2.add(board.get(ib1 + 1)); //Ouest
                piece2.add(board.get(ib1 + 2)); //Sud
                piece2.add(board.get(ib1 + 3)); //Est
                piece2.add(board.get(ib1)); //Nord
            }
            else if(i == 1){
                ib1 = 0; //indice piece en haut a gauche
                ib2 = dimensions * (dimensions-1) * 4; //indice piece en bas a gauche
                piece1.add(board.get(ib2 + 1)); //Ouest
                piece1.add(board.get(ib2 + 2)); //Sud
                piece1.add(board.get(ib2 + 3)); //Est
                piece1.add(board.get(ib2)); //Nord
                piece2.add(board.get(ib1 + 3)); //Est
                piece2.add(board.get(ib1)); //Nord
                piece2.add(board.get(ib1 + 1)); //Ouest
                piece2.add(board.get(ib1 + 2)); //Sud
            }
            else if(i == 2){
                ib1 = 0; //indice piece en haut a gauche
                ib2 = (dimensions * dimensions - 1) * 4; //indice piece en bas a droite
                piece1.add(board.get(ib2 + 2)); //Sud
                piece1.add(board.get(ib2 + 3)); //Est
                piece1.add(board.get(ib2)); //Nord
                piece1.add(board.get(ib2 + 1)); //Ouest
                piece2.add(board.get(ib1 + 2)); //Sud
                piece2.add(board.get(ib1 + 3)); //Est
                piece2.add(board.get(ib1)); //Nord
                piece2.add(board.get(ib1 + 1)); //Ouest
            }
            else if(i == 3){
                ib1 = (dimensions-1) * 4; //indice piece en haut a droite
                ib2 = dimensions * (dimensions-1) * 4; //indice piece en bas a gauche
                piece1.add(board.get(ib2 + 2)); //Sud
                piece1.add(board.get(ib2 + 3)); //Est
                piece1.add(board.get(ib2)); //Nord
                piece1.add(board.get(ib2 + 1)); //Ouest
                piece2.add(board.get(ib1 + 2)); //Sud
                piece2.add(board.get(ib1 + 3)); //Est
                piece2.add(board.get(ib1)); //Nord
                piece2.add(board.get(ib1 + 1)); //Ouest
            }
            else if(i == 4){
                ib1 = (dimensions-1) * 4; //indice piece en haut a droite
                ib2 = (dimensions * dimensions - 1) * 4; //indice piece en bas a droite
                piece1.add(board.get(ib2 + 3)); //Est
                piece1.add(board.get(ib2)); //Nord
                piece1.add(board.get(ib2 + 1)); //Ouest
                piece1.add(board.get(ib2 + 2)); //Sud
                piece2.add(board.get(ib1 + 1)); //Ouest
                piece2.add(board.get(ib1 + 2)); //Sud
                piece2.add(board.get(ib1 + 3)); //Est
                piece2.add(board.get(ib1)); //Nord
            }
            else{
                ib1 = dimensions * (dimensions-1) * 4; //indice piece en bas a gauche
                ib2 = (dimensions * dimensions - 1) * 4; //indice piece en bas a droite
                piece1.add(board.get(ib2 + 1)); //Ouest
                piece1.add(board.get(ib2 + 2)); //Sud
                piece1.add(board.get(ib2 + 3)); //Est
                piece1.add(board.get(ib2)); //Nord
                piece2.add(board.get(ib1 + 3)); //Est
                piece2.add(board.get(ib1)); //Nord
                piece2.add(board.get(ib1 + 1)); //Ouest
                piece2.add(board.get(ib1 + 2)); //Sud
            }
            board.set(ib1, piece1.get(0));
            board.set(ib1 + 1, piece1.get(1));
            board.set(ib1 + 2, piece1.get(2));
            board.set(ib1 + 3, piece1.get(3));
            board.set(ib2, piece2.get(0));
            board.set(ib2 + 1, piece2.get(1));
            board.set(ib2 + 2, piece2.get(2));
            board.set(ib2 + 3, piece2.get(3));
        }
        scoreCourant = score(board);
    }

    @Override
    public int randomVoisin()
    {
       return genrand.nextInt(nbrVoisins());
    }

    @Override
    public boolean solution()
    {
        return score() == 0;
    }

    @Override
    public String toString()
    {
        StringBuilder out = new StringBuilder("Puzzle : \n" + board);

        return out.toString();
    }
}