import java.util.ListIterator;
import java.util.LinkedList;
 
public class IterateLinkedListUsingListIteratorExample {
 
  public static void main(String[] args) {
 
    //create LinkedList object
    LinkedList lList = new LinkedList();
   
    //add elements to LinkedList
    lList.add("1");
    lList.add("2");
    lList.add("3");
    lList.add("4");
    lList.add("5");
 
    /*
     * To get an ListIterator object of LinkedList, use
     * ListIterator listIterator() method.
     */
   
    ListIterator itr = lList.listIterator();
   
    System.out.println("Iterating through elements of Java LinkedList using   ListIterator in forward direction...");
    while(itr.hasNext())
    {
      System.out.println(itr.next());
    }
 
    System.out.println("Iterating through elements of Java LinkedList using  ListIterator in reverse direction...");
    while(itr.hasPrevious())
      System.out.println(itr.previous());
   
  }
}