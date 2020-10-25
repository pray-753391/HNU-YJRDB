package YJRdataBase;

public abstract class Operator implements java.io.Serializable{
	// _start 成员变量及函数定义
		private String op; // 符号的字符串表达方式
		private int precedence; // 优先级

		/*
		 * 构造函数
		 */

		protected Operator(String op, int precedence) {
			this.op = op;
			this.precedence = precedence;
		}

		public abstract Operand eval(Operand o1, Operand o2);

		public int precedence() {
			return precedence;
		}

		String stringRepresentation() {
			return op;
		}

		// _end
		
		public static Operator get(String op) {
		    if (op.equals("+")) { return add_op; }
		    else if (op.equals("-")) { return sub_op; }
		    else if (op.equals("*")) { return mul_op; }
		    else if (op.equals("/")) { return fdiv_op; }
		    else if (op.equals("%")) { return mod_op;}
		    else if (op.equals("(")) { return lpara_op;}
		    else if (op.equals(")")) { return rpara_op;}
		    else if (op.equals(">")) { return great_op;}
		    else if (op.equals("<")) { return less_op;}
		    else if (op.equals("==")) { return equal_op;}
		    else if (op.equals("and")) { return and_op;}
		    else if (op.equals("or")) { return or_op;}
		    else if (op.equals("not")) { return not_op;}
		    throw new Error("Unrecognised operator type: " + op);
		}
		private final static AddOperator add_op = new AddOperator();
		private final static SubOperator sub_op = new SubOperator();
		private final static MulOperator mul_op = new MulOperator();
		private final static FDivOperator fdiv_op = new FDivOperator();
		private final static ModOperator mod_op = new ModOperator();
		private final static LParaOperator lpara_op = new LParaOperator();
		private final static RParaOperator rpara_op = new RParaOperator();
		private final static GreatOperator great_op = new GreatOperator();
		private final static LessOperator less_op = new LessOperator();
		private final static EqualOperator equal_op = new EqualOperator();
		private final static AndOperator and_op = new AndOperator();
		private final static OrOperator or_op = new OrOperator();
		private final static NotOperator not_op = new NotOperator();
	}
	//_start 各操作符及操作定义
	class AddOperator extends Operator {
		public AddOperator() {
			super("+", 10);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				
				rod=o1.OperatorAdd(o2);
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};

	class SubOperator extends Operator {
		public SubOperator() {
			super("-", 10);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorSub(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};

	class MulOperator extends Operator {
		public MulOperator() {
			super("*", 25);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorMul(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};

	class FDivOperator extends Operator {
		public FDivOperator() {
			super("/", 25);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorFDiv(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};

	class ModOperator extends Operator {
		public ModOperator() {
			super("%", 25);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorMod(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};
	class LParaOperator extends Operator {
		public LParaOperator() {
			super("(", -1);
		}

		public Operand eval(Operand o1, Operand o2) {
			try {
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return o1;
		}
		
		
	};
	class RParaOperator extends Operator {
		public RParaOperator() {
			super(")", 100);//实际没有意义，因为在使用时不会用到优先级
		}

		public Operand eval(Operand o1, Operand o2) {
			try {
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return o1;
		}
	};

	class GreatOperator extends Operator {
		public GreatOperator() {
			super(">", 6);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorGreat(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};


	class LessOperator extends Operator {
		public LessOperator() {
			super("<", 6);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorLess(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};


	class EqualOperator extends Operator {
		public EqualOperator() {
			super("==", 6);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorEqual(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};

	class AndOperator extends Operator {
		public AndOperator() {
			super("and", 4);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorAnd(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};
	class OrOperator extends Operator {
		public OrOperator() {
			super("or", 2);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorOr(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
	};
	class NotOperator extends Operator {
		public NotOperator() {
			super("not", 5);
		}

		public Operand eval(Operand o1, Operand o2) {
			Operand rod=new Operand();
			try {
				rod=o1.OperatorNot(o2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return rod;
		}
}
