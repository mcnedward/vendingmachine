/**
 * 
 */
package com.mcnedward.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.mcnedward.app.repository.ItemRepository;

/**
 * @author Edward
 *
 */
@ManagedBean
@ViewScoped
public class VendingMachine {

	private List<Item> items;
	private List<Item> itemsA;
	private List<Item> itemsB;
	private List<Item> itemsC;
	private List<Item> itemsD;
	private List<Item> itemsE;

	private List<Item> purchasedItems;

	private int currentChange;
	private int currentBills;
	private double depositedMoney;

	private String message;

	@Inject
	ItemRepository repository;

	@PostConstruct
	public void init() {
		items = repository.findAllItems();
		if (items.isEmpty()) {
			items = repository.fillMachine();
		}
		itemsA = new ArrayList<Item>();
		itemsB = new ArrayList<Item>();
		itemsC = new ArrayList<Item>();
		itemsD = new ArrayList<Item>();
		itemsE = new ArrayList<Item>();
		for (Item item : items) {
			if (item.getLocation().contains("A")) {
				itemsA.add(item);
			} else if (item.getLocation().contains("B")) {
				itemsB.add(item);
			} else if (item.getLocation().contains("C")) {
				itemsC.add(item);
			} else if (item.getLocation().contains("D")) {
				itemsD.add(item);
			} else if (item.getLocation().contains("E")) {
				itemsE.add(item);
			}
		}

		purchasedItems = new ArrayList<Item>();
	}

	public String reloadMachine() {
		items = repository.fillMachine();
		return "";
	}

	public String refillItem(Item item) {
		repository.refillItem(item);
		return "";
	}

	public String buyItem(Item item) {
		if (item.getStock() != 0) {
			item.setStock(item.getStock() - 1);
			repository.update(item);
			purchasedItems.add(item);
		}
		return "";
	}

	public String addChange() {
		if (currentChange == 1 || currentChange == 5 || currentChange == 10 || currentChange == 25 || currentChange == 50) {
			message = "";
			double change = 0.0;
			switch (currentChange) {
			case 1:
				change = 0.01;
				break;
			case 5:
				change = 0.05;
				break;
			case 10:
				change = 0.1;
				break;
			case 25:
				change = 0.25;
				break;
			case 50:
				change = 0.5;
				break;
			}

			depositedMoney += change;
			return "";
		}
		message = "You need to enter correct change! (1 for penny, 5 for nickel, 10 for dime, 25 for quarter, 50 for half-dollar)";
		return "";
	}

	public String addBills() {
		if (currentBills == 1 || currentBills == 2 || currentBills == 5 || currentBills == 10) {
			message = "";
			depositedMoney += currentBills;
			return "";
		}
		message = "You need to enter correct bills! (1, 2, 5, 10)";
		return "";
	}

	public String returnChange() {
		depositedMoney = 0;
		return "";
	}

	/**
	 * @return the itemsA
	 */
	public List<Item> getItemsA() {
		return itemsA;
	}

	/**
	 * @param itemsA
	 *            the itemsA to set
	 */
	public void setItemsA(List<Item> itemsA) {
		this.itemsA = itemsA;
	}

	/**
	 * @return the itemsB
	 */
	public List<Item> getItemsB() {
		return itemsB;
	}

	/**
	 * @param itemsB
	 *            the itemsB to set
	 */
	public void setItemsB(List<Item> itemsB) {
		this.itemsB = itemsB;
	}

	/**
	 * @return the itemsC
	 */
	public List<Item> getItemsC() {
		return itemsC;
	}

	/**
	 * @param itemsC
	 *            the itemsC to set
	 */
	public void setItemsC(List<Item> itemsC) {
		this.itemsC = itemsC;
	}

	/**
	 * @return the itemsD
	 */
	public List<Item> getItemsD() {
		return itemsD;
	}

	/**
	 * @param itemsD
	 *            the itemsD to set
	 */
	public void setItemsD(List<Item> itemsD) {
		this.itemsD = itemsD;
	}

	/**
	 * @return the itemsE
	 */
	public List<Item> getItemsE() {
		return itemsE;
	}

	/**
	 * @param itemsE
	 *            the itemsE to set
	 */
	public void setItemsE(List<Item> itemsE) {
		this.itemsE = itemsE;
	}

	/**
	 * @return the purchasedItems
	 */
	public List<Item> getPurchasedItems() {
		return purchasedItems;
	}

	/**
	 * @param purchasedItems
	 *            the purchasedItems to set
	 */
	public void setPurchasedItems(List<Item> purchasedItems) {
		this.purchasedItems = purchasedItems;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the currentChange
	 */
	public int getCurrentChange() {
		return currentChange;
	}

	/**
	 * @param currentChange
	 *            the currentChange to set
	 */
	public void setCurrentChange(int currentChange) {
		this.currentChange = currentChange;
	}

	/**
	 * @return the currentBills
	 */
	public int getCurrentBills() {
		return currentBills;
	}

	/**
	 * @param currentBills
	 *            the currentBills to set
	 */
	public void setCurrentBills(int currentBills) {
		this.currentBills = currentBills;
	}

	/**
	 * @return the depositedMoney
	 */
	public String getDepositedMoney() {
		return String.format("$%.2f", depositedMoney);
	}

	/**
	 * @param depositedMoney
	 *            the depositedMoney to set
	 */
	public void setDepositedMoney(double depositedMoney) {
		this.depositedMoney = depositedMoney;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return message;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.message = errorMessage;
	}
}
