package s10;
public class BST<E extends Comparable<E>> {
  protected BTree<E> tree;
  protected int   crtSize;
  // --------------------------------------------------
  public BST() {
    tree = new BTree<E>();
    crtSize = 0;
  }
  // --------------------------------------------------
  public void      add(E e) {
    BTreeItr<E> ti = locate(e);
    if (!ti.isBottom()) return;
    ti.insert(e);
    crtSize++;
  }
  // --------------------------------------------------
  public void      remove(E e) {
    BTreeItr<E> ti = locate(e);
    if(ti.isBottom()) return;
    crtSize--;
    while(ti.hasRight()) {
      ti.rotateLeft();
      ti = ti.left();
    }
    BTree<E> l = ti.left().cut();
    ti.paste(l);
  }
  // --------------------------------------------------
  public boolean   contains(E e) {
    BTreeItr<E> ti = locate(e);
    return ! ti.isBottom();
  }
  // --------------------------------------------------
  public int       size()    { return crtSize;}
  // --------------------------------------------------
  public boolean   isEmpty() { return size() == 0;}
  // --------------------------------------------------
  public E    minElt() {
    return (tree.root().leftMost().up().consult());
  }
  // --------------------------------------------------
  public E    maxElt() {
    return (tree.root().rightMost().up().consult());
  }
  // --------------------------------------------------
  public String toString() {return ""+tree;}
  // --------------------------------------------------
  // --- Non-public methods
  // --------------------------------------------------
  protected  BTreeItr<E> locate(E e) {
    BTreeItr<E> ti = tree.root();
    while(!ti.isBottom()) {
      E c = ti.consult();
      if (e.compareTo(c)==0) break;
      if (e.compareTo(c)< 0) ti = ti.left();
      else                   ti = ti.right();
    }
    return ti;
  }
}