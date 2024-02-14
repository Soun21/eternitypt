#include "../headers/PuzzlePiece.hpp"

using namespace std;

//Constructeurs
PuzzlePiece::PuzzlePiece(){
    this->N = 0;
    this->W = 0;
    this->S = 0;
    this->E = 0;
}

PuzzlePiece::PuzzlePiece(int N, int W, int S, int E){
    this->N = N;
    this->W = W;
    this->S = S;
    this->E = E;
}
//Méthodes

void PuzzlePiece::rotateClockwise(){
    int tmp = this->N;
    this->N = this->W;
    this->W = this->S;
    this->S = this->E;
    this->E = tmp;
}

void PuzzlePiece::rotateTrigonometric(){
    int tmp = this->N;
    this->N = this->E;
    this->E = this->S;
    this->S = this->W;
    this->W = tmp;
}

string PuzzlePiece::toString(){
    string str = "";
    str += to_string(this->N)+" ";
    str += to_string(this->W)+" ";
    str += to_string(this->S)+" ";
    str += to_string(this->E);
    return str;
}

bool PuzzlePiece::leftRight(PuzzlePiece piece1, PuzzlePiece piece2){
    return (piece1.getE() == piece2.getW());
}


bool PuzzlePiece::upDown(PuzzlePiece piece1, PuzzlePiece piece2){
    return (piece1.getS() == piece2.getN());
}

bool PuzzlePiece::isCorner(){
    return  (this->N == 0 && this->W == 0 && this->E != 0 && this->S != 0) ||
            (this->N == 0 && this->E == 0 && this->S != 0 && this->W != 0) ||
            (this->S == 0 && this->W == 0 && this->N != 0 && this->E != 0) ||
            (this->S == 0 && this->E == 0 && this->N != 0 && this->W != 0);
}

bool PuzzlePiece::isCornerTopLeft(){
    return  (this->N == 0 && this->W == 0 && this->S != 0 && this->E != 0);
}

bool PuzzlePiece::isCornerTopRight(){
    return  (this->N == 0 && this->E == 0 && this->S != 0 && this->W != 0);
}

bool PuzzlePiece::isCornerBottomLeft(){
    return  (this->S == 0 && this->W == 0 && this->N != 0 && this->E != 0);
}

bool PuzzlePiece::isCornerBottomRight(){
    return  (this->S == 0 && this->E == 0 && this->N != 0 && this->W != 0);
}

bool PuzzlePiece::isBorderTop(){
    return  (this->N == 0 && this->W != 0 && this->E != 0 && this->S != 0);
}

bool PuzzlePiece::isBorderBottom(){
    return  (this->S == 0 && this->W != 0 && this->E != 0 && this->N != 0);
}

bool PuzzlePiece::isBorderLeft(){
    return  (this->W == 0 && this->N != 0 && this->E != 0 && this->S != 0);
}

bool PuzzlePiece::isBorderRight(){
    return  (this->E == 0 && this->W != 0 && this->N != 0 && this->S != 0);
}



bool PuzzlePiece::isBorder(){
    return  (this->N == 0 && this->W != 0 && this->E != 0 && this->S != 0) ||
            (this->S == 0 && this->W != 0 && this->E != 0 && this->N != 0) ||
            (this->W == 0 && this->N != 0 && this->E != 0 && this->S != 0) ||
            (this->E == 0 && this->W != 0 && this->N != 0 && this->S != 0);
}

bool PuzzlePiece::equals(PuzzlePiece &piece){
    return (this->N == piece.getN() && this->W == piece.getW() && this->S == piece.getS() && this->E == piece.getE());
}
