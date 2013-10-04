package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class Declaration {
private TokenWord mTokenVar;
private TokenWord mTokenIdent;
private TokenWord mTokenAs;
private Type mType;
private TokenWord mTokenSc;
private Declaration mDeclaration;
	
	public Declaration(TokenWord pTokenVar, TokenWord pTokenID, TokenWord pTokenAs, Type pParseType, TokenWord pTokenSc, Declaration pDeclaration) {
		mTokenVar = pTokenVar;
		mTokenIdent = pTokenID;
		mTokenAs = pTokenAs;
		mType = pParseType;
		mTokenSc = pTokenSc;
		mDeclaration = pDeclaration;
		
		
	}

	public TokenWord getTokenVar() {
		return mTokenVar;
	}

	public TokenWord getTokenIdent() {
		return mTokenIdent;
	}

	public TokenWord getTokenAs() {
		return mTokenAs;
	}

	public Type getType() {
		return mType;
	}

	public TokenWord getTokenSc() {
		return mTokenSc;
	}

	public Declaration getDeclaration() {
		return mDeclaration;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Declaration);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mTokenVar!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mTokenVar.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mTokenIdent!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mTokenIdent.getWord().getValue()+" : "+ mTokenIdent.getIdentifier());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mTokenAs!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mTokenAs.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mType!=null){
			buffer.append(mType.getOutPut(pCounter,parent));
		}
		
		if(mTokenSc!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mTokenSc.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		if(mDeclaration!=null){
			buffer.append(mDeclaration.getOutPut(pCounter,parent));
		}
		
		if(mTokenVar==null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", " empty");
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);	
		}
		
		return buffer.toString();
		
	}
	

}
