#include "PuzzlePiece.hpp"
#include <vector>
#include <string>
#include <iostream>
#include <fstream>
#include <time.h>

class Puzzle{
    private:
        std::vector<PuzzlePiece> *board;
        int dimension;
        int nbMotifs;
        int getNbMotifs(){return nbMotifs;};
        void setDimension(int dimension){this->dimension = dimension;};
        void setNbMotifs(int nbMotifs){this->nbMotifs = nbMotifs;};
        
    public:
        int getDimension(){return dimension;};
        std::vector<PuzzlePiece>* getPuzzle(){return board;};
        Puzzle(int dimension, int nbMotifs);
        void generatePuzzle();
        void puzzleFill(int i);
        void shufflePuzzle();
        void importPuzzle(std::string filename);
        void exportPuzzle();
        void showPuzzle();
        std::string whichCorner(PuzzlePiece &piece);
};