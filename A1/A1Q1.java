/**
 * Kristine Trinh
 * nlt895 
 * 11190412
 */
import java.util.Random;
import java.util.Scanner;

import lib280.list.*;

/**
 * A model that will help Tractor Jack keep track of his grains
 * by defining which type of grains and calculate how much of each type
 * of grains he plundered
 */
public class A1Q1 {
	
	/**
	 * The given method generates data that represents Captain Jack's plunder for the day
	 * @param howMany the number of element that the method will generate
	 * @return an array of randomly Sack objects with howMany elements
	 */
    public static Sack [] generatePlunder ( int howMany ) {
        Random generator = new Random ();
        Sack grain [] = new Sack [ howMany ];
        for ( int i =0; i < howMany ; i ++) {
            grain [i] = new Sack (
                    Grain . values ()[ generator . nextInt ( Grain . values (). length )] ,
                    generator . nextDouble () * 100 );
        }
        return grain ;
    }

    /**
     * Main method
     * @param argv
     */
    public static void main(String argv[]) 
    {
        /*
         * An array of the linked list is created for each type of grain
         */
    	LinkedList280<Sack> bag [] = new LinkedList280[5];
        for (int i = 0; i < 5; i++){
            bag[i] = new LinkedList280<Sack>();
        }
        
        /*
         * Generate randomly the data of grains by using generatePlunder() method
         * The generated information is stored in the array of grain
         */
        final int NUM_EL = 10;
        Sack[] grain = generatePlunder(NUM_EL);
        
        /*
         * Sorting the grains into each bag base on its type
         */
        for (int i = 0; i < grain.length; i++) {
			switch (grain[i].getType().toString()) {
			case "WHEAT":
				bag[0].insert(grain[i]);
				break;
			case "BARLEY":
				bag[1].insert(grain[i]);
				break;
			case "OATS":
				bag[2].insert(grain[i]);
				break;
			case "RYE":
				bag[3].insert(grain[i]);
				break;
			case "OTHER":
				bag[4].insert(grain[i]);
				break;
			default:
				break;
			}
		}

        
        //An array type of grain that the method will loop through
        String type []= {"WHEAT", "BARLEY", "OATS", "RYE", "OTHER"};
        /*
         * Calculating the total weight for each type of grain
         */
        for(int i = 0; i < type.length; i++){
            if (!bag[i].isEmpty()){
                double totalWeight = 0.0;
                //Iterating through the array list
                LinkedIterator280<Sack> iterator = new LinkedIterator280<Sack>(bag[i]);
                if(iterator.itemExists()){
                	//Assign item to the actual value at the current available item
                    String item = iterator.item().getType().toString();
                    //Looping through the whole list
                    while (iterator.itemExists()){
                        totalWeight = totalWeight + iterator.item().getWeight();
                        iterator.goForth();
                    }
                    //Output the weight for the specific value  
                    System.out.println("Jack plundered " + totalWeight + " pounds of " + item);
                }
            }
            else 
            {
            	//if the method loop through an empty bag of a type of grain --> output its type name and value 0.0
                System.out.println("Jack plundered " + 0.0 + " pounds of " + type[i]);
            }
        }

    }

}