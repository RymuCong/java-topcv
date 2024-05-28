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
	public Boolean existsByUserName(String userName) {

		// check the database if the account already exists
		TypedQuery<Account> theQuery = entityManager.createQuery("from Account where userName=:uName", Account.class);
		theQuery.setParameter("uName", userName);

		Account theAccount;
		try {
			theAccount = theQuery.getSingleResult();
		} catch (Exception e) {
			theAccount = null;
		}

		return theAccount != null;
	}

	@Override
	public Boolean existsByEmail(String email) {

		// check the database if the account already exists
		TypedQuery<Account> theQuery = entityManager.createQuery("from Account where email=:uEmail", Account.class);
		theQuery.setParameter("uEmail", email);

		Account theAccount;
		try {
			theAccount = theQuery.getSingleResult();
		} catch (Exception e) {
			theAccount = null;
		}

		return theAccount != null;
	}

	@Override
	public Account findByUserName(String theUserName) {

		// retrieve/read from database using username
		TypedQuery<Account> theQuery = entityManager.createQuery("from Account where userName=:uName and status=1", Account.class);
		theQuery.setParameter("uName", theUserName);

		Account theAccount;
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

		// save to database
		entityManager.merge(theAccount);
	}


}
