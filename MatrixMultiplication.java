import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is an implementation of the divide and conquer algorithm for matrix multiplication.
 * This works when n is a power of 2.
 * 
 * @author Joshua Johnston
 * @date 6/15/2019
 *
 */
public class MatrixMultiplication {
	
	public static void main(String[] args){			
		
		int[][] a = createMatrix(4);
		int[][] b = createMatrix(4);		
		
		int[][] c = matrixMult(a, b);
		
		System.out.println(Arrays.deepToString(a));
		System.out.println(Arrays.deepToString(b));
		System.out.println(Arrays.deepToString(c));
	
		}
	
	/**
	 * The matrixMult method multiplies the matrix a and matrix b using the divide and conquer paradigm.
	 * The size of n has to be a power of two or the result won't be correct.  
	 * @param a : int[][]
	 * @param b : int[][]
	 * @return c : int[][]
	 */
	public static int[][] matrixMult(int[][] a, int[][] b){
		
		int n = a.length;
		
		int[][] c = new int[n][n];		
		
		if(n == 1){   //Base case: 1X1 matrix.
			c[0][0] = (a[0][0] * b[0][0]);
		}
		else{
			int size = n / 2; 
			                                        //list index: 0    1    2    3
			List<int[][]> listA = partition(a, size);  //Creating A11, A12, A21, A22
			List<int[][]> listB = partition(b, size);  //Creating B11, B12, B21, B22
			
		    int[][] c11 = add(matrixMult(listA.get(0), listB.get(0)), matrixMult(listA.get(1), listB.get(2)));
			int[][] c12 = add(matrixMult(listA.get(0), listB.get(1)), matrixMult(listA.get(1), listB.get(3)));
			int[][] c21 = add(matrixMult(listA.get(2), listB.get(0)), matrixMult(listA.get(3), listB.get(2)));
			int[][] c22 = add(matrixMult(listA.get(2), listB.get(1)), matrixMult(listA.get(3), listB.get(3)));
			c = combine(c11, c12, c21, c22);
		}		
		
		return c;		
	}
	
	/**
	 * The partition method splits the 2D-array into four quadrants (sub-matrices): top-left, top-right, bottom-left, and bottom-right.
	 * 
	 * top-left     | top-right
	 * -------------|------------
	 * bottom-left  | bottom-right
	 * 
	 * @param a : int[][]
	 * @param size : int
	 * @return List
	 */
	private static List<int[][]> partition(int[][] a, int size){		
		
	    //The top left row and column both start at 0 and ends at mid of the input a.		 
		int[][] top_left = new int[size][size];
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				
				top_left[i][j] = a[i][j];
			}
		}
		
		//The top right row starts at 0 and ends at mid. The column starts at the mid and ends at a.length.
		int[][] top_right = new int[size][size];
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				
				top_right[i][j] = a[i][j + size];   
			}
		}
		
		//The bottom left row starts at the mid and ends at a.length. The column starts at 0 and ends at mid.
		int[][] bottom_left = new int[size][size];
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				
				bottom_left[i][j] = a[i + size][j];
			}
		}
		
		//The bottom right row and column both start at mid and end at a.length.
		int[][] bottom_right = new int[size][size];
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				
				bottom_right[i][j] = a[i + size][j + size];
			}
		}
		
		List<int[][]> list = new ArrayList<int[][]>();
		list.add(top_left);
		list.add(top_right);
		list.add(bottom_left);
		list.add(bottom_right);
		
		return list;		
	}
	
	/**
	 * The add method adds the corresponding matrix entries.
	 * @param a : int[][]
	 * @param b : int[][]
	 * @return temp : int[][]
	 */
	private static int[][] add(int[][] a, int[][] b){
		
		int[][] temp = new int[a.length][a.length];
		
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a.length; j++){
				
				temp[i][j] = a[i][j] + b[i][j];
			}
		}
		return temp;
	}
	
	/**
	 * The combine method inserts the four quadrants (sub-matrices) back into one matrix. 
	 * @param c11 : int[][]
	 * @param c12 : int[][]
	 * @param c21 : int[][]
	 * @param c22 : int[][]
	 * @return c : int[][]
	 */
	private static int[][] combine(int[][] c11, int[][] c12, int[][] c21, int[][] c22){
		
		int size = c11.length;
		
		int[][] c = new int[size * 2][size * 2];		
		
		//Top left(c11) rows and columns start at 0 and ends at mid in c.
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				
				c[i][j] = c11[i][j];
			}
		}
		
		//Top right(c12) rows start at 0 and ends at mid. The columns start at mid and ends at c.length.
		for(int i = 0; i < size; i++){
			for(int j = size; j < size * 2; j++){
				c[i][j] = c12[i][j - size];
			}
		}
		
		//Bottom left(c21) rows start at mid and end at c.length. The columns start 0 and end at mid.
		for(int i =  size; i < size * 2; i++){
			for(int j = 0; j < size; j++){
				
				c[i][j] = c21[i - size][j];
			}
		}
		
		//Bottom right(c22) rows and columns both start at mid and end at c.length.
		for(int i = size; i < size * 2; i++){
			for(int j = size; j < size * 2; j++){
				c[i][j] = c22[i - size][j - size];
			}
		}
		
		return c;
	}	
	
	
	/**
	 * The createMatrix method makes a new 2D-array and fills it up with random integers.
	 * @param size : int
	 * @return temp : int[][]
	 */
	public static int[][] createMatrix(int size){
		
		int[][] temp = new int[size][size];
		
		int max = 500; 
        int min = -500; 
        int range = max - min + 1; 
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				
				temp[i][j] = (int)(Math.random() * range) + min;
			}
		}
		return temp;
	}
}
