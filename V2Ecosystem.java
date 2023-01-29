package V2;

import java.util.Random;
//Clark_HW4
//Autumn Clark
//CS 2235
//Dr. Kirby
public class V2Ecosystem {
    //Instance variables
    public static DoublyLinkedList<V2Animal> _river = new DoublyLinkedList();

    //Method to populate _river ; 33% V2Fish, 33% V2Bear, and 33% null
    public static void PopulateRiver(){
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 320000; i++){ //Make _river of size 500
            int x = rand.nextInt(3);
            if (x == 0){
                //Add a V2Fish
                _river.AddHead(new V2Fish());
            } else if (x == 1){
                //Add a V2Bear
                _river.AddHead(new V2Bear());
            } else {
                //Leave the cell null
                _river.AddHead(null);
            }
        }
    }

    //Method to check if there are any V2Fish in _river
    public static boolean IsV2FishInRiver(){
        //Loop through every V2Animal in _river
        DoublyLinkedList.Node tempNode = _river.GetHead(); //Start with head
        for (int i = 0; i < _river.GetSize(); i++) {
            if (tempNode.GetElement() != null) { //Ensure null cells don't get checked
                if (((V2Animal)tempNode.GetElement()).getClass() == V2Fish.class) {
                    //Then there is a V2Fish in _river, then the program needs to continue
                    return true;
                }
            }
            tempNode = tempNode.GetNext(); //Go to the next node
        }
        //There are no V2Fish
        return false;
    }

    //Method to get the current count of V2Animals and null cells
    public static void DisplayCounts(){
        int V2FishCount = 0;
        int V2BearCount = 0;
        int emptyCount = 0;
        DoublyLinkedList.Node tempNode = _river.GetHead(); //Start with head
        for (int i = 0; i < _river.GetSize(); i++) {
            if (tempNode.GetElement() != null) { //Exclude null cells to look for V2Animals
                if (tempNode.GetElement().getClass() == V2Fish.class) {
                    V2FishCount++;
                } else if (tempNode.GetElement().getClass() == V2Bear.class) {
                    V2BearCount++;
                }
            } else { //Null get counted here
                emptyCount++;
            }
            tempNode = tempNode.GetNext(); //Go to the next node
        }
        System.out.println("Theres " + V2FishCount + " V2Fish, " + V2BearCount + " V2Bears, and "
                + emptyCount + " empty cells in the river.");
    }

    //Main code
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();

        //int count = 0; //Int to use as a counter
        PopulateRiver();
        //DisplayCounts(); No longer need to display river
        boolean isGoingRight;
        Random rand = new Random(System.currentTimeMillis());



        while (IsV2FishInRiver()){
            DoublyLinkedList.Node tempNode = _river.GetHead(); //Start with the head
            for (int i = 0; i < _river.GetSize(); i++){
                if (i == 0){ //We are on the head and can only go right
                    isGoingRight = true;
                } else if (i == _river.GetSize()){ //We are on the tail and can only go left
                    isGoingRight = false;
                } else {
                    int x = rand.nextInt(2);
                    if (x == 0){ //Go right half the time
                        isGoingRight = true;
                    } else { //Go left half the time
                        isGoingRight = false;
                    }
                }
                if (tempNode.GetElement() != null) {
                    tempNode.SwapNodes(isGoingRight);
                }
                /*count++;
                if (count == 100){ //Counts are displayed every 100th time the for loop is iterated to shorten output frequency
                    DisplayCounts();
                    count = 0;
                }
                 */
                tempNode = tempNode.GetNext();
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime);
        System.out.println("Version 2 took " + totalTime + " milliseconds to run.");
    }
}
