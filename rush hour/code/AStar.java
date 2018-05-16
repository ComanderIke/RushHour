import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
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
    private ArrayList<NodeWithEqualsMethod> closed;
    private MyPriorityQueue<NodeWithEqualsMethod> open;
    Map<NodeWithEqualsMethod,Float> costSoFar;
    private Heuristic heuristic;
    private Node startNode;
    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {
    	
    	this.heuristic = heuristic;
    	startNode = puzzle.getInitNode();
    	closed = new ArrayList<NodeWithEqualsMethod>();
        open = new MyPriorityQueue<NodeWithEqualsMethod>();
        costSoFar = new HashMap<NodeWithEqualsMethod,Float>();
    	FindPath();
    }
    private void FindPath() {
    	
//    	int maxDepth = 0;
//    	int maxSearchDistance = 1000;
    	Node current = null;
    	addToOpen(startNode, heuristic.getValue(startNode.getState()));
    	addToCost(startNode, 0f);
    	while(open.size() != 0)//(maxDepth < maxSearchDistance) && 
    	{
    		current = getFirstInOpen();
    		if(current.getState().isGoal()) {
    			break;
    		}
    		removeFromOpen(current);
    		addToClosed(current);
    		Node[] nextPossibleMoves= current.expand();
    		for(Node node : nextPossibleMoves) {
    			int heuristicValueNext = heuristic.getValue(node.getState());
    			float scoreNext = +heuristicValueNext+node.getDepth();
    	
    			if(inCostList(node) && scoreNext < getCost(node)) {
    				if(inOpenList(node)) {
    					removeFromOpen(node);
    				}
    				if(inClosedList(node)) {
    					removeFromClosed(node);
    				}
    			}
    			if(!inOpenList(node) && !inClosedList(node)) {
//    				maxDepth = Math.max(maxDepth, node.getDepth());
    				addToCost(node, scoreNext);
    				addToOpen(node, scoreNext);
    			}
    		}
    	}
    	//Solution found
    	if(current!=null) {
	    	int cnt=0;
	    	path = new State[current.getDepth()+1];
	    	while(current.getParent()!=null) {
	    		path[cnt] = current.getState();
	    		cnt++;
	    		current = current.getParent();
	    	}
	    	path[path.length-1]=current.getState();
	    	ReversePath();
    	}
    	else {
    		path = null;
    	}
    	
    }
    private void ReversePath() {
    	List<State> list = Arrays.asList(path);
    	Collections.reverse(list);
    	path = list.toArray(path);
    }
    private void addToCost(Node node, Float cost)
    {
    	costSoFar.put(new NodeWithEqualsMethod(node), cost);
    }
    private boolean inCostList(Node node)
    {
        return costSoFar.containsKey(new NodeWithEqualsMethod(node));
    }
    private Float getCost(Node node) {
    	return costSoFar.get(new NodeWithEqualsMethod(node));
    }
    
    
    private void addToClosed(Node node)
    {
        closed.add(new NodeWithEqualsMethod(node));
    }
    
    private boolean inClosedList(Node node)
    {
        return closed.contains(new NodeWithEqualsMethod(node));
    }
    
    private void removeFromClosed(Node node)
    {
        closed.remove(new NodeWithEqualsMethod(node));
    }
    
    private void addToOpen(Node node, float priority)
    {
        open.enqueue(new NodeWithEqualsMethod(node), priority);
    }
    private boolean inOpenList(Node node)
    {
        return open.contains(new NodeWithEqualsMethod(node));
    }
    
    private Node getFirstInOpen()
    {
        return open.dequeue().node;
    }
    private void removeFromOpen(Node node)
    {
        open.remove(new NodeWithEqualsMethod(node));
    }
    
}
