package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PiCalculator {

//    /**
//     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
//     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
//     Experiment with different algorithms to find accurate results.
//
//     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
//     * Create as many classes and threads as you need.
//     * Your code must pass all of the test cases provided in the test folder.
//
//     * @param floatingPoint the exact number of digits after the floating point
//     * @return pi in string format (the string representation of the BigDecimal object)
//     */
    public static class Calculator implements Runnable{
        MathContext mc = new MathContext(1000); //todo: what
        int n;
        int sign;
        public Calculator(int n , int sign) {
            this.n = n;
            this.sign = sign;
        }
        // + (-) 4 / (n)(n + 1)(n + 2)
        @Override
        public void run() {
            BigDecimal sign = new BigDecimal(this.sign);
            BigDecimal numerator = new BigDecimal(4);
            numerator = numerator.multiply(sign , mc);
            BigDecimal denominator = new BigDecimal(n * (n + 1) * (n + 2));

            BigDecimal result = numerator.divide(denominator , mc);
            addToSum(result);
        }
    }
    public static BigDecimal sum;

    public static synchronized void addToSum(BigDecimal value){
        sum = sum.add(value);
    }
    public String calculate(int floatingPoint)
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        sum = new BigDecimal(0);
        int sign = 1;
        for (int n = 2 ; n < 200 ; n+= 2){
            Calculator task = new Calculator(n ,sign);
            threadPool.execute(task);
            sign *= -1;
        }
        threadPool.shutdown();
        try{
            threadPool.awaitTermination(10000 , TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        sum = sum.setScale(floatingPoint , RoundingMode.DOWN);
        BigDecimal three = new BigDecimal(3);
        sum = sum.add(three);
        return sum.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Floating Point: ");
        int floatingPoint =  scanner.nextInt();
        PiCalculator piCalculator = new PiCalculator();
        System.out.println(piCalculator.calculate(floatingPoint));
    }
}
