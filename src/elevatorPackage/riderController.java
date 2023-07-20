package elevatorPackage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class riderController implements Runnable{
        private volatile int totalRiders = 0;
        private volatile int riderRequest = 0;
        private volatile int riderInitialFloor = 0;
        private ArrayList<Integer> riderInitialFloors = new ArrayList<Integer>();
        private ArrayList<Integer> riderDirectionSelected = new ArrayList<Integer>();
        
        // to stop the thread
    private boolean exit;
    
    public riderController(ArrayList<Integer> riderInitialFloors, ArrayList<Integer> riderDirectionSelected) {
        this.riderInitialFloors = riderInitialFloors;
        this.riderDirectionSelected = riderDirectionSelected;
        exit = false;
    }
        
        @Override
        public void run() {
                while(!exit) {
                        
                        int Min = 1; //TODO: change to 20
                        int Max = 5; //TODO: change to 120
        
                                rider newRider = new rider();
                                riderInitialFloors.add(newRider.getInitialFloor());
                                riderDirectionSelected.add(newRider.getDirectionSelected());
                                totalRiders++;
                                
                                synchronized(riderInitialFloors) {
                                        riderInitialFloors.notify();
                                }
                        
                        try {
                                 int r = (Min + (int)(Math.random() * ((Max - Min) + 1)));
//                               System.out.println("ridercontroller sleeping for " + r);
                                 TimeUnit.SECONDS.sleep(r);
                        } catch (InterruptedException e) {
                                 // TODO Auto-generated catch block
                                 e.printStackTrace();
                        }
                        
                        
                        
                }
                        
                        

        }
        
        public int getTotalRiders() {
                return totalRiders;
        }
        
        public int getRiderRequest() {
                return riderRequest;
        }
        
        public int getRiderInitialFloor() {
                return riderInitialFloor;
        }
        
        public ArrayList<Integer> getRiderInitialFloors() {
                return riderInitialFloors;
        }
        
        public ArrayList<Integer> riderDirectionSelected() {
                return riderDirectionSelected;
        }
        
        public void stopRiders() {
                System.out.println("stopped ridersz...");
                exit = true;
        }
}