package villagegaulois;

import myExceptions.VillageSansChefException;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.marche = new Marche(nbEtals);
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

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if (chef == null)
			throw new VillageSansChefException("Le village " + nom + " n'a pas de chef");
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder text = new StringBuilder(
				vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + produit + "\n");
		int posEtal = marche.trouverEtalLibre();
		if (posEtal == -1) {
			text.append("Aucun étal disponible pour " + vendeur.getNom());
		} else {
			marche.utiliserEtal(posEtal, vendeur, produit, nbProduit);
			text.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + posEtal + "\n");
		}
		return text.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder text = new StringBuilder("Les vendeurs qui proposent des " + produit + " sont :\n");
		Etal[] listEtal = marche.trouverEtals(produit);
		for (int i = 0; i < listEtal.length; i++) {
			text.append("	- " + listEtal[i].getVendeur().getNom() + "\n");
		}
		return text.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		StringBuilder text = new StringBuilder();
		try {
			Etal etal = marche.trouverVendeur(vendeur);
			text.append(etal.libererEtal());

		} catch (IllegalStateException e) {
			text.append(e.getMessage());
			e.printStackTrace();
		}
		return text.toString();
	}

	public String afficherMarche() {
		return marche.afficherMarche();
	}


	private static class Marche {
		private Etal[] etals;
		private int nbEtals;

		Marche(int nbEtals) {
			this.nbEtals = nbEtals;
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		int trouverEtalLibre() {
			int ind = 0;
			while (etals[ind].isEtalOccupe() && ind < etals.length) {
				ind++;
			}
			return (ind == etals.length ? -1 : ind);
		}

		Etal[] trouverEtals(String produit) {
			Etal[] listeEtals = new Etal[nbEtals];
			int ind = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					listeEtals[ind] = etals[i];
					ind++;
				}
			}
			Etal[] sListeEtals = new Etal[ind];
			for (int i = 0; i < ind; i++) {
				sListeEtals[i] = listeEtals[i];
			}
			return sListeEtals;
		}

		 Etal trouverVendeur(Gaulois gaulois) {
			 Etal etal = null;
			 for(int i = 0; i < etals.length; i++) {
				 if(etals[i].getVendeur().equals(gaulois) ) {
					 etal = etals[i];
					 i = etals.length - 1;
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