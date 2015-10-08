package s04;
// =======================
public class BST<E extends Comparable<E>> {
	protected BTree<E> tree;
	protected int   crtSize;
	// --------------------------------------------------
	public BST() {
		tree = new BTree<E>();
		crtSize = 0;
	}
	// --------------------------------------------------
	public BST(E [] tab) {  // PRE sorted, no duplicate
		tree = optimalBST(tab, 0, tab.length);
		crtSize = tab.length;

	}
	// --------------------------------------------------
	/** returns where e is, or where it should be inserted as a leaf */
	protected  BTreeItr<E> locate(E e) {
		BTreeItr<E> itr = new BTreeItr<E>(tree);
		while(!itr.isBottom()){
			if(e.compareTo(itr.consult()) == 0)
				return itr;
			if(e.compareTo(itr.consult()) < 0){
				itr = itr.left();
			}else{
				itr = itr.right();
			}
		}
		return itr; 

	}
	// --------------------------------------------------
	public void      add(E e) {
		BTreeItr<E> itr = locate(e);

		if(itr.isBottom()){
			itr.insert(e);
			crtSize++;
		}
	}
	// --------------------------------------------------
	public void      remove(E e) {
		BTreeItr<E> itr = locate(e);
		if(itr.isBottom()) 
			return;
		while(itr.hasRight()){
			itr.rotateLeft();
			itr = itr.left();
		}
		BTree<E> temp = itr.left().cut();
		itr.cut();
		itr.paste(temp);
		crtSize--;
	}
	// --------------------------------------------------
	public boolean   contains(E e) {
		BTreeItr<E> ti = locate(e);
		return ! ti.isBottom();
	}
	// --------------------------------------------------
	public int       size()    {
		return crtSize;
	}
	// --------------------------------------------------
	public boolean   isEmpty() {
		return size() == 0;
	}
	// --------------------------------------------------
	public E    minElt() {
		BTreeItr<E> itr = new BTreeItr<E>(tree);
		return itr.leftMost().consult();
	}
	// --------------------------------------------------
	public E    maxElt() {
		BTreeItr<E> itr = new BTreeItr<E>(tree);
		return itr.rightMost().consult();
	}
	// --------------------------------------------------
	public String toString() {
		return ""+tree;
	}
	// --------------------------------------------------
	// --- Non-public methods
	// --------------------------------------------------
	private BTree<E> optimalBST(E [] sorted, int left, int right) {
		BTree<E> r = new BTree<E>();
		BTreeItr<E> ri = r.root();
		optimalBST(sorted, left, right,ri);
		return r;
	}
	private void optimalBST(E [] sorted, int left, int right, BTreeItr<E> itr) {
		if (left >= right) return;
		itr.insert(sorted[(left + right)/2]);
		crtSize++;
		optimalBST(sorted, left, (left+right)/2,itr.left()); //left;
		optimalBST(sorted, (left+right)/2+1,right,itr.right()); //right
	}
}