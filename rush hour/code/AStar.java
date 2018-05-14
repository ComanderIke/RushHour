import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This is the template for a class that performs A* search on a given
 * rush hour puzzle with a given heuristic.  The main search
 * computation is carried out by the constructor for this class, which
 * must be filled in.  The solution (a path from the initial state to
 * a goal state) is returned as an array of <tt>State</tt>s called
 * <tt>path</tt> (where the first element <tt>path[0]</tt> is the
 * initial state).  If no solution is found, the <tt>path</tt> field
 * should be set to <tt>null</tt>.  You may also wish to return other
 * information by adding additional fields to the class.
 */
public class AStar {

    /** The solution path is stored here */
    public State[] path;
    private ArrayList<Node> closed;
    private MyPriorityQueue<Node> open;
    Map<Node,Float> costSoFar;
    private int width;
    private int height;
    private Puzzle puzzle;
    private Heuristic heuristic;
    private Node startNode;
    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {
    	
    	this.width = 6;
    	this.height = 6;
    	this.puzzle = puzzle;
    	this.heuristic = heuristic;
    	startNode = puzzle.getInitNode();
    	 closed = new ArrayList<Node>();
         open = new MyPriorityQueue<Node>();
         costSoFar = new HashMap<Node,Float>();
    	FindPath();
    }
    private void FindPath() {
    	addToOpen(startNode, 0);
    	int maxDepth = 0;
    	int maxSearchDistance = 100;
    	Node current = null;
    	costSoFar.put(startNode, 0f);
    	while((maxDepth < maxSearchDistance) && (open.size() != 0))
    	{
    		current = getFirstInOpen();
    		if(current.getState().isGoal()) {
    			break;
    		}
    		removeFromOpen(current);
    		addToClosed(current);
    		Node[] nextPossibleMoves= current.expand();
    		for(Node node : nextPossibleMoves) {
//    			int heuristicValueCurrent = heuristic.getValue(current.getState());
//    			int heuristicValueNext = heuristic.getValue(node.getState());
    			//TODO Choose node with the least cost?
    			//PriorityQueue
    			float scoreCurrent = current.getDepth()+1;
    			if(costSoFar.containsKey(node)&&scoreCurrent < costSoFar.get(node)) {//TODO
  
    				if(inOpenList(node)) {
    					removeFromOpen(node);
    				}
    				if(inClosedList(node)) {
    					removeFromClosed(node);
    				}
    			}
    			if(!inOpenList(node) && !inClosedList(node)) {
    				maxDepth = Math.max(maxDepth, node.getDepth());
    				costSoFar.put(node, scoreCurrent);
    				addToOpen(node,scoreCurrent);
    			}
    		}
    	}
    	//Solution found
    	if(current!=null) {
	    	int cnt=0;
	    	path= new State[current.getDepth()];
	    	while(current.getParent()!=null) {
	    		path[cnt] = current.getState();
	    		cnt++;
	    		current = current.getParent();
	    	}
    	}
    	else {
    		path = null;
    	}
    	
    }
    private void addToClosed(Node node)
    {
        closed.add(node);
    }
    private void addToOpen(Node node, float priority)
    {
        open.enqueue(node, priority);
    }
    private boolean inClosedList(Node node)
    {
        return closed.contains(node);
    }
    private boolean inOpenList(Node node)
    {
        return open.contains(node);
    }
    private Node getFirstInOpen()
    {
        return open.dequeue();
    }
    private void removeFromClosed(Node node)
    {
        closed.remove(node);
    }
    private void removeFromOpen(Node node)
    {
        open.remove(node);
    }
    
}
