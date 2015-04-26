/**
 * 
 */
package com.mcnedward.app.repository;

/**
 * @author Edward
 *
 */
public interface IRepository<Item> {

	/**
	 * 
	 */
	void beginTransaction();

	/**
	 * 
	 */
	void commitTransaction();

	/**
	 * 
	 */
	void rollbackTransaction();

	/**
	 * @param entity
	 * @return
	 */
	boolean save(Item entity);

	/**
	 * @param entity
	 * @return
	 */
	boolean saveAndCommit(Item entity);

	/**
	 * @param item
	 * @return
	 */
	Item update(Item item);

}
