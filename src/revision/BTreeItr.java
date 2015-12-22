package revision;
import revision.BTree.BTNode;
public class BTreeItr<E> {
	private BTree<E> whole;
	// ------------------------------------------------------------
	private BTNode<E> up   = null;  
	private BTNode<E> down = null; 
	private boolean isLeftArc = false; // not relevant at the root
	// ------------------------------------------------------------
	public BTreeItr(BTree<E> t) { whole=t;down = whole.root;}
	// ------------------------------------------------------------
	// ------ Accessors
	// ------------------------------------------------------------
	public boolean isRoot()     { return up==null; }
	public boolean hasLeft()    { return down!=null && down.left !=null;}
	public boolean hasRight()   { return down!=null && down.right!=null;}
	public boolean isBottom()   { return down==null; }
	public boolean isLeafNode() { return !(isBottom()||hasLeft()||hasRight());}
	public boolean isLeftArc()  { return isLeftArc;}
	// ------------------------------------------------------------
	// ------ Movements
	// ------------------------------------------------------------
	public BTree<E>    whole()     { return whole;}
	public BTreeItr<E> up() { 
		assert (!isRoot());
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.down=up; r.up=up.parent; r.isLeftArc = (r.up!=null && r.up.left==r.down);
		return r;
	}
	public BTreeItr<E> left()      {
		assert (!isBottom());
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=down; r.down=down.left; r.isLeftArc = true;
		return r;
	}
	public BTreeItr<E> right()     { 
		assert (!isBottom());
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=down; r.down=down.right; r.isLeftArc = false;
		return r;
	}
	public BTreeItr<E> leftMost()  { 
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=up; r.down=down; r.isLeftArc = isLeftArc;
		while (!r.isBottom()) {
			r.up=r.down; r.down=r.down.left; r.isLeftArc = true;
		} 
		return r;
	}
	public BTreeItr<E> rightMost() {
		BTreeItr<E> r = new BTreeItr<E>(whole);
		r.up=up; r.down=down; r.isLeftArc = isLeftArc;
		while (!r.isBottom()) {
			r.up=r.down; r.down=r.down.right; r.isLeftArc = false;
		} 
		return r;    
	}
	//------------------------------------------------------------
	public BTreeItr<E> alias() {
		BTreeItr<E> t = new BTreeItr<E>(whole);
		t.whole = whole; t.up=up; t.down=down; t.isLeftArc=isLeftArc;
		return t;
	}
	//------------------------------------------------------------
	public boolean isInside(BTree<E> t) { 
		return whole==t;
	}
	// ------------------------------------------------------------
	// ------ Modifiers
	// ------------------------------------------------------------
	public E consult()          {
		assert (!isBottom());  assert (isConsistent());
		return down.elt;
	}
	//------------------------------------------------------------
	public void  update(E elt) {
		assert (!isBottom());  assert (isConsistent());
		down.elt = elt; 
	}
	//------------------------------------------------------------
	public void insert(E elt) {
		cut();
		BTNode<E> n = new BTNode<E>(elt, null, null, up);
		if (isRoot())
			whole.root=n;
		else {
			if (isLeftArc) up.left = n;
			else           up.right= n;
		}
		down = n;
	}
	//------------------------------------------------------------
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
	public BTree<E> cut() {
		assert (isConsistent());
		BTree<E> t = new BTree<E>();
		if (isBottom()) return t;
		t.root = down; t.root.parent = null;
		if (isRoot())
			whole.root=null;
		else {
			if (isLeftArc) up.left = null;
			else           up.right= null;
		}
		down = null;
		return t;
	}
	//------------------------------------------------------------
	//------ "rotation" methods
	//------------------------------------------------------------
	public void rotateLeft() {
		assert(hasRight());
		BTree<E> rightLeft = this.right().left().cut();
		BTree<E> right = this.right().cut();
		BTree<E> child = this.cut();
		this.paste(right);
		this.left().paste(child);
		this.left().right().paste(rightLeft);
	}
	//------------------------------------------------------------
	public void rotateRight() {
		assert(hasLeft());
		BTree<E> leftRight = this.left().right().cut();
		BTree<E> left = this.left().cut();
		BTree<E> child = this.cut();
		this.paste(left);
		this.right().paste(child);
		this.right().left().paste(leftRight);
	}
	//------------------------------------------------------------
	public String toString()         {  return whole.toString();         }
	public String toReadableString() {  return whole.toReadableString(); }
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