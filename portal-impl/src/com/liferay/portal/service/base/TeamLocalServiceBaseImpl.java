/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.TeamLocalService;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.TeamFinder;
import com.liferay.portal.service.persistence.TeamPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserGroupFinder;
import com.liferay.portal.service.persistence.UserGroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the team local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.TeamLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.TeamLocalServiceImpl
 * @see com.liferay.portal.service.TeamLocalServiceUtil
 * @generated
 */
public abstract class TeamLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements TeamLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.service.TeamLocalServiceUtil} to access the team local service.
	 */

	/**
	 * Adds the team to the database. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Team addTeam(Team team) {
		team.setNew(true);

		return teamPersistence.update(team);
	}

	/**
	 * Creates a new team with the primary key. Does not add the team to the database.
	 *
	 * @param teamId the primary key for the new team
	 * @return the new team
	 */
	@Override
	public Team createTeam(long teamId) {
		return teamPersistence.create(teamId);
	}

	/**
	 * Deletes the team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamId the primary key of the team
	 * @return the team that was removed
	 * @throws PortalException if a team with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Team deleteTeam(long teamId) throws PortalException {
		return teamPersistence.remove(teamId);
	}

	/**
	 * Deletes the team from the database. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Team deleteTeam(Team team) throws PortalException {
		return teamPersistence.remove(team);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Team.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery) {
		return teamPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end) {
		return teamPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return teamPersistence.findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return teamPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return teamPersistence.countWithDynamicQuery(dynamicQuery, projection);
	}

	@Override
	public Team fetchTeam(long teamId) {
		return teamPersistence.fetchByPrimaryKey(teamId);
	}

	/**
	 * Returns the team with the primary key.
	 *
	 * @param teamId the primary key of the team
	 * @return the team
	 * @throws PortalException if a team with the primary key could not be found
	 */
	@Override
	public Team getTeam(long teamId) throws PortalException {
		return teamPersistence.findByPrimaryKey(teamId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.TeamLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(Team.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("teamId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portal.service.TeamLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(Team.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("teamId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return teamLocalService.deleteTeam((Team)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return teamPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TeamModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of teams
	 */
	@Override
	public List<Team> getTeams(int start, int end) {
		return teamPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of teams.
	 *
	 * @return the number of teams
	 */
	@Override
	public int getTeamsCount() {
		return teamPersistence.countAll();
	}

	/**
	 * Updates the team in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Team updateTeam(Team team) {
		return teamPersistence.update(team);
	}

	/**
	 */
	@Override
	public void addUserTeam(long userId, long teamId) {
		userPersistence.addTeam(userId, teamId);
	}

	/**
	 */
	@Override
	public void addUserTeam(long userId, Team team) {
		userPersistence.addTeam(userId, team);
	}

	/**
	 */
	@Override
	public void addUserTeams(long userId, long[] teamIds) {
		userPersistence.addTeams(userId, teamIds);
	}

	/**
	 */
	@Override
	public void addUserTeams(long userId, List<Team> Teams) {
		userPersistence.addTeams(userId, Teams);
	}

	/**
	 */
	@Override
	public void clearUserTeams(long userId) {
		userPersistence.clearTeams(userId);
	}

	/**
	 */
	@Override
	public void deleteUserTeam(long userId, long teamId) {
		userPersistence.removeTeam(userId, teamId);
	}

	/**
	 */
	@Override
	public void deleteUserTeam(long userId, Team team) {
		userPersistence.removeTeam(userId, team);
	}

	/**
	 */
	@Override
	public void deleteUserTeams(long userId, long[] teamIds) {
		userPersistence.removeTeams(userId, teamIds);
	}

	/**
	 */
	@Override
	public void deleteUserTeams(long userId, List<Team> Teams) {
		userPersistence.removeTeams(userId, Teams);
	}

	/**
	 * Returns the userIds of the users associated with the team.
	 *
	 * @param teamId the teamId of the team
	 * @return long[] the userIds of users associated with the team
	 */
	@Override
	public long[] getUserPrimaryKeys(long teamId) {
		return teamPersistence.getUserPrimaryKeys(teamId);
	}

	/**
	 */
	@Override
	public List<Team> getUserTeams(long userId) {
		return userPersistence.getTeams(userId);
	}

	/**
	 */
	@Override
	public List<Team> getUserTeams(long userId, int start, int end) {
		return userPersistence.getTeams(userId, start, end);
	}

	/**
	 */
	@Override
	public List<Team> getUserTeams(long userId, int start, int end,
		OrderByComparator orderByComparator) {
		return userPersistence.getTeams(userId, start, end, orderByComparator);
	}

	/**
	 */
	@Override
	public int getUserTeamsCount(long userId) {
		return userPersistence.getTeamsSize(userId);
	}

	/**
	 */
	@Override
	public boolean hasUserTeam(long userId, long teamId) {
		return userPersistence.containsTeam(userId, teamId);
	}

	/**
	 */
	@Override
	public boolean hasUserTeams(long userId) {
		return userPersistence.containsTeams(userId);
	}

	/**
	 */
	@Override
	public void setUserTeams(long userId, long[] teamIds) {
		userPersistence.setTeams(userId, teamIds);
	}

	/**
	 */
	@Override
	public void addUserGroupTeam(long userGroupId, long teamId) {
		userGroupPersistence.addTeam(userGroupId, teamId);
	}

	/**
	 */
	@Override
	public void addUserGroupTeam(long userGroupId, Team team) {
		userGroupPersistence.addTeam(userGroupId, team);
	}

	/**
	 */
	@Override
	public void addUserGroupTeams(long userGroupId, long[] teamIds) {
		userGroupPersistence.addTeams(userGroupId, teamIds);
	}

	/**
	 */
	@Override
	public void addUserGroupTeams(long userGroupId, List<Team> Teams) {
		userGroupPersistence.addTeams(userGroupId, Teams);
	}

	/**
	 */
	@Override
	public void clearUserGroupTeams(long userGroupId) {
		userGroupPersistence.clearTeams(userGroupId);
	}

	/**
	 */
	@Override
	public void deleteUserGroupTeam(long userGroupId, long teamId) {
		userGroupPersistence.removeTeam(userGroupId, teamId);
	}

	/**
	 */
	@Override
	public void deleteUserGroupTeam(long userGroupId, Team team) {
		userGroupPersistence.removeTeam(userGroupId, team);
	}

	/**
	 */
	@Override
	public void deleteUserGroupTeams(long userGroupId, long[] teamIds) {
		userGroupPersistence.removeTeams(userGroupId, teamIds);
	}

	/**
	 */
	@Override
	public void deleteUserGroupTeams(long userGroupId, List<Team> Teams) {
		userGroupPersistence.removeTeams(userGroupId, Teams);
	}

	/**
	 * Returns the userGroupIds of the user groups associated with the team.
	 *
	 * @param teamId the teamId of the team
	 * @return long[] the userGroupIds of user groups associated with the team
	 */
	@Override
	public long[] getUserGroupPrimaryKeys(long teamId) {
		return teamPersistence.getUserGroupPrimaryKeys(teamId);
	}

	/**
	 */
	@Override
	public List<Team> getUserGroupTeams(long userGroupId) {
		return userGroupPersistence.getTeams(userGroupId);
	}

	/**
	 */
	@Override
	public List<Team> getUserGroupTeams(long userGroupId, int start, int end) {
		return userGroupPersistence.getTeams(userGroupId, start, end);
	}

	/**
	 */
	@Override
	public List<Team> getUserGroupTeams(long userGroupId, int start, int end,
		OrderByComparator orderByComparator) {
		return userGroupPersistence.getTeams(userGroupId, start, end,
			orderByComparator);
	}

	/**
	 */
	@Override
	public int getUserGroupTeamsCount(long userGroupId) {
		return userGroupPersistence.getTeamsSize(userGroupId);
	}

	/**
	 */
	@Override
	public boolean hasUserGroupTeam(long userGroupId, long teamId) {
		return userGroupPersistence.containsTeam(userGroupId, teamId);
	}

	/**
	 */
	@Override
	public boolean hasUserGroupTeams(long userGroupId) {
		return userGroupPersistence.containsTeams(userGroupId);
	}

	/**
	 */
	@Override
	public void setUserGroupTeams(long userGroupId, long[] teamIds) {
		userGroupPersistence.setTeams(userGroupId, teamIds);
	}

	/**
	 * Returns the team local service.
	 *
	 * @return the team local service
	 */
	public com.liferay.portal.service.TeamLocalService getTeamLocalService() {
		return teamLocalService;
	}

	/**
	 * Sets the team local service.
	 *
	 * @param teamLocalService the team local service
	 */
	public void setTeamLocalService(
		com.liferay.portal.service.TeamLocalService teamLocalService) {
		this.teamLocalService = teamLocalService;
	}

	/**
	 * Returns the team remote service.
	 *
	 * @return the team remote service
	 */
	public com.liferay.portal.service.TeamService getTeamService() {
		return teamService;
	}

	/**
	 * Sets the team remote service.
	 *
	 * @param teamService the team remote service
	 */
	public void setTeamService(
		com.liferay.portal.service.TeamService teamService) {
		this.teamService = teamService;
	}

	/**
	 * Returns the team persistence.
	 *
	 * @return the team persistence
	 */
	public TeamPersistence getTeamPersistence() {
		return teamPersistence;
	}

	/**
	 * Sets the team persistence.
	 *
	 * @param teamPersistence the team persistence
	 */
	public void setTeamPersistence(TeamPersistence teamPersistence) {
		this.teamPersistence = teamPersistence;
	}

	/**
	 * Returns the team finder.
	 *
	 * @return the team finder
	 */
	public TeamFinder getTeamFinder() {
		return teamFinder;
	}

	/**
	 * Sets the team finder.
	 *
	 * @param teamFinder the team finder
	 */
	public void setTeamFinder(TeamFinder teamFinder) {
		this.teamFinder = teamFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the role local service.
	 *
	 * @return the role local service
	 */
	public com.liferay.portal.service.RoleLocalService getRoleLocalService() {
		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(
		com.liferay.portal.service.RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role remote service.
	 *
	 * @return the role remote service
	 */
	public com.liferay.portal.service.RoleService getRoleService() {
		return roleService;
	}

	/**
	 * Sets the role remote service.
	 *
	 * @param roleService the role remote service
	 */
	public void setRoleService(
		com.liferay.portal.service.RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * Returns the role persistence.
	 *
	 * @return the role persistence
	 */
	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	/**
	 * Sets the role persistence.
	 *
	 * @param rolePersistence the role persistence
	 */
	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	/**
	 * Returns the role finder.
	 *
	 * @return the role finder
	 */
	public RoleFinder getRoleFinder() {
		return roleFinder;
	}

	/**
	 * Sets the role finder.
	 *
	 * @param roleFinder the role finder
	 */
	public void setRoleFinder(RoleFinder roleFinder) {
		this.roleFinder = roleFinder;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the user group local service.
	 *
	 * @return the user group local service
	 */
	public com.liferay.portal.service.UserGroupLocalService getUserGroupLocalService() {
		return userGroupLocalService;
	}

	/**
	 * Sets the user group local service.
	 *
	 * @param userGroupLocalService the user group local service
	 */
	public void setUserGroupLocalService(
		com.liferay.portal.service.UserGroupLocalService userGroupLocalService) {
		this.userGroupLocalService = userGroupLocalService;
	}

	/**
	 * Returns the user group remote service.
	 *
	 * @return the user group remote service
	 */
	public com.liferay.portal.service.UserGroupService getUserGroupService() {
		return userGroupService;
	}

	/**
	 * Sets the user group remote service.
	 *
	 * @param userGroupService the user group remote service
	 */
	public void setUserGroupService(
		com.liferay.portal.service.UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	/**
	 * Returns the user group persistence.
	 *
	 * @return the user group persistence
	 */
	public UserGroupPersistence getUserGroupPersistence() {
		return userGroupPersistence;
	}

	/**
	 * Sets the user group persistence.
	 *
	 * @param userGroupPersistence the user group persistence
	 */
	public void setUserGroupPersistence(
		UserGroupPersistence userGroupPersistence) {
		this.userGroupPersistence = userGroupPersistence;
	}

	/**
	 * Returns the user group finder.
	 *
	 * @return the user group finder
	 */
	public UserGroupFinder getUserGroupFinder() {
		return userGroupFinder;
	}

	/**
	 * Sets the user group finder.
	 *
	 * @param userGroupFinder the user group finder
	 */
	public void setUserGroupFinder(UserGroupFinder userGroupFinder) {
		this.userGroupFinder = userGroupFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portal.model.Team",
			teamLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.model.Team");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return Team.class;
	}

	protected String getModelClassName() {
		return Team.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = teamPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portal.service.TeamLocalService.class)
	protected com.liferay.portal.service.TeamLocalService teamLocalService;
	@BeanReference(type = com.liferay.portal.service.TeamService.class)
	protected com.liferay.portal.service.TeamService teamService;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TeamFinder.class)
	protected TeamFinder teamFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.RoleLocalService.class)
	protected com.liferay.portal.service.RoleLocalService roleLocalService;
	@BeanReference(type = com.liferay.portal.service.RoleService.class)
	protected com.liferay.portal.service.RoleService roleService;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = RoleFinder.class)
	protected RoleFinder roleFinder;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = com.liferay.portal.service.UserGroupLocalService.class)
	protected com.liferay.portal.service.UserGroupLocalService userGroupLocalService;
	@BeanReference(type = com.liferay.portal.service.UserGroupService.class)
	protected com.liferay.portal.service.UserGroupService userGroupService;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupFinder.class)
	protected UserGroupFinder userGroupFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}