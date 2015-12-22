package s08;

public class SplayTree<K extends Comparable<K>> {
	BTree<K> tree = new BTree<K>();
	int crtSize = 0;
	// --------------------------------------------------
	public SplayTree() { super(); }
	// --------------------------------------------------
	public void    add     (K e) {
		/*if (contains(e)) return;  // This "splays" the tree!
		crtSize++;
		splayToRoot(locate(e));
		if (tree.root().isBottom()) {
			tree.root().insert(e);
			System.out.println(e.toString());
		}

		else if(tree.root().consult().compareTo(e) > 0){
			BTreeItr<K> itr = new BTreeItr<K>(tree);
			BTree<K> right = itr.right().cut();
			BTree<K> left = itr.cut(); //avec la racine
			itr.insert(e);
			itr.left().paste(left);
			itr.right().paste(right);
		}else{
			BTreeItr<K> itr = new BTreeItr<K>(tree);
			BTree<K> left = itr.left().cut();
			BTree<K> right = itr.cut(); //avec la racine
			itr.insert(e);
			itr.left().paste(left);
			itr.right().paste(right);
		}*/
		
	}
	// --------------------------------------------------
	public void    remove  (K e) {
		if (! contains(e)) return; // This "splays" the tree!
		crtSize--;
		if (tree.root().hasLeft() && tree.root().hasRight()) {
			BTree<K> oldRight = tree.root().right().cut();
			tree=tree.root().left().cut();
			BTreeItr<K> maxInLeft= tree.root().rightMost().up();
			BTreeItr<K> ti=splayToRoot(maxInLeft); // now tree has no right subtree!
			ti.right().paste(oldRight);
		} else {  // the tree has only one child
			if (tree.root().hasLeft()) tree=tree.root().left() .cut();
			else                       tree=tree.root().right().cut();
		}
	}
	// --------------------------------------------------
	public boolean contains(K e) {
		if (isEmpty()) return false;
		BTreeItr<K> ti=locate(e);
		boolean absent=ti.isBottom();
		if (absent) ti=ti.up();
		ti=splayToRoot(ti);
		return !absent;
	}
	// --------------------------------------------------
	protected  BTreeItr<K> locate(K e) {
		BTreeItr<K> ti = tree.root();
		while(!ti.isBottom()) {
			K c = ti.consult();
			if (e.compareTo(c)==0) break;
			if (e.compareTo(c)< 0) ti = ti.left();
			else                   ti = ti.right();
		}
		return ti;
	}
	// --------------------------------------------------
	public int     size()    { return crtSize;}
	// --------------------------------------------------
	public boolean isEmpty() { return size() == 0;}
	// --------------------------------------------------
	public K    minElt() { 
		//remonter l'élément
		BTreeItr<K> itr = new BTreeItr<K>(tree);
		return (K) itr.leftMost(); // TODO - A COMPLETER...
	}
	// --------------------------------------------------
	public K    maxElt() {
		//remonter l'élément
		BTreeItr<K> itr = new BTreeItr<K>(tree);
		return (K) itr.rightMost(); // TODO - A COMPLETER...
	}
	// --------------------------------------------------
	public String toString() {
		return ""+tree.toReadableString()+"SIZE:"+size();
	}
	// --------------------------------------------------
	// --- Non-public methods
	// --------------------------------------------------
	// PRE:     ! ti.isBottom()
	// RETURNS: root position
	// WARNING: ti is no more valid
	private BTreeItr<K> splayToRoot(BTreeItr<K> ti) {
		while(!ti.isRoot()){
			if(ti.up().isRoot()){
				ti = applyZig(ti);
			}
			else if (ti.isLeftArc() == ti.up().isLeftArc()){
				ti = applyZigZig(ti);
			}
			else{
				ti = applyZigZag(ti);
			}
		}
		return ti;
	}
	// --------------------------------------------------  
	// PRE / RETURNS : Zig situation (see schemas)
	// WARNING: ti is no more valid
	private BTreeItr<K> applyZig(BTreeItr<K> ti) {
		boolean leftZig=ti.isLeftArc();
		ti=ti.up();
		if (leftZig) ti.rotateRight();
		else         ti.rotateLeft();
		return ti;
	}
	// --------------------------------------------------
	// PRE / RETURNS : ZigZig situation (see schemas)
	// WARNING: ti is no more valid
	private BTreeItr<K> applyZigZig(BTreeItr<K> ti) {
		boolean leftZigZig = ti.isLeftArc();
		ti=ti.up().up();
		if(leftZigZig){
			ti.rotateRight();
			ti.rotateRight();
		}else{
			ti.rotateLeft();
			ti.rotateLeft();
		}
		return ti;
	}
	// --------------------------------------------------
	// PRE / RETURNS : ZigZag situation (see schemas)
	// WARNING: ti is no more valid
	private BTreeItr<K> applyZigZag(BTreeItr<K> ti) {
		boolean RightZigZag = ti.isLeftArc();
		ti=ti.up();
		if(RightZigZag){
			ti.rotateRight();
			ti = ti.up();
			ti.rotateLeft();
		}else{
			ti.rotateLeft();
			ti = ti.up();
			ti.rotateRight();
		}
		return ti;
	}
	// --------------------------------------------------
}