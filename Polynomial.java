public class Polynomial{

	double [] coefficient = new double[1];

	public Polynomial(){
		coefficient = new double[1];
	
		for(int i = 0; i<coefficient.length; i++){
			coefficient[i] = 0.0;
		}
	
	}
	
	public Polynomial(double [] entries){
	
		coefficient = entries;
	}
	
	public Polynomial add(Polynomial to_add){
				
		int len = Math.max(to_add.coefficient.length, coefficient.length);
		double [] added_coefficients = new double[len];	
		
		for (int i=0; i<to_add.coefficient.length; i++){
			added_coefficients[i] += to_add.coefficient[i]; 
		}
		
		for (int i=0; i<coefficient.length; i++){
			added_coefficients[i] += coefficient[i]; 
		}
		
		return new Polynomial(added_coefficients); 
		
	}

	public double evaluate(double x){
		double result=0.0; 
		
		for(int i=0; i<coefficient.length; i++){
			result += coefficient[i] * Math.pow(x, i);
		}
		return result; 
	}

	public boolean hasRoot(double y){
		if (evaluate(y)==0) return true;
		else return false;
	}
	



}