package s12;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 
public class Ex7 {
  public static int findMissingInt(String filename) throws NumberFormatException, IOException{
    BufferedReader br = new BufferedReader(new FileReader(filename));
    int i = 0;
    int calc = 0;
    String line = br.readLine();
    while (line != null) {
      int n = Integer.parseInt(line);
      calc+=n;
      i+=i;
      i++;
      line = br.readLine();
    }  
    return i- calc;
  }
   
  public static void main(String[] args) {
    try {
      System.out.println(findMissingInt("bin/resource/data.txt"));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}