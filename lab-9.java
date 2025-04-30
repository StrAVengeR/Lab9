import java.util.*;

class Product {
    private String name;
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class Warehouse {
    private String id;
    private List<Product> products;

    public Warehouse(String id) {
        this.id = id;
        this.products = new ArrayList<>();
    }

    public void receiveProduct(Product product) {
        for (Product p : products) {
            if (p.getName().equals(product.getName())) {
                p.setQuantity(p.getQuantity() + product.getQuantity());
                return;
            }
        }
        products.add(product);
    }

    public void releaseProduct(Product product) {
        for (Product p : products) {
            if (p.getName().equals(product.getName())) {
                int newQuantity = p.getQuantity() - product.getQuantity();
                p.setQuantity(Math.max(newQuantity, 0));
                return;
            }
        }
    }

    public List<Product> getInventory() {
        return products;
    }

    public String getId() {
        return id;
    }
}

class Manager {
    private String name;
    private Warehouse warehouse;

    public Manager(String name, Warehouse warehouse) {
        this.name = name;
        this.warehouse = warehouse;
    }

    public void createIncomeReceipt(Product product, String giver, Date date) {
        warehouse.receiveProduct(product);
        IncomeReceipt receipt = new IncomeReceipt(product, giver, date);
        System.out.println("Orlogin padaan uusgelee: " + product.getName() + ", " + product.getQuantity());
    }

    public void createOutcomeReceipt(Product product, String receiver, Date date) {
        warehouse.releaseProduct(product);
        OutcomeReceipt receipt = new OutcomeReceipt(product, receiver, date);
        System.out.println("Zarlagiin padaan uusgelee: " + product.getName() + ", " + product.getQuantity());
    }

    public void generateStockReport(Date startDate, Date endDate) {
        System.out.println("=== Nooziin tailan ===");
        for (Product p : warehouse.getInventory()) {
            System.out.println(p.getName() + ": " + p.getQuantity());
        }
    }

    public void performInventoryCheck(Product product, int actualQuantity, Date date) {
        for (Product p : warehouse.getInventory()) {
            if (p.getName().equals(product.getName())) {
                int difference = actualQuantity - p.getQuantity();
                p.setQuantity(actualQuantity);
                System.out.println("Toollogo khiiv. Iluu/Dutuu: " + difference);
                return;
            }
        }
    }
}

class IncomeReceipt {
    private Product product;
    private String giver;
    private Date date;

    public IncomeReceipt(Product product, String giver, Date date) {
        this.product = product;
        this.giver = giver;
        this.date = date;
    }
}

class OutcomeReceipt {
    private Product product;
    private String receiver;
    private Date date;

    public OutcomeReceipt(Product product, String receiver, Date date) {
        this.product = product;
        this.receiver = receiver;
        this.date = date;
    }
}

class WarehouseSystem {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("WH01");
        Manager manager = new Manager("Nyarav Bat", warehouse);

        Product rice = new Product("Budaa", 100);
        Product flour = new Product("Guril", 50);

        manager.createIncomeReceipt(rice, "Niiluulegch A", new Date());
        manager.createIncomeReceipt(flour, "Niiluulegch B", new Date());

        manager.createOutcomeReceipt(new Product("Budaa", 30), "Hereglegch V", new Date());

        manager.generateStockReport(new Date(), new Date());

        manager.performInventoryCheck(new Product("Budaa", 0), 65, new Date());
    }
}
