package s04;

public class BSTTest {

	public static void main(String [] args){
		//int [] tab ={1,2,3,4,5,6,7,8,9};
		Integer [] tab = new Integer[9];
		for(int i = 0; i < tab.length;i++){
			tab[i] = i+1;
		}

		BST<Integer> t = new BST<Integer>(tab);
		BTree<Integer> tree = t.tree;
		
		BTreeItr<Integer> itr = new BTreeItr<Integer>(tree);
		System.out.println(itr.consult());
		System.out.println(itr.left().consult());
		System.out.println(itr.right().consult());
		
		System.out.println(itr.left().left().consult());
		System.out.println(itr.left().right().consult());
		System.out.println(itr.right().left().consult());
		System.out.println(itr.right().right().consult());
		
	}
	
}
