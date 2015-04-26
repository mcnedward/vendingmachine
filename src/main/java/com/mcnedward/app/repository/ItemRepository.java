/**
 * 
 */
package com.mcnedward.app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.Query;

import com.mcnedward.app.Item;

/**
 * @author Edward
 *
 */
@Stateful
public class ItemRepository extends BaseRepository<Item> {

	private static String[] CANDIES1 = { "Kit-Kat", "Snickers", "Reeses'" };
	private static String[] CANDIES2 = { "M & M's", "Butterfinger", "Hershey's" };
	private static String[] SODAS1 = { "Pepsi", "Coke", "Sprite" };
	private static String[] SODAS2 = { "Mountain Dew", "A & W", "Fanta" };
	private static String[] CHIPS = { "Lays", "Doritos", "Cheetos" };

	@SuppressWarnings("unchecked")
	public List<Item> findAllItems() {
		List<Item> items = (List<Item>) getNamedQuery("Item.findAll").getResultList();
		return items;
	}

	public Item findByLocation(String location) {
		Item item = null;
		Query query = getNamedQuery("findByLocation");
		query.setParameter("location", location);
		if (hasResults(query)) {
			item = (Item) query.getResultList().get(0);
		}
		return item;
	}

	public void refillItem(Item item) {
		try {
			beginTransaction();
			item.setStock(item.getMax());
			commitTransaction();
		} catch (Exception e) {
			System.out.println(e);
			rollbackTransaction();
		}
	}

	public List<Item> fillMachine() {
		List<Item> items = new ArrayList<Item>();
		try {
			String locationA = "A";
			for (int x = 0; x < CANDIES1.length; x++) {
				int num = x + 1;
				String location = locationA + num;
				Item item = new Item(CANDIES1[x], 0.7, location, 10, 10);
				items.add(item);
			}
			String locationB = "B";
			for (int x = 0; x < CANDIES2.length; x++) {
				int num = x + 1;
				String location = locationB + num;
				Item item = new Item(CANDIES2[x], 0.8, location, 20, 20);
				items.add(item);
			}
			String locationC = "C";
			for (int x = 0; x < SODAS1.length; x++) {
				int num = x + 1;
				String location = locationC + num;
				Item item = new Item(SODAS1[x], 1.0, location, 20, 20);
				items.add(item);
			}
			String locationD = "D";
			for (int x = 0; x < SODAS2.length; x++) {
				int num = x + 1;
				String location = locationD + num;
				Item item = new Item(SODAS2[x], 1.0, location, 10, 10);
				items.add(item);
			}
			String locationE = "E";
			for (int x = 0; x < CHIPS.length; x++) {
				int num = x + 1;
				String location = locationE + num;
				Item item = new Item(CHIPS[x], 0.9, location, 15, 15);
				items.add(item);
			}

			beginTransaction();
			for (Item item : items) {
				save(item);
			}
			commitTransaction();
		} catch (Exception e) {
			System.out.println(e);
			items = new ArrayList<Item>();
			items.add(new Item("No Items!", 0.0, "A1", 0, 0));
		}
		return items;
	}

}
