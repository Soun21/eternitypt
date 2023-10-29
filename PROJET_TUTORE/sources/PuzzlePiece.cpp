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

//Accesseurs

int PuzzlePiece::getN(){
    return this->N;
}

int PuzzlePiece::getW(){
    return this->W;
}

int PuzzlePiece::getS(){
    return this->S;
}

int PuzzlePiece::getE(){
    return this->E;
}

//Mutateurs

void PuzzlePiece::setN(int N){
    this->N = N;
}

void PuzzlePiece::setW(int W){
    this->W = W;
}

void PuzzlePiece::setS(int S){
    this->S = S;
}

void PuzzlePiece::setE(int E){
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



