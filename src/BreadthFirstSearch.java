public class BreadthFirstSearch extends BestFirstSearch {

    /**
     *
     * @param b the board that the priority will be computed upon
     * @return the priority of the board by its depth
     */
    @Override
    int CalculatePriority(Board b) {
        return b.getDepth();
    }
}
