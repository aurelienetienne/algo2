package s01;
// ======================================================================
public class Queue {
  static class QueueNode {
    Object e;
    QueueNode prev = null;
    QueueNode(Object elt) {e=elt;}
  }
  //==============================
  QueueNode front = null;
  QueueNode back = null;
  // --------------------------
  public Queue() {};
  public void enqueue (Object elt) {
    QueueNode aux = new QueueNode(elt);
    if (back==null) {
      back = aux; front = aux;
    } else {
      back.prev = aux;
      back = aux;
    } 
  }
  // --------------------------
  public boolean isEmpty() {return back==null;}
  // --------------------------
  public Object consult() {return front.e;}
  // --------------------------
  public Object dequeue() {
    Object e = front.e;
    if (front == back) {
      back = null; front = null;
    } else {
      front = front.prev;
    }
    return e;
  }
}