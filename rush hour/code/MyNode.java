
public class MyNode {

	public Node node;
	
	public MyNode(Node n) {
		this.node = n;
	}
	

	@Override
    public boolean equals(Object o) {
    	MyNode s;
    	try {
    	    s = (MyNode) o;
    	}
    	catch (ClassCastException e) {
    	    return false;
    	}
    	return s.node.getState().equals(this.node.getState());
    }
}
