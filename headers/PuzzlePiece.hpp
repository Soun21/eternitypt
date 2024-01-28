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
        int getN(){return N;};
        int getW(){return W;};
        int getS(){return S;};
        int getE(){return E;};
        //Mutateurs
        void setN(int N){this->N = N;};
        void setW(int W){this->W = W;};
        void setS(int S){this->S = S;};
        void setE(int E){this->E = E;};
        //Méthodes
        void rotateClockwise(); //rotation dans le sens horaire
        void rotateTrigonometric(); //rotation dans le sens trigo
        std::string toString(); //affichage de la pièce
        bool leftRight(PuzzlePiece piece1, PuzzlePiece piece2); //compare les motifs EST d'une pièce et OUEST d'une autre
        bool upDown(PuzzlePiece piece1, PuzzlePiece piece2); //compare les motifs de SUD d'une pièce et NORD d'une autre
        bool isCorner(); //vérifie si la pièce est un coin
        bool isBorder(); //vérifie si la pièce est un bord
        bool isCornerBottomLeft(); //vérifie si la pièce est un coin en bas à gauche
        bool isCornerBottomRight(); //vérifie si la pièce est un coin en bas à droite
        bool isCornerTopLeft(); //vérifie si la pièce est un coin en haut à gauche
        bool isCornerTopRight(); //vérifie si la pièce est un coin en haut à droite
        bool isBorderBottom(); //vérifie si la pièce est un bord en bas
        bool isBorderTop(); //vérifie si la pièce est un bord en haut
        bool isBorderLeft(); //vérifie si la pièce est un bord à gauche
        bool isBorderRight(); //vérifie si la pièce est un bord à droite
        bool equals(PuzzlePiece &piece); //vérifie si deux pièces sont identiques
};