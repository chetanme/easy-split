package edu.usc.easysplit;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class AddBillResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SplitGroup updatedGroup;
	
	private boolean success;
	
	private String failMsg;

	public SplitGroup getUpdatedGroup() {
		return updatedGroup;
	}

	public void setUpdatedGroup(SplitGroup updatedGroup) {
		this.updatedGroup = updatedGroup;
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