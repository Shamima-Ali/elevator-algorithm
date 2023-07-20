package elevatorPackage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class elevator extends Thread{
        int Min = 1;
        int Max = 5;
        int currentFloor = 0;
        int direction = 0; // 0 is down, 1 is up
        int maxPersons = 0;
        int currentPersons = 0;
        private ArrayList<Integer> riderInitialFloors = new ArrayList<Integer>();
        private ArrayList<Integer> riderDirectionSelected = new ArrayList<Integer>();
        private ArrayList<Integer> ridersGoingUp = new ArrayList<Integer>();
        private ArrayList<Integer> ridersGoingDown = new ArrayList<Integer>();
        private ArrayList<Integer> riderFinalFloors = new ArrayList<Integer>();

        // to stop the thread
    private boolean exit;
    
    int riderFinal = 0;
        
        private ElevatorStatus status;
        public enum ElevatorStatus {
        GOING_UP,
        GOING_DOWN,
        IDLE;
    }
        
        private int name;
        
        public elevator(int persons, ArrayList<Integer> riderInitialFloors, ArrayList<Integer> riderDirectionSelected, int nme) {
                maxPersons = persons;
                this.riderInitialFloors = riderInitialFloors;
        this.riderDirectionSelected = riderDirectionSelected;
                currentFloor = (Min + (int)(Math.random() * ((Max - Min) + 1)));
                System.out.println("currentFloor in constructor = " + currentFloor);
                exit = false;
                
                name = nme;
                System.out.println("name of elevator is = " + nme);
                
        }
        
        @Override
        public void run() {
                while (!exit) {
                        synchronized(riderInitialFloors) {
                        System.out.println("NAME: " + name + " --> " + "len of riderInitialFloors " + riderInitialFloors.size());
                System.out.println("NAME: " + name + " --> " + "len of riderDirectionSelected " + riderDirectionSelected.size());

                                while (riderInitialFloors.size() == 0)
                                        try {
                                                status = ElevatorStatus.IDLE;
                                                System.out.println("NAME: " + name + " --> " + "waiting for riderss...");
                                                riderInitialFloors.wait();
                                        } catch (InterruptedException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                        }
                        }
                                
                                // pick up a person
                                System.out.println("NAME: " + name + " --> " + "el picking up a person");
                                                                
                                int riderInit = riderInitialFloors.get(0);
                                int riderDir = riderDirectionSelected.get(0);
                                
                                System.out.println("NAME: " + name + " --> " + "adding the first Rider");
                                int direction = categorizeRider();
//                              System.out.println("LEN of ridersGoingUp BEFORE     = " + ridersGoingUp.size());
//                      System.out.println("LEN of ridersGoingDown BEFORE = " + ridersGoingDown.size());
                                
                                
                                System.out.println("NAME: " + name + " --> " + "riderInitial= " + riderInit);
                                System.out.println("NAME: " + name + " --> " + "riderDirection= " + riderDir);
                                
                                if (direction == 0) {
                                        while (ridersGoingDown.size() != 0) {
                                                moveElevator(ridersGoingDown, direction);
                                        }
                                } else {
                                        while (ridersGoingUp.size() != 0) {
                                                moveElevator(ridersGoingUp, direction);
                                        }
                                }
                                
                                try {
//                                       System.out.println("sleeping for " + r);
                                         TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException e) {
                                         // TODO Auto-generated catch block
                                         e.printStackTrace();
                                }       
                }
        }
        
        public void moveElevator(ArrayList<Integer> ridersList, int dir) {
                moveToRider(ridersList);
                riderFinalFloors.add(pickUpRider(ridersList, dir));
                
                System.out.println("NAME: " + name + " --> " + "removing riders from going down or up list... ");
                if (dir == 0) {
                        System.out.println("NAME: " + name + " --> " + "removed ridersList " + ridersList.get(0));
                        ridersList.remove(0);  
                } else {
                        System.out.println("NAME: " + name + " --> " + "removed ridersGoingUp " + ridersList.get(0));
                        ridersList.remove(0);
                }
                
                moveToDestination(riderFinalFloors.get(0), dir);
                if (currentFloor == riderFinalFloors.get(0)) {
                        dropRider();
                        
                        System.out.println("NAME: " + name + " --> " + "riderFinalFloors removed for " + riderFinalFloors.get(0));
                        riderFinalFloors.remove(0);
                        System.out.println("NAME: " + name + " --> " + "_______________________END OF AN ELEVATOR RIDE");

                }

        }
                public int categorizeRider() {
                if (riderDirectionSelected.get(0) == 0) {
                        ridersGoingDown.add(riderInitialFloors.get(0));
                        riderInitialFloors.remove(0);
                        return 0;

                } else {
                        ridersGoingUp.add(riderInitialFloors.get(0));
                        riderDirectionSelected.remove(0);
                }
                return 1;
        }
        
        public void stopElevator() {
                System.out.println("NAME: " + name + " --> " + "stopped elevszz");
                exit = true;
        }
        
        public int getCurrentFloor() {
                return currentFloor;
        }
        
        public void pickAnotherRider(int currFloor) {
                System.out.println("NAME: " + name + " --> " + "assessing Conditions");
                System.out.println("NAME: " + name + " --> " + "tempCurrFloor = currFloor in pickAnotherRider is " + currFloor);
                System.out.println("NAME: " + name + " --> " + "adding a new Rider");
                int direction = categorizeRider();
                
//              System.out.println("LEN of ridersGoingUp AFTER     = " + ridersGoingUp.size());
//      System.out.println("LEN of ridersGoingDown AFTER = " + ridersGoingDown.size());
        if (ridersGoingUp.size() > 0) {
                for (int i : ridersGoingUp) {
                        System.out.println("NAME: " + name + " --> " + "the ridersGoingUp init floors are " + i );
                }

                        } else if (ridersGoingDown.size() > 0) {
                for (int i : ridersGoingDown) {
                        System.out.println("NAME: " + name + " --> " + "the ridersGoingDown init floors are " + i );
                }
        }
                
                if (ridersGoingUp.size() > 0 && currFloor == ridersGoingUp.get(0) && 
                                status == ElevatorStatus.GOING_UP) {
                        System.out.println("NAME: " + name + " --> " + "el going up and found another rider that's also going up");
                        
                        currentFloor = riderInitialFloors.get(0);
                        riderFinalFloors.add(pickUpRider(direction == 0 ? ridersGoingDown :  ridersGoingUp, direction));
                        if (riderFinalFloors.get(1) > riderFinalFloors.get(0)) {
                                System.out.println("NAME: " + name + " --> " + "the new rider that's going down stops before the og one");
                                System.out.println("NAME: " + name + " --> " + "therefore, moving to the new rider destination");
                                moveToDestination(riderFinalFloors.get(1), direction);
                                
                                System.out.println("NAME: " + name + " --> " + "dropping the new rider to it's destination");
                                dropRider();
                        }
                        
                        return;
                        
                } else if (ridersGoingDown.size() > 0 && currFloor == ridersGoingDown.get(0) && 
                                status == ElevatorStatus.GOING_DOWN) {
                        System.out.println("NAME: " + name + " --> " + "el going down and found another rider that's also going down");
                        currentFloor = riderInitialFloors.get(0);

                                                riderFinalFloors.add(pickUpRider(direction == 0 ? ridersGoingDown :  ridersGoingUp, direction));
                        if (riderFinalFloors.get(1) < riderFinalFloors.get(0)) {
                                System.out.println("NAME: " + name + " --> " + "the new rider that's going down stops before the og one");
                                
                                System.out.println("NAME: " + name + " --> " + "therefore, moving to the new rider destination");
                                moveToDestination(riderFinalFloors.get(1), direction);
                                
                                System.out.println("NAME: " + name + " --> " + "dropping the new rider to it's destination");
                                dropRider();
                        }

                        return;
                }
                
                return;
        }
        
        public void moveToRider(ArrayList<Integer> riderInitial) {
                System.out.println("NAME: " + name + " --> " + "moving el to rider");
                System.out.println("NAME: " + name + " --> " + "currentFloor in moveToRider = " + currentFloor);
                if (currentFloor > riderInitial.get(0)) {
                        
                        System.out.println("NAME: " + name + " --> " + "el going GOING_UP from" + currentFloor + "to " + riderInitial.get(0));
                        status = ElevatorStatus.GOING_UP;
                        
                        try {
                                 TimeUnit.SECONDS.sleep(2*(currentFloor-riderInitial.get(0))); //TODO: change to 5
                        } catch (InterruptedException e) {
                                 // TODO Auto-generated catch block
                                 e.printStackTrace();
                        }

                         currentFloor = riderInitial.get(0);
                        System.out.println("NAME: " + name + " --> " + "el going GOING_UP and reached" + currentFloor);
                        
                } else if (currentFloor < riderInitial.get(0)) {
                        System.out.println("NAME: " + name + " --> " + "el going GOING_DOWN from " + currentFloor + "to " + riderInitial.get(0));
                        status = ElevatorStatus.GOING_DOWN;
                        
                        try {
                                 TimeUnit.SECONDS.sleep(2*(riderInitial.get(0)-currentFloor)); //TODO: change to 5
                        } catch (InterruptedException e) {
                                 // TODO Auto-generated catch block
                                 e.printStackTrace();
                        }
                        
                        currentFloor = riderInitial.get(0);
                        System.out.println("NAME: " + name + " --> " + "el going GOING_DOWN and reached" + currentFloor);
                        
                } else {
                        System.out.println("NAME: " + name + " --> " + "el idle");
                        status = ElevatorStatus.IDLE;
                }
                
        }
        
        public int pickUpRider(ArrayList<Integer> riderInitial, int riderDirection) {
                System.out.println("NAME: " + name + " --> " + "picking Up Rider...");
                currentPersons++;
                int selectFloor = 0;
                
                if (riderDirection == 0) {
                        int MinR = riderInitial.get(0)+1;
                        int MaxR = 5;
                        selectFloor = (MinR + (int)(Math.random() * ((MaxR - MinR) + 1)));
System.out.println("NAME: " + name + " --> " + "Rider selected to go down to " + selectFloor + " from " + riderInitial.get(0));

                } else if (riderDirection == 1) {
                        int MinR = 1;
                        int MaxR = riderInitial.get(0)-1;
                        selectFloor = (MinR + (int)(Math.random() * ((MaxR - MinR) + 1)));
                        System.out.println("NAME: " + name + " --> " + "Rider selected to go up to " + selectFloor + " from " + riderInitial.get(0));
                } 
                
                try {
                         TimeUnit.SECONDS.sleep(5); //TODO:change to 15
                } catch (InterruptedException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                
                }
                
                System.out.println("NAME: " + name + " --> " + "picked Up Rider");
                return selectFloor;
        }


        public void moveToDestination(int destFloor, int riderDirection) {
                System.out.println("NAME: " + name + " --> " + "moving el to destination");
                System.out.println("NAME: " + name + " --> " + "currentFloor in moveToDestination = " + currentFloor);
                int tempCurrFloor = currentFloor;
                
                if (riderDirection == 1) {
                        System.out.println("NAME: " + name + " --> " + "el going GOING_UP from" + currentFloor + "to " + destFloor);
                        status = ElevatorStatus.GOING_UP;
                        
                        try {
                                 TimeUnit.SECONDS.sleep(2*(currentFloor-destFloor)); //TODO: change to 5
                                 tempCurrFloor--;
                                 System.out.println("NAME: " + name + " --> " + "tempCurrFloor = " + tempCurrFloor);
                                 if (currentPersons < maxPersons) {
                                         System.out.println("NAME: " + name + " --> " + "currentPersons < maxPersons, picking up another rider");
                                         pickAnotherRider(tempCurrFloor);
                                 }
                                
                        } catch (InterruptedException e) {
                                 // TODO Auto-generated catch block
                                 e.printStackTrace();
                        }
                        
                        currentFloor = destFloor;
                        System.out.println("NAME: " + name + " --> " + "el going GOING_UP and reached " + currentFloor);
                        
                } else if (riderDirection == 0) {
                        System.out.println("NAME: " + name + " --> " + "el going GOING_DOWN from " + currentFloor + " to " + destFloor);
                        status = ElevatorStatus.GOING_DOWN;
                        
                        try {
                        TimeUnit.SECONDS.sleep(2*(destFloor-currentFloor)); //TODO: change to 5
                                 tempCurrFloor++;
                                 System.out.println("NAME: " + name + " --> " + "tempCurrFloor = " + tempCurrFloor);
                                 if (currentPersons < maxPersons) {
                                         System.out.println("NAME: " + name + " --> " + "currentPersons < maxPersons, picking up another rider");
                                         pickAnotherRider(tempCurrFloor);
                                 }
                                 
                        } catch (InterruptedException e) {
                                 // TODO Auto-generated catch block
                                 e.printStackTrace();
                        }
                        
                        currentFloor = destFloor;
                        System.out.println("NAME: " + name + " --> " + "el going GOING_DOWN and reached " + currentFloor);
                        
                }
                
        }

 public void dropRider() {
                System.out.println("NAME: " + name + " --> " + "dropping Rider...");
                currentPersons--;
                
                try {
                         TimeUnit.SECONDS.sleep(5); //TODO:change to 15
                } catch (InterruptedException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                
                }
                System.out.println("NAME: " + name + " --> " + "dropped Rider");
        }
         }
        


//moveToRider(direction == 0 ? ridersGoingDown :  ridersGoingUp);
//riderFinalFloors.add(pickUpRider(direction == 0 ? ridersGoingDown :  ridersGoingUp, direction));
//
//System.out.println("removing riders from going up or down list... ");
//if (direction == 0) {
//      System.out.println("removed ridersGoingDown " + ridersGoingDown.get(0));
//      ridersGoingDown.remove(0);  
//} else {
//      System.out.println("removed ridersGoingUp " + ridersGoingUp.get(0));
//      ridersGoingUp.remove(0);
//}
//
//moveToDestination(riderFinalFloors.get(0), direction);
//if (currentFloor == riderFinalFloors.get(0)) {
//      dropRider();
//      
//      System.out.println("riderFinalFloors removed for " + riderFinalFloors.get(0));
//      riderFinalFloors.remove(0);
//      System.out.println("_______________________END");
//
//}