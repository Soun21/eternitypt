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
        int getDimension();
        int getNbMotifs();
        void setDimension(int dimension);
        void setNbMotifs(int nbMotifs);
    public:
        Puzzle(int dimension, int nbMotifs);
        std::vector<PuzzlePiece>* getPuzzle();
        void generatePuzzle();
        void puzzleFill(int i);
        void shufflePuzzle();
        void importPuzzle(std::string filename);
        void exportPuzzle();
        void showPuzzle();
};