package s02;

public class IntQueueChained {
	//======================================================================
	/* TODO: adapt using pseudo-pointers instead of queue node objects
	 * "Memory management" code:
	 * - define "memory" arrays, the NIL constant, and firstFreeCell
	 * - define allocate/deallocate, with automatic array expansion
	 * "User" code:
	 * - modify enqueue/dequeue/..., keeping the same logic/algorithm
	 * - test
	 */
	//======================================================================

	private final int NIL = -1;
	private static int sizeTab = 100;
	private static char[] elts = new char[sizeTab];
	private static int[] nexts = new int[sizeTab];
	private int firstFreeCell = 0;
	private static int initTab = 0;
	private int front;
	private int back;

	public int allocate(){
		if(firstFreeCell == NIL){
			int newSize = sizeTab *2;
			char[] tmpElt = new char[newSize];
			int[] tmpNexts = new int[newSize];
			for(int i = 0; i < sizeTab; i++){
				tmpNexts[i] = nexts[i];
				tmpElt[i] = elts[i];
				if(tmpNexts[i] == NIL)
					tmpNexts[i] = sizeTab;
			}
			for(int i = sizeTab; i < newSize -1; i++){
				tmpNexts[i] = i+1;
			}
			tmpNexts[newSize-1] =NIL;
			firstFreeCell = sizeTab;
			elts = tmpElt;
			nexts = tmpNexts;
			sizeTab = newSize;
		}
		int tmp = firstFreeCell;
		firstFreeCell = nexts[tmp];
		nexts[tmp] =NIL;
		return tmp;
	}

	public void deallocate(int i){
		nexts[i] = firstFreeCell;
		firstFreeCell = i;
	}
	// ------------------------------
	public IntQueueChained() {
		this.front = -1;
		this.back = -1;
		if(initTab == 0){
			initTab = 1;
			for(int i=0; i< sizeTab-1; i++){
				nexts[i] = i+1;
			}
			nexts[sizeTab-1] = NIL;
		}
	}

	public void enqueue (int elt) {
		int n = allocate();
		if(isEmpty()){
			front = n;
		}else{
			nexts[back] = n;
		}
		nexts[n] = NIL;
		elts[n] = (char)elt;
		back = n;
	}
	// --------------------------
	public boolean isEmpty() {
		return front==NIL;
	}
	// --------------------------
	public int consult() {
		return elts[front];
	}
	// --------------------------
	public int dequeue() {
		int tmpFront = front;
		int valReturn = elts[front];
		front = nexts[front];
		deallocate(tmpFront);
		return valReturn;
	}
}
