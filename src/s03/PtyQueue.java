package s03;

public class PtyQueue<E, P extends Comparable<P>> {
  private final Heap<HeapElt> heap;
  private static int old = 0;
  // ------------------------------------------------------------
  public PtyQueue() {
    heap=new Heap<HeapElt>(); // TODO - A COMPLETER
  }
  // ------------------------------------------------------------
  public boolean isEmpty() {
    return heap.isEmpty();
  }

  /** Adds an element elt with priority pty */ 
  public void enqueue(E elt, P pty) {
	  HeapElt hElt = new HeapElt(elt, pty);
	  heap.add(hElt);
  }
  
  /** Returns the element with highest priority. PRE: !isEmpty() */ 
  public E consult() {
	  if(!isEmpty())
		  return heap.min().e;
	  return null;
    //return null; // TODO - A COMPLETER 
  }

  /** Returns the priority of the element with highest priority. PRE: !isEmpty() */ 
  public P consultPty() {
	  if(!isEmpty())
		  return heap.min().pty;
	  return null;
    //return null; // TODO - A COMPLETER 
  }
  
  /** Removes and returns the element with highest priority. PRE: !isEmpty() */ 
  public E dequeue() {
	  if(!isEmpty()){
		  return heap.removeMin().e;
	  }
    return null; // TODO - A COMPLETER 
  }

  @Override public String toString() {
    return heap.toString(); 
  }
  //=============================================================
  class HeapElt implements Comparable<HeapElt> {
    // TODO A COMPLETER
	  private E e;
	  private P pty;
	  private int oldElt;
	  
	  public HeapElt(E e, P pty){
		  this.e = e;
		  this.pty = pty;
		  this.oldElt = old;
		  old++;
	  }

    @Override public int compareTo(HeapElt arg0) {
    	return this.pty.compareTo(arg0.pty);
    }
  }
}
