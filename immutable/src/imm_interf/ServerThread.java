package imm_interf;


//import java.io.ByteArrayOutputStream;
import static java.lang.System.err;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerThread extends Thread {

	private final static Map<Class<?>, Object> defaultValues = 
			new HashMap<Class<?>, Object>();
	static
	{
		defaultValues.put(String.class, "");
		defaultValues.put(Integer.class, 0);
		defaultValues.put(int.class, 0);
		defaultValues.put(Long.class, 0L);
		defaultValues.put(long.class, 0L);
		defaultValues.put(Character.class, '\0');
		defaultValues.put(char.class, '\0');
		// etc
	}

	final static int SPORT = 4000;
	final static int DSPORT = 4001;
	final static int DPORT = 4002;
	final static String DADDRESS = "228.5.6.7";
	public ServerSocket server;
	public Socket client;

	public DatagramSocket dSocket;
	public InetAddress group;

	public ObjectInputStream in;
	protected ObjectOutputStream out;
	public Object received,receivedClone;

	private Class<?> c;
	private static String whyNotImmutable;


	ServerThread(Socket client, InetAddress group, DatagramSocket dSocket) {
		this.client = client;
		this.group = group;
		this.dSocket = dSocket;
	}

	public boolean validate(List<Immutable> coupleClone) throws InstantiationException, IllegalAccessException
	{
		if (coupleClone.size() != 2) 
		{
			whyNotImmutable= " ERRORE! IL VALIDATOR VUOLE UNA COPPIA DI OGGETTI UGUALI";
			return false;
		}
		Object o = coupleClone.get(0);
		Object oClone = coupleClone.get(1);
		if (! o.equals(oClone))  
		{
			whyNotImmutable= " ERRORE! IL VALIDATOR VUOLE UNA COPPIA DI OGGETTI UGUALI";
			return false;
		}

		c = o.getClass();
		System.out.format("Class:%n  %s%n%n", c.getCanonicalName());
		//------------------------------------------------------------------------
		Package p = c.getPackage();
		System.out.format("Package:%n  %s%n%n",
				(p != null ? p.getName() : "-- No Package --"));
		//------------------------------------------------------------------------
		System.out.format("Modifiers:%n  %s%n%n", Modifier.toString(c.getModifiers()));
		if (!Modifier.toString(c.getModifiers()).contains("final")) 
		{
			whyNotImmutable="the class is not declared as final";
			return false;
		}		
		//------------------------------------------------------------------------
		System.out.format("Type Parameters:%n");
		TypeVariable[] tv = c.getTypeParameters();
		if (tv.length != 0) {
			System.out.format("  ");
			for (TypeVariable t : tv)
				System.out.format("%s ", t.getName());
			System.out.format("%n%n");
		} else {
			System.out.format("  -- No Type Parameters --%n%n");
		}
		//------------------------------------------------------------------------
		System.out.format("Implemented Interfaces:%n");
		boolean implementsImmutable = false;
		Type[] intfs = c.getGenericInterfaces();
		if (intfs.length != 0) {
			for (Type intf : intfs)
			{
				System.out.format("  %s%n", intf.toString());
				if(intf.toString().contains("Immutable")) implementsImmutable=true;
			}
			System.out.format("%n");
		} else {
			System.out.format("  -- No Implemented Interfaces --%n%n");
		}
		if (!implementsImmutable) 
		{
			whyNotImmutable = "it doesn't implement the interface Immutable";
			return false; 
		}
		//------------------------------------------------------------------------
		System.out.format("Inheritance Path:%n");
		List<Class> l = new ArrayList<Class>();
		printAncestor(c, l);
		if (l.size() != 0) {
			for (Class<?> cl : l)
				System.out.format("  %s%n", cl.getCanonicalName());
			System.out.format("%n");
		} else {
			System.out.format("  -- No Super Classes --%n%n");
		}
		//------------------------------------------------------------------------
		System.out.format("Annotations:%n");
		Annotation[] ann = c.getAnnotations();
		if (ann.length != 0) {
			for (Annotation a : ann)
				System.out.format("  %s%n", a.toString());
			System.out.format("%n");
		} else {
			System.out.format("  -- No Annotations --%n%n");
		}
		//------------------------------------------------------------------------
		if (!printMembers(c.getDeclaredConstructors(), "Constuctors")) return false;
		//------------------------------------------------------------------------
		if (!printMembers(c.getDeclaredFields(), "Fields")) return false;
		//------------------------------------------------------------------------
		if (!printMembers(c.getDeclaredMethods(), "Methods")) return false;
		//------------------------------------------------------------------------
		printClasses(c);
		//------------------------------------------------------------------------
		System.out.format("lancio tutti i metodi pubblici dell'oggetto...%n%n");
		Method[] allMethods = c.getDeclaredMethods();
		for (Method m : allMethods) 	//per ogni metodo
		{
			String mname = m.getName();
			//			List<Class<?>> args = new ArrayList<Class<?>>();
			//parametri del metodo
			Class<?>[] argTypes = m.getParameterTypes();  
			//			for (Class<?> arg : argTypes)  
			//			{
			//				args.add(arg);
			//			}
			if (Modifier.isPublic(m.getModifiers())) //se si tratta di un metodo pubblico...
			{
				//				Object[] arguments = args.toArray();
				Object[] arguments = new Object[argTypes.length];
				for (int i=0; i< argTypes.length; i++) //per ogni argomento .. lo inizializzo
				{
					Class<?> argom = argTypes[i];
					if (!argom.isPrimitive())
					{
						try {
							Constructor ctor = argom.getConstructor();
							try {
								arguments[i]= ctor.newInstance();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}

						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							err.println("non esiste il costruttore senza argomenti per il parametro " + i + "del metodo " + mname );
						}
					} else //se è primitivo
					{
						arguments[i]= defaultValues.get(argom);

					}
				}
				try 
				{
					System.out.println("invocation of method" + mname);
					m.setAccessible(true);
					m.invoke(o, arguments );			
					// Handle any exceptions thrown by method to be invoked.
				} catch (InvocationTargetException x) {
					Throwable cause = x.getCause();
					err.format("invocation of %s failed: %s%n",
							mname, cause.getMessage());
				} catch (IllegalArgumentException x) {
					err.format("invocation of %s failed: argomenti illegali%n", mname);				
				}

			}

		}


		//------------------------------------------------------------------------
		System.out.format("controllo che i campi non siano cambiati...%n%n");

		/*
		 * <debug>
		 */
		Field[] flds = c.getFields();
		for	(Field f: flds)
		{
			System.out.println("il valore del campo pubblico " + f + " dell'oggetto modificato è : " +  f.get(o));
			System.out.println("mentre quello dell'oggetto originale era : " +  f.get(oClone));
		}
		/*
		 *  <\debug>
		 */


		if (! o.equals(oClone))  
		{
			whyNotImmutable= "some public methods can modify the object's fields";
			return false;
		}
		else
			return true;
	}



	private static void printAncestor(Class<?> c, List<Class> l) {
		Class<?> ancestor = c.getSuperclass();
		if (ancestor != null) {
			l.add(ancestor);
			printAncestor(ancestor, l);
		}
	}

	private static boolean printMembers(Member[] mbrs, String s) {
		System.out.format("%s:%n", s);
		for (Member mbr : mbrs) {
			if (mbr instanceof Field)
			{
				System.out.format("  %s%n", ((Field)mbr).toGenericString());
				//				if (!  
				//						(((Field)mbr).toGenericString().contains("private") && 
				//								(((Field)mbr).toGenericString().contains("final")))
				//						)	
				//				{
				//					whyNotImmutable="every field must be declared as PRIVATE and FINAL";
				//					return false;
				//				}
			}
			else if (mbr instanceof Constructor)
				System.out.format("  %s%n", ((Constructor)mbr).toGenericString());
			else if (mbr instanceof Method)
				System.out.format("  %s%n", ((Method)mbr).toGenericString());
		}
		if (mbrs.length == 0)
			System.out.format("  -- No %s --%n", s);
		System.out.format("%n");

		return true;
	}

	private static void printClasses(Class<?> c) {
		System.out.format("Classes:%n");
		Class<?>[] clss = c.getClasses();
		for (Class<?> cls : clss)
			System.out.format("  %s%n", cls.getCanonicalName());
		if (clss.length == 0)
			System.out.format("  -- No member interfaces, classes, or enums --%n");
		System.out.format("%n");
	}


	public void run() {
		try {
			in = new ObjectInputStream(client.getInputStream());
			int numberOfObjectToReceive = 2;
			while (numberOfObjectToReceive-- != 0) {
				received = in.readObject();
				receivedClone = in.readObject();
				List<Immutable> l = new ArrayList<Immutable>();
				l.add((Immutable)received);
				l.add((Immutable)receivedClone);
//				if (received instanceof List<?> && 
//						((List<?>) received).get(0) instanceof Immutable &&
//						((List<?>) received).get(1) instanceof Immutable) 
//				{
//					received = (List<Immutable>) received;
//				}
//				else {
//					whyNotImmutable = "il server deve ricevere una coppia di oggetti uguali contenuti in una List<Immutable>";
//					return;
//				}
				try {					
					boolean result = this.validate((List<Immutable>)l);	
					System.out.println("the object validation resutl is : " + result + "!" );
					if (result==false)
						System.out.println("for the following reason: "+ whyNotImmutable);

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
		} catch (ClassNotFoundException e1) {
		}
	}
}
