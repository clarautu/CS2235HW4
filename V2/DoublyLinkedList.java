package V2;
//Clark_HW4
//Autumn Clark
//CS 2235
//Dr. Kirby
public class DoublyLinkedList<E> {
    //Nested Node class
    public static class Node<E> {
        //Instance variables
        public E _element; //The element stored at this node/cell
        private DoublyLinkedList.Node<E> _next; //Reference to the next node/cell
        private DoublyLinkedList.Node<E> _prev;

        //Constructors
        public Node(E e, DoublyLinkedList.Node<E> next, DoublyLinkedList.Node<E> prev) {
            _element = e;
            _next = next;
            _prev = prev;
        }

        //Access methods
        public E GetElement(){
            return _element;
        }
        public DoublyLinkedList.Node<E> GetNext(){
            return _next;
        }
        public DoublyLinkedList.Node<E> GetPrevious(){
            return _prev;
        }

        //Update method
        public void SetNext(DoublyLinkedList.Node<E> next){
            _next = next;
        }
        public void SetPrevious(DoublyLinkedList.Node<E> prev){
            _prev = prev;
        }
        public void SetElement(E element){
            _element = element;
        }

        private void EatOrReproduce(boolean isGoingRight){
            boolean isV2Bear = false;
            if (this.GetElement() != null) {
                if (this.GetElement().getClass() == V2Bear.class) {
                    isV2Bear = true;
                }
                V2Animal myClass = ((V2Animal) this.GetElement());
                if (isGoingRight) { //I'm going right
                    V2Animal nextClass = ((V2Animal) this.GetNext().GetElement());
                    if (myClass.getClass() == nextClass.getClass()) {
                        //Then the V2Animals are of same type and reproduce
                        Reproduce(isGoingRight);
                    } else { //They are not the same type
                        if (isV2Bear) { //Am I a V2Bear?
                            this.GetNext().SetElement(null); //Eat the V2Fish
                        } else { //I am a V2Fish
                            this.SetElement(null);//The V2Bear eats me
                        }
                    }
                } else { //I'm going left
                    V2Animal prevClass = ((V2Animal) this.GetPrevious().GetElement());
                    if (myClass.getClass() == prevClass.getClass()) {
                        //Then the V2Animals are of the same type and reproduce
                        Reproduce(isGoingRight);
                    } else { //They are not the same type
                        if (isV2Bear) { //Am I a V2Bear?
                            this.GetPrevious().SetElement(null); //Eat the V2Fish
                        } else { //I am a V2Fish
                            this.SetElement(null);
                        }
                    }
                }
            }
        }
        private void Reproduce(boolean isGoingRight){
            V2Animal myType = ((V2Animal)this.GetElement());
            if (isGoingRight){ //Add between this and the node to the right
                Node newV2Animal = new Node(myType, this.GetNext(), this);
                this.GetNext().SetPrevious(newV2Animal);
                this.SetNext(newV2Animal);
            } else { //Add between this and the node to the left
                Node newV2Animal = new Node(myType, this, this.GetPrevious());
                this.GetPrevious().SetNext(newV2Animal);
                this.SetPrevious(newV2Animal);
            }
        }
        private boolean DidICollide(boolean isGoingRight){
            if (isGoingRight){
                //Going right
                if (this.GetNext().GetElement() != null){
                    return true; //I hit another V2Animal
                }
                return false; //I didn't hit anyone
            } else {
                //Going left
                if (this.GetPrevious() != null && this.GetPrevious().GetElement() != null){
                    return true; //I hit another V2Animal
                }
                return false; //I didn't hit anyone
            }
        }
        public void SwapNodes(boolean isGoingRight){
            if (isGoingRight){
                if (DidICollide(isGoingRight)) {
                    //Call Eat or Reproduce
                    EatOrReproduce(isGoingRight);
                } else { //Swap with next node
                    E tempThis = this.GetElement();
                    this.GetNext().SetElement(tempThis);
                    this.SetElement(null);
                }
            } else {
                if (DidICollide(isGoingRight)){
                    //Call Eat or Reproduce
                    EatOrReproduce(isGoingRight);
                } else { //Swap with previous node
                    if (this.GetPrevious() != null) {
                        E tempThis = this.GetElement();
                        this.GetPrevious().SetElement(tempThis);
                        this.SetElement(null);
                    }
                }
            }
        }
        //End of nested Node class
    }
    //Instance variable - back in DoublyLinkedList
    private DoublyLinkedList.Node<E> _head = null; //Head node of list (or null if empty)
    private DoublyLinkedList.Node<E> _tail = null; //Tail node of list (or null if empty)
    private DoublyLinkedList.Node<E> _header; //Sentinel for the start of the list
    private DoublyLinkedList.Node<E> _trailer; //Sentinel for the end of the list
    public int _size = 0; //Number of nodes in list

    //Constructors
    public DoublyLinkedList(){ //Construct an initially empty list
        _header = new Node<>(null, null, null);
        _trailer = new Node<>(null, _header, null);
        _header.SetNext(_trailer);
        _trailer.SetPrevious(_header);
    }

    //Access methods
    public int GetSize() {
        return _size;
    }
    public boolean IsEmpty(){
        return _size == 0;
    }
    public Node<E> GetHead() {
        if (IsEmpty()) return null;
        return _head;
    }
    public Node<E> GetTail(){
        if (IsEmpty()) {
            return null;
        }
        return _tail;
    }

    //Update Methods
    public void AddHead(E e){ //Add element e to the front of the list
        //AddBetween(e, _header.GetNext(), _header);

        DoublyLinkedList.Node<E> newHead = new DoublyLinkedList.Node<>(e, _head, _header); //Temp node for new head
        if (_size == 0){ //Special case when just one node
            _head = newHead; //Set newHead as head
            _tail = _head; //Make tail and head the same node
            _head.SetNext(_trailer); //Sets next node as trailer in this case
        } else {
            newHead.SetNext(_head); //Points new head at old head
            _head = newHead; //Set newHead as head
        }
        _head.SetPrevious(_header); //Point head at header
        _header.SetNext(_head);
        _size++;


    }
    public void AddTail(E e) {
        //AddBetween(e, _trailer.GetPrevious(), _trailer);
        DoublyLinkedList.Node<E> newTail = new DoublyLinkedList.Node<E>(e, _trailer, _tail); //Our new tail
        if (IsEmpty()){
            _head = newTail; //Special case of just one node
            newTail.SetPrevious(_header); //Point tail at header in this case
        } else {
            newTail.SetPrevious(_tail); //Point new tail at old tail
            _tail.SetNext(newTail); //Point old tail to new tail
        }
        _tail = newTail; //New node becomes tail
        _tail.SetNext(_trailer); //Points new tail at trailer
        _trailer.SetPrevious(_tail); //Points trailer at tail
        _size++;

    }
    public void AddBetween(E e, DoublyLinkedList.Node next, DoublyLinkedList.Node prev){ //Add a node between two others
        DoublyLinkedList.Node<E> newNode = new DoublyLinkedList.Node<E>(e, next, prev);
        prev.SetNext(newNode);
        next.SetPrevious(newNode);
        _size++;
    }
    public E RemoveHead(){ //Removes and returns the first element
        if (IsEmpty()){
            return null;
        }
        E answer = _head.GetElement();
        _head = _head.GetNext();
        _head.SetPrevious(_header);
        _size--;
        if (_size == 0){
            _tail = null; //Special case of one node (before removing head)
        }
        return answer;
    }
    public E RemoveTail(){ //Removes and returns the last element
        if (IsEmpty()){
            return null;
        }
        E answer = _tail._element;
        _tail = _tail.GetPrevious();
        _tail.SetNext(_trailer);
        _size--;
        if (_size == 0){
            _head = null; //Special case of one node (before removing tail)
        }
        return answer;
    }
    public Node<E> RemoveBetween(Node<E> node){ //Remove a node between two others
        Node<E> next = node.GetNext();
        Node<E> prev = node.GetPrevious();
        next.SetPrevious(prev);
        prev.SetNext(next);
        _size--;
        return node;
    }

    //Methods
    public String toString(){
        String out = "Singly Linked List: \n";
        out += "0 " + _head.GetElement() + "\n";
        DoublyLinkedList.Node<E> nextNode;
        nextNode = _head.GetNext();
        for (int i = 1; i < this.GetSize(); i++){
            out += Integer.toString(i) + " " + nextNode.GetElement() + "\n";
            nextNode = nextNode.GetNext();
        }
        return out;
    }
}
