package V1;

import java.util.Random;
//Clark_HW4
//Autumn Clark
//CS 2235
//Dr. Kirby
public abstract class V1Animal {
    //Instance variables
    private int _index;
    //Methods
    public int GetIndex() {
        return _index;
    }
    public void SetIndex(int index){
        if (index >= 0 && index < V1Ecosystem._river.length){ //Checks if index is within the bounds of _river
            _index = index;
        }
    }

    //Method to place new V1Animal in a random nearby null cell ; Uses V1Animal's index to find a nearby null cell
    public int FindNewCell(int index){
        for (int i = 0; i < V1Ecosystem._river.length; i++){
            if (V1Ecosystem._river[i] == null){
                return i;
            }
        }
        //All V1Fish should be gone before all empty cells are gone, so this should be unreachable code
        return -1;
    }

    //Method to create a new V1Animal in a random nearby null cell based on what kind of V1Animal is reproducing
    // Calls FindRandomCell ; Takes V1Animal's index to find a nearby null cell
    public V1Animal Reproduce(int index) {
        if (this.getClass() == V1Bear.class){
            return new V1Bear(FindNewCell(index));
        } else {
            return new V1Fish(FindNewCell(index));
        }
    }

    //Method to check if the V1Animal will collide with another V1Animal
    public boolean DidICollide(boolean isGoingRight) {
        if(isGoingRight){
            if (V1Ecosystem._river[_index + 1] != null){
                return true;
            }
        } else {
            if (V1Ecosystem._river[_index - 1] != null){
                return true;
            }
        }
        return false;
    }

    //Method to move the V1Animal ; Calls DidICollide, Reproduce, and Eat
    public void Move() {
        boolean didICollide; //Boolean to track if the V1Animal has collided with another V1Animal
        boolean didIMoveRight; //Boolean to track the direction the V1Animal moves
        //Check to see if the V1Animal is on the either edge of the river
        if (this._index == 0){
            didICollide = DidICollide(true); //On left edge, go right
            didIMoveRight = true;
        } else if (this._index == V1Ecosystem._river.length - 1){
            didICollide = DidICollide(false); //On right edge, go left
            didIMoveRight = false;
        } else {
            //If not on a edge, randomly assign a direction
            Random rand = new Random(System.currentTimeMillis());
            int x = rand.nextInt(2);
            //Half the time go right, half the time go left
            if (x == 0){
                didICollide = DidICollide(true);
                didIMoveRight = true;
            } else {
                didICollide = DidICollide(false);
                didIMoveRight = false;
            }
        }
        if (didICollide){
            //Check if the V1Animal collided with is the same type
            boolean isSameType = false; //Boolean to track if the V1Animal has collided with another V1Animal of the same type
            if (didIMoveRight){
                if (V1Ecosystem._river[this.GetIndex() + 1] != null) { //Check if cell to the right is null first
                    if (V1Ecosystem._river[this.GetIndex() + 1].getClass() == this.getClass()) {
                        isSameType = true;
                    }
                }
            } else if (!didIMoveRight){
                if (V1Ecosystem._river[this.GetIndex() - 1] != null) {
                    if (V1Ecosystem._river[this.GetIndex() - 1].getClass() == this.getClass()) {
                        isSameType = true;
                    }
                }
            }
            //If the collided V1Animal is the same type, call Reproduce
            if (isSameType) {
                V1Animal V1Animal = this.Reproduce(this.GetIndex());
            } else {
                //Call Eat method to remove the V1Fish
                if (this.getClass() == V1Bear.class){ //Check if this V1Animal is a V1Bear
                    if (didIMoveRight){ //V1Fish is to the right
                        ((V1Bear) this).Eat((V1Fish)V1Ecosystem._river[this.GetIndex() + 1]);
                    } else { //V1Fish is to the left
                        ((V1Bear) this).Eat((V1Fish)V1Ecosystem._river[this.GetIndex() - 1]);
                    }
                } else { //Am V1Fish, so call V1Bear's Eat method
                    if (didIMoveRight){ //V1Bear is to the right
                        ((V1Bear)V1Ecosystem._river[this.GetIndex() + 1]).Eat((V1Fish)this);
                    } else { //V1Bear is to the left
                        ((V1Bear)V1Ecosystem._river[this.GetIndex() - 1]).Eat((V1Fish)this);
                    }
                }
            }
        } else { //No collision, actually move the V1Animal
            if (didIMoveRight){
                V1Ecosystem._river[this.GetIndex() + 1] = V1Ecosystem._river[this.GetIndex()];
                V1Ecosystem._river[this.GetIndex()] = null;
                this.SetIndex(this.GetIndex() + 1);
            } else {
                V1Ecosystem._river[this.GetIndex() - 1] = V1Ecosystem._river[this.GetIndex()];
                V1Ecosystem._river[this.GetIndex()] = null;
                this.SetIndex(this.GetIndex() - 1);
            }
        }
    }
}
