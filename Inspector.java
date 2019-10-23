
import java.lang.reflect.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	
    	//Output:
    	//Class Name
    	//Superclass (Recurse into superclass)
    	//Interfaces (Recurse)
    	//Constructors
    		//name
    		//parameters + types
    		//modifiers
    	//Methods
    		//name
    		//Exceptions
    		//parameters + types
    		//return type
    		//modifiers
    	//Fields
    		//name
    		//type
    		//modifiers
    		//current value
    		//If recursive set to true, recurse into non-primitive fields
    		//If recursive false, print out class + identity hash code
    	
    }

}
