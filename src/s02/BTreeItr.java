package s02;
import s02.BTree.BTNode;

public class BTreeItr<E> {
	private BTree<E> whole;
	// ------------------------------------------------------------
	private BTNode<E> up   = null; 
	private BTNode<E> down = null; 
	private boolean isLeftArc = false; // not relevant at the root
	// ------------------------------------------------------------
	/** Position on root. */
	public BTreeItr(BTree<E> t) { 
		whole=t;down = whole.root;
	}
	// ------------------------------------------------------------
	// ------ Accessors
	// ------------------------------------------------------------
	/** Terminal position: no node above. */
	public boolean isRoot() {
		return up==null;
	}
	/** Position on a node with a left child. */
	public boolean hasLeft() { 
		return !isBottom() && down.left != null;
	}
	/** Position on a node with a right child. */
	public boolean hasRight() {
		return !isBottom() && down.right != null;
	}
	/** Terminal position: no node below. */
	public boolean isBottom() {
		return down==null; 
	}
	/** Position on a node with no child. */
	public boolean isLeafNode() { 
		return !(isBottom()||hasLeft()||hasRight());
	}
	/** Position on a left child. Not relevant if isRoot(). */
	public boolean isLeftArc() {
		return isLeftArc;
	}
	/** PRE: !isBottom(). Returns the element stored at that position. */
	
	public E consult() {
		assert (!isBottom());
		assert (isConsistent());
		return down.elt;
	}
	// ------------------------------------------------------------
	// ------ Movements
	// ------------------------------------------------------------
	/** The tree containing that position */
	public BTree<E> whole() { 
		return whole;
	}
	/** PRE: !isRoot(). Does not move this itr but creates a new one.*/
	public BTreeItr<E> up() {
		assert(!isRoot());
		BTreeItr<E> r = new BTreeItr<E>(whole); 
		r.up = this.up.parent;
		r.down = this.up;
		if (r.isRoot()){
			r.isLeftArc = false;
		}else if (r.up.left == this.up){
			r.isLeftArc = true;
		}else {
			r.isLeftArc = false;
		}
		return r;
	}

	/** PRE: !isBottom(). Does not move this itr but creates a new one. */
	public BTreeItr<E> left()      {
		assert (!isBottom());
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=this.down; r.down=this.down.left; r.isLeftArc = true;
		return r;
	}
	/** PRE: !isBottom(). Does not move this itr but creates a new one.  */
	public BTreeItr<E> right()     { 
		assert (!isBottom());
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=this.down; r.down=this.down.right; r.isLeftArc = false;
		return r;
	}
	/** Goes down to the left as far as possible, from here. Always returns
	 *  a bottom arc. Does not move this itr but creates a new one.  */
	public BTreeItr<E> leftMost()  { 
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=up; r.down=down; r.isLeftArc = isLeftArc;
		while (!r.isBottom()) {
			r.up=r.down; r.down=r.down.left; r.isLeftArc = true;
		} 
		return r;
	}
	/** Goes down to the right as far as possible, from here. Always returns
	 *  a bottom arc. Does not move this itr but creates a new one.  */
	public BTreeItr<E> rightMost() {
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=up; r.down=down; r.isLeftArc = isLeftArc;
		while (!r.isBottom()) {
			r.up=r.down; r.down=r.down.right; r.isLeftArc = false;
		} 
		return r;    
	}
	//------------------------------------------------------------
	/** Returns a new itr on the same position. */
	public BTreeItr<E> alias() {
		BTreeItr<E> t = new BTreeItr<E>(whole);
		t.whole = whole; t.up=up; t.down=down; t.isLeftArc=isLeftArc;
		return t;
	}
	//------------------------------------------------------------
	/** Each position belongs to exactly one tree. */
	public boolean isInside(BTree<E> t) { 
		return whole==t;
	}
	// ------------------------------------------------------------
	// ------ Modifiers
	// ------------------------------------------------------------
	/** PRE: !isBottom(). Replaces the element stored at that position. */
	public void  update(E elt) {
		assert(!isBottom());
		this.down.elt = elt;
	}
	//------------------------------------------------------------
	/** Replaces the subtree with a single node containing that element. */
	public void insert(E elt) {
		cut();
		this.down = new BTNode<E>(elt, null, null, this.up);
		if(!isRoot()){
			if(isLeftArc){
				this.up.left = this.down;
			}else{
				this.up.right = this.down;
			}  	
		}else{
			whole.root = this.down;
		}
	}
	//------------------------------------------------------------
	/** Replaces the subtree with that whole tree. 
	 * PRE: !this.isInside(t). POST: t is now empty.*/
	public void paste(BTree<E> t) {
		if (isInside(t)) throw new IllegalArgumentException();
		cut();
		if (t.isEmpty()) return;
		BTNode<E> n = t.root; 
		n.parent=up;
		if (isRoot())
			whole.root=n;
		else {
			if (isLeftArc) up.left = n;
			else           up.right= n;
		}
		down = n;  
		t.root = null; 
	}
	//------------------------------------------------------------
	/** Removes the subtree and returns it as a new tree. */
	public BTree<E> cut() {
		BTree<E> t = new BTree<E>();
		t.root = down;
		if (isBottom())
			return t;
		if (isRoot()){
			whole.root = null;
			this.down = null;
			return t;
		}
		if(isLeftArc)
			this.up.left = null;
		else
			this.up.right = null;
		this.down = null;
		return t;
	}
	//------------------------------------------------------------
	public String toString() {
		return whole.toString();
	}
	/** A strange representation where you have to "roll" your head to
	 * see the tree structure. */
	public String toReadableString() {
		return whole.toReadableString();
	}
	//------------------------------------------------------------
	//------ Non-public methods
	//------------------------------------------------------------
	// isConsistent() : tracks tree inconsistency (due to recent 
	//       PRE-conditions violations for cut/paste/insert) :
	//       - local bidirectional chaining
	//       - recursively, upwards, till the root arc
	//       This slows a lot, but may help detect bugs 
	// ------------------------------------------------------------
	private boolean isConsistent() {
		BTNode<E> u=up, d=down;
		if (u!=null && d!=((isLeftArc)?(u.left):(u.right))) return false;
		while (u!=null) {
			if (d!=null && d.parent!=u) return false;
			if (d!=u.left && d!=u.right) return false;
			d=u; u=u.parent;
		}
		return (d==whole.root);
	}

}