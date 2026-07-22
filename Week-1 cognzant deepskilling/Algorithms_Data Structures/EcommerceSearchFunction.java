
/*Big O Notation 
       - > describe time or space complexity of an algorithm.
       - > Search Algorithms : Linear and Binary Search.
       
       Best Case : (element found at first position)
         -> Linear search : O(1)
         - > Binary Search : O(1)
         
        Average Case : (element found at middle )
          -> Linear Search : O(n)
          - > Binary Search : O(log n)
          
        Worst Case : (element found at the last)
           - > Linear Search : O(n)
           - > Binary Search : O(log n) */
           
class Product{
    
    int productId;
    String productName;
    String category;
    
    Product(int productId, String productName, String category){
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }
    
    @Override
    public String toString() {
        return "Product ID: " + productId +
           "  Product Name: " + productName +
           "  Category: " + category;
}
}
public class EcommerceSearchFunction
{
    
    public static Product LinearSearch(Product[] products,int targetId){
        
        for(Product product : products){
            if(product.productId == targetId) return product;
        }
        return null;
    }
    
    public static Product BinarySearch(Product[] products, int targetId){
        
        int left = 0,right = products.length-1;
        
        while(left<=right){
            
            int mid = left + (right - left)/2;
            
            if(products[mid].productId == targetId) return products[mid];
            else if(products[mid].productId > targetId) right = mid -1;
            else left = mid + 1;
            
        }
        return null;
    }
	public static void main(String[] args) {
		
		Product[] products = {
		    new Product(101,"Fan","Electronics"),
		    new Product(102,"Chair","Furniture"),
		    new Product(103,"Watch","Accesories"),
		    new Product(104,"Mobile","Electronics"),
		    new Product(105,"Shoes","Fashion")
		};
		
		int targetId = 104;
		
		Product p = LinearSearch(products,targetId);
		
		if(p!=null) System.out.println(p);
		else System.out.println("No such Product found!!!");
		
		p = BinarySearch(products,targetId);
		
		if(p!=null) System.out.println(p);
		else System.out.println("No such Product found!!!");
	}
}
