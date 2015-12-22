package s11;
public class IntStack {  // BUGGY !!
  private int [] buf;     
  private int    top;
  // ----------------
  public          IntStack()        { this(10);                         } 
  public          IntStack(int cap) { buf = new int[cap+1]; top = -1;   } 
  public  boolean isEmpty()         { return top == -1;                 }
  public  int     top()             { return buf[top];                  }
  public  int     pop()             { int a=buf[top];  top--; return a; }
  public  void    push(int x)       { checkSize(); top++; buf[top] = x; }
  // ----------------------------------------------------------------------
  private void    checkSize()       {
    if (top < buf.length-1) return; 
    int [] t = new int[2*buf.length];
    for (int i=0; i++<buf.length-1;) t[i] = buf[i];
    buf = t;
  }
}