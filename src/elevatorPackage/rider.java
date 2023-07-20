package elevatorPackage;

public class rider {
    int floorArrived;
    int DirectionSelected;
    int floorSelected;
    int Min = 1;
    int Max = 5;
    
    public rider() {
            riderArrives();
            riderSelects();
    }
    
    public void riderArrives() {
            floorArrived = (Min + (int)(Math.random() * ((Max - Min) + 1)));
    }
    
    // 0 is down, 1 is up
    public void riderSelects() {
            if (floorArrived < Max) {
                    // riderGoingDown
                    DirectionSelected = 0;
            } else {
                    // riderGoingUp
                    DirectionSelected = 1;
            }
    }
            
    public int getDirectionSelected() {
            return DirectionSelected;
    }
    
    public int getInitialFloor() {
            return floorArrived;
    }
    }
