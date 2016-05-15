package ecust.strategy;

import java.util.List;
import java.util.Map;

import ecust.model.Product;

public interface ItemList {
			List<Product> getItemList(String url,String query,Map<String,String> data);
}
