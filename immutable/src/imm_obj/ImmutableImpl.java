/**
 * 
 */
package imm_obj;

import java.util.ArrayList;
import java.util.List;
import imm_interf.*;

/**
 * @author lorenzo rotteglia
 * 
 */
public final class ImmutableImpl implements Immutable {

	/**
	 * 
	 */
	private final int firstRequiredMember;
	private final int secondRequiredMember;
	private final int thirdMember;
	private final int fourthMember;
	private final int fifthMember;
	public  int sixthMember;
	
//	public DeepCopy d = new DeepCopy();
	public void setmember(int val) {sixthMember=val;}

	public static class Builder {
		// Required parameters
		private final int firstRequiredMember;
		private final int secondRequiredMember;
		// Optional parameters - initialized to default values
		private int thirdMember = 0;
		private int fourthMember = 0;
		private int fifthMember = 0;
		private int sixthMember = 0;

		public Builder(int firstRequiredMember, int secondRequiredMember) {
			this.firstRequiredMember = firstRequiredMember;
			this.secondRequiredMember = secondRequiredMember;
		}

		public Builder thirdMember(int val) {
			thirdMember = val;
			return this;
		}

		public Builder fourthMember(int val) {
			fourthMember = val;
			return this;
		}

		public Builder fifthMember(int val) {
			fifthMember = val;
			return this;
		}

		public Builder sixthMember(int val) {
			sixthMember = val;
			return this;
		}

		public ImmutableImpl build() {
			return new ImmutableImpl(this);
		}
	}

	//private constructor
	private ImmutableImpl(Builder builder) {
		firstRequiredMember = builder.firstRequiredMember;
		secondRequiredMember = builder.secondRequiredMember;
		thirdMember = builder.thirdMember;
		fourthMember = builder.fourthMember;
		fifthMember = builder.sixthMember;
		sixthMember = builder.fifthMember;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.lorenzo.rotteglia.immutable_interface.Immutable#validateMyself(java
	 * .lang.Object)
	 */
	@Override
	public List<Immutable> validateMyself() {
		List<Immutable> coupleCLone = new ArrayList<Immutable>();
		coupleCLone.add(this);
		coupleCLone.add(this);
		return coupleCLone;
	}
}
