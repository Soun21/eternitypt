#include "../headers/PuzzleDisplayer.hpp"
#include <cstdlib> // Pour la fonction rand
#include <ctime> // Pour initialiser le générateur de nombres aléatoires


using namespace std;

// Largeur de la fenêtre
const int WINDOW_WIDTH = 800;

// Hauteur de la fenêtre
const int WINDOW_HEIGHT = 800;

//Constructeurs

PuzzleDisplayer::PuzzleDisplayer(Puzzle * pRef) : p(pRef){
    // Variable pour le puzzle
    this->p = pRef;

    // On crée 22 couleurs aléatoires pour chaque motifs
    this->colors = {
        {0, 0, 0},   // Noir
        {0, 255, 0},   // Vert
        {0, 0, 255},   // Bleu
        {255, 255, 0}, // Jaune
        {255, 0, 255}, // Magenta
        {0, 255, 255}, // Cyan
        {128, 0, 0},   // Rouge foncé
        {0, 128, 0},   // Vert foncé
        {0, 0, 128},   // Bleu foncé
        {128, 128, 0}, // Jaune foncé
        {128, 0, 128}, // Magenta foncé
        {0, 128, 128}, // Cyan foncé
        {255, 128, 0}, // Orange
        {255, 0, 128}, // Rose
        {128, 255, 0}, // Vert clair
        {0, 255, 128}, // Turquoise
        {128, 0, 255}, // Violet
        {0, 128, 255}, // Bleu ciel
        {255, 128, 128}, // Rose clair
        {128, 255, 128}, // Vert pomme
        {128, 128, 255}, // Bleu lavande
        {255, 255, 128}  // Jaune pâle
    };
}


void PuzzleDisplayer::drawTriangles(sf::RenderWindow& window, sf::Vector2f center, float size,int x,int y) {
    
    
    // Calcul de l'index de la pièce
    int index = y * p->getDimension() +x;

    // On crée des index pour colorier chaque motifs ensuite
    int numT3 = p->getPuzzle()->at(index).getN();
    int numT2 = p->getPuzzle()->at(index).getW();
    int numT1 = p->getPuzzle()->at(index).getS();
    int numT4 = p->getPuzzle()->at(index).getE();




    // Générer des valeurs aléatoires pour les composantes RGB (pour colorier les motifs)
    int rT1 = std::get<0>(colors.at(numT1));
    int gT1 = std::get<1>(colors.at(numT1));
    int bT1 = std::get<2>(colors.at(numT1));
    int rT2 = std::get<0>(colors.at(numT2));
    int gT2 = std::get<1>(colors.at(numT2));
    int bT2 = std::get<2>(colors.at(numT2));
    int rT3 = std::get<0>(colors.at(numT3));
    int gT3 = std::get<1>(colors.at(numT3));
    int bT3 = std::get<2>(colors.at(numT3));
    int rT4 = std::get<0>(colors.at(numT4));
    int gT4 = std::get<1>(colors.at(numT4));
    int bT4 = std::get<2>(colors.at(numT4));
    

    // Création des 4 triangles (un par motif de la pièce)
    sf::ConvexShape triangle,triangle2,triangle3,triangle4;
    triangle.setPointCount(3);
    triangle.setPoint(0, sf::Vector2f(0, 0));
    triangle.setPoint(1, sf::Vector2f(size, size));
    triangle.setPoint(2, sf::Vector2f(-size, size));
    triangle.setPosition(center);
    triangle.setOutlineThickness(2); // Épaisseur du contour en pixels
    triangle.setOutlineColor(sf::Color::Black); // Couleur du contour


    triangle2.setPointCount(3);
    triangle2.setPoint(0, sf::Vector2f(0, 0));
    triangle2.setPoint(1, sf::Vector2f(-size, size)); // Pointe du triangle en haut
    triangle2.setPoint(2, sf::Vector2f(-size, -size));
    triangle2.setPosition(center);
    triangle2.setOutlineThickness(2); // Épaisseur du contour en pixels
    triangle2.setOutlineColor(sf::Color::Black); // Couleur du contour


    triangle3.setPointCount(3);
    triangle3.setPoint(0, sf::Vector2f(0, 0));
    triangle3.setPoint(1, sf::Vector2f(-size, -size)); // Pointe du triangle en haut
    triangle3.setPoint(2, sf::Vector2f(size, -size));
    triangle3.setPosition(center);
    triangle3.setOutlineThickness(2); // Épaisseur du contour en pixels
    triangle3.setOutlineColor(sf::Color::Black); // Couleur du contour


    triangle4.setPointCount(3);
    triangle4.setPoint(0, sf::Vector2f(0, 0));
    triangle4.setPoint(1, sf::Vector2f(size, -size)); // Pointe du triangle en haut
    triangle4.setPoint(2, sf::Vector2f(size, size));
    triangle4.setPosition(center);
    triangle4.setOutlineThickness(2); // Épaisseur du contour en pixels
    triangle4.setOutlineColor(sf::Color::Black); // Couleur du contour


    // Définir la couleur aléatoire de chaque triangle
    triangle.setFillColor(sf::Color(rT1, gT1, bT1));
    triangle2.setFillColor(sf::Color(rT2, gT2, bT2));
    triangle3.setFillColor(sf::Color(rT3, gT3, bT3));
    triangle4.setFillColor(sf::Color(rT4, gT4, bT4));



    // On ajoute les triangles
    window.draw(triangle);
    window.draw(triangle2);
    window.draw(triangle3);
    window.draw(triangle4);

}


void PuzzleDisplayer::drawSquare(sf::RenderWindow& window, sf::Vector2f position, float size) {
    // On crée le carré représentant la pièce
    sf::RectangleShape square(sf::Vector2f(size, size));

    // On le place
    square.setPosition(position);

    // On le colorie
    square.setFillColor(sf::Color::Transparent);

    // On définit l'épaissuer du contour
    square.setOutlineThickness(1.f);

    // On définit la couleur du contour
    square.setOutlineColor(sf::Color::Black);

    // On dessine le carré
    window.draw(square);
}

void PuzzleDisplayer::drawGrid(sf::RenderWindow& window) {
    // On récupére la taille de la grille depuis Puzzle
    int gridSize = this->p->getDimension(); 

    // On définit la taille des carrés (pièce de puzzle) et des triangles (motifs)
    const float squareSize = WINDOW_WIDTH / float(gridSize);
    const float triangleSize = squareSize / 2.f;

    for (int y = 0; y < gridSize; ++y) {
        for (int x = 0; x < gridSize; ++x) {
            // Pour chaque pièce on crée un carré et 4 triangles
            sf::Vector2f position(x * squareSize, y * squareSize);
            drawSquare(window, position, squareSize);
            sf::Vector2f center(position.x + squareSize / 2, position.y + squareSize / 2);
            drawTriangles(window, center, triangleSize,x,y);
        }
    }
}


void PuzzleDisplayer::display() {

    // Création de la fenêtre de rendu
    sf::RenderWindow window(sf::VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), "Eternity II");

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                // Création d'un évènement de fermeture
                window.close();
        }
        // On vide la fenêtre
        window.clear(sf::Color::White);

        // On dessine la grille dans la fenêtre
        drawGrid(window);

        // On affiche la fenêtre
        window.display();
    }

}



