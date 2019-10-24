
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class clazz, Object obj, boolean recursive, int depth) {
    	
    	//Output:
    	String padding = "";
    	for(int i = 0; i < depth; i++) {
    		padding += "\t";
    	}
    	StringBuilder outputBuilder = new StringBuilder();
    	
    	//Class Name
    	outputBuilder.append(clazz.getName() + ":\n");
    	//Superclass (Recurse into superclass)
    	//Interfaces (Recurse)
    	//Constructors
    	Constructor cons[] = clazz.getConstructors();
    	for(Constructor c : cons) {
    		outputBuilder.append(padding + " CONSTRUCTOR - ");
    		outputBuilder.append(interpretModifiers(c.getModifiers()));
    		outputBuilder.append(c.getName() + "(");
    		
    		Parameter params[] = c.getParameters();
    		for(Parameter p : params) {
    			if(p.getType().getComponentType() == null) {
    				outputBuilder.append(p.getType() + " " + p.getName() + ", ");
    			} else {
    				outputBuilder.append(p.getType().getComponentType() + "[] " +p.getName() + ", ");
    			}
    		}
    		outputBuilder.append(")\n");
    	}
    		//name
    		//parameters + types
    		//modifiers
    	//Methods
    	//Method methods[] = clazz.getMethods();
    		//name
    		//Exceptions
    		//parameters + types
    		//return type
    		//modifiers
    	//Fields
    	//Field fields[] = clazz.getFields();
    		//name
    		//type
    		//modifiers
    		//current value
    		//If recursive set to true, recurse into non-primitive fields
    		//If recursive false, print out class + identity hash code
    	
    	System.out.println(outputBuilder.toString());
    }
    
    private String interpretModifiers(int mods) {
    	String toReturn = " ";
    	
    	if(Modifier.isPublic(mods))
    		toReturn += "public ";
    	else if(Modifier.isPrivate(mods))
    		toReturn += "private ";
    	else if(Modifier.isProtected(mods))
    		toReturn += "protected ";
    	
    	if(Modifier.isAbstract(mods))
    		toReturn += "abstract ";
    	
    	if(Modifier.isStatic(mods))
    		toReturn += "static ";
    	
    	if(Modifier.isFinal(mods))
    		toReturn += "final ";
    	
    	if(Modifier.isTransient(mods))
    		toReturn += "transient ";
    	
    	if(Modifier.isVolatile(mods))
    		toReturn += "volatile ";
    	
    	if(Modifier.isSynchronized(mods))
    		toReturn += "synchronized ";
    	
    	if(Modifier.isNative(mods))
    		toReturn += "native ";
    	
    	if(Modifier.isStrict(mods))
    		toReturn += "strict ";
    	
    	return toReturn;
    }

}
