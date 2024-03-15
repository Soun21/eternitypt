package testrelou;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.ParallelPortfolio;
import org.chocosolver.solver.Solution;

public class eternity {

	public static void main(String[] args) {
	    	

		String fileName = "D:\\cours\\M2\\eternitypt-main\\eternitycplusplus\\eternity\\export\\export.txt";
		// Récupération des paramètres spéciaux
	   int[] specialParams = readSpecialParams(fileName);
	   int param1 = specialParams[0];
	   int param2 = specialParams[1];

	   // Récupération des pièces du puzzle
	   List<PuzzlePiece> puzzlePieces = readPuzzlePieces(fileName);

	 //  afficherPuzzle(puzzlePieces);
	 //  System.out.println("Solver 1");
	  // solvePuzzle(param1, param2, puzzlePieces);
	   
	  // System.out.println("Solver 2");
	   multiSolver(param1, param2, puzzlePieces);


   }
	   
		public static void affichePiece(List<PuzzlePiece> puzzlePieces, int i, int orientation) {
			int j= i-1;
			System.out.println(puzzlePieces.get(j).getNorthValue(orientation) + " "
					+ puzzlePieces.get(j).getWestValue(orientation) + " "
					+ puzzlePieces.get(j).getSouthValue(orientation) + " "
					+ puzzlePieces.get(j).getEastValue(orientation));	
		}
	
	
	
	   public static List<PuzzlePiece> readPuzzlePieces(String fileName) {
	        List<PuzzlePiece> puzzlePieces = new ArrayList<>();

	        try {
	            BufferedReader reader = new BufferedReader(new FileReader(fileName));

	            // Ignorer la première ligne
	            reader.readLine();

	            // Lecture des pièces de puzzle
	            String puzzleLine;
	            while ((puzzleLine = reader.readLine()) != null) {
	                String[] puzzlePiece = puzzleLine.split(" ");
	                int northValue = Integer.parseInt(puzzlePiece[0]);
	                int westValue= Integer.parseInt(puzzlePiece[1]);
	                int southValue = Integer.parseInt(puzzlePiece[2]);
	                int eastValue = Integer.parseInt(puzzlePiece[3]);

	                PuzzlePiece piece = new PuzzlePiece(northValue, westValue, southValue, eastValue);
	                puzzlePieces.add(piece);
	            }

	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return puzzlePieces;
	    }
	
	   public static int[] readSpecialParams(String fileName) {
	        int[] specialParams = new int[2];

	        try {
	            BufferedReader reader = new BufferedReader(new FileReader(fileName));

	            // Lecture de la première ligne pour obtenir les paramètres spéciaux
	            String specialParamsLine = reader.readLine();
	            String[] specialParamsStr = specialParamsLine.replaceAll("[^0-9 ]", "").split(" ");

	            specialParams[0] = Integer.parseInt(specialParamsStr[0]);
	            specialParams[1] = Integer.parseInt(specialParamsStr[1]);

	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return specialParams;
	    }


	   public static void multiSolver(int param1, int param2, List<PuzzlePiece> puzzlePieces) {
		   
													// initialisation du nombre de solutions
		    int nbModels = 3;													// nombre de modèles
		    
		    
		    ParallelPortfolio portfolio = new ParallelPortfolio();				//crée un portfolio

	
		    for(int s=0;s<nbModels;s++){
		        portfolio.addModel(makeModel(s, param1, param2, puzzlePieces));		//ajoute les modeles au portfolio
		    }

		    System.out.println("Solving portfolio...");
		    long start = System.currentTimeMillis();

		    portfolio.solve();
		    
		    long end = System.currentTimeMillis();
		    System.out.println("Solving Finished!");
		    
		    
	        //portfolio.streamSolutions().distinct().limit(4).forEach(s -> System.out.println(s));
		    Solution solution = portfolio.streamSolutions().limit(1).findFirst().get();
		    System.out.println(solution.toString());
		    System.out.println("Time: " + (end - start)/1000 + "." + (end - start)%1000 + "s");
		    portfolio.getModels().forEach(m -> m.getSolver().showStatistics());	//affiche les statistiques	
	        Model finder = portfolio.getBestModel();
	        finder.getSolver().showStatistics();
		    //portfolio.streamSolutions().limit(1).forEach(s -> System.out.println(s));
		     // Affichage des pièces dans leurs orientations solutions
  
	        
		    //System.out.println("Number of solutions found: " + nbSols);
	        //portfolio.getModels().forEach(m -> m.getSolver().reset());	//reset les contraintes des solveurs

	        //portfolio.streamSolutions().forEach(s -> System.out.println(s));	//affiche les solutions;
		    //portfolio.streamSolutions();

		    //long totalsol = portfolio.streamSolutions().count();
	        
	        //System.out.println("Total solutions: " + totalsol);
	        
	        

	        //portfolio.getModels().forEach(m -> m.getSolver().reset());	//reset les contraintes des solveurs
	        
	        //portfolio.getModels().forEach(m -> System.out.println(m.toString()));	//reset les contraintes des solveurs
//	        Model finder = portfolio.getBestModel();
//	        finder.displayPropagatorOccurrences();
//	        System.out.println(finder.toString());
//	        finder.getObjective();	//affiche les objectifs
//	        finder.getSolver().showStatistics();							//affiche les statistiques

	
	} 
	   
	   public static Model makeModel(int id, int param1, int param2, List<PuzzlePiece> puzzlePieces) {
		    int gridSize = param1;  											//taille de la grille
		    int numPieces = gridSize * gridSize;								//nombre de pièces
		    
		    Model model = new Model("Model " + id);								//création du modèle

		    IntVar[] Pi = model.intVarArray("Pi", numPieces, 1, numPieces);		//création des variables Pi

		    IntVar[] Ri = model.intVarArray("Ri", numPieces, 0, 3);				//création des variables Ri
		    
		    //déclaration des 18 tuples
		    
		    //tuples horizontaux
		    //tuples coins+bord
		    Tuples tuplesH_CoinsHautGauche = new Tuples(true); 	
		    Tuples tuplesH_CoinsHautDroit = new Tuples(true);
		    Tuples tuplesH_CoinsBasGauche = new Tuples(true); 	
		    Tuples tuplesH_CoinsBasDroit = new Tuples(true);

		    //tuples bord+bord
		    Tuples tuplesH_BordBordHaut = new Tuples(true);
		    Tuples tuplesH_BordBordBas = new Tuples(true);
		    
		    //tuples bord+centre
		    Tuples tuplesH_BordCentreGauche = new Tuples(true);
		    Tuples tuplesH_BordCentreDroit = new Tuples(true);
		    
		    //tuples centre+centre
		    Tuples tuplesH_CentreCentre = new Tuples(true);
		    
		    //tuples verticaux
		    //tuples coins+bord
		    Tuples tuplesV_CoinsHautGauche = new Tuples(true);
		    Tuples tuplesV_CoinsHautDroit = new Tuples(true);
		    Tuples tuplesV_CoinsBasGauche = new Tuples(true);
		    Tuples tuplesV_CoinsBasDroit = new Tuples(true);
		    
		    //tuples bord+bord
		    Tuples tuplesV_BordBordGauche = new Tuples(true);
		    Tuples tuplesV_BordBordDroit = new Tuples(true);
		    
		    //tuples bord+centre
		    Tuples tuplesV_BordCentreHaut = new Tuples(true);
		    Tuples tuplesV_BordCentreBas = new Tuples(true);
		    
		    //tuples centre+centre
		    Tuples tuplesV_CentreCentre = new Tuples(true);
		    

		    
		    //System.out.println("taille de la grille : " + puzzlePieces.size());

		    IntVar[] X = new IntVar[4];
		          
		    // pour toutes les cases de la grille
		    for (int i = 1; i <= puzzlePieces.size(); i++) { 	//de 1 a 16
		        for (int j = 1; j <= puzzlePieces.size(); j++) {
		        	if(i != j){
		        		X[0] = Pi[i-1];
		       	        X[1] = Ri[i-1];
		       	        X[2] = Pi[j-1];
		       	        X[3] = Ri[j-1];         	
		            	//pour toutes les orientations des pieces
		                for (int orientation1 = 0; orientation1 < 4; orientation1++) {
		                    for (int orientation2 = 0; orientation2 < 4; orientation2++) {
		                    	if(isCoinHautGaucheH(puzzlePieces.get(i-1), orientation1, puzzlePieces.get(j-1), orientation2)) {
		                    		tuplesH_CoinsHautGauche.add(i, orientation1, j, orientation2);
		                    	}
								if (isCoinHautDroitH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_CoinsHautDroit.add(i, orientation1, j, orientation2);
								}
								if (isCoinBasGaucheH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_CoinsBasGauche.add(i, orientation1, j, orientation2);
								}
								if (isCoinBasDroitH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_CoinsBasDroit.add(i, orientation1, j, orientation2);
								}
								if (isBordHautH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_BordBordHaut.add(i, orientation1, j, orientation2);
								}
								if (isBordBasH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_BordBordBas.add(i, orientation1, j, orientation2);
									}
								if (isBordCentreGaucheH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_BordCentreGauche.add(i, orientation1, j, orientation2);
									}
								if (isBordCentreDroitH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_BordCentreDroit.add(i, orientation1, j, orientation2);
									}
								if (isCentreCentreH(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesH_CentreCentre.add(i, orientation1, j, orientation2);
									}						
								if (isCoinHautGaucheV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_CoinsHautGauche.add(i, orientation1, j, orientation2);
									}
								if (isCoinHautDroitV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_CoinsHautDroit.add(i, orientation1, j, orientation2);
									}
								if (isCoinBasGaucheV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_CoinsBasGauche.add(i, orientation1, j, orientation2);
									}
								if (isCoinBasDroitV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_CoinsBasDroit.add(i, orientation1, j, orientation2);
									}
								if (isBordGaucheV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_BordBordGauche.add(i, orientation1, j, orientation2);
								}
								if (isBordDroitV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_BordBordDroit.add(i, orientation1, j, orientation2);
								}
								if (isBordCentreHautV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_BordCentreHaut.add(i, orientation1, j, orientation2);
								}
								if (isBordCentreBasV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_BordCentreBas.add(i, orientation1, j, orientation2);
								}
								if (isCentreCentreV(puzzlePieces.get(i - 1), orientation1, puzzlePieces.get(j - 1), orientation2)) {
									tuplesV_CentreCentre.add(i, orientation1, j, orientation2);
								}

		                    }
		                } 
		            }
		        }
		    }
		    
		    
		   	    
		    

		    // déclaration des contraintes
		    
		    
		    model.allDifferent(Pi).post();						//toutes les pièces sont différentes
		    
		    
		    IntVar[] X2 = new IntVar[4];	//pour contenir les variables de 2 pièces
		    
		    
		    for (int i = 0; i < puzzlePieces.size(); i++) { 	     // pour toutes les cases de la grille
		        for (int j = 0; j < puzzlePieces.size(); j++) {		 //de 1 a 16
		        	
		        	if(i != j){		                        //s'assurer que les pièces sont différentes
	            		X2[0] = Pi[i];
	            		X2[1] = Ri[i];
	            		X2[2] = Pi[j];
	            		X2[3] = Ri[j];

		       	        if(i==0 && j==1 ){
		             		model.table(X2, tuplesH_CoinsHautGauche,"CT+").post();			//coin haut gauche horizontal
		       	        }
						if (i == 0 && j == gridSize) {
							model.table(X2, tuplesV_CoinsHautGauche,"CT+").post();			//coin haut gauche vertical
						}

		       	        if(i==gridSize-1-1 && j==gridSize-1){
		       	        	model.table(X2, tuplesH_CoinsHautDroit,"CT+").post();			//coin haut droit horizontal
		       	        }
						if (i == gridSize-1 && j == gridSize+gridSize-1) {
							model.table(X2, tuplesV_CoinsHautDroit,"CT+").post();			//coin haut droit vertical
						}

						if (i == (gridSize-1)*gridSize  && j == (gridSize-1)*gridSize+1) {
							model.table(X2, tuplesH_CoinsBasGauche,"CT+").post();			//coin bas gauche horizontal
						}
						if (i == (gridSize-2)*gridSize && j == (gridSize-1)*gridSize) {		//coin bas gauche vertical
							model.table(X2, tuplesV_CoinsBasGauche,"CT+").post();
						}

						if (i == (gridSize*gridSize-2) && j == (gridSize*gridSize-1)) {		//coin bas droit horizontal
							model.table(X2, tuplesH_CoinsBasDroit,"CT+").post();
						}
						if (i == ((gridSize-1)*gridSize-1) && j == (gridSize*gridSize-1)) {	//coin bas gauche vertical
							model.table(X2, tuplesV_CoinsBasDroit,"CT+").post();
						}

						if (i > 0 && j > 0 && i < gridSize-1 &&
								j < gridSize-1 && i < j && j == i+1) {						// bord haut horiztontal
							model.table(X2, tuplesH_BordBordHaut,"CT+").post();		
						}
						
						if (i > (gridSize-1)*gridSize && j > (gridSize-1)*gridSize 
								&& i < gridSize*gridSize-1 && j < gridSize*gridSize-1 		//bord bas horizontal 
								&& i < j && j==i+1) {		
							model.table(X2, tuplesH_BordBordBas,"CT+").post();
						}

						if (i%gridSize == 0 && i != 0 
								&& j != 0	&& j == i+1										// bord centre gauche horizontal
								&& i != (gridSize-1)*gridSize
								&& j != (gridSize-1)*gridSize) {
							model.table(X2, tuplesH_BordCentreGauche,"CT+").post();  
						}

						if(i !=gridSize-2 && j!=gridSize-1
								&& i!=gridSize*gridSize-2 && j!=gridSize*gridSize-1			// bord centre droit horizontal
								&& (i + 1) % gridSize == gridSize-1
								&& j == i+1) {
							model.table(X2, tuplesH_BordCentreDroit,"CT+").post();
						}

						if (i > gridSize-1 && i < gridSize*(gridSize-1) 					// centre centre horizontal
								&& j > gridSize-1 && j < gridSize*(gridSize-1)
								&& j == i+1 && i%gridSize != 0 && j%gridSize != 0 
								&& i%gridSize != gridSize-1 && j%gridSize != gridSize-1 ) {		 
							model.table(X2, tuplesH_CentreCentre,"CT+").post();
						}

						if (i%gridSize == 0 && j%gridSize == 0 && j == i+gridSize			//bord gauche vertical
								&& i != 0 && j != 0	&& i != gridSize*(gridSize-1) 
								&& j != gridSize*(gridSize-1) ) {			
							model.table(X2, tuplesV_BordBordGauche,"CT+").post();
						}

						if (i%gridSize == gridSize-1 && j%gridSize == gridSize-1
								&& j == i+gridSize	&& i != gridSize-1 && j != gridSize-1	//bord droit vertical
								&& i != gridSize*gridSize-1 && j != gridSize*gridSize-1 ) {		
							model.table(X2, tuplesV_BordBordDroit,"CT+").post();
						}

						if (i > 0 && i < gridSize-1 && j == i+gridSize) {
							model.table(X2, tuplesV_BordCentreHaut,"CT+").post();			// bord centre haut vertical
						}
						
						if (j > gridSize*(gridSize-1) && j < gridSize*gridSize-1			// bord centre bas vertical
								&& i == j-gridSize) {
							model.table(X2, tuplesV_BordCentreBas,"CT+").post();
						}
						
						if (i > gridSize - 1 && i < gridSize * (gridSize - 1) 
								&& j > gridSize - 1 && j < gridSize * (gridSize - 1) 		// centre centre vertical
								&& j == i + gridSize && i % gridSize != 0 
								&& j % gridSize != 0 && i % gridSize != gridSize-1 
								&& j % gridSize != gridSize-1) { 
							model.table(X2, tuplesV_CentreCentre,"CT+").post();
						}
						
		            }
		        }
		    }
		    
		    System.out.println("Model " + id + " created");
		    
		    
		   return model;
	   }
  
		public static void affichePieceSolution(List<PuzzlePiece> puzzlePieces, int i, int orientation) {
//			int j= i-1;
//			System.out.println(puzzlePieces.get(j).getNorthValue(orientation) + " "
//					+ puzzlePieces.get(j).getWestValue(orientation) + " "
//					+ puzzlePieces.get(j).getSouthValue(orientation) + " "
//					+ puzzlePieces.get(j).getEastValue(orientation));	
		    int j= i-1;
		    // Affichage de la valeur nord, du numéro de pièce, et de la valeur sud
		    System.out.print(puzzlePieces.get(j).getNorthValue(orientation) + " ");
		    System.out.print(i + " ");
		    System.out.print(puzzlePieces.get(j).getSouthValue(orientation) + "\n");
		    // Affichage de la valeur ouest et de la valeur est
		    System.out.print(puzzlePieces.get(j).getWestValue(orientation) + "   ");
		    System.out.print(puzzlePieces.get(j).getEastValue(orientation) + "   ");
			
			
		}
		
		public static void affichePieceNorthValue(List<PuzzlePiece> puzzlePieces, int i, int orientation) {
		    int j= i-1;
		    System.out.print("  "+puzzlePieces.get(j).getNorthValue(orientation) + "      ");
		}

		public static void affichePieceEastIndexWestValue(List<PuzzlePiece> puzzlePieces, int i, int orientation, int index) {
		    int j= i-1;
		    System.out.print(puzzlePieces.get(j).getWestValue(orientation) + " ");
		    System.out.print(formatPieceNumber(index) + " ");
		    System.out.print(puzzlePieces.get(j).getEastValue(orientation) + "   ");
		}

		public static void affichePieceSouthValue(List<PuzzlePiece> puzzlePieces, int i, int orientation) {
		    int j= i-1;
		    System.out.print("  "+puzzlePieces.get(j).getSouthValue(orientation) + "      ");
		}
		public static String formatPieceNumber(int number) {
		    // Ajoute un zéro devant le numéro si celui-ci est inférieur à 10
		    return (number < 10) ? "0" + number : String.valueOf(number);
		}
		   	
	   
	   	//check adjacence horizontale
	   	//coins+bord horizontaux
	    private static Boolean isCoinHautGaucheH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2) 
					&& piece1.getNorthValue(orientation1) == 0	&& 	piece1.getWestValue(orientation1) == 0
					&& piece1.getSouthValue(orientation1) != 0	&& 	piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) == 0	&& 	piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) != 0	&& 	piece2.getEastValue(orientation2) != 0) {
				return true;
			}
			return false;
	   }
		private static Boolean isCoinHautDroitH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2) 
					&& piece1.getNorthValue(orientation1) == 0	&& 	piece1.getWestValue(orientation1) != 0
					&& piece1.getSouthValue(orientation1) != 0	&& 	piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) == 0	&& 	piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) != 0	&& 	piece2.getEastValue(orientation2) == 0) {
				return true;
			}
			return false;
		}
		private static Boolean isCoinBasGaucheH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2)
					&& piece1.getNorthValue(orientation1) != 0 &&	piece1.getWestValue(orientation1) == 0
					&& piece1.getSouthValue(orientation1) == 0 &&	piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) != 0 &&	piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) == 0 &&	piece2.getEastValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		private static Boolean isCoinBasDroitH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2)
					&& piece1.getNorthValue(orientation1) != 0 && piece1.getWestValue(orientation1) != 0
					&& piece1.getSouthValue(orientation1) == 0 && piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) != 0 && piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) == 0 && piece2.getEastValue(orientation2) == 0) {
				return true;
			}
			return false;
		}
		//bord+bord horizontaux
		private static Boolean isBordHautH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2)
					&& piece1.getNorthValue(orientation1) == 0 && piece1.getWestValue(orientation1) != 0
					&& piece1.getSouthValue(orientation1) != 0 && piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) == 0 && piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) != 0 && piece2.getEastValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		private static Boolean isBordBasH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2)
					&& piece1.getNorthValue(orientation1) != 0 && piece1.getWestValue(orientation1) != 0
					&& piece1.getSouthValue(orientation1) == 0 && piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) != 0 && piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) == 0 && piece2.getEastValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		//bord+centre horizontaux
		private static Boolean isBordCentreGaucheH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2)
					&& piece1.getNorthValue(orientation1) != 0 && piece1.getWestValue(orientation1) == 0
					&& piece1.getSouthValue(orientation1) != 0 && piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) != 0 && piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) != 0 && piece2.getEastValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		private static Boolean isBordCentreDroitH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {	
			if (piece2.getWestValue(orientation1) == piece1.getEastValue(orientation2)
					&& piece1.getNorthValue(orientation1) != 0 && piece1.getWestValue(orientation1) != 0
					&& piece1.getSouthValue(orientation1) != 0 && piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) != 0 && piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) != 0 && piece2.getEastValue(orientation2) == 0) {
				return true;
			}
			return false;
		}
		//centre+centre horizontaux
		private static Boolean isCentreCentreH(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getEastValue(orientation1) == piece2.getWestValue(orientation2)
					&& piece1.getNorthValue(orientation1) != 0 && piece1.getWestValue(orientation1) != 0
					&& piece1.getSouthValue(orientation1) != 0 && piece1.getEastValue(orientation1) != 0
					&& piece2.getNorthValue(orientation2) != 0 && piece2.getWestValue(orientation2) != 0
					&& piece2.getSouthValue(orientation2) != 0 && piece2.getEastValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		
		//check adjacence verticale
		//coins+bord verticaux
		
		private static Boolean isCoinHautGaucheV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) == 0 && piece1.getNorthValue(orientation1) == 0
					&& piece1.getEastValue(orientation1) != 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) == 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) != 0 && piece2.getSouthValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		private static Boolean isCoinHautDroitV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) != 0 && piece1.getNorthValue(orientation1) == 0
					&& piece1.getEastValue(orientation1) == 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) != 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) == 0 && piece2.getSouthValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		private static Boolean isCoinBasGaucheV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) == 0 && piece1.getNorthValue(orientation1) != 0
					&& piece1.getEastValue(orientation1) != 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) == 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) != 0 && piece2.getSouthValue(orientation2) == 0) {
				return true;
			}
			return false;
		}
		private static Boolean isCoinBasDroitV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) != 0 && piece1.getNorthValue(orientation1) != 0
					&& piece1.getEastValue(orientation1) == 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) != 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) == 0 && piece2.getSouthValue(orientation2) == 0) {
				return true;
			}
			return false;
		}
		//bord+bord verticaux
		private static Boolean isBordGaucheV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) == 0 && piece1.getNorthValue(orientation1) != 0
					&& piece1.getEastValue(orientation1) != 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) == 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) != 0 && piece2.getSouthValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		private static Boolean isBordDroitV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) != 0 && piece1.getNorthValue(orientation1) != 0
					&& piece1.getEastValue(orientation1) == 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) != 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) == 0 && piece2.getSouthValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		//bord+centre verticaux
		private static Boolean isBordCentreHautV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) != 0 && piece1.getNorthValue(orientation1) == 0
					&& piece1.getEastValue(orientation1) != 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) != 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) != 0 && piece2.getSouthValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		private static Boolean isBordCentreBasV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) != 0 && piece1.getNorthValue(orientation1) != 0
					&& piece1.getEastValue(orientation1) != 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) != 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) != 0 && piece2.getSouthValue(orientation2) == 0) {
				return true;
			}
			return false;
		}
		//centre+centre verticaux
		private static Boolean isCentreCentreV(PuzzlePiece piece1, int orientation1, PuzzlePiece piece2, int orientation2) {
			if (piece1.getSouthValue(orientation1) == piece2.getNorthValue(orientation2)
					&& piece1.getWestValue(orientation1) != 0 && piece1.getNorthValue(orientation1) != 0
					&& piece1.getEastValue(orientation1) != 0 && piece1.getSouthValue(orientation1) != 0
					&& piece2.getWestValue(orientation2) != 0 && piece2.getNorthValue(orientation2) != 0
					&& piece2.getEastValue(orientation2) != 0 && piece2.getSouthValue(orientation2) != 0) {
				return true;
			}
			return false;
		}
		
		
			public static void afficherPuzzle(List<PuzzlePiece> puzzle) {
			int cpt = 1;
			for (PuzzlePiece piece : puzzle) {
				System.out.println(
						"Pièce " + cpt++ + " : " +
						piece.northValue + " " + piece.westValue + " " + piece.southValue + " " + piece.eastValue);
			}
		}
	}





