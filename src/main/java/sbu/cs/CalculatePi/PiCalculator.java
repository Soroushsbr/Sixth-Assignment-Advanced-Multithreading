package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
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
        MathContext mc = new MathContext(1000);
        int n;
        public Calculator(int n) {
            this.n = n;
        }
        @Override
        public void run() {
            //previous code I used:
//            BigDecimal sign = new BigDecimal(this.sign);
//            BigDecimal numerator = new BigDecimal(4);
//            numerator = numerator.multiply(sign , mc);
//            BigDecimal denominator = new BigDecimal(n * (n + 1) * (n + 2));
//
//            BigDecimal result = numerator.divide(denominator , mc);
//            addToSum(result);
            BigDecimal denominator1 = new BigDecimal(8*n + 1);
            BigDecimal denominator2 = new BigDecimal(8*n + 4);
            BigDecimal denominator3 = new BigDecimal(8*n + 5);
            BigDecimal denominator4 = new BigDecimal(8*n + 6);

            BigDecimal numerator1 = new BigDecimal(4);
            numerator1 = numerator1.divide(denominator1 , mc);
            BigDecimal numerator2 = new BigDecimal(-2);
            numerator2 = numerator2.divide(denominator2 , mc);
            BigDecimal numerator3 = new BigDecimal(-1);
            numerator3 = numerator3.divide(denominator3 , mc);
            BigDecimal numerator4 = new BigDecimal(-1);
            numerator4 = numerator4.divide(denominator4 , mc);

            BigDecimal result = numerator1.add(numerator2);
            result = result.add(numerator3);
            result = result.add(numerator4);

            BigDecimal denominator5 = new BigDecimal(16).pow(n);
            result = result.divide(denominator5);
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
        for (int n = 0 ; n < 1000 ; n ++){
            Calculator task = new Calculator(n);
            threadPool.execute(task);
        }
        threadPool.shutdown();
        try{
            threadPool.awaitTermination(10000 , TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        sum = sum.setScale(floatingPoint , RoundingMode.DOWN);
        return sum.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Floating Point: ");
        int floatingPoint =  scanner.nextInt();
        PiCalculator piCalculator = new PiCalculator();
        System.out.println("π: " + piCalculator.calculate(floatingPoint));
    }
}
