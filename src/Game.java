public class Game {
    public static void main(String[] args) {
        double p = 0.6;
        int size = 65;
        Board board = new Board(size, p);

        for(int i=0; i<1000; i++) {
            System.out.println(board);
            board = cycle(board, p);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static Board cycle(Board board, double probability) {
        Board newBoard = new Board(board.getSize(), probability);
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                CellState cellState = board.getFutureCellState(board.getCell(i,j));
                newBoard.getCell(i,j).setCellState(cellState);
            }
        }
        return newBoard;
    }
}
