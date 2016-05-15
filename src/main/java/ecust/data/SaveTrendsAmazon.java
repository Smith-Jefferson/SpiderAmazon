package ecust.data;

import java.util.List;

import ecust.model.Product;

public class SaveTrendsAmazon implements Runnable {
	private List<Product> products;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	
	public void run() {
		TrendAmazonDataOp save=new TrendAmazonDataOp();
		save.saveAll(products);
	}

}
