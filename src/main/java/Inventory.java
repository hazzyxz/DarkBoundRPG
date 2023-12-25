//to handle all inventory logic
public class Inventory {

    private Item[] items;
    public Item[] getItems() { return items; }
    public Item get(int i) { return items[i]; }

    public Inventory(int max){
        items = new Item[max];
    }

    //method to add item in our first open slot in our inventory
    public void add(Item item){
        for (int i = 0; i < items.length; i++){
            if (items[i] == null){
                items[i] = item;
                break;
            }
        }
    }

    //remove item from our inventory
    public void remove(Item item){
        for (int i = 0; i < items.length; i++){
            if (items[i] == item){
                items[i] = null;
                return;
            }
        }
    }

    //to know if our inventory is full and we cant carry no more
    public boolean isFull(){
        int size = 0;
        for (int i = 0; i < items.length; i++){
            if (items[i] != null)
                size++;
        }
        return size == items.length;
    }
}
