package s12;

public class SinglyList {
	private int elt;
	private SinglyList next;
	
	public SinglyList(int elt, SinglyList next){
		this.elt = elt;
		this.next = next;
	}

	public static boolean isCyclic(SinglyList first){
		if(first == null)
			return false;
		SinglyList oneHop = first;
		SinglyList twoHop = first.next;
		
		while(oneHop != twoHop){
			if(twoHop == null)
				return false;
			twoHop = twoHop.next;
			
			if(twoHop == oneHop){
				return true;
			}
			
			if(twoHop == null)
				return false;
			twoHop = twoHop.next;
			oneHop = oneHop.next;
		}
		return true;
	}
	public static void main(String[] args) {
		//Test 
		SinglyList n5 = new SinglyList(5, null);
		SinglyList n4 = new SinglyList(4, n5);
		SinglyList n3 = new SinglyList(3, n4);
		SinglyList n2 = new SinglyList(2, n3);
		SinglyList n1 = new SinglyList(1, n2);
		boolean cycle = isCyclic(n1);
		System.out.println(cycle);
		
		SinglyList nc5 = new SinglyList(5, null);
		SinglyList nc4 = new SinglyList(4, nc5);
		SinglyList nc3 = new SinglyList(3, nc4);
		nc5.next = nc3; //Est une liste cyclique
		SinglyList nc2 = new SinglyList(2, nc3);
		SinglyList nc1 = new SinglyList(1, nc2);
		boolean cCycle = isCyclic(nc1);
		System.out.println(cCycle);
		
		SinglyList t7 = new SinglyList(7, null);
		SinglyList t6 = new SinglyList(6, t7);
		SinglyList t5 = new SinglyList(5, t6);
		SinglyList t4 = new SinglyList(4, t5);
		SinglyList t3 = new SinglyList(3, t4);
		SinglyList t2 = new SinglyList(2, t3);
		SinglyList t1 = new SinglyList(1, t2);
		t7.next = t3; //Est une liste cyclique
		boolean tCycle = isCyclic(t1);
		System.out.println(tCycle);
	}
}
