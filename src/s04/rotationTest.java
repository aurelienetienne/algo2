package s04;
import s04.BTree.BTNode;
public class rotationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] tab = {1,2,3};
		System.out.println(tab.length);
		testTree();

	}

	private static void testTree() {
		// TODO Auto-generated method stub



		BTree<Integer> t = new BTree<Integer>();
		BTNode<Integer> n = new BTNode<Integer>(10, null, null, null);
		t.root = n;

		BTreeItr<Integer> itr = new BTreeItr<Integer>(t);
		itr.right().insert(3);
		itr.left().insert(20);
		itr.left().left().insert(1);
		itr.left().right().insert(2);

		itr.rotateRight();
		itr.rotateLeft();
		System.out.println(t.toReadableString());




	}


}
