package edu.usc.easysplit.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.usc.easysplit.Constants;
import edu.usc.easysplit.GroupRequest;
import edu.usc.easysplit.GroupResponse;
import edu.usc.easysplit.SplitGroup;
import edu.usc.easysplit.SplitUser;
import edu.usc.easysplit.services.IGroupService;

@Service
public class GroupServiceImpl implements IGroupService {
	
	@Autowired
	private CacheManagerImpl cacheService;

	@Override
	public GroupResponse createGroup (GroupRequest request) {
		GroupResponse response = new GroupResponse();
		
		int lastGroupId = 0;
		if (this.cacheService.getValue(Constants.LAST_GROUP_ID) != null) {
			lastGroupId = Integer.parseInt(((String) this.cacheService.getValue(Constants.LAST_GROUP_ID)));
		}
		lastGroupId++;
		
		SplitGroup newGroup = new SplitGroup();
		newGroup.setGroupId(lastGroupId);
		newGroup.setGroupName(request.getGroupName());
		newGroup.setOwner(request.getOwner());
		
		Map<String, Double> stats = new HashMap<String, Double>();
		stats.put(request.getOwner(), 0.0);
		newGroup.setStats(stats);
		
		if (!this.cacheService.hasMapKey(Constants.ALL_GROUPS)) {
			Map<Integer, SplitGroup> allGroups = new HashMap<Integer, SplitGroup>();
			allGroups.put(0, newGroup);
			this.cacheService.storeMap(Constants.ALL_GROUPS, allGroups);
		} else {
			this.cacheService.storeValue(Constants.ALL_GROUPS, Integer.toString(lastGroupId), newGroup);
		}
		
		this.cacheService.storeValue(Constants.LAST_GROUP_ID, Integer.toString(lastGroupId));
		
		SplitUser owner = (SplitUser) this.cacheService.getValue(Constants.ALL_USERS, request.getOwner());
		owner.getPartOf().add(lastGroupId);
		this.cacheService.storeValue(Constants.ALL_USERS, request.getOwner(), owner);
		
		response.setThisGroup(newGroup);
		response.setSuccess(true);
		return response;
	}

	@Override
	public GroupResponse alterGroup(GroupRequest request) {
		GroupResponse response = new GroupResponse();
		if (request.getGroupId() != 0) {
			if (!this.cacheService.hasValueForKeys(Constants.ALL_GROUPS, Integer.toString(request.getGroupId()))) {
				response.setSuccess(false);
				response.setFailMsg("No group with this id");
			} if (!this.cacheService.hasValueForKeys(Constants.ALL_USERS, request.getMemberName())) {
				response.setSuccess(false);
				response.setFailMsg("No member with this name");
			} else {
				SplitGroup group = (SplitGroup) this.cacheService.getValue(Constants.ALL_GROUPS, Integer.toString(request.getGroupId()));
				SplitUser member = (SplitUser) this.cacheService.getValue(Constants.ALL_USERS, request.getMemberName());
				
				group.getStats().put(request.getMemberName(), 0.0);
				member.getPartOf().add(request.getGroupId());
				
				this.cacheService.storeValue(Constants.ALL_GROUPS, Integer.toString(request.getGroupId()), group);
				this.cacheService.storeValue(Constants.ALL_USERS, request.getMemberName(), member);
				
				response.setThisGroup(group);
				response.setSuccess(true);
			}
		} else {
			response.setSuccess(false);
			response.setFailMsg("No group id in request");
		}
		return response;
	}

	@Override
	public GroupResponse deleteGroup(GroupRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}