import java.util.LinkedList;
import java.util.List;

public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
	// For clarity only, this is the default ctor created implicitly.
	public BacktrackingBTree() {
		super();
	}

	public BacktrackingBTree(int order) {
		super(order);
	}

	//You are to implement the function Backtrack.
	
	public void Backtrack() {
		if(size!=0) { // If the tree is not empty
			T insertedValue = (T)backTrackDeque.removeFirst();
			if (size == 1) { // If we want to backtrack the insertion of the root
				size --;
				root = null;
				backTrackDeque.removeFirst(); // To remove the marker "Done"
			}
			else {
				Node<T> toRemoveKey = super.getNode(insertedValue); // From this node we want to delete the inserted key
				toRemoveKey.removeKey(insertedValue);
				while (backTrackDeque.peekFirst() != "Done") { // Continue until we reach the "Done" marker == this backtrack is over
					Node<T> splittedNode = (Node<T>)backTrackDeque.removeFirst();
					Node<T> dad = splittedNode.parent;
					if (backTrackDeque.peekFirst()=="I am root")  {// In case that the splitted node was the root
						root=splittedNode;
						backTrackDeque.removeFirst();
						root.parent=null;
					}
					else {
						int medIndex = (splittedNode.getNumberOfKeys()/2); // Finding the index of the key that was moved up in the split
						T medValue = splittedNode.getKey(medIndex); // Finding the value of the key that was moved up in the split
						int index = dad.indexOf(medValue); // Finding the index of the key that was moved up in the split in the current parent
						dad.removeKey(medValue); // Removing it
						dad.removeChild(index); 
						dad.removeChild(index);
						dad.addChild(splittedNode); // Adding the original node
					}
					
				}
				size--; // Updating size field
				backTrackDeque.removeFirst(); // To remove the marker "Done"		
			}
		}
	}

	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample(){
		List<Integer> ans = new LinkedList<>();
		ans.add(1); ans.add(2); ans.add(3); ans.add(4); ans.add(5); ans.add(6); 
		return ans;
	}
}
