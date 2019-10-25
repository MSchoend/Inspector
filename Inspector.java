
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
    	Method methods[] = clazz.getMethods();
    	for(Method m : methods) {
    		outputBuilder.append(padding + " METHOD - ");
    		outputBuilder.append(interpretModifiers(m.getModifiers()));
    		outputBuilder.append(m.getReturnType() + " ");
    		outputBuilder.append(m.getName() + "(");
    		
    		Parameter params[] = m.getParameters();
    		for(Parameter p : params) {
    			if(p.getType().getComponentType() == null) {
    				outputBuilder.append(p.getType() + " " + p.getName() + ", ");
    			} else {
    				outputBuilder.append(p.getType().getComponentType() + "[] " +p.getName() + ", ");
    			}
    		}
    		outputBuilder.append(")\n" + padding + " -EXCEPTIONS: ");
    		
    		Class<?>[] exceps = m.getExceptionTypes();
    		for(Class<?> c : exceps) {
    			outputBuilder.append(c.getName() + " ");
    		}
    		outputBuilder.append("\n");
    	}
    		//name
    		//Exceptions
    		//parameters + types
    		//return type
    		//modifiers
    	//Fields
    	Field fields[] = clazz.getFields();
    	for(Field f : fields){
    		outputBuilder.append(" FIELD - ");
    		outputBuilder.append(interpretModifiers(f.getModifiers()));
    		outputBuilder.append(f.getName() + " = ");
    		
    		Class<?> t = f.getType();
    		
    		if(t.isArray()){
    			try {
					outputBuilder.append(processArray(f, obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
    		} else if (/*is a class*/false){
    			
    		} else { //is a primitive
    			try {
					outputBuilder.append(f.get(obj).toString());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
    		}
    	}
    		//name
    		//type
    		//modifiers
    		//current value
    		//If recursive set to true, recurse into non-primitive fields
    		//If recursive false, print out class + identity hash code
    	
    	System.out.println(outputBuilder.toString());
    }
    
    private Object processArray(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder output = new StringBuilder();
    	
		Class<?> t = f.getType();
		Class compType = t.getComponentType();
		Object arr = f.get(obj);
		int length = Array.getLength(arr);
		
		if(compType.equals(Integer.TYPE)) {
			
		}else if (compType.equals(Byte.TYPE)) {
			
		}else if (compType.equals(Short.TYPE)) {
			
		} else if (compType.equals(Long.TYPE)) {
			
		}else if (compType.equals(Character.TYPE)) {
			
		} else if (compType.equals(Boolean.TYPE)) {
			
		} else if (compType.equals(Double.TYPE)) {
			
		} else if (compType.equals(Float.TYPE)) {
			
		} else { //Object Array
			
		}
    	
		return null;
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
