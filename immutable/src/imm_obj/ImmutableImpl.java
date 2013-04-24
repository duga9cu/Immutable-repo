/**
 * 
 */
package imm_obj;

import java.util.ArrayList;
import java.util.List;
import imm_interf.*;

/**
 * This class is just an example of an immutable object implemented with the builder pattern.
 * 
 * @author lorenzo rotteglia
 * 
 */
public final class ImmutableImpl implements Immutable {

	private final int firstRequiredMember;
	private final int secondRequiredMember;
	private final int thirdMember;
	private final int fourthMember;
	private final int fifthMember;
	private final int sixthMember;
	
	public static class Builder {
		// Required parameters
		private final int firstRequiredMember;
		private final int secondRequiredMember;
		// Optional parameters - initialized to default values
		private int thirdMember = 0;
		private int fourthMember = 0;
		private int fifthMember = 0;
		private int sixthMember = 0;

		public Builder() {
			this.firstRequiredMember=0;
			this.secondRequiredMember=0;
		}
		
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

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * imm_interf.Immutable#validateMyself()
	 */
	@Override
	public Immutable validateMyself() {
		
		return this;
	}
}
