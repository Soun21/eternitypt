#include <iostream>
#include "Puzzle.hpp"
#include <SFML/Graphics.hpp> 
#include <tuple>
#include <vector>



class PuzzleDisplayer{
    private:
        // Puzzle
        Puzzle * p;

        // Liste de couleurs aléatoires
        std::vector<std::tuple<int, int, int>> colors;
        
    public:
        //Constructeurs
        PuzzleDisplayer(Puzzle * p);
        void display();// Affiche le puzzle
        void drawTriangles(sf::RenderWindow& window, sf::Vector2f center, float size,int x,int y);// Dessine les triangles (motifs) de chaque pièces
        void drawSquare(sf::RenderWindow& window, sf::Vector2f position, float size);// Dessine les carrés (un par pièce)
        void drawGrid(sf::RenderWindow& window);// Dessine la grille
};