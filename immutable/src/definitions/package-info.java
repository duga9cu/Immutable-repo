/**
 *  The sole purpose of this Package is to provide a static value that can decide which mode of functioning to use:
 *  
 * 	Immutable 
 * 	or
 * 	NOT Immutable
 * 
 * ________________________________________________________________________________________________________________
 * 				TO LAUNCH THE SOFTWARE:
 * 
 * 		- select the mode of functioning in Definitions.java (true or false)
 * 		- launch Server.java
 * 		- launch Client.java
 * 		- wait for the results in the shell
 * ________________________________________________________________________________________________________________ 
 * 
 *  			FUNCTIONING OF THE SOFTWARE:
 * 		- the client instantiate a class which implements the interface Immutable -
 * 		- the implementation of the function validateMyself() must return the object itself -
 * 		- the client then sends the object to validate to the server -
 * 		- the server then accept the client's object and pass it to a thread - 
 * 		- the serverThread checks using reflection and processors whether the object is really immutable - 
 * ________________________________________________________________________________________________________________ 

 * 
 */
/**
 * @author Lorenzo Rotteglia
 *
 */
package definitions;