package s09;

import java.util.Random;
// ------------------------------------------------------------ 
public class StringSearching {
	// ------------------------------------------------------------ 
	static /*final*/ int HASHER = 301; // Maybe also try with 7 and 46237
	static /*final*/ int BASE   = 256; // Please also try with 257
	// ---------------------
	static int firstFootprint(String s, int len) {
		int print = 0;
		for(int i = 0; i < len; i++){
			print += (s.charAt(i)*powerModulo(BASE,len-i-1,HASHER));
		}
		return print%HASHER;
	}
	// ---------------------
	// must absolutely be O(1)
	// coef is (BASE  power  P.LENGTH-1)  mod  HASHER
	static int nextFootprint(int previousFootprint, char dropChar, char newChar, int coef) {
		int h = previousFootprint;
		h = (h + HASHER - (dropChar * coef)%HASHER);
		h = (h * BASE);
		h = (h + newChar)%HASHER;
		return h;
		// TODO - A COMPLETER
		// h = previousFootprint
		// h = h - ...               // dropChar        (bien réfléchir !)
		// h = h * ...               // shift
		// h = h + ...               // newChar*/

	}
	private static int powerModulo(int base, int exp, int mod) {
	    if (exp==0) return 1;
	    int tmp = powerModulo((base*base)%mod, exp/2, mod);
	    if (exp%2 != 0)
	      tmp = (tmp * base) % mod;
	    return tmp;
	  }

	// ---------------------
	// Rabin-Karp algorithm
	public static int indexOf_rk(String t, String p) {
		int printT = firstFootprint(t, p.length());
		int printP = firstFootprint(p, p.length());
		int lengthSearch = t.length()-(p.length())+1;
		for(int i = 0; i < lengthSearch; i++){
			if(printT == printP){ //Même hash
				boolean sameText = sameText(t.substring(i, i+p.length()),p);
				if(sameText) //Même chaîne de caractères
					return i;				
			}
			if(i+p.length() >= t.length())
				return -1;
				
			printT = nextFootprint(printT, t.charAt(i), t.charAt(i+p.length()), powerModulo(BASE, p.length()-1,HASHER));
		}
		return -1;
	}
	
	public static boolean sameText(String t, String p){
		return t.equals(p);
	}
}