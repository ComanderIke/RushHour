import java.util.LinkedList;
import java.util.List;

public class MyPriorityQueue<T> {

	

	  private List<KeyValuePair<T,Float>> elements = new LinkedList<KeyValuePair<T, Float>>();

	  public int size() {
	    return elements.size();
	  }

	  public void enqueue(T item, float priority) {
		  elements.add(new KeyValuePair<T, Float>(item, priority));
	  }
	  public boolean contains(T item) {
		 
		  for (KeyValuePair<T, Float> keyValuePair : elements) {
			if(keyValuePair.key.equals(item)) {
			
				return true;
			}
		  }
		  return false;
	  }
	  public void remove(T item) {
		  KeyValuePair<T, Float> tmp=null;
		  
		  for (KeyValuePair<T, Float> keyValuePair : elements) {
				if(keyValuePair.key.equals(item)) {
					tmp = keyValuePair;
					break;
				}
		  }
		  if(tmp!=null)
			  elements.remove(tmp);
	  }
	  // Returns the Location that has the lowest priority
	  public T dequeue() {
	    int bestIndex = 0;

	    for (int i = 0; i < elements.size(); i++) {
	      if (elements.get(i).value < elements.get(bestIndex).value) {
	        bestIndex = i;
	      }
	    }

	    T bestItem = elements.get(bestIndex).key;
	    elements.remove(bestIndex);
	    return bestItem;
	  }
}
