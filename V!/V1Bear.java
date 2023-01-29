package V1;
//Clark_HW4
//Autumn Clark
//CS 2235
//Dr. Kirby
public class V1Bear extends V1Animal {
    //Constructors
    public V1Bear(){}
    public V1Bear(int index){
        SetIndex(index);
    }
    //Methods
    //Method to eat a Fish
    public void Eat(V1Fish fish){
        V1Ecosystem._river[fish.GetIndex()] = null;
    }
}
