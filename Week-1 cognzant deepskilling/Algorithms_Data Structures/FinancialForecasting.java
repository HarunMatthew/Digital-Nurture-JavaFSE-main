
class Main {

	public static double futureValue(double currentValue, double growthRate, int years) {

		// Base Case
		if (years == 0) {
			return currentValue;
		}

		// Recursive Case
		return futureValue( currentValue * (1 + growthRate), growthRate, years - 1);
	}

	public static void main(String[] args) {

		double initialAmount = 10000;
		double growthRate = 0.10; // 10%
		int years = 5;

		double result = futureValue( initialAmount, growthRate, years);

		System.out.println("Future Value after " + years + " years = ₹" + result);
	}
}

