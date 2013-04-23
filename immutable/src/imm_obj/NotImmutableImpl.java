package imm_obj;

import imm_interf.Immutable;


public final class NotImmutableImpl implements Immutable {
	
	private int a;
		
	public void setInt(int val) { a = val;}
	
	
	public NotImmutableImpl(int val) { this.a=val;}

	@Override
	public Immutable validateMyself() {
		// TODO Auto-generated method stub
		return this;
	}

}
