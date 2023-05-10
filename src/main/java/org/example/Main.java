package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);

        // Step 1: Input costs and interest rate
        System.out.print("Enter unit cost: ");
        double unitCost = scanner.nextDouble();

        System.out.print("Enter ordering cost: ");
        double orderingCost = scanner.nextDouble();

        System.out.print("Enter penalty cost: ");
        double penaltyCost = scanner.nextDouble();

        System.out.print("Enter interest rate: ");
        double interestRate = scanner.nextDouble();


        // Step 2: Input lead time and lead time demand
        System.out.print("Enter lead time in weeks: ");
        double leadTime = scanner.nextDouble();

        System.out.print("Enter lead time demand: ");
        double leadTimeDemand = scanner.nextDouble();

        System.out.print("Enter lead time standard deviation: ");
        double leadTimeStdDev = scanner.nextDouble();


        //Holding cost
        double holdingCost = (unitCost*interestRate)/2 ;

        //Annual Demand
        double annualDemand = leadTimeDemand*(365/leadTime);

        //Optimal Lot size and Reorder Point

        int Q = 0;
        int R = 0;
        int iterations = 0;
        double safetyStock = 0;
        double avgHoldingCost = 0;
        double avgSetupCost = 0;
        double avgPenaltyCost = 0;
        double avgTimeBetweenOrders = 0;
        double proportionNoStockout = 0;
        double proportionUnmetDemand = 0;

        // Step 3: Request z and L(z) values for loss function
        System.out.print("Enter z for loss function: ");
        double z = scanner.nextDouble();

        System.out.print("Enter L(z) for loss function: ");
        double L = scanner.nextDouble();

        // Step 4: Find optimal Q and R values
        boolean converged = false;
        while (!converged) {
            iterations++;

            // Calculate optimal Q and R values
            Q = (int) Math.sqrt((2 * annualDemand * orderingCost) / holdingCost);
            R = (int) (leadTimeDemand * leadTime) + (int) (z * Math.sqrt(leadTimeStdDev * leadTime) * Math.sqrt(orderingCost / holdingCost));

            // Calculate safety stock
            safetyStock = z * Math.sqrt(leadTimeStdDev * leadTime) * Math.sqrt(holdingCost / orderingCost);

            // Calculate average holding, setup and penalty costs
            avgHoldingCost = (holdingCost * Q) / 2;
            avgSetupCost = (orderingCost * annualDemand) / Q;
            avgPenaltyCost = (penaltyCost * annualDemand * proportionUnmetDemand);

            // Calculate average time between orders
            avgTimeBetweenOrders = 365 / (annualDemand / Q);

            // Calculate proportion of order cycles with no stockout
            proportionNoStockout = L * (1 - proportionUnmetDemand);

            // Check convergence criterion
            if (Math.abs((double) (Q - R) / Q) < 0.01) {
                converged = true;
            }
        }

        //Average annual cost

        double avgAnnualCost = (holdingCost*Q)/2 +(orderingCost*annualDemand)/Q + (penaltyCost*(annualDemand - leadTimeDemand)*leadTime);

        System.out.println("Optimal lot size:" + Q);
        System.out.println("Reorder point:" + R);
        System.out.println(" Number of iterations the software run to obtain the optimal lot size and reorder point:" + iterations);
        System.out.println("Safety stock level:" + safetyStock);
        System.out.println(" Average annual holding cost:" + avgHoldingCost);
        System.out.println("Average annual holding cost:" + avgSetupCost);
        System.out.println("Average annual penalty cost:" + avgPenaltyCost);
        System.out.println(" Proportion of order cycles in which no stockout occurs:" + proportionNoStockout);
        System.out.println("Proportion of demands that are not met:" + proportionUnmetDemand);
        System.out.println("Optimal average annual cost:" + avgAnnualCost);





    }}




