package s07;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int size = 4;
		DisjSets set1 = new DisjSets(size);
		set1.union(0, 1);
		set1.union(0, 3);
		set1.toString();
		System.out.println(set1.isUnique());
		System.out.println(set1.isInSame(0, 1));
		System.out.println(set1.isInSame(0, 2));
		System.out.println(set1.isInSame(2, 3));
		System.out.println(set1.minInSame(0));
		
		DisjSets set2 = new DisjSets(size);
		set2.union(0, 1);
		set2.toString();
		System.out.println(set2.isUnique());

		System.out.println("Test de minInSame");
		DisjSets set3 = new DisjSets(50);
		set3.union(0, 1);
		System.out.println("Doit retourner 0: " + set3.minInSame(0));
		System.out.println("Doit retourner 0: " + set3.minInSame(1));
		System.out.println("Doit retourner 2: " + set3.minInSame(10));
		set3.union(43, 44);
		System.out.println("Doit retourner 43: " + set3.minInSame(44));
		set3.union(44, 45);
		set3.union(46, 43);
		set3.union(39, 46);
		System.out.println("Doit retourner 39: " + set3.minInSame(44));
		String s = set3.toString();
		System.out.println(s);
		
		
		
	}

}
