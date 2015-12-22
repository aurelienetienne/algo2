package s08;

// ======================================================================
class BTNode <E>{
  E elt;
  BTNode<E> left, right, parent;
  //-------------------------
  public BTNode(E e, BTNode<E> l, BTNode<E> r, BTNode<E> p) {
    elt = e;left = l; right = r; parent = p;
  }
  public String toString() {
    String ls=""+((  left==null)?"-":left.elt);
    String rs=""+(( right==null)?"-":right.elt);
    String ps=""+((parent==null)?"-":parent.elt);
    return ""+elt+"_"+ls+"_"+rs+"_"+ps;
  }
}
//class BTT<E> extends BTree<Integer> {
//  //BTT t = new BTT<Integer>();{t.root().insert(3);}
//  BTT<Integer> t = new BTT<Integer>();
//}
// ======================================================================
public class BTree<E> {
  BTNode<E> root = null;
  // ------------------------------------------------------------
  public boolean          isEmpty()  { return root == null;                }
  public BTreeItr<E>      root()     { return new BTreeItr<E>(this);       }
  public String           toString() { return toStr(root);                 }
  public String   toReadableString() { return "\n"+toReadableStr(root,""); }

  //------------------------------------------------------------
  //------ Static methods
  //------------------------------------------------------------
  public void collectInorder(BTreeItr<E> t, E [] res) {
    collectInorder(t, res, 0);
  }
  //------------------------------------------------------------
  public void collectPreorder(BTreeItr<E> t, E [] res) {
    collectPreorder(t, res, 0);
  }
  //------------------------------------------------------------
  public void collectPostorder(BTreeItr<E> t, E [] res) {
    collectPostorder(t, res, 0);
  }
  //------------------------------------------------------------
  //------ Non-public methods
  //------------------------------------------------------------
  private String toStr(BTNode<E> n) {
    if (n==null) return "()";
    return "(" + toStr(n.left) +","+n.elt+","+ toStr(n.right) + ")";
  }
  //------------------------------------------------------------
  private String toReadableStr(BTNode<E> n, String path) {
    if (n==null) return path + "-\n";
    String s ="";
    String b = "";
    String e = n.elt.toString();
    String pad = "";
    int i;
    for (i=0; i<e.length(); i++) pad+=" ";
    for (i=0; i<path.length(); i++) {
      //b+=" ";
      char a = path.charAt(i);
      b+=(a=='/' || a=='\\')?""+a:" ";
    }
    s += toReadableStr(n.right,  b +pad+"/");
    s += path + n.elt+"\n";
    s += toReadableStr(n.left, b +pad+"\\");
    return s;
  }
  //------------------------------------------------------------
  private int collectInorder(BTreeItr<E> t, E []  res, int i) {
    if (t.isBottom()) return i;
    int j = collectInorder(t.left(), res, i);
    res[j] = t.consult();
    return collectInorder(t.right(), res, j+1);
  }
  //------------------------------------------------------------
  private int collectPreorder(BTreeItr<E> t, E []  res, int i) {
    if (t.isBottom()) return i;
    res[i] = t.consult();
    int j = collectPreorder(t.left(),  res, i+1);
    return  collectPreorder(t.right(), res, j);
  }
  //------------------------------------------------------------
  private int collectPostorder(BTreeItr<E> t, E []  res, int i) {
    if (t.isBottom()) return i;
    int j = collectPostorder(t.left(),  res, i);
    j     = collectPostorder(t.right(), res, j);
    res[j] = t.consult();
    return j+1;
  }
}