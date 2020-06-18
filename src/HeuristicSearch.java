public class HeuristicSearch extends BestFirstSearch {
    /**
     *
     * @param b the board that the priority will be computed upon
     * @return the priority of the board by the number of the misplaced tiles
     */
    @Override
    int CalculatePriority(Board b) {
        return b.misplacedTiles(b);
    }
}
