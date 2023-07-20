package elevatorPackage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class controller extends Thread{ 
        int request[] = null;
        
        int Min = 1;
        int Max = 4;
        
        long millis;

        @Override
        public void run() {
                long start = System.currentTimeMillis();
            long end = 0;
            
                ArrayList<Integer> riderInitialFloors = new ArrayList<Integer>();
                ArrayList<Integer> riderDirectionSelected = new ArrayList<Integer>();
                
                riderController rc = new riderController(riderInitialFloors, riderDirectionSelected);
                Thread thread = new Thread(rc);
                thread.start();
                
                elevator el = new elevator(2, riderInitialFloors, riderDirectionSelected, 1);
                Thread threadEl = new Thread(el);
                threadEl.start();
                
                elevator ela = new elevator(2, riderInitialFloors, riderDirectionSelected, 2);
                Thread threadEla = new Thread(ela);
                threadEla.start();
                
                while (end - start <= millis) {
                    
                        System.out.println("Current number of riders who are waiting to be served = " + riderInitialFloors.size());
                        
                try {
                        TimeUnit.SECONDS.sleep(1); //TODO: increase sleep here
                } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                
                end = System.currentTimeMillis();
            }
            
            float sec = (end - start) / 1000F; 
            System.out.println(sec + " seconds");
            rc.stopRiders();
            el.stopElevator();
            ela.stopElevator();
            System.out.println(" ---END OF SIMULATION--- ");
        }
        
        
    public controller(long millis ) {
        System.out.println("starting...");
        this.millis = millis;
    }
    
}