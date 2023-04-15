package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		return connector.getSession().createCriteria(Participant.class).list();
	}

	public Collection<Participant> getAll(String sortBy, String sortOrder, String login) {
		if (login.equals("")) {
			String hql = "FROM Participant";
			if (sortBy.equals("login")) {
				hql = hql + " ORDER BY login ";
				if (sortOrder.equals("ASC") || sortOrder.equals("DESC")) {
					hql = hql + sortOrder;
				}
			}
			Query query = connector.getSession().createQuery(hql);
			return query.list();
		} else {
			String hql = "FROM Participant p WHERE p.login LIKE '%" + login + "%'";
			Query query = connector.getSession().createQuery(hql);
			System.out.println(hql);
			return query.list();
		}
	}

	public Participant findByLogin(String login) {
		return connector.getSession().get(Participant.class, login);
	}

	public Participant add(Participant participant) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(participant);
		transaction.commit();
		return participant;
	}

	public void update(Participant participant) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().merge(participant);
		transaction.commit();
	}

	public void delete(Participant participant) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().delete(participant);
		transaction.commit();
	}

}
