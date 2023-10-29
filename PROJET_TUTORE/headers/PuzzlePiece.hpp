#include <iostream>

class PuzzlePiece{
    private:
        int N; //Nord
        int W; //Ouest
        int S; //Sud
        int E; //Est
    public:
        //Constructeurs
        PuzzlePiece();
        PuzzlePiece(int N, int W, int S, int E);
        //Accesseurs
        int getN();
        int getW();
        int getS();
        int getE();
        
        //Mutateurs
        void setN(int N);
        void setW(int W);
        void setS(int S);
        void setE(int E);
        //Méthodes
        void rotateClockwise(); //rotation dans le sens horaire
        void rotateTrigonometric(); //rotation dans le sens trigo
        std::string toString(); //affichage de la pièce
        bool leftRight(PuzzlePiece piece1, PuzzlePiece piece2); //compare les motifs EST d'une pièce et OUEST d'une autre
        bool upDown(PuzzlePiece piece1, PuzzlePiece piece2); //compare les motifs de SUD d'une pièce et NORD d'une autre
};