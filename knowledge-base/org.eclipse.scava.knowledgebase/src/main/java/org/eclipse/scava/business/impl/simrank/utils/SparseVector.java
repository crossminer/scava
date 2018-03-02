/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl.simrank.utils;

/*************************************************************************
 *  Compilation:  javac SparseVector.java
 *  Execution:    java SparseVector
 *  
 *  A sparse vector, implementing using a symbol table.
 *
 *  [Not clear we need the instance variable N except for error checking.]
 *
 *************************************************************************/

public class SparseVector {
    private final int N;             // length
    private ST<Integer, Float> st;  // the vector, represented by index-value pairs

    // initialize the all 0s vector of length N
    public SparseVector(int N) {
        this.N  = N;
        this.st = new ST<Integer, Float>();
    }

    // put st[i] = value
    public void put(int i, float value) {
        if (i < 0 || i >= N) throw new RuntimeException("Illegal index");
        if (value == 0.0) st.delete(i);
        else              st.put(i, value);
    }

    // return st[i]
    public float get(int i) {
        if (i < 0 || i >= N) throw new RuntimeException("Illegal index");
        if (st.contains(i)) return st.get(i);
        else                return (float) 0.0;
    }

    // return the number of nonzero entries
    public int nnz() {
        return st.size();
    }

    // return the size of the vector
    public int size() {
        return N;
    }

    // return the dot product of this vector a with b
    public float dot(SparseVector b) {
        SparseVector a = this;
        if (a.N != b.N) throw new RuntimeException("Vector lengths disagree");
        float sum = (float) 0.0;

        // iterate over the vector with the fewest nonzeros
        if (a.st.size() <= b.st.size()) {
            for (int i : a.st)
                if (b.st.contains(i)) sum += a.get(i) * b.get(i);
        }
        else  {
            for (int i : b.st)
                if (a.st.contains(i)) sum += a.get(i) * b.get(i);
        }
        return sum;
    }

    // return the 2-norm
    public float norm() {
        SparseVector a = this;
        return (float) Math.sqrt(a.dot(a));
    }

    // return alpha * a
    public SparseVector scale(float alpha) {
        SparseVector a = this;
        SparseVector c = new SparseVector(N);
        for (int i : a.st) c.put(i, alpha * a.get(i));
        return c;
    }
       
    // return the inner product space between two vectors
    
    public float cosineSimilarity(SparseVector b){		
   
    	//int count=0;
    	
    	SparseVector a = this;
        if (a.N != b.N) throw new RuntimeException("Vector lengths disagree");
        double sum = 0.0, val1 = 0.0, val2 = 0.0;
        
        for (int i : a.st) 
        	val1 += a.get(i) * a.get(i);        	
    	for (int j : b.st) 
    		val2 += b.get(j) * b.get(j);
        
        if (a.st.size() <= b.st.size()) {       	       	
        	for (int i : a.st) if (b.st.contains(i)) {
        		sum += a.get(i) * b.get(i);
        		//count+=1;
        	}        	
        }  else  {
            for (int i : b.st) if (a.st.contains(i)) {
            	sum += a.get(i) * b.get(i);
            	//count+=1;            
            }
        }
        //System.out.println("The number of common elements: " + count);
        
        if(sum==0) return 0f;
        else return (float) (sum/Math.sqrt(val1 * val2));      
	}
   
    // return a + b
    public SparseVector plus(SparseVector b) {
        SparseVector a = this;
        if (a.N != b.N) throw new RuntimeException("Vector lengths disagree");
        SparseVector c = new SparseVector(N);
        for (int i : a.st) c.put(i, a.get(i));                // c = a
        for (int i : b.st) c.put(i, b.get(i) + c.get(i));     // c = c + b
        return c;
    }

    // return a string representation
    public String toString() {
        String s = "";
        for (int i : st) {
            s += "(" + i + ", " + st.get(i) + ") ";
        }
        return s;
    }


    // test client
    
    public void Test() {
        SparseVector a = new SparseVector(10);
        SparseVector b = new SparseVector(10);
        a.put(3, 0.50f);
        a.put(9, 0.75f);
        a.put(6, 0.11f);
        a.put(6, 0.00f);
        b.put(3, 0.60f);
        b.put(4, 0.90f);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a dot b = " + a.dot(b));
        System.out.println("a + b   = " + a.plus(b));
    }

}
