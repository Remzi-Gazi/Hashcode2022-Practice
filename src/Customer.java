import java.util.ArrayList;
import java.util.Set;

public class Customer {

	private ArrayList<String> likedMaterials;
	private ArrayList<String> dislikedMaterials;
	private int customerId;
	private static int id = 0;
	private int customerCost;
	private Set<Integer> obtainable;
	private Set<Integer> unobtainable;
	
	public Set<Integer> getObtainable() {
		return obtainable;
	}

	public void setObtainable(Set<Integer> obtainable) {
		this.obtainable = obtainable;
	}

	public Set<Integer> getUnobtainable() {
		return unobtainable;
	}

	public void setUnobtainable(Set<Integer> unobtainable) {
		this.unobtainable = unobtainable;
	}

	public int getCustomerCost() {
		return customerCost;
	}
	public void addCustomerCost() {
		customerCost++;
	}

	public void setCustomerCost(int customerCost) {
		this.customerCost = customerCost;
	}

	public Customer(){
		likedMaterials = new ArrayList<String>();
		dislikedMaterials = new ArrayList<String>();
		id++;
		this.customerId = id;
		
	}

	public ArrayList<String> getLikedMaterials() {
		return likedMaterials;
	}

	public ArrayList<String> getDislikedMaterials() {
		return dislikedMaterials;
	}

	public int getCustomerId() {
		return customerId;
	}

	@Override
	public String toString() {
		return "Id: "+customerId+"  Cost: "+ customerCost+"\n";
	}
	
	public void removeLiked(String material) {
		likedMaterials.remove(material);
	}
	
	public void removeDislikedLiked(String material) {
		dislikedMaterials.remove(material);
	}
	
	
}
