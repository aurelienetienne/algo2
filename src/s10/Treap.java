package s10;
import java.util.Random;
//--------------------------------------------------
public class Treap<E extends Comparable<E>>  {
	//============================================================
	static class TreapElt<E extends Comparable<E>> implements Comparable<TreapElt<E>> {
		static  Random rnd=new Random(); 
		// -----------------------
		private final E elt;
		private int     pty;
		// -----------------------
		public TreapElt(E e) {
			elt=e; 
			pty=rnd.nextInt();
		}

		public int pty() {
			return pty;
		}

		public E elt() {
			return elt;
		}

		public int compareTo(TreapElt<E> o) {
			return elt.compareTo(o.elt);
		}

		public boolean  equals(Object o) {
			if (! (o instanceof TreapElt<?>)) return false;
			if (elt==null) return false;
			return elt.equals(((TreapElt<?>)o).elt);
		}

		public String toString() {
			return ""+elt+"#"+pty;
		}

		public int hashCode() {
			return elt.hashCode();
		}
	}
	//============================================================
	private BST<TreapElt<E>> bst;
	public int withdrawal = 0;
	public int addition = 0;
	public int rotationWithdrawal = 0;
	public int rotationAddition = 0;
	// --------------------------
	public Treap() {
		this.bst = new BST<TreapElt<E>>();
	}

	public void add(E e) {
		if(contains(e))
			return;
		TreapElt<E> elt = new TreapElt<E>(e);
		BTreeItr<TreapElt<E>> itr = this.bst.locate(elt);
		itr.insert(elt);
		this.percolateUp(itr);
		this.bst.crtSize++;
		this.addition++;
	}

	public void remove(E e) {
		if(!contains(e))
			return;
		TreapElt<E> elt = new TreapElt<E>(e);
		BTreeItr<TreapElt<E>> itr = this.bst.locate(elt);
		this.siftDownAndCut(itr);
		this.bst.crtSize--;
		this.withdrawal++;
	}

	public boolean contains(E e) {
		TreapElt<E> elt = new TreapElt<E>(e);
		return this.bst.contains(elt);
	}

	public int size() {
		return this.bst.crtSize;
	}

	public E minElt() {
		return bst.minElt().elt;
	}

	public E maxElt() {
		return bst.maxElt().elt;
	}

	public String toString() {
		return bst.toString();
	}

	// --------------------------------------------------
	// --- Non-public methods
	// --------------------------------------------------
	private void siftDownAndCut(BTreeItr<TreapElt<E>> ti) {	
		while(!ti.isLeafNode()){
			if(ti.hasRight()){
				ti.rotateLeft();
				ti = ti.left();
			}else if(ti.hasLeft()){
				ti.rotateRight();
				ti = ti.right();
			}else{
				if(ti.left().consult().pty > ti.right().consult().pty){
					ti.rotateLeft();
					ti = ti.left();
				}else{
					ti.rotateRight();
					ti = ti.right();
				}
			}
			this.rotationWithdrawal++;
		}
		ti.cut();
	}

	private void percolateUp(BTreeItr<TreapElt<E>> ti) {
		while((!ti.isRoot()) && isLess(ti, ti.up())) {
			if (ti.isLeftArc()) {ti=ti.up(); ti.rotateRight();}
			else                {ti=ti.up(); ti.rotateLeft(); }
			this.rotationAddition++;
		}
	}

	private boolean isLess(BTreeItr<TreapElt<E>> a, BTreeItr<TreapElt<E>> b) {
		TreapElt<E> ca= a.consult();
		TreapElt<E> cb= b.consult();
		return ca.pty()<cb.pty();
	}
}