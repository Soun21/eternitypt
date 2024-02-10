#include <iostream>
#include "Puzzle.hpp"
#include <SFML/Graphics.hpp> 
#include <tuple>
#include <vector>



class PuzzleDisplayer{
    private:
        Puzzle * p;
        std::vector<std::tuple<int, int, int>> colors;
        
    public:
        //Constructeurs
        PuzzleDisplayer(Puzzle * p);
        void display();
        void drawTriangles(sf::RenderWindow& window, sf::Vector2f center, float size,int x,int y);
        void drawSquare(sf::RenderWindow& window, sf::Vector2f position, float size);
        void drawGrid(sf::RenderWindow& window);

};