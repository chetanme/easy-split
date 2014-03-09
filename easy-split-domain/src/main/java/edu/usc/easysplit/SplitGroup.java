package edu.usc.easysplit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class SplitGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private int groupId;
	
	private String groupName;
	
	private String owner;
	
	private Map<String, Double> stats = new HashMap<String, Double>();

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Map<String, Double> getStats() {
		return stats;
	}

	public void setStats(Map<String, Double> stats) {
		this.stats = stats;
	}
}