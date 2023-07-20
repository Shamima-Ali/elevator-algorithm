package elevatorPackage;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class sim {

        public static void main(String[] args) {
                // TODO Auto-generated method stub
                
                Scanner myObj = new Scanner(System.in); 
            System.out.println("Enter duration of simulation in minutes");

            String duration = myObj.nextLine();  // Read user input
            long millis = Integer.parseInt(duration) * 60 * 1000;
                
            System.out.println(" ---STARTING SIMULATION--- ");

            //begin simulation
            controller cA = new controller(millis);
            cA.start(); 
        }
}