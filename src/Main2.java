import java.lang.reflect.Method;

/**
 *
 * @author javadb.com
 */
public class Main2 
{
    
    /**
     * Lists the methods of a class using the Reflection api.
     */
    public void listMethods() 
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
 
        //Loop through the methods and print out their names
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


    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new Main2().listMethods();
    }
    
}
