public class BoardPriority implements Comparable<BoardPriority> {

    // the board that it represents
    private Board board;
    // the priority of the board
    private int priority;

    /**
     * @param b the board object of this instance
     * @param p the priority of this board
     */
    public BoardPriority(Board b, int p) {
        board = b;
        priority = p;
    }

    /**
     * @return the board object of this instance
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param otherBoard the other Board that is being compared to the current Board
     * @return whether this bored has a larger priority or not
     */
    @Override
    public int compareTo(BoardPriority otherBoard) {
        return Integer.compare(priority, otherBoard.priority);
    }

}
