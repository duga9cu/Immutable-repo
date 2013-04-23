package imm_obj;

import imm_interf.Immutable;


public final class NotImmutableImpl implements Immutable {
	
	private int a;
	private Object o;
		
	public void set(int val) { a = val;}
	
	public NotImmutableImpl(int val, Object obj) { this.a=val; this.o=obj;}

	@Override
	public Immutable validateMyself() {
		// TODO Auto-generated method stub
		return this;
	}

}
