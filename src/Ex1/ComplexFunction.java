package Ex1;

public class ComplexFunction implements complex_function {
	function left;
	function right;
	Operation op;


	public ComplexFunction(String s, function left, function right) {
		this.op = StringToOperation(s);
		this.left = left;
		this.right = right; 
		if (this.left == null) {
			this.right = left;
			this.right = null;
		}
		if (this.right == null) {
			this.op = Operation.None;
		}
	}

	
	public ComplexFunction(String s) {
		String operation = "";
		int counter = 0;
		int i = 0;
		for (; i < s.length(); i++) {
			if (s.charAt(i) != '(') {
				operation = operation + s.charAt(i);
			}
			else {
				counter++;
				i++;
				break;
			}
		}
		String func1 = "";
		for (; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				counter++;
			}
			if (s.charAt(i) == ')') {
				counter--;
			}
			if(s.charAt(i) == ',' && counter == 1) {
				i++;
				break;
			}
			func1 = func1 + s.charAt(i);
		}
		String func2 = "";
		for (; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				counter++;
			}
			if (s.charAt(i) == ')') {
				counter--;
			}
			if(s.charAt(i) == ')' && counter == 0) {
				break;
			}
			func2 = func2 + s.charAt(i);
		}
		if(!(func1.charAt(0) == 'P' || func1.charAt(0) == 'p'|| func1.charAt(0) == 'T' || func1.charAt(0) == 't'|| func1.charAt(0) == 'M' || func1.charAt(0) == 'm'|| func1.charAt(0) == 'D' || func1.charAt(0) == 'd'|| func1.charAt(0) == 'C' || func1.charAt(0) == 'c' || func1.charAt(0) == 'A' || func2.charAt(0) == 'a')) {
			this.left = new Polynom(func1);
		}
		else {
			this.left = new ComplexFunction(func1);
		}
		if(!(func2.charAt(0) == 'P' || func2.charAt(0) == 'p'|| func2.charAt(0) == 'T' || func2.charAt(0) == 't'|| func2.charAt(0) == 'M' || func2.charAt(0) == 'm'|| func2.charAt(0) == 'D' || func2.charAt(0) == 'd'|| func2.charAt(0) == 'C' || func2.charAt(0) == 'c'|| func1.charAt(0) == 'A' || func2.charAt(0) == 'a')) {
			this.right = new Polynom(func2);
		}
		else {
			this.right = new ComplexFunction(func2);
		}
		this.op = StringToOperation(operation);
				
	}
	
	
	public static Operation StringToOperation(String s) {
		if (s.equals("Plus") || s.equals("plus")) {
			return  Operation.Plus;
		}
		else if (s.equals("Times") || s.equals("mul")) {
			return Operation.Times;
		}
		else if (s.equals("div") || s.equals("Divide")) {
			return Operation.Divid;
		}
		else if (s.equals("Max") || s.equals("max")) {
			return Operation.Max;
		}
		else if (s.equals("min") || s.equals("Min")) {
			return Operation.Min;
		}
		else if (s.equals("comp") || s.equals("Comp")) {
			return Operation.Comp;
		}
		return Operation.None;
	}
	
	
	@Override
	public double f(double x) {
		double l = left.f(x);
		double r = right.f(x);
		if(op == op.Plus) {
			return l+r;
		}
		if(op == op.Times) {
			return l*r;
		}
		if(op == op.Divid) {
			return l/r;
		}
		if(op == op.Max) {
			return Math.max(l, r);
		}
		if(op == op.Min) {
			return Math.min(l, r);
		}
		if(op == op.Comp) {
			return left.f(right.f(x));
		}
		return 0;
	}
	
	
	@Override
	public function initFromString(String s) {
		ComplexFunction ans = new ComplexFunction(s);
		return ans;
	}
	
	
	public String toString() {
		String ans = this.left.toString() + this.op.toString() + this.right.toString();
		return ans;
	}
	
	
	@Override
	public function copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void plus(function f1) {
		function temp = this.copy();
		this.left = temp;
		this.op = Operation.Plus;
		this.right  = f1;
	}
	
	
	@Override
	public void mul(function f1) {
		function temp = this.copy();
		this.left = temp;
		this.op = Operation.Times;
		this.right  = f1;
	}
	
	
	@Override
	public void div(function f1) {
		function temp = this.copy();
		this.left = temp;
		this.op = Operation.Divid;
		this.right  = f1;
	}
	
	
	@Override
	public void max(function f1) {
		function temp = this.copy();
		this.left = temp;
		this.op = Operation.Max;
		this.right  = f1;
	}


	@Override
	public void min(function f1) {
		function temp = this.copy();
		this.left = temp;
		this.op = Operation.Min;
		this.right  = f1;
	}


	@Override
	public void comp(function f1) {
		function temp = this.copy();
		this.left = temp;
		this.op = Operation.Comp;
		this.right  = f1;
	}


	@Override
	public function left() {
		return this.left;
	}


	@Override
	public function right() {
		return this.right;
	}


	@Override
	public Operation getOp() {
		return op;
	}
}