/**
 * 
 */
package imm_interf;


import java.io.Serializable;
import java.util.List;

/**
 * This is an Interface to be implemented by every object claiming to be immutable to the server
 * 
 * @author lorenzo rotteglia
 *
 */
public interface Immutable extends Serializable{

	/**
	 * This function must be contained in every Immutable object and should just return the Immutable object itself
	 * 
	 * @return <code> this </code>
	 */
	public Immutable validateMyself ();

}
