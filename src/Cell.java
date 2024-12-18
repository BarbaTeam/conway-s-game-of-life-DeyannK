import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cell {

    CellState cellState;
    int row;
    int col;

    public Cell(int row, int col, CellState cellState) {
        this.cellState = cellState;
        this.row = row;
        this.col = col;
    }

    public Cell(int row, int col, double prob) {
        setCellStateRand(prob, new Random());
        this.row = row;
        this.col = col;
    }

    public CellState getCellState() {
        return cellState;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public void setCellStateRand(double p, Random rand) {
        cellState = (rand.nextDouble() < p) ? CellState.ALIVE : CellState.DEAD;
    }

    @Override
    public String toString() {
        return cellState.toString();
    }
}
