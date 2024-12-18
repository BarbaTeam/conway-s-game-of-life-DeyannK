import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Board {
    private Cell[][] cells;
    private int size;

    /**
     * Constructeur du tableau, va appeler la méthode initRand() pour initialiser de manière aléatoire le tableau
     * @param size nombre de colonnes, lignes du tableau
     * @param probability probabilité qu'une cellule soit vivante, valeur comprise entre 0 et 1.
     */
    public Board(int size, double probability) {
        cells = new Cell[size][size]; //+2 pour une barriere de securité
        this.size = size;
        initRand(probability);
    }

    private void initRand(double probability){
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                cells[row][col] = new Cell(row, col, probability);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Cellule hors de porté: (" + row + ", " + col + ")");
        }
        return cells[row][col];
    }

    private List<Cell> getNeighbours(Cell cell) {
        int x=cell.getCol();
        int y=cell.getRow();
        List<Cell> neighbours = new ArrayList<>();
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                if(i==0 && j==0) continue;

                // Indices modulaires
                int newRow = (y + i + size) % size;
                int newCol = (x + j + size) % size;

                neighbours.add(cells[newRow][newCol]);

            }
        }
        return neighbours;
    }

    private int getNumberOfAliveNeighbour(Cell cell) {
        List<Cell> neighbours = getNeighbours(cell);
        int count = 0;
        for (Cell neighbour : neighbours) {
            if (neighbour.getCellState() == CellState.ALIVE) {
                count++;
            }
        }
        return count;
    }

    public CellState getFutureCellState(Cell cell) {
        if (cell.getCellState() == CellState.DEAD && getNumberOfAliveNeighbour(cell) == 3) {
            return CellState.ALIVE;
        }
        if (cell.getCellState() == CellState.ALIVE && (getNumberOfAliveNeighbour(cell) == 2 || getNumberOfAliveNeighbour(cell) == 3)) {
            return CellState.ALIVE;
        }
        return CellState.DEAD;
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell != null) {
                    boardString.append(cell.toString());
                } else {
                    boardString.append("null ");
                }
            }
            boardString.append("\n"); // Newline after each row
        }
        return boardString.toString();
    }
}
