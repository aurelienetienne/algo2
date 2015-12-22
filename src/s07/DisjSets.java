package s07;

import java.util.Arrays;
import java.util.Stack;
// ------------------------------------------------------------
public class DisjSets {

	private int set[];
	private boolean oneSet = false;

	public DisjSets(int nbOfElements) {
		this.set = new int[nbOfElements];
		if(nbOfElements == 1)
			oneSet = true;
		for(int i = 0; i < nbOfElements; i++){
			set[i] = -1;
		}
		// TODO - A COMPLETER
	}

	public boolean isInSame(int i, int j) {
		if(root(i) == root(j)){
			return true;
		}
		return false;     // TODO - A COMPLETER
	}

	public void union(int i, int j) {
		if(i == j || isInSame(i, j))
			return;
		int t1 = root(i); //Récupère l'id de la racine de l'ensemble i
		int t2 = root(j);
		if(this.set[t1] <= this.set[t2]){
			this.set[t1] += this.set[t2];
			this.set[t2] = t1;
		}else{
			this.set[t2] += this.set[t1];
			this.set[t1] = t2;
		}
		if(Math.abs(this.set[t1]) == set.length || Math.abs(this.set[t2]) == set.length ){
			this.oneSet = true;
		}
	}

	//retourne l'indice du tableau ou se trouve la racine
	private int root(int elt){
		if(this.set[elt] < 0)
			return elt;
		int r = root(this.set[elt]);
		this.set[elt] = r;
		return r;
	}

	public int nbOfElements() {  // as given in the constructor
		return 0; // TODO - A COMPLETER
	}

	public int minInSame(int i) {
		int min = Integer.MAX_VALUE;
		int racine = root(i);
		for(int j = 0; j < this.set.length; j++){
			if(this.set[i] == this.set[j] || j == racine){
				if(j <= min){
					min = j;
					return min;
				}
			}
		}
		return min;
	}

	/*public int minInSame(int i) {
		int min = Integer.MAX_VALUE;
		for(int j = 0; j < this.set.length; j++){
			if(this.set[i] == this.set[j]){
				if(j <= min){
					min = j;
				}
			}
		}
		//Tester encore la racine
		int racine = root(i);
		if(racine <= min)
			min = racine;
		return min; // TODO - A COMPLETER
	}*/

	public boolean isUnique() {
		if(oneSet)
			return true;
		return false;    // TODO - A COMPLETER
	}

	@Override
	public String toString() {
		/*for(int i = 0; i < this.set.length; i++){
			System.out.print(this.set[i]+",");
		}
		System.out.printf("\n");*/
		//Une arrayList qui stocke toutes mes racines
		
		return null;    // TODO - A COMPLETER
	}

	@Override
	public boolean equals  (Object otherDisjSets) {
		return false;    // TODO - A COMPLETER
	}
}