package com.codelab.common;

import java.util.Date;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import com.codelab.beans.User;
import com.codelab.beans.general.AbstractBO;

public class CommonImportListener implements PreInsertEventListener, PreUpdateEventListener {
	private static final long serialVersionUID = -811081623549360520L;

	private void setPropertyState(Object[] propertyStates, String[] propertyNames, String propertyName,
			Object propertyState) {
		for (int i = 0; i < propertyNames.length; i++) {
			if (propertyName.equals(propertyNames[i])) {
				propertyStates[i] = propertyState;
				return;
			}
		}
	}

	private void onInsert(Object entity, Object[] state, String[] propertyNames) {
		if (entity instanceof AbstractBO) {
			AbstractBO defaultImport = (AbstractBO) entity;
			Date date = new Date();
			defaultImport.setCreatedOn(date);
			setPropertyState(state, propertyNames, "createdOn", date);
			defaultImport.setUpdatedOn(date);
			setPropertyState(state, propertyNames, "updatedOn", date);

			User user = CommonUtility.getContextUser();
			if (user != null && user.getId() != null) {
				Long userId = user.getId();
				defaultImport.setCreatedById(userId);
				setPropertyState(state, propertyNames, "createdById", userId);
				defaultImport.setUpdatedById(user.getId());
				setPropertyState(state, propertyNames, "updatedById", userId);
			}

		}
	}

	private void onUpdate(Object entity, Object[] state, String[] propertyNames) {
		if (entity instanceof AbstractBO) {
			AbstractBO defaultImport = (AbstractBO) entity;
			Date date = new Date();
			setPropertyState(state, propertyNames, "createdOn", defaultImport.getCreatedOn());
			defaultImport.setUpdatedOn(date);
			setPropertyState(state, propertyNames, "updatedOn", date);

			User user = CommonUtility.getContextUser();
			if (user != null && user.getId() != null) {
				Long userId = user.getId();
				setPropertyState(state, propertyNames, "createdById", defaultImport.getCreatedById());
				defaultImport.setUpdatedById(userId);
				setPropertyState(state, propertyNames, "updatedById", userId);
			}
		}
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		onInsert(event.getEntity(), event.getState(), event.getPersister().getPropertyNames());
		return false;
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		onUpdate(event.getEntity(), event.getState(), event.getPersister().getPropertyNames());
		return false;
	}
}