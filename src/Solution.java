import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {

		// c_coarse.in.txt
		// d_difficult.in.txt
		// e_elaborate.in.txt
		String fileName = "e_elaborate.in.txt";
		File file = new File(fileName);

		try {

			// eðerki bir müþterinin sevdiði tüm malzemeler pizzanýn içindeyse
			// ve sevmediði hiçbir malzeme yoksa o zaman bu müþteri geliyor.

			// bu soruyla ilgili birkaç fikir
			// unique sadece sevilen malzemeleri seçmek
			// en çok kiþinin sevdiði malzemeleri seçmek (ama bu insanlarýn sevmediði
			// malzemeleri seçemezsin)

			// çok fazla þeyi sevmeyen kiþileri elemek (bunlarý getirmek çok zor)
			// çok fazla þeyi ayný anda seven kiþileri elemek (bunlarý getirmek çok zor)

			// sadece sevilmeyen malzemeleri kesinlikle ekleme
			// sadece sevilen malzemeleri kesinlikle ekle

			/*
			 * Maliyet sistemi HashMap de hangi materyali kaç kiþinin sevmediði yada sevdiði
			 * tutuluyor buna göre ben kiþiyi almaya çalýþýyor gibi yapýcam kiþinin
			 * sevmediklerine bakýcam bu kiþinin sevmediði materyalleri seven kaç kiþi var
			 * buna göre bir maliyet çýkarýcam kiþinin sevdiklerine bakýcam bu kiþinin
			 * sevdiðini materyalleri sevmeyen kaç kiþi var buna göre de bir maliyet
			 * çýkarýcam
			 * 
			 * bu kiþiye total bir maliyet atayacam ardýndan minimum maliyetli kiþilerden
			 * almaya baþlayacaðým uygunluðunu kontrol edicem uygunsa listeme ekliyecem
			 * 
			 * 
			 * mümkün olursa aldýðým kiþinin materyallerinden en az birine baðlý olanlarýn
			 * listesini o kiþinin yanýna koyup eðer bu kiþiyi alýrsam bu listedekilerin
			 * maliyetini o malzemenin maliyeti kadar düþür diyerek tekrardan sort edip en
			 * düþük maliyetliden tekrar baþlayabiliriz
			 * 
			 * Bu kiþiler materyallere baðlý olursa direkt olarak aldýðýmýz materyalin
			 * hashmapinde baðlý olan müþterilerin maliyetini o malzemenin maliyeti kadar
			 * düþürebiliriz üsttekine alternatif
			 * 
			 * burdaki soru en güzel maliyeti nasýl çýkartýrýz
			 * 
			 * eðer bir kiþiyi de almaya karar verirsem o kiþinin sevmediði malzemeleri
			 * sevenlerin maliyetini direkt sonsuz yapabiliriz
			 * 
			 * 
			 * bu kimler gelebilir kimler gelemez hesaplanýnca bu arraylistlerin
			 * benzerliðine göre birleþtirme yapmaya çalýþabiliriz.
			 *
			 */

			Scanner scanner = new Scanner(file);
			File outputFile = new File(fileName.charAt(0) + "_output.txt");
			FileWriter fileWriter = new FileWriter(outputFile);

			// actual code

			// a dosyasý çözüm
			/*
			 * fileWriter.write("4 cheese mushrooms tomatoes peppers");
			 */

			// b dosyasý çözüm
			/*
			 * int totalCustomer = scanner.nextInt(); ArrayList<String> materials = new
			 * ArrayList<String>(); scanner.nextLine(); for(int i = 0; i < totalCustomer;
			 * i++) {
			 * 
			 * 
			 * String line = scanner.nextLine(); String str[] = line.split(" ");
			 * 
			 * for(int j = 1; j < str.length; j++) { if(!materials.contains(str[j])) {
			 * materials.add(str[j]); }
			 * 
			 * } scanner.nextLine();
			 * 
			 * } fileWriter.write(materials.size()+" "); for(int i = 0; i <
			 * materials.size(); i++) { fileWriter.write(materials.get(i)+" "); }
			 */
			//

			// c-d-e dosyasý

			int totalCustomer = scanner.nextInt();

			HashMap<String, ArrayList<Customer>> likedMaterials = new HashMap<String, ArrayList<Customer>>();
			HashMap<String, ArrayList<Customer>> dislikedMaterials = new HashMap<String, ArrayList<Customer>>();

			ArrayList<Customer> customers = new ArrayList<Customer>();

			scanner.nextLine();

			for (int i = 0; i < totalCustomer; i++) {
				Customer customer = new Customer();

				String line = scanner.nextLine();
				String str[] = line.split(" ");

				for (int j = 1; j < str.length; j++) {
					customer.getLikedMaterials().add(str[j]);
				}

				line = scanner.nextLine();
				str = line.split(" ");

				for (int j = 1; j < str.length; j++) {
					customer.getDislikedMaterials().add(str[j]);
				}

				customers.add(customer);

			}

			for (int i = 0; i < totalCustomer; i++) {

				for (int j = 0; j < customers.get(i).getLikedMaterials().size(); j++) {
					if (likedMaterials.containsKey(customers.get(i).getLikedMaterials().get(j))) {
						likedMaterials.get(customers.get(i).getLikedMaterials().get(j)).add(customers.get(i));
					} else {
						likedMaterials.put(customers.get(i).getLikedMaterials().get(j), new ArrayList<Customer>());
						likedMaterials.get(customers.get(i).getLikedMaterials().get(j)).add(customers.get(i));
					}

				}

				for (int j = 0; j < customers.get(i).getDislikedMaterials().size(); j++) {

					if (dislikedMaterials.containsKey(customers.get(i).getDislikedMaterials().get(j))) {
						dislikedMaterials.get(customers.get(i).getDislikedMaterials().get(j)).add(customers.get(i));
					} else {
						dislikedMaterials.put(customers.get(i).getDislikedMaterials().get(j),
								new ArrayList<Customer>());
						dislikedMaterials.get(customers.get(i).getDislikedMaterials().get(j)).add(customers.get(i));
					}

				}
			}

			for (int i = 0; i < totalCustomer; i++) {

				int customerCost = 0;
				HashMap<Integer, Integer> obtainableCustomers = new HashMap<Integer, Integer>();
				HashMap<Integer, Integer> unobtainableCustomers = new HashMap<Integer, Integer>();
				for (int j = 0; j < customers.get(i).getLikedMaterials().size(); j++) {

					// bu kiþinin sevdiði materyalleri sevenleri kaydet
					for (int k = 0; k < likedMaterials.get(customers.get(i).getLikedMaterials().get(j)).size(); k++) {

						obtainableCustomers.put(
								likedMaterials.get(customers.get(i).getLikedMaterials().get(j)).get(k).getCustomerId(),
								0);

					}

					// bu kiþinin sevdiði materyalleri sevmeyenleri kaydet
					for (int k = 0; dislikedMaterials.get(customers.get(i).getLikedMaterials().get(j)) != null
							&& k < dislikedMaterials.get(customers.get(i).getLikedMaterials().get(j)).size(); k++) {

						unobtainableCustomers.put(dislikedMaterials.get(customers.get(i).getLikedMaterials().get(j))
								.get(k).getCustomerId(), 0);

					}

				}

				// bu kiþinin sevmediði materyalleri sevenleri getirilemez müþteri olarak kaydet
				for (int j = 0; j < customers.get(i).getDislikedMaterials().size(); j++) {

					for (int k = 0; likedMaterials.get(customers.get(i).getDislikedMaterials().get(j)) != null
							&& k < likedMaterials.get(customers.get(i).getDislikedMaterials().get(j)).size(); k++) {

						unobtainableCustomers.put(likedMaterials.get(customers.get(i).getDislikedMaterials().get(j))
								.get(k).getCustomerId(), 0);

					}

				}

				// Getirilemez görünen ama getirilebilir mapinde bulunanlarý kaldýr
				for (Integer key : unobtainableCustomers.keySet()) {

					obtainableCustomers.remove(key);
				}

				obtainableCustomers.remove(i + 1);
				customerCost = obtainableCustomers.size() - unobtainableCustomers.size();
				customers.get(i).setCustomerCost(customerCost);
				customers.get(i).setObtainable(obtainableCustomers.keySet());
				customers.get(i).setUnobtainable(unobtainableCustomers.keySet());

			}

			ArrayList<String> pizzaMaterials = new ArrayList<String>();
			ArrayList<String> nonPizzaMaterials = new ArrayList<String>();

			for (int k = 0; k < customers.size(); k++) {

				customers.sort((Customer o1, Customer o2) -> {
					return o2.getCustomerCost() - o1.getCustomerCost();
				});

				Customer customer = customers.get(0);
				//customers.remove(0);

				pizzaMaterials.addAll(customer.getLikedMaterials());
				nonPizzaMaterials.addAll(customer.getDislikedMaterials());

				//bu customerin sevdiði materyalleri dolaþ
				ArrayList<String> customerLiked = (ArrayList<String>) customer.getLikedMaterials().clone();
				
				for(int i = 0; i < customerLiked.size(); i++) {
					
					String material = customerLiked.get(i);
					System.out.println("Added Material: "+ material);
					
					//bu materyalleri seven kiþileri likedMaterials tablosundan bulup 
					//bu materyalleri bu kiþilerin liked materiallerinden sil
					
					//seven kiþileri dolaþýyoruz
					
					for(int j = 0; likedMaterials.get(material) != null && j < likedMaterials.get(material).size(); j++) {
						
						//kiþi
						//likedMaterials.get(material).get(j);
						
						likedMaterials.get(material).get(j).addCustomerCost();
						likedMaterials.get(material).get(j).removeLiked(material);
						
						
					}
					
					likedMaterials.remove(material);
				}
				
				
				
				//bu customerin sevmediði materyalleri dolaþ
				ArrayList<String> customerDisliked = (ArrayList<String>) customer.getDislikedMaterials().clone();
				
				for(int i = 0; i < customerDisliked.size(); i++) {
					
					String material = customerDisliked.get(i);
					System.out.println("Unobtainable Material: "+material);
					
					//bu materyalleri seven kiþileri likedMaterials tablosundan bulup 
					//silicez ayný zamanda customer arraylistinden de silicez
					
					//sevmediðimizi seven kiþileri dolaþýyoruz
					ArrayList<Customer> unobtainableCustomers = null;
					if(likedMaterials.get(material)!=null) {
						unobtainableCustomers = (ArrayList<Customer>) likedMaterials.get(material).clone();
					}
					 
					for(int j = 0;  unobtainableCustomers != null && j < unobtainableCustomers.size(); j++) {
						
						
						System.out.println(likedMaterials.get(material));
						
						Customer unobtainable = unobtainableCustomers.get(j);//sevmediðimizi seven kiþi 
						
						
						ArrayList<String> unobtainableMaterials = (ArrayList<String>) unobtainable.getLikedMaterials().clone();//sevmediðimizi seven kiþilenin sevdiði materyaller
						for(int l = 0; l < unobtainableMaterials.size(); l++) {
							
							//ArrayList<Customer> customersThatLoveWhatIDont = likedMaterials.get(likedMaterials.get(material).get(j).getLikedMaterials().get(l));
							likedMaterials.get(unobtainableMaterials.get(l)).remove(unobtainable);
							
						}
						
						
						
						//kiþiyi direkt sil
						customers.remove(unobtainable);
						//
						//j--;
					}
					
					likedMaterials.remove(material);
				}
				

				k--;
				if(customers.size()!=0) {
					customers.remove(0);
				}
				
				
				
				/*int loop = customer.getLikedMaterials().size();
				for (int i = 0; i < loop; i++) {
					int loop2 = likedMaterials.get(customer.getLikedMaterials().get(0)).size();
					for (int j = 0; j < loop2; j++) {
						likedMaterials.get(customer.getLikedMaterials().get(0)).get(0).addCustomerCost();
						//likedMaterials.get(customer.getLikedMaterials().get(0)).get(j).removeLiked(customer.getLikedMaterials().get(0));
						likedMaterials.get(customer.getLikedMaterials().get(0)).get(0).removeLiked(customer.getLikedMaterials().get(0));
						likedMaterials.get(customer.getLikedMaterials().get(0)).remove(0);
						
						
					}
					likedMaterials.remove(customer.getLikedMaterials().get(0));
					customer.getLikedMaterials().remove(0);
					
				}*/
			}

			// obtainable olanlara bak
			// en yüksek costluyu bul
			// git onu alabiliyon mu bak
			// alamýyosan at gitsin
			fileWriter.write(pizzaMaterials.size()+" ");
			for(int i = 0; i < pizzaMaterials.size(); i++) {
				fileWriter.write(pizzaMaterials.get(i)+" ");
			}

			
			// fileWriter.write(dislikedMaterials.toString());

			fileWriter.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Dosya Bulunamadý!");
		} catch (IOException e) {
			System.out.println("Muhtemel FileWriter Hatasý");
		}
	}

}
