package s03;
import java.util.ArrayList;
import java.util.Random;

public class Heap<E extends Comparable<E>> {
	private final ArrayList<E> buffer;
	// ------------------------------------------------------------
	public Heap() {
		buffer = new ArrayList<E>();
	}
	// ------------------------------------------------------------
	public void add(E e) {
		buffer.add(e);
		percolateUp(buffer.size()-1);
	}
	// ------------------------------------------------------------
	public E removeMin() {
		E m = min();
		int last = buffer.size()-1;
		buffer.set(0, buffer.get(last));
		buffer.remove(last);
		siftDown(0);
		return m;
	}
	// ------------------------------------------------------------
	public E min() {
		return buffer.get(0);
	}
	// ------------------------------------------------------------
	public boolean isEmpty() {
		return buffer.size()==0;
	}
	// ------------------------------------------------------------
	public String toString() {
		String res="Array: ";
		for(int i=0; i<buffer.size(); i++)
			res += buffer.get(i)+" ";
		return res+"\n Tree: \n"+toReadableStr(0,"");
	}

	// ------------------------------------------------------------
	/** Moves down the element at index i, by successive swaps, 
	 *  until it is correctly positioned */
	private void siftDown(int i) {
		while(leftChild(i) <= buffer.size()-1 && rightChild(i) <= buffer.size()-1){
			if(buffer.get(i).compareTo(buffer.get(leftChild(i))) >0 || buffer.get(i).compareTo(buffer.get(rightChild(i))) >0){
				if(buffer.get(leftChild(i)).compareTo(buffer.get(rightChild(i))) < 0){
					swap(i, leftChild(i));
					i=leftChild(i);
				}else{
					swap(i, rightChild(i));
					i=rightChild(i);
				}
			}else{
				return;
			}
		}
		//Si il y a un fils gauche
		if(leftChild(i) <= buffer.size()-1 && buffer.get(i).compareTo(buffer.get(leftChild(i))) >0){
			swap(i,leftChild(i));
			return;
		}
	}
	// ------------------------------------------------------------
	/** Moves up the element at index i, by successive swaps, 
	 *  until it is correctly positioned */
	private void percolateUp(int i) {
		while(buffer.get(i).compareTo(buffer.get(parent(i))) < 0){
			swap(i, parent(i));
			i = parent(i);
		}
	}
	// ------------------------------------------------------------
	private int parent(int i) {
		return (i-1)/2;
	}
	private int leftChild (int i) {
		return 2*i+1;
	}
	private int rightChild(int i) {
		return 2*i+2;  
	}
	// --------------
	private void swap(int i, int j) {
		E aux=buffer.get(i);
		buffer.set(i, buffer.get(j));
		buffer.set(j, aux);
	}
	// ------------------------------------------------------------
	// ------------------------------------------------------------
	// ------------------------------------------------------------
	private String toReadableStr(int r, String path) {
		if (r>=buffer.size()) return path + "-\n";
		String s ="";
		String b = "";
		String e = "" + buffer.get(r);
		String pad = "";
		int i;
		for (i=0; i<e.length(); i++) pad+=" ";
		for (i=0; i<path.length(); i++) {
			//b+=" ";
			char a = path.charAt(i);
			b+=(a=='/' || a=='\\')?""+a:" ";
		}
		s += toReadableStr(rightChild(r),  b +pad+"/");
		s += path + buffer.get(r)+"\n";
		s += toReadableStr(leftChild(r), b +pad+"\\");
		return s;
	}
	// ------------------------------------------------------------
	public static void main(String [] args) {
		int n=10;
		if (args.length>0) n = Integer.parseInt(args[0]);
		Random r = new Random();
		Heap<Integer> h= new Heap<Integer>();
		Integer e;
		for (int i=0; i<n; i++) {
			String log="";
			if (r.nextInt(10)<4 && !h.isEmpty()) { 
				e = h.removeMin();
				log += "\n--- remove "+e+"\n" + h;
			} else {
				e = new Integer(r.nextInt(n));
				h.add(e);
				log += "\n--- add    "+e+"\n" + h;
			}
			if (i<20) System.out.println(log);
		}
	}
	// ------------------------------------------------------------

}