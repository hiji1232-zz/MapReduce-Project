package sparkMapReduce;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * This class is to generate permutation sequence, by using Johnson Trotter algosithm
 */
public class JohnsonTrotter {	
    public static void perm(int N, List<String> list) {  
        // permutation 	
        int[] p = new int[N]; 
        // inverse permutation    
        int[] pi  = new int[N];
        // direction = +1 or -1     
        int[] dir = new int[N];     
        for (int i = 0; i < N; i++) {
            dir[i] = -1;
            p[i]  = i;
            pi[i] = i;
        }        
        System.out.println("Start permutation");
        perm(0, p, pi, dir, list);
    }

    public static void perm(int n, int[] p, int[] pi, int[] dir, List<String> list) { 
        // base case - print out permutation
        if (n >= p.length) {
            for (int i = 0; i < p.length; i++) {            	
                // write to file					
                if (i != p.length - 1) {
                    String str = (p[i] + 1) + ", ";
                    list.add(str);
                } else {
                    String str = (p[i] + 1) + "\n";
                    list.add(str);
                }
            }                
            return;
        }
        perm(n+1, p, pi, dir, list);
        for (int i = 0; i <= n-1; i++) {
            // swap 
            int z = p[pi[n] + dir[n]];
            p[pi[n]] = z;
            p[pi[n] + dir[n]] = n;
            pi[z] = pi[n];
            pi[n] = pi[n] + dir[n];  
            perm(n+1, p, pi, dir, list); 
        }
        dir[n] = - dir[n];
    }
}