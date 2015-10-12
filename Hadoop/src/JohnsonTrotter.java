import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class is to generate permutation sequence, by using Johnson Trotter algosithm
 */ 
public class JohnsonTrotter {	
    public static void perm(int N,OutputStreamWriter writer) {
        int[] p   = new int[N];     // permutation
        int[] pi  = new int[N];     // inverse permutation
        int[] dir = new int[N];     // direction = +1 or -1
        for (int i = 0; i < N; i++) {
            dir[i] = -1;
            p[i]  = i;
            pi[i] = i;
        }
        perm(0, p, pi, dir, writer);
    }

    public static void perm(int n, int[] p, int[] pi, int[] dir, OutputStreamWriter writer) { 	
        // base case - print out permutation
        if (n >= p.length) {
            for (int i = 0; i < p.length; i++) {         	
            	try {
            		// write to file					
					if (i != p.length - 1) {
						String str = (p[i] + 1) + ", ";
						writer.write(str);
					} else {
						String str = (p[i] + 1) + "\n";
						writer.write(str);	
					}										
				} catch (IOException e) {
					e.printStackTrace();
				}
            }              
            return;
        }
        perm(n+1, p, pi, dir, writer);
        for (int i = 0; i <= n-1; i++) {
            // swap 
            int z = p[pi[n] + dir[n]];
            p[pi[n]] = z;
            p[pi[n] + dir[n]] = n;
            pi[z] = pi[n];
            pi[n] = pi[n] + dir[n];  
            perm(n+1, p, pi, dir, writer); 
        }
        dir[n] = - dir[n];
    }
}