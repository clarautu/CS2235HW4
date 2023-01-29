package V1;

import java.util.Random;
//Clark_HW4
//Autumn Clark
//CS 2235
//Dr. Kirby
public class V1Ecosystem {
    //Instance variables
    public static V1Animal[] _river;

    //Method to populate _river ; 33% V1Fish, 33% V1Bear, and 33% null
    public static void PopulateRiver(){
        _river = new V1Animal[5000];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < _river.length; i++){
            int x = rand.nextInt(3);
            if (x == 0){
                //Add a V1V1Fish
                _river[i] = new V1Fish(i);
            } else if (x == 1){
                //Add a V1Bear
                _river[i] = new V1Bear(i);
            } else {
                //Leave the cell null
            }
        }
    }

    //Method to check if there are any V1Fish in _river
    public static boolean IsV1FishInRiver(){
        //Loop through every V1Animal in _river
        for (V1Animal nemo:_river) {
            if (nemo != null) { //Ensure null cells don't get checked
                if (nemo.getClass() == V1Fish.class) {
                    //Then there is a V1Fish in _river, then the program needs to continue
                    return true;
                }
            }
        }
        //There are no V1Fish
        return false;
    }

    //Method to get the current count of V1Animals and null cells
    public static void DisplayCounts(){
        int V1FishCount = 0;
        int V1BearCount = 0;
        int emptyCount = 0;
        for (int i = 0; i < _river.length; i++) {
            if (_river[i] != null) { //Exclude null cells to look for V1Animals
                if (_river[i].getClass() == V1Fish.class) {
                    V1FishCount++;
                } else if (_river[i].getClass() == V1Bear.class && _river[i] != null) {
                    V1BearCount++;
                }
            } else { //Null get counted here
                emptyCount++;
            }
        }
        System.out.println("Theres " + V1FishCount + " V1Fish, " + V1BearCount + " V1Bears, and "
                + emptyCount + " empty cells in the river.");
    }

    //Main code
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        long count = 0; //Long to use as a counter
        PopulateRiver();
        //DisplayCounts(); No longer need to display river
        while (IsV1FishInRiver()) {
            for (V1Animal V1Animal : _river) {
                if (V1Animal != null) {
                    V1Animal.Move();
                }
                count++;
                /*
                if (count == 100){ //Counts are displayed every 100th time the foreach loop is completed to shorten output frequency
                    DisplayCounts();
                    count = 0;
                }
                 */
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime);

        System.out.println("Version 1 took " + totalTime + " milliseconds to run.\n" + count + " Steps.");
    }
}
