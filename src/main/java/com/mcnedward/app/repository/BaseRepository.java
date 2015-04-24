/**
 * 
 */
package com.mcnedward.app.repository;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * @author Edward
 *
 */
@TransactionManagement(TransactionManagementType.BEAN)
public class BaseRepository<Item> implements IRepository<Item> {

	@PersistenceContext(unitName = "VendingMachine")
	private EntityManager em;
	@Resource
	private EJBContext context;
	protected UserTransaction transaction;

	public BaseRepository() {

	}

	public BaseRepository(EntityManager em, UserTransaction utx) {
		this.em = em;
		transaction = utx;
	}

	@Override
	public boolean save(Item entity) {
		try {
			em.persist(entity);
		} catch (IllegalStateException | SecurityException e) {
			e.printStackTrace();
			rollbackTransaction();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveAndCommit(Item entity) {
		try {
			verifyTransaction();
			em.persist(entity);
			commitTransaction();
		} catch (Exception e) {
			System.out.println(e);
			rollbackTransaction();
			return false;
		}
		return true;
	}

	/**
	 * This is used to find an entity from the database. Pass the class of the entity and the id.
	 * 
	 * @param entityClass
	 *            The class of the entity to find.
	 * @param id
	 *            The id of the entity in the database.
	 * @return The entity from the database.
	 */
	public Item find(Class<Item> entityClass, int id) {
		return em.find(entityClass, id);
	}

	public Query getNamedQuery(String queryName) {
		return em.createNamedQuery(queryName);
	}

	protected boolean hasResults(Query query) {
		try {
			if (query.getResultList().size() > 0) {
				return true;
			}
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Verify that a transaction is started and active. If not, this will start the transaction and join it with the Entity Manager.
	 */
	private void verifyTransaction() {
		// Check for transaction status(6 = STATUS_NO_TRANSACTION, 5 = STATUS_UNKNOWN, 0 = STATUS_ACTIVE)
		try {
			if (transaction == null) {
				transaction = context.getUserTransaction();
				startTransaction();
				return;
			}
			if (transaction.getStatus() == 6 || transaction.getStatus() == 5) {
				startTransaction();
				return;
			}
			if (transaction.getStatus() == 0) {
				em.joinTransaction();
				return;
			}
		} catch (IllegalStateException | SystemException | NotSupportedException e) {
			e.printStackTrace();
		} catch (TransactionRequiredException e) {
			System.out.println(e);
			System.out.println("Transaction required...");
		}
	}

	private void startTransaction() throws NotSupportedException, SystemException {
		if (em != null) {
			if (transaction != null) {
				transaction.begin();
				em.joinTransaction();
				System.out.println("Transaction started and joined!");
			}
		}
	}

	@Override
	public void beginTransaction() {
		verifyTransaction();
	}

	@Override
	public void commitTransaction() {
		try {
			if (transaction != null) {
				System.out.println("About to commit!");
				transaction.commit();
				System.out.println("Committed!");
			} else {
				System.out.println("Transaction is null...");
			}
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException
				| SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void rollbackTransaction() {
		try {
			if (transaction == null) {
				System.out.println("Transaction is null so will not be rolled back!");
				return;
			}
			transaction.rollback();
		} catch (IllegalStateException | SecurityException | SystemException e) {
			e.printStackTrace();
		}
	}
}
