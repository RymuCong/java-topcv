package com.t3h.topcv.dao;

import com.t3h.topcv.entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountDaoImpl implements AccountDao {

	private EntityManager entityManager;

	@Autowired
	public AccountDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Account findByUserName(String theUserName) {

		// retrieve/read from database using username
		TypedQuery<Account> theQuery = entityManager.createQuery("from Account where userName=:uName and status=1", Account.class);
		theQuery.setParameter("uName", theUserName);

		Account theAccount = null;
		try {
			theAccount = theQuery.getSingleResult();
		} catch (Exception e) {
			theAccount = null;
		}

		return theAccount;
	}

	@Override
	@Transactional
	public void save(Account theAccount) {

		// create the account ... finally LOL
		entityManager.merge(theAccount);
	}


}
