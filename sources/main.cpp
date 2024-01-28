#include "../headers/Puzzle.hpp"
#include <iostream>
#include <filesystem>

using namespace std;


int main(int argc, char *argv[]) {
    if(argc < 2){
        cout << "Veuillez lancer le programme de cette façon :" << endl;
        cout << "Sous windows :" << endl;
        cout << "eternity dimension nbMotifs" << endl;
        cout << "Sous linux : " << endl;
        cout << "./eternity dimension nbMotifs" << endl;
    }
    if(atoi(argv[1]) < 3)
    {
        cout << "La dimension doit être supérieure ou égale à 3" << endl;
        exit(1);
    }
    if(atoi(argv[2]) < 2)
    {
        cout << "Le nombre de motifs doit être supérieur ou égal à 2" << endl;
        exit(1);
    }
    int dimension = atoi(argv[1]);
    int nbMotifs = atoi(argv[2]);
    Puzzle *puzzle = new Puzzle(dimension, nbMotifs);
    puzzle->generatePuzzle();
    puzzle->shufflePuzzle();
    puzzle->showPuzzle();
    

    /*
    std::filesystem::path currentPath = std::filesystem::current_path();
    std::cout << "Répertoire de travail actuel : " << currentPath << std::endl;
    */

    //Test import fichier
    // Puzzle *testPuzzle = new Puzzle(4,4);
    // testPuzzle->importPuzzle("import/test.txt");
    // testPuzzle->showPuzzle();
    // testPuzzle->shufflePuzzle();
    // testPuzzle->exportPuzzle();
    return 0;
}

