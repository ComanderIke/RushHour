import java.util.ArrayList;

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
    public int getBlocks(int blockedCar, State state, ArrayList<Integer>blockedCars) {
    	int blocks=0;
    	boolean blockedCarOrient = carOrient[blockedCar];
    	if(blockedCarOrient) {
    		for(int car = 1; car < numCars; car++) {
				if(!carOrient[car]) {
					int min = state.getVariablePosition(car);
					int max = min + (carSizes[car]-1);
					for( int i = min; i <= max; i++) {
						if(carFPos[blockedCar]==i) {
							blockedCars.add(car);
							blocks++;
							i=max+1;
						}
					}
				}
				else {
					blockedCars.add(car);
					blocks++;
					//vertical cars always block each other
				}
    		}
    	}
    	else {
    		for(int car = 1; car < numCars; car++) {
        		
				if(carOrient[car]) {
					//System.out.println("Car: "+car);
					if(carFPos[car] > state.getVariablePosition(blockedCar) + (carSizes[blockedCar]-1)) {
						int min = state.getVariablePosition(car);
						int max = min + (carSizes[car]-1);
						for( int i = min; i <= max; i++) {
							if(carFPos[blockedCar]==i) {
								//blockedCars.add(car);
								blocks++;
								i=max+1;
							}
						}
					}
				}
				else {
					blocks++;
					//horizontal cars always block each other
				}
    		}
    	}
    	return blocks;
    }
    public int getBlocksGoalCar(State state, ArrayList<Integer> blockedCars) {
    	int blocks=0;
    	for(int car = 1; car < numCars; car++) {
    		if(carOrient[car]) {
    			//System.out.println("Car: "+car);
    			if(carFPos[car] > state.getVariablePosition(goalCar) + (carSizes[goalCar]-1)) {
    				int min = state.getVariablePosition(car);
    				int max = min + (carSizes[car]-1);
    				for( int i = min; i <= max; i++) {
    					if(carFPos[goalCar]==i) {
    						blockedCars.add(car);
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
    	return blocks;
    }
    public int getValue(State state) {
    	ArrayList<Integer> blockedCars = new ArrayList<Integer>();
    	int blocks=0;
    	blocks = getBlocksGoalCar( state, blockedCars);
    	ArrayList<Integer> blockedCars2 = new ArrayList<Integer>();
    	for(Integer blockedCar : blockedCars) {
    		blocks+=getBlocks(blockedCar, state, blockedCars2);
    	}
    	blockedCars.clear();
    	for(Integer blockedCar : blockedCars2) {
    		blocks+=getBlocks(blockedCar, state, blockedCars);
    	}
    	blockedCars2.clear();
//    	for(Integer blockedCar : blockedCars) {
//    		blocks+=getBlocks(blockedCar, state, blockedCars2);
//    	}
//    	blockedCars.clear();
//    	for(Integer blockedCar : blockedCars2) {
//    		blocks+=getBlocks(blockedCar, state, blockedCars);
//    	}
//    	blockedCars2.clear();
    	//System.out.println(blocks == 0 ? 0 : blocks + 1);
    	
    	return blocks == 0 ? 0 : blocks + 1;
    }

}
