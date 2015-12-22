package s05;
import java.util.TreeMap;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class GSTTestJU {
  // ------------------------------------------------------------
  @Test 
  public void testAddRemoveGet() {
    int n=500;
    testAddRemoveGet(n, 3);
  }
  // ------------------------------------------------------------
  @Test 
  public void testRangeSearch7ary() {
    int n=1500;
    testRangeSearch(n, 7);
  }
  @Test 
  public void testAddRemoveGet7ary() {
    int n=500;
    testAddRemoveGet(n, 7);
  }
  // ------------------------------------------------------------
  @Test 
  public void testRangeSearch() {
    int n=1500;
    testRangeSearch(n, 3);
  }
  // ------------------------------------------------------------
  static void rndAddRm(Random r, GeneralSearchTreeMap<Integer,Double> s, 
                       TreeMap<Integer,Double> u, int i) {
    if (r.nextBoolean()) { 
      s.put(new Integer(i), new Double(i));
      u.put(new Integer(i), new Double(i));
    } else {
      s.remove(new Integer(i));
      u.remove(new Integer(i));
    }
  }
  // ------------------------------------------------------------
  static boolean areSetEqual(GeneralSearchTreeMap<Integer,Double>    s, 
                             TreeMap<Integer,Double> u) {
    int lastKey=10;
    if (!u.isEmpty()) lastKey=u.lastKey();
    for (int i=-5; i<lastKey+5; i++) {
      Double du=u.get(i), ds=(Double)(s.get(i));
      if (ds==null && du==null) continue;
      if (ds==null || du==null) {
        System.out.println("conflicting element : " +i);
        System.out.println(" should be "+ (du==null?"absent":"present"));
        return false;
      }
      if(!u.get(i).equals(s.get(i))) {
        System.out.println("conflicting element : " +i);
        System.out.println(u.get(i));
        System.out.println(s.get(i));
        System.out.println(" (bad key-val pair) ");
        return false;
      }
    }
    return true;
  }
  // ------------------------------------------------------------
  // testAddRemoveGet : Simple test method for the Set specification. 
  //           It only verifies that an arbitrary sequence of add/remove
  //           results in a correct set. 
  //     prm : n is the number of add/remove operations (typically 1000).
  //           m defines the use of M-ary trees
  // ------------------------------------------------------------
  public static void testAddRemoveGet(int n, int m) {
    GeneralSearchTreeMap<Integer,Double> s;
    TreeMap             <Integer,Double> u;
    s = new GeneralSearchTreeMap<Integer,Double>(m);
    u = new              TreeMap<Integer,Double>();
    Random r = new Random();
    int a=1;
    for (a=0; a<n; a++) {
      rndAddRm(r, s, u, r.nextInt(n));
      if (a%(1+n/10)==0) System.out.print(".");
      assertTrue(areSetEqual(s,u));
    }
    System.out.println("set size: "+s.size());
    if (u.size()<100) System.out.println(s);
  }
  // ------------------------------------------------------------
  // testAddRemoveGet : Simple test method for the Set specification. 
  //           It only verifies that an arbitrary sequence of add/remove
  //           results in a correct set. 
  //     prm : n is the number of add/remove operations (typically 1000).
  //           m defines the use of M-ary trees
  // ------------------------------------------------------------
  public static void testRangeSearch(int n, int m) {
    GeneralSearchTreeMap<Integer,Double> s;
    TreeMap             <Integer,Double> u;
    s = new GeneralSearchTreeMap<Integer,Double>(m);
    u = new              TreeMap<Integer,Double>();
    Random r = new Random();
    int a=1;
    for (a=0; a<n; a++) {
      rndAddRm(r, s, u, r.nextInt(n));
      if (a%(1+n/10)==0) System.out.print(".");
      int from=r.nextInt(n+n/2)-n/2, to=from+r.nextInt(n/2);
      int sn=s.nbOfKeysInRange(from, to);
      int un=u.subMap(from, to).size();
      assertTrue("nbOfKeysInRange: "+sn+", should be "+un, sn==un);
    }
  }

}