package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private int nbEtal;
	private Marche marche;
	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nbEtal = nbEtal;
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	

	
	
	public static class Marche {
		private Etal[] etals;
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			for(int i = 0; i < nbEtal; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		int trouverEtalLibre() {
			int etalLibre = -1;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() == false) {
					etalLibre = i;
					i = etals.length;
				}
			}
			return etalLibre;
		}
		
		Etal[] trouverEtals(String produit) {
			int nbEtalsProd = 0;
			Etal[] etalsProd;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit) == true) {
					nbEtalsProd++;
				}
			}
			etalsProd = new Etal[nbEtalsProd];
			int ind = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit) == true) {
					etalsProd[ind] = etals[i];
					ind++;
				}
			}
			return etalsProd;
		}
		
		 Etal trouverVendeur(Gaulois gaulois) {
			 Etal etal = null;
			 for(int i = 0; i < etals.length; i++) {
				 if(etals[i].getVendeur().equals(gaulois) ) {
					 etal = etals[i];
					 i = etals.length;
				 }
			 }
		 return etal;
		 }
		 
		 String afficherMarche(){
			 String chaine = new String();
			 int nbEtalVide = 0;
			 for(int i = 0; i < etals.length; i++) {
				 if(etals[i].isEtalOccupe() == true) {
				 	chaine = chaine + etals[i].afficherEtal();
				 }
				 else {
					 nbEtalVide++;
				 }
			 }
			 chaine = chaine + "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
			 return chaine;
		 }
		 
	}
}