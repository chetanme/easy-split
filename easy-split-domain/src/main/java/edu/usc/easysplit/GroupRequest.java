package edu.usc.easysplit;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class GroupRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int groupId;
	
	private String owner;
	
	private String groupName;
	
	private String memberName;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}