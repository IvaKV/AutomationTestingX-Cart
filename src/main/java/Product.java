import java.util.ArrayList;

public class Product {
    String product1 = "";
    String product2 = "";
    String product3 = "";

    public Product (String product1) {
        this.product1 = product1;
    }
    public Product (String product1, String product2) {
        this.product1 = product1;
        this.product2 = product2;
    }
    public Product (String product1, String product2, String product3) {
        this.product1 = product1;
        this.product2 = product2;
        this.product3 = product3;
    }

    public ArrayList<String> getProductsList() {
        ArrayList<String> products = new ArrayList<String>();
        products.add("iphone");
        products.add("dress");

        if (!product1.isEmpty()) {
            products.add(product1);
        }
        if (!product2.isEmpty()) {
            products.add(product2);
        }

        if (!product3.isEmpty()) {
            products.add(product3);
        }

        return products;
    }
}
