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

			// e�erki bir m��terinin sevdi�i t�m malzemeler pizzan�n i�indeyse
			// ve sevmedi�i hi�bir malzeme yoksa o zaman bu m��teri geliyor.

			// bu soruyla ilgili birka� fikir
			// unique sadece sevilen malzemeleri se�mek
			// en �ok ki�inin sevdi�i malzemeleri se�mek (ama bu insanlar�n sevmedi�i
			// malzemeleri se�emezsin)

			// �ok fazla �eyi sevmeyen ki�ileri elemek (bunlar� getirmek �ok zor)
			// �ok fazla �eyi ayn� anda seven ki�ileri elemek (bunlar� getirmek �ok zor)

			// sadece sevilmeyen malzemeleri kesinlikle ekleme
			// sadece sevilen malzemeleri kesinlikle ekle

			/*
			 * Maliyet sistemi HashMap de hangi materyali ka� ki�inin sevmedi�i yada sevdi�i
			 * tutuluyor buna g�re ben ki�iyi almaya �al���yor gibi yap�cam ki�inin
			 * sevmediklerine bak�cam bu ki�inin sevmedi�i materyalleri seven ka� ki�i var
			 * buna g�re bir maliyet ��kar�cam ki�inin sevdiklerine bak�cam bu ki�inin
			 * sevdi�ini materyalleri sevmeyen ka� ki�i var buna g�re de bir maliyet
			 * ��kar�cam
			 * 
			 * bu ki�iye total bir maliyet atayacam ard�ndan minimum maliyetli ki�ilerden
			 * almaya ba�layaca��m uygunlu�unu kontrol edicem uygunsa listeme ekliyecem
			 * 
			 * 
			 * m�mk�n olursa ald���m ki�inin materyallerinden en az birine ba�l� olanlar�n
			 * listesini o ki�inin yan�na koyup e�er bu ki�iyi al�rsam bu listedekilerin
			 * maliyetini o malzemenin maliyeti kadar d���r diyerek tekrardan sort edip en
			 * d���k maliyetliden tekrar ba�layabiliriz
			 * 
			 * Bu ki�iler materyallere ba�l� olursa direkt olarak ald���m�z materyalin
			 * hashmapinde ba�l� olan m��terilerin maliyetini o malzemenin maliyeti kadar
			 * d���rebiliriz �sttekine alternatif
			 * 
			 * burdaki soru en g�zel maliyeti nas�l ��kart�r�z
			 * 
			 * e�er bir ki�iyi de almaya karar verirsem o ki�inin sevmedi�i malzemeleri
			 * sevenlerin maliyetini direkt sonsuz yapabiliriz
			 * 
			 * 
			 * bu kimler gelebilir kimler gelemez hesaplan�nca bu arraylistlerin
			 * benzerli�ine g�re birle�tirme yapmaya �al��abiliriz.
			 *
			 */

			Scanner scanner = new Scanner(file);
			File outputFile = new File(fileName.charAt(0) + "_output.txt");
			FileWriter fileWriter = new FileWriter(outputFile);

			// actual code

			// a dosyas� ��z�m
			/*
			 * fileWriter.write("4 cheese mushrooms tomatoes peppers");
			 */

			// b dosyas� ��z�m
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

			// c-d-e dosyas�

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

					// bu ki�inin sevdi�i materyalleri sevenleri kaydet
					for (int k = 0; k < likedMaterials.get(customers.get(i).getLikedMaterials().get(j)).size(); k++) {

						obtainableCustomers.put(
								likedMaterials.get(customers.get(i).getLikedMaterials().get(j)).get(k).getCustomerId(),
								0);

					}

					// bu ki�inin sevdi�i materyalleri sevmeyenleri kaydet
					for (int k = 0; dislikedMaterials.get(customers.get(i).getLikedMaterials().get(j)) != null
							&& k < dislikedMaterials.get(customers.get(i).getLikedMaterials().get(j)).size(); k++) {

						unobtainableCustomers.put(dislikedMaterials.get(customers.get(i).getLikedMaterials().get(j))
								.get(k).getCustomerId(), 0);

					}

				}

				// bu ki�inin sevmedi�i materyalleri sevenleri getirilemez m��teri olarak kaydet
				for (int j = 0; j < customers.get(i).getDislikedMaterials().size(); j++) {

					for (int k = 0; likedMaterials.get(customers.get(i).getDislikedMaterials().get(j)) != null
							&& k < likedMaterials.get(customers.get(i).getDislikedMaterials().get(j)).size(); k++) {

						unobtainableCustomers.put(likedMaterials.get(customers.get(i).getDislikedMaterials().get(j))
								.get(k).getCustomerId(), 0);

					}

				}

				// Getirilemez g�r�nen ama getirilebilir mapinde bulunanlar� kald�r
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

				//bu customerin sevdi�i materyalleri dola�
				ArrayList<String> customerLiked = (ArrayList<String>) customer.getLikedMaterials().clone();
				
				for(int i = 0; i < customerLiked.size(); i++) {
					
					String material = customerLiked.get(i);
					System.out.println("Added Material: "+ material);
					
					//bu materyalleri seven ki�ileri likedMaterials tablosundan bulup 
					//bu materyalleri bu ki�ilerin liked materiallerinden sil
					
					//seven ki�ileri dola��yoruz
					
					for(int j = 0; likedMaterials.get(material) != null && j < likedMaterials.get(material).size(); j++) {
						
						//ki�i
						//likedMaterials.get(material).get(j);
						
						likedMaterials.get(material).get(j).addCustomerCost();
						likedMaterials.get(material).get(j).removeLiked(material);
						
						
					}
					
					likedMaterials.remove(material);
				}
				
				
				
				//bu customerin sevmedi�i materyalleri dola�
				ArrayList<String> customerDisliked = (ArrayList<String>) customer.getDislikedMaterials().clone();
				
				for(int i = 0; i < customerDisliked.size(); i++) {
					
					String material = customerDisliked.get(i);
					System.out.println("Unobtainable Material: "+material);
					
					//bu materyalleri seven ki�ileri likedMaterials tablosundan bulup 
					//silicez ayn� zamanda customer arraylistinden de silicez
					
					//sevmedi�imizi seven ki�ileri dola��yoruz
					ArrayList<Customer> unobtainableCustomers = null;
					if(likedMaterials.get(material)!=null) {
						unobtainableCustomers = (ArrayList<Customer>) likedMaterials.get(material).clone();
					}
					 
					for(int j = 0;  unobtainableCustomers != null && j < unobtainableCustomers.size(); j++) {
						
						
						System.out.println(likedMaterials.get(material));
						
						Customer unobtainable = unobtainableCustomers.get(j);//sevmedi�imizi seven ki�i 
						
						
						ArrayList<String> unobtainableMaterials = (ArrayList<String>) unobtainable.getLikedMaterials().clone();//sevmedi�imizi seven ki�ilenin sevdi�i materyaller
						for(int l = 0; l < unobtainableMaterials.size(); l++) {
							
							//ArrayList<Customer> customersThatLoveWhatIDont = likedMaterials.get(likedMaterials.get(material).get(j).getLikedMaterials().get(l));
							likedMaterials.get(unobtainableMaterials.get(l)).remove(unobtainable);
							
						}
						
						
						
						//ki�iyi direkt sil
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
			// en y�ksek costluyu bul
			// git onu alabiliyon mu bak
			// alam�yosan at gitsin
			fileWriter.write(pizzaMaterials.size()+" ");
			for(int i = 0; i < pizzaMaterials.size(); i++) {
				fileWriter.write(pizzaMaterials.get(i)+" ");
			}

			
			// fileWriter.write(dislikedMaterials.toString());

			fileWriter.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Dosya Bulunamad�!");
		} catch (IOException e) {
			System.out.println("Muhtemel FileWriter Hatas�");
		}
	}

}
