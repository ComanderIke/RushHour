
public class NodeWithEqualsMethod {

	public Node node;
	
	public NodeWithEqualsMethod(Node n) {
		this.node = n;
	}
	

	@Override
    public boolean equals(Object o) {
    	NodeWithEqualsMethod s;
    	try {
    	    s = (NodeWithEqualsMethod) o;
    	}
    	catch (ClassCastException e) {
    	    return false;
    	}
    	return s.node.getState().equals(this.node.getState());
    }
}
