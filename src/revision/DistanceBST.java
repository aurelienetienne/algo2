package revision;

import java.util.ArrayList;

public class DistanceBST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree<Integer> t = new BTree<Integer>();
		BTreeItr<Integer> itr = new BTreeItr<Integer>(t);
		itr.insert(7);
		itr.left().insert(2);
		itr.right().insert(8);
		itr.left().left().insert(1);
		itr.left().right().insert(5);
		itr.right().insert(8);
		itr.left().left().left().insert(0);
		itr.left().right().left().insert(4);
		itr.left().right().right().insert(6);
		itr.right().right().insert(9);
		System.out.println(itr.toString());
		
		System.out.println(Distance(t,4,9));
	}

	public static int Distance(BTree<Integer> t, int a, int b){
		BTreeItr<Integer> itrA = locate(t,a);
		BTreeItr<Integer> itrB = locate(t,b);
		
		//Premier élément monte jusqu'a la racine et ajoute ses parents dans un tableau
		//Refaire avec des ArrayList
		ArrayList<Integer> arrayA = new ArrayList();
		
		//Récupère tous les parents de a jusqu'à la racine
		while(!itrA.isRoot()){
			arrayA.add(itrA.up().consult());
			itrA = itrA.up();
		}
		
		int racineCommune = -1;
		int count = 1;
		while(!itrB.isRoot()){
			int temp = itrB.up().consult();
			if(arrayA.contains(temp)){
				racineCommune = temp;
				break;
			}
			itrB = itrB.up();
			count++;
		}
		System.out.println(arrayA.indexOf(racineCommune)+1);
		System.out.println(count);
		return (arrayA.indexOf(racineCommune)+count+1);
	}
	
	public static BTreeItr<Integer> locate(BTree<Integer> t, int elt){
		BTreeItr<Integer> itr = new BTreeItr<Integer>(t);
		while(!itr.isBottom()){
			if(itr.consult() == elt)
				return itr;
			if(itr.consult() > elt)
				itr = itr.left();
			if(itr.consult() < elt)
				itr = itr.right();	
		}
		return itr;
	}
}
