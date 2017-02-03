package homework6;

public class Test {
	public static void main(String[] args){
		F(3,4,2);
		
	}
	
	
	public static int F(int i, int j, int k) {
		 int s, t;
		 if ( i <= 0 || j <= 0 )
		 return 1;
		 else if ( i + j < k )
		 return (i+k);
		 else {
		 s = 0;
		 for (t = 1; t < k; t++) {
			 
		 s = s + F(i-t, k-t, j-1) + 3;
		 System.out.println("s = " + s);
		 }
		 return s;
		 }
		}
}

