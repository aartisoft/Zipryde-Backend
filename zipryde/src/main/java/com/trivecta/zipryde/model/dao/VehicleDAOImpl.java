
package com.trivecta.zipryde.model.dao;

import java.util.Date;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trivecta.zipryde.constants.ErrorMessages;
import com.trivecta.zipryde.constants.ZipRydeConstants.STATUS;
import com.trivecta.zipryde.framework.exception.NoResultEntityException;
import com.trivecta.zipryde.framework.exception.UserValidationException;
import com.trivecta.zipryde.model.entity.CabPermit;
import com.trivecta.zipryde.model.entity.CabType;
import com.trivecta.zipryde.model.entity.Make;
import com.trivecta.zipryde.model.entity.Model;
import com.trivecta.zipryde.model.entity.Status;
import com.trivecta.zipryde.model.entity.VehicleDetail;

@Repository
public class VehicleDAOImpl implements VehicleDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private VehicleDetail getVehicleDetailById(int vehicleId) {
		Session session = this.sessionFactory.getCurrentSession();
		VehicleDetail vehicleDetail = session.find(VehicleDetail.class, vehicleId);
		
		vehicleDetail.getCabType();
		vehicleDetail.getModel();
		
		if(vehicleDetail.getCabPermits() != null)
			vehicleDetail.getCabPermits().size();
		
		return vehicleDetail;
	}
	
	private VehicleDetail getVehicleDetailByVIN(String vin) {
		Session session = this.sessionFactory.getCurrentSession();
		
		VehicleDetail vehicleDetail = null;
		try {
			vehicleDetail = (VehicleDetail) session.getNamedQuery("").setParameter("vin", vin).getSingleResult();
		}
		catch(NoResultException e){
			//No VIN Exists
		}
		return vehicleDetail;
	}
	
	public VehicleDetail createVehicle(VehicleDetail vehicleDetail,CabPermit cabPermit) throws UserValidationException {
		
		VehicleDetail vinVehicle = getVehicleDetailByVIN(vehicleDetail.getVin());
		
		if(vinVehicle != null) {
			Session session = this.sessionFactory.getCurrentSession();
			
			
			CabType cabType = session.find(CabType.class, vehicleDetail.getCabType().getId());
			vehicleDetail.setCabType(cabType);
		
			Model model = session.find(Model.class, vehicleDetail.getModel().getId());
			vehicleDetail.setModel(model);
			
			Status status = null;
			
			if(vehicleDetail.getStatus() != null) {
				status = (Status)
						session.getNamedQuery("Status.findByStatus").
						setParameter("status", vehicleDetail.getStatus().getStatus()).getSingleResult();			
			}
			else {
				status = (Status)
						session.getNamedQuery("Status.findByStatus").
						setParameter("status", STATUS.REQUESTED).getSingleResult();			
			}
			vehicleDetail.setStatus(status);
			
			if(vehicleDetail.getIsEnable() == null) {
				vehicleDetail.setIsEnable(0);
			}
			
			vehicleDetail.setCreationDate(new Date());
			vehicleDetail.setModifiedDate(new Date());
			session.save(vehicleDetail);
			
			cabPermit.setVehicleDetail(vehicleDetail);
			session.save(cabPermit);
			
			return getVehicleDetailById(vehicleDetail.getId());	
		}
		else {
			throw new UserValidationException(ErrorMessages.VIN_EXISTS_ALREADY);
		}
		
	}
	
	public VehicleDetail updateVehicle(VehicleDetail vehicleDetail,CabPermit cabPermit) {
		Session session = this.sessionFactory.getCurrentSession();
		
		VehicleDetail origVehicle = session.find(VehicleDetail.class, vehicleDetail.getId());
		
		CabType cabType = session.find(CabType.class, vehicleDetail.getCabType().getId());
		origVehicle.setCabType(cabType);
	
		Model model = session.find(Model.class, vehicleDetail.getModel().getId());
		origVehicle.setModel(model);
		
		Status status = null;
		
		if(vehicleDetail.getStatus() != null) {
			status = (Status)
					session.getNamedQuery("Status.findByStatus").
					setParameter("status", vehicleDetail.getStatus().getStatus()).getSingleResult();	
			
			if(STATUS.APPROVED.equalsIgnoreCase(vehicleDetail.getStatus().getStatus())) {
				origVehicle.setIsEnable(1);	
			}	
			else {
				origVehicle.setIsEnable(0);
			}			
		}
		else {
			status = (Status)
					session.getNamedQuery("Status.findByStatus").
					setParameter("status", STATUS.REQUESTED).getSingleResult();		
			origVehicle.setIsEnable(0);
			
			
		}
		origVehicle.setStatus(status);
	
		
		origVehicle.setModifiedDate(new Date());
		session.merge(origVehicle);
		
		if(cabPermit.getId() != null) {
			CabPermit origCabPermit = session.find(CabPermit.class,cabPermit.getId());
			origCabPermit.setVehicleDetail(vehicleDetail);
			session.merge(origCabPermit);
			
		}
		
		return getVehicleDetailById(origVehicle.getId());	
		
	}
	
	
	
	
}
