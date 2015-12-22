package s12;

public class ex4 {

	public static void main(String[] args) {
		int tab[] = {1,2,3,4};
		//System.out.println(max(tab,0,tab.length-1));
		//System.out.println(min(tab,0,tab.length-1));
		//minMax(tab);
		int result[] = minMax(tab, 0, tab.length-1);
		System.out.println(result[0]);
		System.out.println(result[1]);
	}

	static int max(int[] t, int l, int r){
        if(l==r) return t[l];
        int m=(l+r)/2;
        int a=max(t,l,m);
        int b=max(t,m+1,r);
        return (a>b)?a:b;
    }
	
	static int min(int[] t, int l, int r){
        if(l==r) return t[l];
        int m=(l+r)/2;
        int a=min(t,l,m);
        int b=min(t,m+1,r);
        return (a<b)?a:b;
    }
	static int min = 100;
	static int max = -100;
	static void minMax(int[] t){
		for(int i = 0; i <= t.length-1; i++){
			if(t[i] > max){
				max = t[i];
				continue;
			}else if(t[i] < min){
				min = t[i];
			}		
		}
		
		System.out.println(max);
		System.out.println(min);
	}
	
	static int[] minMax(int[] t, int l, int r){
		if(l==r) return t;
        int m=(l+r)/2;
        int[] a=minMax(t,l,m);
        int[] b=minMax(t,m+1,r);
        return new int[] {(a[0]<b[0])?a[0]:b[0],(a[1]>b[1])?a[1]:b[1]};
	}
	
}
