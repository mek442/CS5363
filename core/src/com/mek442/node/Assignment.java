package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class Assignment {

	private TokenWord mIdent;
	private TokenWord mAsgn;
	private Assignment1 mParseAssignment1;

	public Assignment(TokenWord pIdent, TokenWord pAsgn, Assignment1 pParseAssignment1) {
		mIdent = pIdent;
		mAsgn = pAsgn;
		mParseAssignment1 = pParseAssignment1;
		
	}
	
	public TokenWord getAsgn() {
		return mAsgn;
	}
	
	public TokenWord getIdent() {
		return mIdent;
	}
	public Assignment1 getParseAssignment1() {
		return mParseAssignment1;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		int parent =pCounter.getCount();
		buffer.append("\n");
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Assignment);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mIdent!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mIdent.getWord().getValue()+" : "+ mIdent.getIdentifier());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mAsgn!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mAsgn.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseAssignment1!=null){
			buffer.append(mParseAssignment1.getOutPut(pCounter,parent));
		}
		
		
		
		return buffer.toString();
		
	}


}
