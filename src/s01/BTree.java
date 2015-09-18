package s01;
public class BTree {
  //======================================================================
  static class BTNode {
    Object elt;
    BTNode left, right, parent;
    //-------------------------
    public BTNode(Object e, BTNode l, BTNode r, BTNode p) {
      elt = e;left = l; right = r; parent = p;
    }
    public String toString() {
      String ls=""+((  left==null)?"-":left.elt);
      String rs=""+(( right==null)?"-":right.elt);
      String ps=""+((parent==null)?"-":parent.elt);
      return ""+elt+"_"+ls+"_"+rs+"_"+ps;
    }
  }
  //======================================================================

  BTNode root = null;
  // -----------------------------------------------------------
  public boolean  isEmpty() {
    return root == null;
  }
  
  public BTreeItr root() {
    return new BTreeItr(this);
  }
  
  public String   toString() {
    return toStr(root); 
  }
  
  public String   toReadableString() {
    return "\n"+toReadableStr(root,"");
  }

  //------------------------------------------------------------
  //------ Non-public methods
  //------------------------------------------------------------
  private String toStr(BTNode n) {
    if(n == null)
    	return "()";
    return "(" + toStr(n.left) +","+ n.elt +","+ toStr(n.right) +")";
    // format : "()"     si le sous-arbre est vide
    //  sinon   "(" sous_arbre_gauche "," élément "," sous_arbre_droit ")"
  }
  //------------------------------------------------------------
  private String toReadableStr(BTNode n, String path) {
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