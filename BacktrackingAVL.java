import java.util.LinkedList;
import java.util.List;

public class BacktrackingAVL extends AVLTree {
	// For clarity only, this is the default ctor created implicitly.
	public BacktrackingAVL() {
		super();
	}

	//You are to implement the function Backtrack.
	public void Backtrack() {
		if (root!=null) {
			while (backTrackDeque.peekFirst()!="Done") {
				if (!(backTrackDeque.peekFirst() instanceof String)) { //if insertion didn't require rotation 
					if(root.left == null && root.right==null) { //if the tree size is 1
						root=null; //delete the root
						backTrackDeque.removeFirst(); //remove from deque the inserted node
					}
					else { //delete the inserted node
						Node insertedNode = (Node) backTrackDeque.removeFirst(); 
						Node parent = insertedNode.parent;
						if(insertedNode.value < parent.value) 
							parent.left = null;
						else
							parent.right = null;
						//update height+size from down , up.
						while (parent != null) {
							parent.updateHeight();
							parent.size = parent.size-1;
							parent=parent.parent;
						}
					}
				}
				else { //insertion did require rotation
					String insertCase =(String) backTrackDeque.removeFirst(); //indicator
					//fixing each case separately
					if (insertCase == "LL") {
						Node y =(Node) backTrackDeque.removeFirst(); //node that was rotated
						deRotatedLeft(y); //to undo the left rotation
						if (y.parent==null) //root case
							root=y;	 
					}
					else if (insertCase == "LR") {
						Node y =(Node) backTrackDeque.removeFirst(); //node that was rotated
						deRotatedLeft(y); //to undo left rotation
						if (root.value == y.value)
							root = y.parent; //update root
						if (y.parent == null) 
							root = y;
						deRotatedRight(y); // to undo right rotation
					}
					else if (insertCase == "RR") {
						Node y =(Node) backTrackDeque.removeFirst(); //node that was rotated 
						deRotatedRight(y); //to undo right rotation
						if (y.parent==null)
							root=y;	 //update root	
					}
					else if (insertCase == "RL") {
						Node y =(Node) backTrackDeque.removeFirst(); //node that was rotated
						deRotatedRight(y); //to undo right rotation
						if (root.value == y.value)
							root = y.parent; //update root
						if (y.parent == null) 
							root = y; //update root
						deRotatedLeft(y); //to undo left rotation
					}
					//delete the inserted node
					Node insertedNode = (Node) backTrackDeque.removeFirst(); 
					Node parent = insertedNode.parent;
					if(insertedNode.value < parent.value) 
						parent.left = null;
					else
						parent.right = null;
					//to update height+size from down , up.
					while (parent != null) {
						parent.updateHeight();
						parent.size = parent.size-1;
						parent=parent.parent;
					}
				}
				//fixing the root if needed
				if (root!=null && root.parent != null)
					root= root.parent;
			}
			//remove the String done from the deque
			backTrackDeque.removeFirst();
		}
	}
	protected void deRotatedLeft(Node y) {
		Node oldDad = y.parent;
		//by performing rotate left , we undo the rotate right 
		Node currDad = this.rotateLeft(y); 
		if (oldDad != null) {
			if (oldDad.value < currDad.value) 
				oldDad.right = currDad;
			else
				oldDad.left = currDad;
		}
	}
	protected void deRotatedRight(Node y) {
		Node oldDad = y.parent;
		//by performing rotate right , we undo the rotate left 
		Node currDad = this.rotateRight(y);
		if (oldDad != null) {
			if (oldDad.value < currDad.value) 
				oldDad.right = currDad;
			else
				oldDad.left = currDad;
		}
	} 
	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> AVLTreeBacktrackingCounterExample() {
		List<Integer> ans = new LinkedList<>();
		ans.add(1); ans.add(2); ans.add(3); ans.add(4); ans.add(5);
		return ans; 
	}

    public int Select(int index) {
    	if (root!=null) {
    		 return SelectRec(index,root); 
    	}
    	else
    		return 0; //empty tree
    }

    private int SelectRec(int index, Node node) {
    	if(index==1) { //looking for the smallest element
    		while (node.left != null)
    			return SelectRec(1,node.left); //smallest element is the most left node
    		return node.value;
    	}
    	else if (node.left == null && node.right==null) //stop condition
    		return node.value;
    	else if (node.left == null)
    		return SelectRec(index-1, node.right);
    	int currSize = node.left.size+1;	
    	if (currSize==index) //stop condtion
    		return node.value; 
    	else if (currSize>index)
    		return SelectRec(index, node.left); //look in left subtree
    	else
    		return SelectRec(index-currSize, node.right); //look in right subtree with index - num of element in subtree left
    }  

    public int Rank(int value) {
    	if(root!=null) 
    		return RankRec(value,root);
    	else
    		return 0; //empty tree
    }
    private int RankRec(int value, Node node) { 
    	if (node == null) //stop condition
			return 0;   
    	else if (node.value==value)
    		if (node.left == null) //stop condition
    			return 0;
    		else
    			return (node.left.size);  
    	else if (node.value > value)
    		if (node.left == null && node.value > value)
				return 0;
    		else
    			return RankRec(value, node.left);
    	else
    		if (node.left == null && node.right == null && node.value < value) //1 for the node
    			return 1;
    		else if (node.left == null && node.right != null && node.value < value) 
    			return RankRec(value,node.right) + 1;
    		if (node.left == null) //no element that is smaller from value in this tree
    			return 0;
    		else
    			return RankRec(value,node.right) + (node.left.size) +1; //all the left tree and search the right tree
	}

}
