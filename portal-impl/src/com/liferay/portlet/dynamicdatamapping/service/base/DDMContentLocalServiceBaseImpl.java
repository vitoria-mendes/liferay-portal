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

package com.liferay.portlet.dynamicdatamapping.service.base;

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
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalService;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMContentPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the d d m content local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMContentLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMContentLocalServiceImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil
 * @generated
 */
public abstract class DDMContentLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements DDMContentLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil} to access the d d m content local service.
	 */

	/**
	 * Adds the d d m content to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmContent the d d m content
	 * @return the d d m content that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMContent addDDMContent(DDMContent ddmContent) {
		ddmContent.setNew(true);

		return ddmContentPersistence.update(ddmContent);
	}

	/**
	 * Creates a new d d m content with the primary key. Does not add the d d m content to the database.
	 *
	 * @param contentId the primary key for the new d d m content
	 * @return the new d d m content
	 */
	@Override
	public DDMContent createDDMContent(long contentId) {
		return ddmContentPersistence.create(contentId);
	}

	/**
	 * Deletes the d d m content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentId the primary key of the d d m content
	 * @return the d d m content that was removed
	 * @throws PortalException if a d d m content with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMContent deleteDDMContent(long contentId)
		throws PortalException {
		return ddmContentPersistence.remove(contentId);
	}

	/**
	 * Deletes the d d m content from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmContent the d d m content
	 * @return the d d m content that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMContent deleteDDMContent(DDMContent ddmContent) {
		return ddmContentPersistence.remove(ddmContent);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DDMContent.class,
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
		return ddmContentPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddmContentPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddmContentPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return ddmContentPersistence.countWithDynamicQuery(dynamicQuery);
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
		return ddmContentPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DDMContent fetchDDMContent(long contentId) {
		return ddmContentPersistence.fetchByPrimaryKey(contentId);
	}

	/**
	 * Returns the d d m content with the matching UUID and company.
	 *
	 * @param uuid the d d m content's UUID
	 * @param  companyId the primary key of the company
	 * @return the matching d d m content, or <code>null</code> if a matching d d m content could not be found
	 */
	@Override
	public DDMContent fetchDDMContentByUuidAndCompanyId(String uuid,
		long companyId) {
		return ddmContentPersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the d d m content matching the UUID and group.
	 *
	 * @param uuid the d d m content's UUID
	 * @param groupId the primary key of the group
	 * @return the matching d d m content, or <code>null</code> if a matching d d m content could not be found
	 */
	@Override
	public DDMContent fetchDDMContentByUuidAndGroupId(String uuid, long groupId) {
		return ddmContentPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the d d m content with the primary key.
	 *
	 * @param contentId the primary key of the d d m content
	 * @return the d d m content
	 * @throws PortalException if a d d m content with the primary key could not be found
	 */
	@Override
	public DDMContent getDDMContent(long contentId) throws PortalException {
		return ddmContentPersistence.findByPrimaryKey(contentId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(DDMContent.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("contentId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(DDMContent.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("contentId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType.toString(),
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType.toString(),
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object)
					throws PortalException {
					DDMContent stagedModel = (DDMContent)object;

					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						stagedModel);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(DDMContent.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return ddmContentLocalService.deleteDDMContent((DDMContent)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return ddmContentPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the d d m content with the matching UUID and company.
	 *
	 * @param uuid the d d m content's UUID
	 * @param  companyId the primary key of the company
	 * @return the matching d d m content
	 * @throws PortalException if a matching d d m content could not be found
	 */
	@Override
	public DDMContent getDDMContentByUuidAndCompanyId(String uuid,
		long companyId) throws PortalException {
		return ddmContentPersistence.findByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the d d m content matching the UUID and group.
	 *
	 * @param uuid the d d m content's UUID
	 * @param groupId the primary key of the group
	 * @return the matching d d m content
	 * @throws PortalException if a matching d d m content could not be found
	 */
	@Override
	public DDMContent getDDMContentByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {
		return ddmContentPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the d d m contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m contents
	 * @param end the upper bound of the range of d d m contents (not inclusive)
	 * @return the range of d d m contents
	 */
	@Override
	public List<DDMContent> getDDMContents(int start, int end) {
		return ddmContentPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of d d m contents.
	 *
	 * @return the number of d d m contents
	 */
	@Override
	public int getDDMContentsCount() {
		return ddmContentPersistence.countAll();
	}

	/**
	 * Updates the d d m content in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmContent the d d m content
	 * @return the d d m content that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMContent updateDDMContent(DDMContent ddmContent) {
		return ddmContentPersistence.update(ddmContent);
	}

	/**
	 * Returns the d d m content local service.
	 *
	 * @return the d d m content local service
	 */
	public com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalService getDDMContentLocalService() {
		return ddmContentLocalService;
	}

	/**
	 * Sets the d d m content local service.
	 *
	 * @param ddmContentLocalService the d d m content local service
	 */
	public void setDDMContentLocalService(
		com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalService ddmContentLocalService) {
		this.ddmContentLocalService = ddmContentLocalService;
	}

	/**
	 * Returns the d d m content persistence.
	 *
	 * @return the d d m content persistence
	 */
	public DDMContentPersistence getDDMContentPersistence() {
		return ddmContentPersistence;
	}

	/**
	 * Sets the d d m content persistence.
	 *
	 * @param ddmContentPersistence the d d m content persistence
	 */
	public void setDDMContentPersistence(
		DDMContentPersistence ddmContentPersistence) {
		this.ddmContentPersistence = ddmContentPersistence;
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.dynamicdatamapping.model.DDMContent",
			ddmContentLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.dynamicdatamapping.model.DDMContent");
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
		return DDMContent.class;
	}

	protected String getModelClassName() {
		return DDMContent.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddmContentPersistence.getDataSource();

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

	@BeanReference(type = com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalService.class)
	protected com.liferay.portlet.dynamicdatamapping.service.DDMContentLocalService ddmContentLocalService;
	@BeanReference(type = DDMContentPersistence.class)
	protected DDMContentPersistence ddmContentPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}