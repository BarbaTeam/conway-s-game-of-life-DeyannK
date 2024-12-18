import java.util.Scanner;
import java.util.Random;
public class JeuVie {
    
    static final Scanner input = new Scanner(System.in);
    static final Random rand = new Random();
    int size ;
    Cell[][] ecosysteme ;
    int[][] voisines ;
    
    enum Cell{ALIVE,DEAD}

    /**
     * Constructeur d'un jeu de la vie de taille n.
     * Initialisé avec nxn cellules dont p% sont vivantes
     * @param n la taille du coté de l'écosystème
     */
    public JeuVie(int n, double p){
        size = n;
        ecosysteme = new Cell[size][size];
        voisines = new int[size][size];
        initialisationEcosysteme(p);
        initialisationVoisines();
        
    }

    public void dispEco() { //adapter la couleur de l'ecriture en fonction de la taille du tableau 
        StdDraw.clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (ecosysteme[i][j] == Cell.ALIVE) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledSquare(j + 0.5, size - i - 0.5, 0.5);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(j +0.5,size - i - 0.5,String.valueOf(voisines[i][j]));
                } else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.filledSquare(j + 0.5, size - i - 0.5, 0.5);
                }
            }
        }
        StdDraw.show();
    }

    public void initialisationEcosysteme(double p) {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (tireProba(p)) {
                    ecosysteme[i][j] = Cell.ALIVE;
                } else {
                    ecosysteme[i][j] = Cell.DEAD;
                }
            }
        }    
    }

    public void initialisationVoisines() {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                voisines[i][j] = nbVoisines(i,j);
            }
        }
    }

    public int nbVoisines(int x, int y) {
        int compteur = 0;
        Cell[] tab_voisine = extractTab(x,y);
        for (int i=0; i<8; i++) {
            if (tab_voisine[i] == Cell.ALIVE) compteur++;
        }
        return compteur;
    }

    /**
     * Cette fonction retourne un tableau a une entrée qui 
     * contient les cellules voisines de x et y
     */
    public Cell[] extractTab(int x, int y) {
        int compteur=0;
        Cell[] tab = new Cell[8];
        for (int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                if (i==0 && j==0) continue;
                //tableau torique
                tab[compteur] = ecosysteme[(x+i+size) % size][(y+j+size) % size];
                compteur++;
            }
        }
        return tab;
    }
    // Si il y a trois cellule vivante autour d'une cellule morte elle revie,
    // mais si il y a moins de deux cellule vivante autour d'une cellule vivante, elle meurs
    // et si il y a plus de trois cellule vivante autour d'une cellule vivante, elle meurs aussi
    public int nextGen() {
        int compteur = 0;
        Cell[][] tabNextGen = new Cell[size][size]; 
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (ecosysteme[i][j] == Cell.DEAD) {
                    if (nbVoisines(i,j) == 3) {
                        tabNextGen[i][j] = Cell.ALIVE;
                        compteur++;
                    }
                    else {
                        tabNextGen[i][j] = Cell.DEAD;
                    }
                }
                else {
                    if (nbVoisines(i,j) < 2 || 3 < nbVoisines(i,j)) {
                        tabNextGen[i][j] = Cell.DEAD;
                        compteur++;
                    }
                    else {
                        tabNextGen[i][j] = Cell.ALIVE;
                    }
                }
            }
        }
        ecosysteme = tabNextGen;
        initialisationVoisines();
        return compteur;    
    }

    /**
     * Renvoie vrai avec une probabilité p
     * @param p la probabilité de tirage vrai souhaitée
     * @return  le tirage vrai ou faux
     */
    public static boolean tireProba(double p){
        return (rand.nextDouble() < p);
    }

    public static void main(String[] args) {
        int n ; // taille de l'ecosystème
        int gen =0; // numéro de la génération courante
        int modification=0;

        final double PROBA = 0.6 ; // pour l'initialisation
        System.out.println("Taille de l'ecosystème : ");
        n = input.nextInt();
        StdDraw.setScale(0, n); // mise à l'échelle de la fenêtre
        StdDraw.enableDoubleBuffering();
        JeuVie jeu = new JeuVie(n,PROBA) ; // création du jeu
        jeu.dispEco();                    // affichage initial

        do{
            StdDraw.pause(100);
            modification=jeu.nextGen();
            gen++;
            System.out.println("Génération : " + gen + " Modification : " + modification);
            jeu.dispEco(); 
        } while (true);
    }
}