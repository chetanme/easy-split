package edu.usc.easysplit;

import java.io.Serializable;

public class GroupResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SplitGroup thisGroup;

	private boolean success;
	
	private String failMsg;

	public SplitGroup getThisGroup() {
		return thisGroup;
	}

	public void setThisGroup(SplitGroup thisGroup) {
		this.thisGroup = thisGroup;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}
}