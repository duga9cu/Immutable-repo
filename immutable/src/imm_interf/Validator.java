///**
// * 
// */
//package imm_interf;
//
//import static java.lang.System.out;
//import imm_obj.ImmutableImpl;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Member;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.lang.reflect.Type;
//import java.lang.reflect.TypeVariable;
//import java.util.ArrayList;
//import java.util.List;
//import static java.lang.System.err;
//
//
///**
// * @author lorenzo rotteglia
// *
// */
//
////import it.lorenzo.rotteglia.immutable_interface.Immutable;
//
//public class Validator {
//
//	public boolean validate(List<Immutable> coupleClone) throws InstantiationException, IllegalAccessException
//	{
//
//		if (coupleClone.size() != 2) 
//		{
//			whyNotImmutable= " ERRORE! IL VALIDATOR VUOLE UNA COPPIA DI OGGETTI UGUALI";
//			return false;
//		}
//		Object o = coupleClone.get(0);
//		Object oClone = coupleClone.get(1);
//		if (! o.equals(oClone))  
//		{
//			whyNotImmutable= " ERRORE! IL VALIDATOR VUOLE UNA COPPIA DI OGGETTI UGUALI";
//			return false;
//		}
//
//		c = o.getClass();
//		out.format("Class:%n  %s%n%n", c.getCanonicalName());
//		//------------------------------------------------------------------------
//		Package p = c.getPackage();
//		out.format("Package:%n  %s%n%n",
//				(p != null ? p.getName() : "-- No Package --"));
//		//------------------------------------------------------------------------
//		out.format("Modifiers:%n  %s%n%n", Modifier.toString(c.getModifiers()));
//		if (!Modifier.toString(c.getModifiers()).contains("final")) 
//		{
//			whyNotImmutable="the class is not declared as final";
//			return false;
//		}		
//		//------------------------------------------------------------------------
//		out.format("Type Parameters:%n");
//		TypeVariable[] tv = c.getTypeParameters();
//		if (tv.length != 0) {
//			out.format("  ");
//			for (TypeVariable t : tv)
//				out.format("%s ", t.getName());
//			out.format("%n%n");
//		} else {
//			out.format("  -- No Type Parameters --%n%n");
//		}
//		//------------------------------------------------------------------------
//		out.format("Implemented Interfaces:%n");
//		boolean implementsImmutable = false;
//		Type[] intfs = c.getGenericInterfaces();
//		if (intfs.length != 0) {
//			for (Type intf : intfs)
//			{
//				out.format("  %s%n", intf.toString());
//				if(intf.toString().contains("Immutable")) implementsImmutable=true;
//			}
//			out.format("%n");
//		} else {
//			out.format("  -- No Implemented Interfaces --%n%n");
//		}
//		if (!implementsImmutable) 
//		{
//			whyNotImmutable = "it doesn't implement the interface Immutable";
//			return false; 
//		}
//		//------------------------------------------------------------------------
//		out.format("Inheritance Path:%n");
//		List<Class> l = new ArrayList<Class>();
//		printAncestor(c, l);
//		if (l.size() != 0) {
//			for (Class<?> cl : l)
//				out.format("  %s%n", cl.getCanonicalName());
//			out.format("%n");
//		} else {
//			out.format("  -- No Super Classes --%n%n");
//		}
//		//------------------------------------------------------------------------
//		out.format("Annotations:%n");
//		Annotation[] ann = c.getAnnotations();
//		if (ann.length != 0) {
//			for (Annotation a : ann)
//				out.format("  %s%n", a.toString());
//			out.format("%n");
//		} else {
//			out.format("  -- No Annotations --%n%n");
//		}
//		//------------------------------------------------------------------------
//		if (!printMembers(c.getDeclaredConstructors(), "Constuctors")) return false;
//		//------------------------------------------------------------------------
//		if (!printMembers(c.getDeclaredFields(), "Fields")) return false;
//		//------------------------------------------------------------------------
//		if (!printMembers(c.getDeclaredMethods(), "Methods")) return false;
//		//------------------------------------------------------------------------
//		printClasses(c);
//		//------------------------------------------------------------------------
//		//		Object clone = DeepCopy.copy(o);	//saved for later comparison
//		//		if (!clone.equals(o))
//		////		if (clone != o)
//		//		{
//		//			whyNotImmutable = "perchè non sono uguali?????????????????";
//		//			return false;
//		//		}
//
//		//		out.format("...copio tutti i campi dell'oggetto..%n%n");
//
//
//		//------------------------------------------------------------------------
//		out.format("lancio tutti i metodi pubblici dell'oggetto...%n%n");
//		Method[] allMethods = c.getDeclaredMethods();
//		for (Method m : allMethods) 
//		{
//			String mname = m.getName();
//			//			Object returned =  m.getReturnType(); /* da chiarire */
//			List<Class<?>> args = new ArrayList<Class<?>>(); 
//			Class<?>[] argTypes = m.getParameterTypes();
//			for (Class<?> arg : argTypes) 
//			{
//				args.add(arg.getClass());
//			}
//			if (Modifier.isPublic(m.getModifiers()))
//			{
//				m.setAccessible(true);
//				Object[] arguments = args.toArray();
//				try 
//				{
//					/*returned =*/ m.invoke(o, arguments );			
//					// Handle any exceptions thrown by method to be invoked.
//				} catch (InvocationTargetException x) {
//					Throwable cause = x.getCause();
//					err.format("invocation of %s failed: %s%n",
//							mname, cause.getMessage());
//				}
//			}
//
//		}
//
//
//		//------------------------------------------------------------------------
//		out.format("controllo che i campi non siano cambiati...%n%n");
//		//		Field[] flds = c.getDeclaredFields();
//		//		for (Field f : flds) 
//		//		{
//		//			if (fieldsValues.get(f) != f.get(o));
//		//			{
//		//				whyNotImmutable = "some public methods can modify the object's fields";
//		//				return false;
//		//			} 
//		//		}
//
//		if (! o.equals(oClone))  
//		{
//			whyNotImmutable= "some public methods can modify the object's fields";
//			return false;
//		}
//		else
//			return true;
//	}
//
//
//
//	private static void printAncestor(Class<?> c, List<Class> l) {
//		Class<?> ancestor = c.getSuperclass();
//		if (ancestor != null) {
//			l.add(ancestor);
//			printAncestor(ancestor, l);
//		}
//	}
//
//	private static boolean printMembers(Member[] mbrs, String s) {
//		out.format("%s:%n", s);
//		for (Member mbr : mbrs) {
//			if (mbr instanceof Field)
//			{
//				out.format("  %s%n", ((Field)mbr).toGenericString());
//				if (!  
//						(((Field)mbr).toGenericString().contains("private") && 
//								(((Field)mbr).toGenericString().contains("final")))
//						)	
//				{
//					whyNotImmutable="every field must be declared as PRIVATE and FINAL";
//					return false;
//				}
//			}
//			else if (mbr instanceof Constructor)
//				out.format("  %s%n", ((Constructor)mbr).toGenericString());
//			else if (mbr instanceof Method)
//				out.format("  %s%n", ((Method)mbr).toGenericString());
//		}
//		if (mbrs.length == 0)
//			out.format("  -- No %s --%n", s);
//		out.format("%n");
//
//		return true;
//	}
//
//	private static void printClasses(Class<?> c) {
//		out.format("Classes:%n");
//		Class<?>[] clss = c.getClasses();
//		for (Class<?> cls : clss)
//			out.format("  %s%n", cls.getCanonicalName());
//		if (clss.length == 0)
//			out.format("  -- No member interfaces, classes, or enums --%n");
//		out.format("%n");
//	}
//
//	private Class<?> c;
//	private static String whyNotImmutable;
//	//	private Map<Field,Object> fieldsValues = new HashMap<Field,Object>();
//
//
//
//	/**
//	 * @param args
//	 * @throws IllegalAccessException 
//	 * @throws InstantiationException 
//	 */
//	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//
//		//create an Immutable object (just to validate it)
//		Immutable clientObj = new ImmutableImpl.Builder(240, 8).
//				thirdMember(100).fourthMember(35).sixthMember(34).build();
//		//get the object to validate from the client
//		List<Immutable> objectToValidate = clientObj.validateMyself();
//		//create the validator 
//		Validator validator = new Validator();
//		boolean result = validator.validate(objectToValidate);
//		out.println("the object validation resutl is : " + result + "!" );
//		if (result==false)
//			out.println("for the following reason: "+ whyNotImmutable);
//	}
//
//}
