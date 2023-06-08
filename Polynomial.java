import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.PrintStream;

public class Polynomial{

	double [] nonZeroCoefficients = new double[1];
	int [] exponents = new int[1]; 

//sets polynomials to zero
	public Polynomial(){
	
		for(int i = 0; i<nonZeroCoefficients.length; i++){
			nonZeroCoefficients[i] = 0.0;
		}
		
		for(int i = 0; i<exponents.length; i++){
			exponents[i] = 0;
		}
	
	}
	
	public Polynomial(double [] entries, int [] givenExponents){

		nonZeroCoefficients = entries;
		exponents = givenExponents;
	}
	
	public Polynomial(File file){
		 try {
            BufferedReader input = new BufferedReader(new FileReader("C:\\Users\\eliza\\b07lab1\\testing.txt"));
            String line = input.readLine();
						
			String [] poly_terms = line.split("(?=[+-])");
			int poly_length = poly_terms.length;

			double [] coefficients = new double[poly_length];
			int [] exponents = new int[poly_length];
			
			int index_of_x = 0;
			
			for(int i=0; i<poly_length; i++){
				index_of_x = poly_terms[i].indexOf('x');
				if (index_of_x == -1) {
					coefficients[i] = Double.parseDouble(poly_terms[i]);
					exponents[i] = 0;
				}
				else{
					coefficients[i] = Double.parseDouble(poly_terms[i].substring(1, index_of_x));
					if(poly_terms[i].charAt(0) == '-') coefficients[i] = -coefficients[i];
					exponents[i] = Integer.parseInt(poly_terms[i].substring(index_of_x+1));
				}
			}
			
			this.nonZeroCoefficients = coefficients;
			this.exponents = exponents;
			
			for(int i=0; i<poly_length; i++){
				System.out.println(this.nonZeroCoefficients[i] + "x^" +this.exponents[i]);
			}

        } catch (IOException e) {
            e.printStackTrace();
        }	
				
	}
	
	public Polynomial add(Polynomial to_add){
				
		int len = Math.max(to_add.nonZeroCoefficients.length, nonZeroCoefficients.length);
		double [] added_coefficients = new double[len];	
		int [] added_exponents = new int[len];
		added_exponents = to_add.exponents;
		
		for (int i=0; i<to_add.nonZeroCoefficients.length; i++){
			added_coefficients[i] += to_add.nonZeroCoefficients[i]; 
		}
		
		for (int i=0; i<nonZeroCoefficients.length; i++){
			added_coefficients[i] += nonZeroCoefficients[i]; 
		}
		
		return new Polynomial(added_coefficients, added_exponents); 
		
	}

	public double evaluate(double x){
		double result=0.0; 
		
		for(int i=0; i<nonZeroCoefficients.length; i++){
			result += nonZeroCoefficients[i] * Math.pow(x, i);
		}
		return result; 
	}

	public boolean hasRoot(double y){
		if (evaluate(y)==0) return true;
		else return false;
	}
	
	public Polynomial multiply(Polynomial to_multiply){
		
		int len = (to_multiply.nonZeroCoefficients.length) * (nonZeroCoefficients.length);
		double [] multiplied_coefficients = new double[len];
		int [] multiplied_exponents = new int[len];
		
		int displacement=0;
		
		for (int i=0; i<to_multiply.nonZeroCoefficients.length; i++){
			for(int j=0; j<nonZeroCoefficients.length; j++){
				multiplied_coefficients[j+displacement] = to_multiply.nonZeroCoefficients[i] * nonZeroCoefficients[j]; 
				multiplied_exponents[j+displacement] = to_multiply.exponents[i] + exponents[j];
			}
			displacement += nonZeroCoefficients.length; 
		}
		
		
		double [] simplified_coeff = new double[len];
		int [] simplified_exp = new int[len];
		int curr_exp = 0;
		
		// Simplify the multiplied arrays
		int [] termIndices = new int[len]; // Store the indices of the terms
		int termCount = 0; // Keep track of the number of terms

		for (int i = 0; i < len; i++) {
			curr_exp = multiplied_exponents[i];
			if (curr_exp != -2) {
				for (int j = i+1; j<len; j++) {
					if (curr_exp == multiplied_exponents[j]) {
						simplified_coeff[i] += multiplied_coefficients[j];
						multiplied_coefficients[j] = 0; // Set coefficient to 0 for redundant exponent
						multiplied_exponents[j] = -1; // Set exponent to -1 for redundant exponent
					}
				}
				simplified_coeff[i] += multiplied_coefficients[i];
				simplified_exp[i] = curr_exp;

				if (simplified_coeff[i] != 0) {
					termIndices[termCount] = i;
					termCount++;
				}
			}
		}

		// Create the final arrays in the correct order
		double[] final_coeff = new double[termCount];
		int[] final_exp = new int[termCount];

		for (int i = 0; i < termCount; i++) {
			int termIndex = termIndices[i];
			final_coeff[i] = simplified_coeff[termIndex];
			final_exp[i] = simplified_exp[termIndex];
		}	
		
		return new Polynomial(final_coeff, final_exp);
		
	}
	
	public void saveToFile(String fileName){
		
		try{
		PrintStream ps = new PrintStream(fileName);
		for (int i=0; i<nonZeroCoefficients.length; i++){
			if(i<(nonZeroCoefficients.length)-1 && nonZeroCoefficients[i+1]>0) ps.print(nonZeroCoefficients[i]+"x"+exponents[i]+"+");
			else ps.print(nonZeroCoefficients[i]+"x"+exponents[i]);
		}
		
		}catch(IOException e) {
            e.printStackTrace();
        }
		
	}
	
}