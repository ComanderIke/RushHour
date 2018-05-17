import java.util.ArrayList;
import java.util.List;

/**
 * This is a template for the class corresponding to your original
 * advanced heuristic.  This class is an implementation of the
 * <tt>Heuristic</tt> interface.  After thinking of an original
 * heuristic, you should implement it here, filling in the constructor
 * and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {

	int numCars;
	boolean [] carOrient;//false = horizontal
	int [] carSizes;
	int [] carFPos;
	int gridSize;
	int goalCar;
	List<Integer> blockingCars;
	
    public AdvancedHeuristic(Puzzle puzzle) {
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
    public int  getCarsBlockingCar(int blockedCar, State state) {
    	int blocks = 0;
    	boolean blockedCarOrient = carOrient[blockedCar];
    	if(blockedCarOrient) {//blockedCar is vertical
    		for(int car = 1; car < numCars; car++) {//start with 1 because 0 is the goalcar
    			if(car==blockedCar)
    				continue;
				if(!carOrient[car]) {
					int min = state.getVariablePosition(car);
					int max = min + (carSizes[car]-1);
					for( int i = min; i <= max; i++) {
						if(carFPos[blockedCar]==i&&!blockingCars.contains(car)) {
							blockingCars.add(car);
							blocks++;
							break;
						}
					}
				}
				else {
					// we assume cars with same orientation dont block each other 
					// unless they have the same FPos! in that case we can't do anything about it
				}
    		}
    	}
    	else {//blockedCar is horizontal
    		for(int car = 1; car < numCars; car++) {
    			if(car==blockedCar)
    				continue;
				if(carOrient[car]) {
					if(carFPos[car] > state.getVariablePosition(blockedCar) + (carSizes[blockedCar]-1)) {
						int min = state.getVariablePosition(car);
						int max = min + (carSizes[car]-1);
						for( int i = min; i <= max; i++) {
							if(carFPos[blockedCar]==i&&!blockingCars.contains(car)) {
								blockingCars.add(car);
								blocks++;
								break;
							}
						}
					}
				}
				else {
					// we assume cars with same orientation dont block each other 
					// unless they have the same FPos! in that case we can't do anything about it
					
				}
    		}
    	}
    	return blocks;
    }

    public int getCarsBlockingGoal(State state) {
    	int blocks=0;
    	for(int car = 1; car < numCars; car++) {
    		if(carOrient[car]) {
    			if(carFPos[car] > state.getVariablePosition(goalCar) + (carSizes[goalCar]-1)) {
    				int min = state.getVariablePosition(car);
    				int max = min + (carSizes[car]-1);
    				for( int i = min; i <= max; i++) {
    					if(carFPos[goalCar]==i&& !blockingCars.contains(car)) {
    						blockingCars.add(car);
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
    	
    	if(state.isGoal())
    		return 0;
    	blockingCars = new ArrayList<Integer>();
    	int blocks=0;
    	blocks = getCarsBlockingGoal(state);
    	List<Integer>blockedCars=new ArrayList<Integer>(blockingCars);
    	for(Integer blockedCar : blockedCars) {
    		blocks += getCarsBlockingCar(blockedCar, state);
    	}

    	return blocks + 1;
    }

}
