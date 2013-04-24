package imm_obj;

import imm_interf.Immutable;

/**
 * This is just a random not immutable object with a simple setter method (which is of course prohibited in immutable objects)
 * 
 * @author Lorenzo Rotteglia
 *
 */
public final class NotImmutableImpl implements Immutable {
	
	private int a;
		
	public void setInt(int val) { a = val;}
	
	
	public NotImmutableImpl(int val) { this.a=val;}

	/**
	 * (non-Javadoc)
	 * @see imm_interf.Immutable#validateMyself()
	 */
	@Override
	public Immutable validateMyself() {
		return this;
	}

}
