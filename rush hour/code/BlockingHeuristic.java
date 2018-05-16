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
	
    public BlockingHeuristic(Puzzle puzzle) {
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
    public int getValue(State state) {
    	int blocks=0;
    	for(int car = 1; car < numCars; car++) {
    		if(carOrient[car]) {
    			//System.out.println("Car: "+car);
    			if(carFPos[car] > state.getVariablePosition(goalCar) + (carSizes[goalCar]-1)) {
    				int min = state.getVariablePosition(car);
    				int max = min + (carSizes[car]-1);
    				for( int i = min; i <= max; i++) {
    					if(carFPos[goalCar]==i) {
    						blocks++;
    						i=max+1;
    					}
    				}
    			}
    		}
    		else {
    			//horizontals cant block otherwise impossible puzzle 
    		}
    	}
    	//System.out.println(blocks == 0 ? 0 : blocks + 1);
    	
    	return blocks == 0 ? 0 : blocks + 1;
	// your code here

    }

}
