package s01;

import s01.BTree.BTNode;

public class TestPerso {
	public static void main(String[] args) {

		BTree t = new BTree();
		BTNode n = new BTNode(null, null, null, null);
		t.root = n;

		BTreeItr itr = new BTreeItr(t);
		//Test insert
		itr.insert(1);
		itr = itr.left();
		itr.insert(3);
		itr = itr.up().right();
		itr.insert(2);
		itr = itr.right();
		itr.insert(4);

		System.out.println(t.toReadableString());

		//Test up
		System.out.println(itr.consult());
		itr = itr.up();
		System.out.println(itr.consult());
		itr = itr.up();
		System.out.println(itr.consult());
		itr = itr.left();
		System.out.println(itr.consult());
		
		
	}

}