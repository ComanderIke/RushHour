import java.util.ArrayList;

/**
 * This is a template for the class corresponding to the blocking
 * heuristic.  This heuristic returns zero for goal states, and
 * otherwise returns one plus the number of cars blocking the path of
 * the goal car to the exit.  This class is an implementation of the
 * <tt>Heuristic</tt> interface, and must be implemented by filling in
 * the constructor and the <tt>getValue</tt> method.
 */
public class BlockingHeuristic implements Heuristic {

    /**
     * This is the required constructor, which must be of the given form.
     */
	int numCars;
	boolean [] carOrient;//false = horizontal
	int [] carSizes;
	int [] carFPos;
	int gridSize;
	int goalCar;
	Puzzle puzzle;
	
    public BlockingHeuristic(Puzzle puzzle) {
    	this.puzzle = puzzle;
    	numCars=puzzle.getNumCars();
    	carOrient = new boolean[numCars];
    	carSizes = new int[numCars];
    	carFPos= new int[numCars];
    	gridSize = puzzle.getGridSize();
    	goalCar=0;
    	
		for( int car=0; car < numCars; car++) {
			carOrient[car]=puzzle.getCarOrient(car);
			carSizes[car]=puzzle.getCarSize(car);
			carFPos[car] = puzzle.getFixedPosition(car);
			
		}

    }
	

    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
//    private boolean isBlockingCar(int blockingCarIndex, State state) {
//        return this.puzzle.getCarOrient(blockingCarIndex) && // only vertical aligned cars are blocking
//                this.puzzle.getFixedPosition(blockingCarIndex) > (state.getVariablePosition(0) + this.carSizes[goalCar] - 1) && // check if car is behind ours (horizontal)
//                this.carFPos[goalCar] >= state.getVariablePosition(blockingCarIndex) && // check if car is between ours (vertical)
//                this.carFPos[goalCar] <= (state.getVariablePosition(blockingCarIndex) + this.puzzle.getCarSize(blockingCarIndex) - 1); // check if car is between ours (vertical)
//    }
    public int getCarsBlockingGoal(State state) {
    	int blocks=0;
    	for(int car = 1; car < numCars; car++) {
    		if(carOrient[car]) {
    			if(carFPos[car] > state.getVariablePosition(goalCar) + (carSizes[goalCar]-1)) {
    				int min = state.getVariablePosition(car);
    				int max = min + (carSizes[car]-1);
    				for( int i = min; i <= max; i++) {
    					if(carFPos[goalCar]==i) {
    						blocks++;
    						break;
    					}
    				}
    			}
    		}
    	}
    	return blocks;
    }
    public int getValue(State state) {
    	int blocks=0;
    	if(state.isGoal())
    		return 0;
    	blocks = getCarsBlockingGoal(state);
    	
    	return blocks + 1;
	// your code here

    }

}
