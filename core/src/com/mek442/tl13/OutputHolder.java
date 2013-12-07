package com.mek442.tl13;

public class OutputHolder {

	String AST = "";
	String ILOC ="";
	String CfgAfterPhi ="";
	String CfgAfterRenaming ="";
	String CfgOptimized ="";
	String CFGRemovePhi ="";
	String MIPS="";
	String OptimiseMIPS="";
	public String getAST() {
		return AST;
	}
	
	public String getILOC() {
		return ILOC;
	}
	
	public void setAST(String pAST) {
		AST = pAST;
	}
	
	public void setILOC(String pILOC) {
		ILOC = pILOC;
	}
	
	public String getMIPS() {
		return MIPS;
	}
	
	public void setMIPS(String pMIPS) {
		MIPS = pMIPS;
	}
	
	public void setOptimiseMIPS(String pOptimiseMIPS) {
		OptimiseMIPS = pOptimiseMIPS;
	}
	
	public String getOptimiseMIPS() {
		return OptimiseMIPS;
	}
	
	public void setCfgAfterPhi(String pCfgAfterPhi) {
		CfgAfterPhi = pCfgAfterPhi;
	}
	
	public String getCfgAfterPhi() {
		return CfgAfterPhi;
	}
	
	public void setCfgAfterRenaming(String pCfgAfterRenaming) {
		CfgAfterRenaming = pCfgAfterRenaming;
	}
	
	public String getCfgAfterRenaming() {
		return CfgAfterRenaming;
	}
	
	public void setCfgOptimized(String pCfgOptimized) {
		CfgOptimized = pCfgOptimized;
	}
	
	public void setCFGRemovePhi(String pCFGRemovePhi) {
		CFGRemovePhi = pCFGRemovePhi;
	}
	
	public String getCfgOptimized() {
		return CfgOptimized;
	}
	
	public String getCFGRemovePhi() {
		return CFGRemovePhi;
	}
}
