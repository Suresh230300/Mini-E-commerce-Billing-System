package Ecommerce;
import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class Ecommerce{
    private static final HashMap<String, Integer> productPrices = new HashMap<>();

    static {
        productPrices.put("Mobile", 20000);
        productPrices.put("Laptop", 60000);
        productPrices.put("Bluetooth Speaker ", 6000);
        productPrices.put("cycle", 9000);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> selectedProducts = new ArrayList<>();
        
       
        System.out.println("Enter Customer Name:");
        String customerName = sc.next();

        System.out.println("Select Products (type 'yes' to select):");
        for (Map.Entry<String, Integer> entry : productPrices.entrySet()) {
            System.out.print("Do you want to buy " + entry.getKey() + " (₹" + entry.getValue() + ")? ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                selectedProducts.add(entry.getKey());
            }
        }

        generateAndStoreBill(customerName, selectedProducts);
        sc.close();
    }

    public static void generateAndStoreBill(String customerName, ArrayList<String> products) throws Exception {
        int subtotal = 0;
        StringBuilder itemsList = new StringBuilder();

        for (String product : products) {
            int price = productPrices.get(product);
            itemsList.append(product).append(" (₹").append(price).append("), ");
            subtotal += price;
        }

        // Remove last comma
        if (itemsList.length() > 0) {
            itemsList.setLength(itemsList.length() - 2);
        }

        // Discount logic
        double discount = 15;
        if (subtotal >= 5000) {
            discount = subtotal * 0.15;
        } else if (subtotal >= 1000) {
            discount = subtotal * 0.10;
        }

        double discountedTotal = subtotal - discount;
        double gst = discountedTotal * 0.18;
        double finalAmount = discountedTotal + gst;

        // Bill text
        StringBuilder bill = new StringBuilder();
        bill.append("===== MINI E-COMMERCE BILL =====\n");
        bill.append("Customer: ").append(customerName).append("\n");
        bill.append("Items Purchased: ").append(itemsList).append("\n");
        bill.append("Subtotal: ₹").append(subtotal).append("\n");
        bill.append("Discount: ₹").append(String.format("%.2f", discount)).append("\n");
        bill.append("GST (18%): ₹").append(String.format("%.2f", gst)).append("\n");
        bill.append("Total Payable: ₹").append(String.format("%.2f", finalAmount)).append("\n");
        bill.append("===============================\n");

        // Save to file
        FileWriter fw = new FileWriter("bill.txt");
        fw.write(bill.toString());
        fw.close();

        System.out.println("\nBill saved to 'bill.txt'.");
        
        
        
        	Class.forName("com.mysql.cj.jdbc.Driver");

        		
        		String DBURL="jdbc:mysql://127.0.0.1/ecommerce";
        		String username="root";
        		String password="Suresh123$6";
        		//Step-1:Create Connection Statement Object
        		Connection con= DriverManager.getConnection(DBURL,username,password);
        		
        		//Step-2:Create Statement Object
        		/*PreparedStatement ps = con.prepareStatement("insert into bills values(2,'name','Mobile',20000,0.10,0.18,23780.30)");
        		
        		ps.executeUpdate();*/
        		String insertSQL = "INSERT INTO bills (id, name, products, subtotal, discount, gst, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        		PreparedStatement ps = con.prepareStatement(insertSQL);
        		

        		// Set dynamic values
        		int billId = (int)(Math.random() * 100);
        		ps.setInt(1, billId);  // random ID or auto-increment in DB
        		ps.setString(2, customerName);
        		ps.setString(3, itemsList.toString());      // product names with prices
        		ps.setInt(4, subtotal);
        		ps.setDouble(5, discount);
        		ps.setDouble(6, gst);
        		ps.setDouble(7, finalAmount);
        		
        		
        		int status=ps.executeUpdate();
        		
        		
        		if(status>0) {
        			  System.out.println("✅ Bill successfully inserted into MySQL database.");
        		} else {
        		    System.out.println("❌ Failed to insert bill into database.");
        		}	
        		

        		
        	


        
    }

  

  

       
        
    }


