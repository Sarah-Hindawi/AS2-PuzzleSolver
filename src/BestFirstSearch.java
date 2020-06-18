import java.util.PriorityQueue;

public abstract class BestFirstSearch {

    abstract int CalculatePriority(Board b);

    /**
     *
     * @param maxTime the max time on which the puzzle should be solved
     * @param intiState the initial state of the Board
     * @return
     */
    public Board Run(int maxTime, Board intiState) {
        //the time when the method started
        long startingTime = System.currentTimeMillis();
        //PriorityQueue that will hold BoardPriority objects
        PriorityQueue<BoardPriority> PQ = new PriorityQueue<>();
        //the priority of the initial board
        int p = CalculatePriority(intiState);
        //creating new BoardPriority object that will hold the Board and its priority
        BoardPriority BP = new BoardPriority(intiState, p);
        //adding that object to the priority queue
        PQ.add(BP);
        //the initial Board
        Board initial = BP.getBoard();
        //setting the depth of the initial board to 0
        initial.setDepth(0);
        //calling the set path method upon the initial board
        initial.setPath(initial.toString());

        //while the method is still running in the allowed amount of time
        while (System.currentTimeMillis() - startingTime < maxTime) {
            //remove the board form the priority queue with the highest priority
            BoardPriority nextState = PQ.remove();
            //if we found the goal board then return it
            if (nextState.getBoard().isGoal()) return nextState.getBoard();
            //else if we did not get to the goal board yet
            else {
                //for each child of the board removed
                for (Board neighbor : nextState.getBoard().neighbors()) {
                    //set its depth to its parent's depth plus one
                    neighbor.setDepth(nextState.getBoard().getDepth() + 1);
                    //set its path to its parent's path plus the board itself
                    neighbor.setPath(nextState.getBoard().UpdatePathToStartState() + "\n" + neighbor.toString());
                    //calculate the priority of that child
                    p = CalculatePriority(neighbor);
                    //create a new BoardPriority object holding the child's board
                    BoardPriority child = new BoardPriority(neighbor, p);
                    //add that child BoardPriority to the priority queue
                    PQ.add(child);
                }
            }
        }
        //could not reach the goal board in the allowed period of time
        System.out.println("The algorithm was unable to find the solution in the given time");
        return null;
    }
}
