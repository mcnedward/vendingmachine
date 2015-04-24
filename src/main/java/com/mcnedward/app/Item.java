package com.mcnedward.app;

import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Edward
 *
 */
@Entity
@Table(name = "Item")
@NamedQueries({ @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
		@NamedQuery(name = "findByLocation", query = "SELECT i FROM Item i WHERE i.location = :location") })
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false)
	private Integer id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Price")
	private Double price;
	@Column(name = "Location")
	private String location;
	@Column(name = "stock")
	private Integer stock;
	@Column(name = "max")
	private Integer max;

	public Item() {

	}

	/**
	 * @param id
	 * @param name
	 * @param price
	 * @param location
	 * @param stock
	 * @param max
	 */
	public Item(String name, Double price, String location, Integer stock, Integer max) {
		super();
		this.name = name;
		this.price = price;
		this.location = location;
		this.stock = stock;
		this.max = max;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(price);
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the stock
	 */
	public Integer getStock() {
		return stock;
	}

	/**
	 * @param stock
	 *            the stock to set
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return name;
	}
}
