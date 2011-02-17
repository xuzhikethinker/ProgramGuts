import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
/**
 *
 * @author javadb.com
	edited and adapted by tforzaglia and nlizzo
 */
public class Main2 
{
    
    /**
     * Lists the methods of a class using the Reflection api.
     */
    public static void listMethods() 
    {

        //Obtain the Class instance
        Class interestingClass = Interesting.class;
	Class listClass = List.class;
	Class bstClass = BST.class;    
	Class leafClass = Leaf.class;    
        
	//Get the methods
        Method[] methods1 = interestingClass.getDeclaredMethods();
	Method[] methods2 = listClass.getDeclaredMethods();
	Method[] methods3 = bstClass.getDeclaredMethods();
        Method[] methods4 = leafClass.getDeclaredMethods(); 
	
	//Get the constructors
	Constructor[] cons1 = interestingClass.getDeclaredConstructors();
	Constructor[] cons2 = listClass.getDeclaredConstructors();
	Constructor[] cons3 = bstClass.getDeclaredConstructors();
	Constructor[] cons4 = leafClass.getDeclaredConstructors();
        
	//Loop through the methods and print out their names
	System.out.println("Methods");
	System.out.println();
        for (Method method : methods1) 
	{
            System.out.println(method.getName());
        }
	for (Method method : methods2) 
        {
            System.out.println(method.getName());
        }
        for (Method method : methods3) 
        {
            System.out.println(method.getName());
        }
	for (Method method : methods4) 
        {
            System.out.println(method.getName());
        }

	 //Loop through the constructors and print out their names
	System.out.println();
	System.out.println("Constructors");
        System.out.println();
	for (Constructor constructor : cons1)
        {
            System.out.println(constructor.getName());
        }
	for (Constructor constructor : cons2)
        {
            System.out.println(constructor.getName());
        }
	for (Constructor constructor : cons3)
        {
            System.out.println(constructor.getName());
        }
	for (Constructor constructor : cons4)
        {
            System.out.println(constructor.getName());
        }

    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //new Main2().listMethods();
	listMethods();
    }
    
}
