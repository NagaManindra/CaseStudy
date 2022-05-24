package com.eShoppingZone.model;

public class Item {

	private CartProduct product;
	private int quantity;
	@SuppressWarnings("unused")
	private double subTotal;

	public Item() {
		super();
	}

	public Item(int quantity) {
		super();
		this.quantity = quantity;
	}

	public CartProduct getProduct() {
		return product;
	}

	public void setProduct(CartProduct product) {
		this.product = product;
	}

	public void setSubTotal() {
		this.subTotal = product.getPrice() * quantity;
	}

	public double getSubTotal() {
		return product.getPrice() * quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
