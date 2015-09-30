package s03;

public class HeapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Heap<Integer> h = new Heap<Integer>();
		h.add(2);
		h.add(3);
		h.add(5);
		
		h.add(6);
		h.add(8);
		h.add(9);
		h.add(11);
		h.add(1);
		
		
		h.removeMin();
		System.out.println(h.toString());
		System.out.println(h.toString());
	
		
		

	}

}
