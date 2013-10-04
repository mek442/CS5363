package com.mek442.node;

public class Term {

	private Factor mParseFactor;
	private Term1 mParseTerm1;

	public Term(Factor pParseFactor, Term1 pParseTerm1) {
		mParseFactor = pParseFactor;
		mParseTerm1 = pParseTerm1;
		
	}
	
	public Factor getParseFactor() {
		return mParseFactor;
	}
	
	public Term1 getParseTerm1() {
		return mParseTerm1;
	}

	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Term);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mParseFactor!=null){
			buffer.append(mParseFactor.getOutPut( pCounter,parent));
		}
		
		if(mParseTerm1!=null){
			buffer.append(mParseTerm1.getOutPut(pCounter, parent));	
		}
		
		return buffer.toString();
		
	}
}
