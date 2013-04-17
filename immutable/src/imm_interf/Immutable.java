/**
 * 
 */
package imm_interf;


import java.io.Serializable;
import java.util.List;

/**
 * @author lorenzo rotteglia
 *
 */
public interface Immutable extends Serializable{
	
	/*
	 * - the client instantiate a class which implements the interface Immutable -
	 * - the implementation of the function validateMyself must return a List<Immutable> object containing two copies of the object itself -
	 * - the client then sends the object to validate to the server -
	 * - the server then accept the client's object and pass it to a thread - 
	 * - the serverThread checks using reflection and processors whether the object is really immutable - 
	 * 
	 * */
	
	List<Immutable> validateMyself ();
	
}
