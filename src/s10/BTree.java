package s10;
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
// ======================================================================
public class BTree<E> {
  BTNode<E> root = null;
  // ------------------------------------------------------------
  public boolean          isEmpty()  { return root == null;                }
  public BTreeItr<E>      root()     { return new BTreeItr<E>(this);       }
  public String           toString() { return toStr(root);                 }
  public String   toReadableString() { return "\n"+toReadableStr(root,""); }

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
}