#include "../headers/Puzzle.hpp"


using namespace std;

//Constructeurs

Puzzle::Puzzle(int dimension, int nbMotifs){
    this->dimension = dimension;
    this->nbMotifs = nbMotifs;
    this->board = new vector<PuzzlePiece>();
}

//Méthodes

void Puzzle::generatePuzzle(){
    int N, W, S, E;
    PuzzlePiece *piece;
    cout << "Génération du puzzle" << endl;
    srand(2);
    for(int i = 0; i < this->dimension; i++){ //ligne
        for(int j = 0; j < this->dimension; j++){ //colonne
            if(i%2 == 0 && j%2 == 0){
                //Coin haut gauche
                if(i == 0 && j == 0){
                    // cout << "Coin haut gauche - " << "i : " << i << " j : " << j << endl;
                    N = 0;
                    W = 0;
                    E = 1 + rand() % this->nbMotifs;
                    S = 1 + rand() % this->nbMotifs; 
                }
                //Coin haut droit
                else if(i == 0 && j == this->dimension - 1){
                    // cout << "Coin haut droit - " << "i : " << i << " j : " << j << endl;
                    N = 0;
                    W = 1 + rand() % this->nbMotifs;
                    E = 0;
                    S = 1 + rand() % this->nbMotifs;
                }
                //Coin bas gauche
                else if(i == this->dimension -1 && j == 0){
                    // cout << "Coin bas gauche - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 0;
                    E = 1 + rand() % this->nbMotifs;
                    S = 0;
                }
                //Coin bas droit
                else if(i == this->dimension - 1 && j == this->dimension - 1){
                    // cout << "Coin bas droit - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 0;
                    S = 0;
                }
                //Bord haut
                else if(i == 0 && (j != 0 || j != this->dimension-1)){
                    // cout << "Bord haut - " << "i : " << i << " j : " << j << endl;
                    N = 0;
                    W = 1 + rand() % this->nbMotifs;
                    E = 1 + rand() % this->nbMotifs;
                    S = 1 + rand() % this->nbMotifs;
                }
                //Bord gauche
                else if(j == 0 && (i != 0 || i != this->dimension-1)){
                    // cout << "Bord gauche - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 0;
                    E = 1 + rand() % this->nbMotifs;
                    S = 1 + rand() % this->nbMotifs;
                }
                
                //Bord droit
                else if(j == this->dimension -1 && (i != 0 || i != this->dimension-1) ){
                    // cout << "Bord droit - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 0;
                    S = 1 + rand() % this->nbMotifs;
                }
                //Bord bas
                else if(i == this->dimension -1 && (j != 0 || j != this->dimension-1) ){
                    // cout << "Bord bas - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 1 + rand() % this->nbMotifs;
                    S = 0;
                }                
                //Ne touche pas un bord
                else{
                    //cout << "Ne touche pas un bord - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 1 + rand() % this->nbMotifs;
                    S = 1 + rand() % this->nbMotifs;
                }
                piece = new PuzzlePiece(N, W, S, E);
                //cout << "N : " << piece->getN() << " W : " << piece->getW() << " S : " << piece->getS() << " E : " << piece->getE() << endl;
            }
            else if(i%2 == 1 && j%2 == 1){
                //Coin bas droit
                if(i == this->dimension - 1 && j == this->dimension - 1){
                    //cout << "Coin bas droit - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 0;
                    S = 0;
                }   
                //Bord droit
                else if(j == this->dimension -1 && (i != 0 || i != this->dimension-1) ){
                    //cout << "Bord droit - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 0;
                    S = 1 + rand() % this->nbMotifs;
                }
                //Bord bas
                else if(i == this->dimension -1 && (j != 0 || j != this->dimension-1) ){
                    //cout << "Bord bas - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 1 + rand() % this->nbMotifs;
                    S = 0;
                }
                //Ne touche pas un bord
                else{
                    //cout << "Ne touche pas un bord - " << "i : " << i << " j : " << j << endl;
                    N = 1 + rand() % this->nbMotifs;
                    W = 1 + rand() % this->nbMotifs;
                    E = 1 + rand() % this->nbMotifs;
                    S = 1 + rand() % this->nbMotifs;
                }
                piece = new PuzzlePiece(N, W, S, E);
                // cout << "N : " << piece->getN() << " W : " << piece->getW() << " S : " << piece->getS() << " E : " << piece->getE() << endl;
                
            }
            else{
                piece = new PuzzlePiece();
            }
            
            //On ajoute la pièce dans le plateau
            this->board->push_back(*piece);
        }
    }
    // cout << "Remplissage " << endl;
    if(this->dimension%2 == 1){ //Dimension impaire
        //Après génération des cases aléatoires, on va remplir le reste des cases
        for(int i = 1; i < this->dimension * this->dimension; i = i+2){
            puzzleFill(i);
        }
    }
    else if(this->dimension%2 == 0){ //Dimension paire
        for(int i = 1; i < this->dimension * this->dimension; i += 2){
            if(i == 1) puzzleFill(i); //première case à remplir
            else if(i%this->dimension == this->dimension-2 && i > this->dimension && i < this->dimension * (this->dimension - 1)){
                puzzleFill(i);
                if((i+3) < this->dimension * this->dimension){
                    i+=3;
                    puzzleFill(i);
                }
            }
            else if(i%this->dimension == this->dimension - 1){
                puzzleFill(i);
                if((i+1) < this->dimension * this->dimension){
                    i++;
                    puzzleFill(i);
                }
            }
            else{
                puzzleFill(i);
            }
        }
    }
}

void Puzzle::puzzleFill(int i){
    // cout << "i :" << i;
    PuzzlePiece index = this->board->at(i);
    int N, W, S, E;
    PuzzlePiece leftPiece;
    PuzzlePiece upPiece;
    PuzzlePiece rightPiece;
    PuzzlePiece downPiece;    
    //Coin haut droit
    if(i == this->dimension-1){
        // cout << "Coin haut droit" << endl;
        leftPiece = this->board->at(i-1);
        downPiece = this->board->at(i+this->dimension);
        index.setW(leftPiece.getE());
        index.setS(downPiece.getN());
        index.setN(0);
        index.setE(0);
    }
    //Bord haut
    else if(i < this->dimension-1 && i > 0){
        // cout << "Bord haut" << endl;
        leftPiece = this->board->at(i-1);
        rightPiece = this->board->at(i+1);
        downPiece = this->board->at(i+this->dimension);
        index.setW(leftPiece.getE());
        index.setE(rightPiece.getW());
        index.setS(downPiece.getN());
        index.setN(0);
    }
    //Bord gauche
    else if(i%this->dimension == 0 && i < this->dimension * (this->dimension - 1)){
        // cout << "Bord gauche" << endl;
        upPiece = this->board->at(i-this->dimension);
        rightPiece = this->board->at(i+1);
        downPiece = this->board->at(i+this->dimension);
        index.setN(upPiece.getS());
        index.setE(rightPiece.getW());
        index.setS(downPiece.getN());
        index.setW(0);
    }
    //Bord droit
    else if(i%this->dimension == this->dimension-1){
        // cout << "Bord droit" << endl;
        upPiece = this->board->at(i-this->dimension);
        leftPiece = this->board->at(i-1);
        downPiece = this->board->at(i+this->dimension);
        index.setN(upPiece.getS());
        index.setW(leftPiece.getE());
        index.setS(downPiece.getN());
        index.setE(0);
    }
    //Coin bas gauche
    else if(i == this->dimension * (this->dimension -1)){
        // cout << "Coin bas gauche" << endl;
        upPiece = this->board->at(i-this->dimension);
        rightPiece = this->board->at(i+1);
        index.setN(upPiece.getS());
        index.setE(rightPiece.getW());
        index.setS(0);
        index.setW(0);
    }
    //Bord bas
    else if(i > this->dimension * (this->dimension-1)){
        // cout << "Bord bas" << endl;
        upPiece = this->board->at(i-this->dimension);
        leftPiece = this->board->at(i-1);
        rightPiece = this->board->at(i+1);
        index.setN(upPiece.getS());
        index.setW(leftPiece.getE());
        index.setE(rightPiece.getW());
        index.setS(0);
    }
    //Autres pièces
    else{
        // cout << "Autres pièces" << endl;
        upPiece = this->board->at(i-this->dimension);
        rightPiece = this->board->at(i+1);
        leftPiece = this->board->at(i-1);
        downPiece = this->board->at(i+this->dimension);
        index.setN(upPiece.getS());
        index.setW(leftPiece.getE());
        index.setE(rightPiece.getW());
        index.setS(downPiece.getN());
    }
    N = index.getN();
    W = index.getW();
    S = index.getS();
    E = index.getE();
    // cout << "Index : " << i << " - " << index.toString() << endl;
    this->board->at(i) = PuzzlePiece(N, W, S, E);
}

void Puzzle::shufflePuzzle(){
    //On veut mélanger les pièces sans que ça touche les coins et les bords
    int random;
    cout << "Mélange du puzzle" << endl;
    srand(time(0));
    if(this->dimension < 3 || this->board->size() == 0){
        cout << "Vous ne pouvez pas mélanger un puzzle de dimension 1, 2 ou vide !" << endl;
        return;
    }
    else{
        //1er temps, on tourne aléatoirement les pièces
        for(int i = 0; i < this->dimension * this->dimension; i++){
            if(!this->board->at(i).isCorner() && !this->board->at(i).isBorder()){
                random = rand() % 3;
                for(int j = 0; j < random; j++){
                    this->board->at(i).rotateClockwise();
                }
            }
        }
        //2ème temps on va permuter aléatoirement les pièces au milieu du puzzle
        for(int i = 0; i < this->dimension * this->dimension; i++){
            //si la pièce n'est ni un coin ni un bord
            if(!this->board->at(i).isCorner() && !this->board->at(i).isBorder()){
                random = rand() % (this->dimension * this->dimension - 1);
                if(!this->board->at(random).isCorner() && !this->board->at(random).isBorder() && !this->board->at(i).equals(this->board->at(random))){
                    PuzzlePiece tmp = this->board->at(i);
                    this->board->at(i) = this->board->at(random);
                    this->board->at(random) = tmp;
                }
            }
        }
        

        //3ème temps on va permuter aléatoirement les pièces des coins entre eux
        vector<PuzzlePiece> *corners = new vector<PuzzlePiece>();
        corners->push_back(this->board->at(0)); //coin haut gauche
        corners->push_back(this->board->at(this->dimension-1)); //coin haut droite
        corners->push_back(this->board->at(this->dimension * (this->dimension-1))); //coin bas gauche
        corners->push_back(this->board->at(this->dimension * this->dimension - 1)); //coin bas droite
        for(int i = 0; i < 16; i++){
            int random = 1 + rand() % 4;
            PuzzlePiece tmp = corners->at(i%4);
            corners->at(i%4) = corners->at(random%4);
            corners->at(random%4) = tmp;
        }
        string str = "";
        for(int i = 0; i < (int) corners->size(); i++){
            str = whichCorner(corners->at(i));
            if(i == 0){ //coin haut gauche
                if(str.compare("topRight") == 0){
                    corners->at(i).rotateTrigonometric();
                }
                else if(str.compare("bottomLeft") == 0){
                    corners->at(i).rotateClockwise();
                }
                else if(str.compare("bottomRight") == 0){
                    corners->at(i).rotateClockwise();
                    corners->at(i).rotateClockwise();
                }
            }
            else if(i==1){
                if(str.compare("topLeft") == 0){
                    corners->at(i).rotateClockwise();
                }
                else if(str.compare("bottomLeft") == 0){
                    corners->at(i).rotateClockwise();
                    corners->at(i).rotateClockwise();
                }
                else if(str.compare("bottomRight") == 0){
                    corners->at(i).rotateTrigonometric();
                }
            }
            else if(i==2){
                if(str.compare("topLeft") == 0){
                    corners->at(i).rotateTrigonometric();
                }
                else if(str.compare("topRight") == 0){
                    corners->at(i).rotateClockwise();
                    corners->at(i).rotateClockwise();
                }
                else if(str.compare("bottomRight") == 0){
                    corners->at(i).rotateClockwise();
                }
            }
            else if(i==3){
                if(str.compare("topLeft") == 0){
                    corners->at(i).rotateClockwise();
                    corners->at(i).rotateClockwise();
                }
                else if(str.compare("topRight") == 0){
                    corners->at(i).rotateClockwise();
                }
                else if(str.compare("bottomLeft") == 0){
                    corners->at(i).rotateTrigonometric();
                }
            }
        }
        this->board->at(0) = corners->at(0); //On remet le coin en haut à gauche
        this->board->at(this->dimension-1) = corners->at(1); //On remet le coin en haut à droite
        this->board->at(this->dimension * (this->dimension-1)) = corners->at(2); //On remet le coin en bas à gauche
        this->board->at(this->dimension * this->dimension - 1) = corners->at(3); //On remet le coin en bas à droite
    
        //4ème temps on va permuter aléatoirement les pièces des bords entre eux sans toucher aux coins
        vector <PuzzlePiece> *borders = new vector<PuzzlePiece>();
        //On récupère les pièces du bord haut sans les coins
        for(int i = 1; i <= this->dimension-2; i++){
            borders->push_back(this->board->at(i));
        }
        //On récupère les pièces du bord gauche sans les coins
        for(int i = this->dimension; i <= this->dimension * (this->dimension-1) - 1; i += this->dimension){
            borders->push_back(this->board->at(i));
        }
        //On récupère les pièces du bord droit sans les coins
        for(int i = this->dimension * 2 - 1; i <= this->dimension * this->dimension - 2; i += this->dimension){
            borders->push_back(this->board->at(i));
        }
        //On récupère les pièces du bord bas sans les coins
        for(int i = this->dimension * (this->dimension-1) + 1; i <= this->dimension * this->dimension - 2; i++){
            borders->push_back(this->board->at(i));
        }
        //On mélange aléatoirement les pièces des bords
        for(int i = 0; i < (int) borders->size(); i++){
            int random = rand() % ((int) borders->size()-1);
            PuzzlePiece tmp = borders->at(i);
            borders->at(i) = borders->at(random);
            borders->at(random) = tmp;
        }

        //On les remet à leur place
        /*
        * De 0 à dimension-2 : bord haut
        * De dimension-1 à dimension * (dimension-1) - 1 : bord gauche
        * De dimension * 2 - 1 à dimension * dimension - 2 : bord droit
        * De dimension * (dimension-1) + 1 à dimension * dimension - 2 : bord bas
        */
        for(int i = 0; i < (int) borders->size(); i++){
            //gestion bord haut
            if(i < this->dimension-2){
                if(borders->at(i).isBorderLeft()){
                    borders->at(i).rotateClockwise();
                }
                else if(borders->at(i).isBorderRight()){
                    borders->at(i).rotateTrigonometric();
                }
                else if(borders->at(i).isBorderBottom()){
                    borders->at(i).rotateClockwise();
                    borders->at(i).rotateClockwise();
                }
            }
            //Gestion bord gauche
            else if(i >= this->dimension-2 && i < 2*(this->dimension-2)){
                if(borders->at(i).isBorderTop()){
                    borders->at(i).rotateTrigonometric();
                }
                else if(borders->at(i).isBorderRight()){
                    borders->at(i).rotateClockwise();
                    borders->at(i).rotateClockwise();
                }
                else if(borders->at(i).isBorderBottom()){
                    borders->at(i).rotateClockwise();
                }
            }
            //Gestion bord droit
            else if(i >= 2*(this->dimension-2) && i < 3*(this->dimension-2) ){
                if(borders->at(i).isBorderTop()){
                    borders->at(i).rotateClockwise();
                }
                else if(borders->at(i).isBorderLeft()){
                    borders->at(i).rotateClockwise();
                    borders->at(i).rotateClockwise();
                }
                else if(borders->at(i).isBorderBottom()){
                    borders->at(i).rotateTrigonometric();
                }
            }
            //gestion bord bas
            else if(i >= 3*(this->dimension-2) && i < this->dimension*this->dimension){
                if(borders->at(i).isBorderTop()){
                    borders->at(i).rotateClockwise();
                    borders->at(i).rotateClockwise();
                }
                else if(borders->at(i).isBorderLeft()){
                    borders->at(i).rotateTrigonometric();
                }
                else if(borders->at(i).isBorderRight()){
                    borders->at(i).rotateClockwise();
                }
            }
        }
        //On remplace les pièces dans le puzzle final
        int index = 0;
        //On remplace les pièces du bord haut
        for(int i = 1; i <= this->dimension-2; i++){
            this->board->at(i) = borders->at(index);
            index++;
        }
        //On remplace les pièces du bord gauche
        for(int i = this->dimension; i <= this->dimension * (this->dimension-1) - 1; i += this->dimension){
            this->board->at(i) = borders->at(index);
            index++;
        }
        //On remplace les pièces du bord droit
        for(int i = this->dimension * 2 - 1; i <= this->dimension * this->dimension - 2; i += this->dimension){
            this->board->at(i) = borders->at(index);
            index++;
        }
        //On remplace les pièces du bord bas
        for(int i = this->dimension * (this->dimension-1) + 1; i <= this->dimension * this->dimension - 2; i++){
            this->board->at(i) = borders->at(index);
            index++;
        }
        
    }
}


void Puzzle::importPuzzle(string filename){
    ifstream file;
    file.open(filename);
    string line = "";
    int limit_dimension; //limite pour récupérer la dimension et le nbMotifs
    int count = 0; //Compteur pour récupérer les 4 valeurs de la pièce
    int mem = 0; //Variable de mémoire
    int N, W, S, E; //Points cardinaux pour récupérer les valeurs et les mettre dans une pièce du puzzle

    if(!file.is_open()){
        cout << "Impossible d'ouvrir le fichier !" << endl;
        return;
    }

    //Tant qu'on parcourt le fichier
    while(!file.eof()){
        getline(file, line);
        //Le fichier commence par un ! pour pouvoir savoir si on récupère la dimension et le nbMotifs ou les valeurs de la pièce directement
        if(line[0] == '!'){
            for(int i = 0; i < (int) line.length(); i++){
                if(line[i] == (' ')){
                    limit_dimension = i;
                    break;
                }
            }
            //On récupère la dimension et le nbMotifs de la première ligne
            this->dimension = stoi(line.substr(1, limit_dimension-1));
            this->nbMotifs  = stoi(line.substr(limit_dimension+1, line.length()-1));
        }
        else{
            //On récupère les informations des pièces
            for(int i = 0; i < (int) line.length(); i++){
                //On récupère ici la ligne
                if(line[i] == (' ')){
                    if(count == 0){
                        mem = i;
                        N = stoi(line.substr(0, mem));
                        count++;
                        cout << "N : " << N;
                    }
                    else if(count == 1){
                        count++;
                        W = stoi(line.substr(mem, i));
                        mem = i;
                        cout << " W : " << W;
                    }
                    else if(count == 2){
                        count++;
                        S = stoi(line.substr(mem, i));
                        mem = i;
                        cout << " S : " << S;
                    }
                    else if (count == 3){
                        count = 0;
                        E = stoi(line.substr(mem, i));
                        cout << " E : " << E << endl;
                    }
                }
            }
            this->board->push_back(PuzzlePiece(N, W, S, E));
        }
    }
}


void Puzzle::exportPuzzle(){ // il faut mettre un espace à la fin pour pouvoir importer plus tard
    ofstream file;
    file.open("export/export.txt");
    if(!file.is_open()){
        cout << "Impossible d'ouvrir le fichier !" << endl;
        return;
    }
    file << "!" << this->dimension << " " << this->nbMotifs << endl;
    for(int i = 0; i < (int) this->board->size(); i++){
        file << this->board->at(i).getN() << " " << this->board->at(i).getW() << " " << this->board->at(i).getS() << " " << this->board->at(i).getE() << " " << endl;
    }
    file.close();
}

void Puzzle::showPuzzle(){
    for(int i = 0; i < (int) this->board->size(); i++){
        cout << "PuzzlePiece " << i << " : ";
        cout << this->board->at(i).getN() << " " << this->board->at(i).getW() << " " << this->board->at(i).getS() << " " << this->board->at(i).getE()  << endl;
    }
}

string Puzzle::whichCorner(PuzzlePiece &piece){
    string str = "";
    if(piece.isCornerTopLeft()){
        str = "topLeft";
    }
    else if(piece.isCornerTopRight()){
        str = "topRight";
    }
    else if(piece.isCornerBottomLeft()){
        str = "bottomLeft";
    }
    else if(piece.isCornerBottomRight()){
        str = "bottomRight";
    }
    return str;
}