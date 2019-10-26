
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

		if (clazz.isArray()) {
			Object objArr[] = (Object[]) obj;
			System.out.println(clazz.getTypeName() + "\nContents: ");
			printArray(objArr, recursive, depth);
			System.out.println("\nLength: " + objArr.length);

		} else {
			String padding = "";
			for (int i = 0; i < depth; i++) {
				padding += "\t";
			}
			System.out.print(padding + clazz.getSimpleName() + ":\n");
			// Superclass (Recurse into superclass)
			Class<?> superclass = clazz.getSuperclass();
			if (superclass != null && !superclass.getSimpleName().equals(clazz.getSimpleName())) {
				System.out.println(padding + "Extends:");
				inspectClass(superclass, obj, recursive, depth + 1);
			}
			// Interfaces (Recurse)
			Class<?> interfaces[] = clazz.getInterfaces();
			for (Class<?> c : interfaces) {
				System.out.println(padding + "Implements:");
				inspectClass(c, obj, recursive, depth + 1);
			}
			Constructor cons[] = clazz.getConstructors();
			for (Constructor c : cons) {
				System.out.print(padding + " CONSTRUCTOR - ");
				System.out.print(interpretModifiers(c.getModifiers()));
				System.out.print(c.getName() + "(");

				Parameter params[] = c.getParameters();
				for (Parameter p : params) {
					Class<?> pt = p.getType().getComponentType();
					if (p.getType().getComponentType() == null) {
						System.out.print(p.getType().getSimpleName() + " " + p.getName() + ", ");
					} else {
						if (pt.isPrimitive()) {
							System.out.print(pt + "[] " + p.getName() + ", ");
						} else {
							System.out.print(pt.getSimpleName() + "[] " + p.getName() + ", ");
						}
					}
				}
				System.out.print(")\n");
			}
			Method methods[] = clazz.getDeclaredMethods();
			for (Method m : methods) {
				System.out.print(padding + " METHOD - ");
				System.out.print(interpretModifiers(m.getModifiers()));
				System.out.print(m.getReturnType() + " ");
				System.out.print(m.getName() + "(");

				Parameter params[] = m.getParameters();
				for (Parameter p : params) {
					Class<?> pt = p.getType().getComponentType();
					if (p.getType().getComponentType() == null) {
						System.out.print(p.getType().getSimpleName() + " " + p.getName() + ", ");
					} else {
						if (pt.isPrimitive()) {
							System.out.print(pt + "[] " + p.getName() + ", ");
						} else {
							System.out.print(pt.getSimpleName() + "[] " + p.getName() + ", ");
						}
					}
				}
				System.out.print(")");

				Class<?>[] exceps = m.getExceptionTypes();
				if (exceps.length > 0) {
					System.out.print("\n" + padding + " -EXCEPTIONS: ");
				}
				for (Class<?> c : exceps) {
					System.out.print(c.getSimpleName() + " ");
				}
				System.out.print("\n");
			}
			Field fields[] = clazz.getDeclaredFields();
			for (Field f : fields) {
				System.out.print(padding + " FIELD - ");
				System.out.print(interpretModifiers(f.getModifiers()));

				Class<?> t = f.getType();

				System.out.print(t.getTypeName() + " " + f.getName() + " = ");

				if (Modifier.isPrivate(f.getModifiers())) {
					f.setAccessible(true);
				}

				Class<?> compType = t.getComponentType();
				try {
					if (t.isArray()) {

						Object arr = f.get(obj);

						if (compType.equals(Integer.TYPE)) {
							int iarr[] = (int[]) arr;
							System.out.print(Arrays.toString(iarr));
						} else if (compType.equals(Byte.TYPE)) {
							byte barr[] = (byte[]) arr;
							System.out.print(Arrays.toString(barr));
						} else if (compType.equals(Short.TYPE)) {
							short sarr[] = (short[]) arr;
							System.out.print(Arrays.toString(sarr));
						} else if (compType.equals(Long.TYPE)) {
							long larr[] = (long[]) arr;
							System.out.print(Arrays.toString(larr));
						} else if (compType.equals(Character.TYPE)) {
							char charr[] = (char[]) arr;
							System.out.print(Arrays.toString(charr));
						} else if (compType.equals(Boolean.TYPE)) {
							boolean barr[] = (boolean[]) arr;
							System.out.print(Arrays.toString(barr));
						} else if (compType.equals(Double.TYPE)) {
							double darr[] = (double[]) arr;
							System.out.print(Arrays.toString(darr));
						} else if (compType.equals(Float.TYPE)) {
							float farr[] = (float[]) arr;
							System.out.print(Arrays.toString(farr));
						} else { // Object Array TODO check and recurse
							Object objArr[] = (Object[]) arr;
							System.out.print(Arrays.toString(objArr));
						}
					} else if (!t.isPrimitive()) {
						Object temp = f.get(obj);
						if (temp == null) {
							System.out.print("null ");
						} else if (recursive) {
							System.out.println();
							inspectClass(temp.getClass(), temp, recursive, depth + 1);
						} else {
							System.out.print(temp.getClass().getSimpleName() + ": "
									+ Integer.toHexString(System.identityHashCode(temp)));
						}
					} else { // is a primitive
						System.out.print(f.get(obj).toString());
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				System.out.print("\n");
			}
			// name
			// type
			// modifiers
			// current value
			// If recursive set to true, recurse into non-primitive fields
			// If recursive false, print out class + identity hash code
		}

//		System.out.println(outputBuilder.toString());
	}

	private void printArray(Object[] objArr, boolean recursive, int depth) {

		for (Object o : objArr) {
			if (o == null) {
				System.out.print("null ");
			} else {
				Class c = o.getClass();
				if(c.isPrimitive()) {
					System.out.print(o + " ");
				} else {
					inspectClass(c, o, recursive, depth);
				}
			}
		}

	}

	private String interpretModifiers(int mods) {
		String toReturn = " ";

		if (Modifier.isPublic(mods))
			toReturn += "public ";
		else if (Modifier.isPrivate(mods))
			toReturn += "private ";
		else if (Modifier.isProtected(mods))
			toReturn += "protected ";

		if (Modifier.isAbstract(mods))
			toReturn += "abstract ";

		if (Modifier.isStatic(mods))
			toReturn += "static ";

		if (Modifier.isFinal(mods))
			toReturn += "final ";

		if (Modifier.isTransient(mods))
			toReturn += "transient ";

		if (Modifier.isVolatile(mods))
			toReturn += "volatile ";

		if (Modifier.isSynchronized(mods))
			toReturn += "synchronized ";

		if (Modifier.isNative(mods))
			toReturn += "native ";

		if (Modifier.isStrict(mods))
			toReturn += "strict ";

		return toReturn;
	}

}
