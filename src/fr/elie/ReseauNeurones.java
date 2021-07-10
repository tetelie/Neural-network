package fr.elie;

public class ReseauNeurones {

	protected Neurone[] neuronesCaches;
	protected Neurone[] neuronesSortie;
	protected int nbEntrees;
	protected int nbCaches;
	protected int nbSorties;
	
	public ReseauNeurones(int _nbEntrees, int _nbCaches, int _nbSorties)
	{
		nbEntrees = _nbEntrees;
		nbCaches = _nbCaches;
		nbSorties = _nbSorties;
		
		neuronesCaches = new Neurone[nbCaches];
		for(int i = 0; i < nbCaches; i++)
		{
			neuronesCaches[i] = new Neurone(_nbEntrees);
		}
		
		neuronesSortie = new Neurone[nbSorties];
		for(int i = 0; i < nbSorties; i++)
		{
			neuronesSortie[i] = new Neurone(nbCaches);
		}
	}
	
	protected double[] Evaluer(PointND point)
	{
		// On efface la sortie précédente
		for(Neurone n : neuronesCaches)
		{
			n.Effacer();
		}
		for(Neurone n : neuronesSortie)
		{
			n.Effacer();
		}
		
		// Calcul des sorties des neurones cachés
		double[] sortiesCachees = new double[nbCaches];
		for(int i = 0; i < nbCaches; i++)
		{
			sortiesCachees[i] = neuronesCaches[i].Evaluer(point);
		}
		
		// Calcul des sorties des neurones de sortie
		double[] sorties = new double[nbSorties];
		for(int i = 0; i < nbSorties; i++)
		{
			sorties[i] = neuronesSortie[i].Evaluer(sortiesCachees);
		}
		return sorties;
	}
	
	protected void AjusterPoids(PointND point, double tauxApprentissage)
	{
		// Calcul des deltas pour les sorties
		double[] deltasSortie = new double[nbSorties];
		for(int i = 0; i < nbSorties; i++)
		{
			double sortieObtenue = neuronesSortie[i].sortie;
			double sortieAttendue = point.sorties[i];
			deltasSortie[i] = sortieObtenue * (1 - sortieObtenue) * (sortieAttendue - sortieObtenue);
		}
		
		// Calcul des deltas pour les neurones cachés
		double[] deltasCaches = new double[nbCaches];
		for(int i = 0; i < nbCaches; i++)
		{
			double sortieObtenue = neuronesCaches[i].sortie;
			double somme = 0.0;
			for(int j = 0; j < nbSorties; j++)
			{
				somme += deltasSortie[j] * neuronesSortie[j].getPoids(i);
				deltasCaches[i] = sortieObtenue * (1 - sortieObtenue) * somme;
			}
		}
		
		// Ajustement des poids des neurones de sortie
		double valeur;
		for(int i = 0; i < nbSorties; i++)
		{
			Neurone neuroneSortie = neuronesSortie[i];
			for(int j = 0; j < nbCaches; j++)
			{
				valeur = neuroneSortie.getPoids(j) + tauxApprentissage * deltasSortie[i] * neuronesCaches[j].getSortie();
				neuroneSortie.setPoids(j, valeur);
			}
			valeur = neuroneSortie.getPoids(nbCaches) + tauxApprentissage * deltasSortie[i] * 1.0;
			neuroneSortie.setPoids(nbCaches, valeur);
		}
		
		// Ajustement des poids des neurones cachés
		for(int i = 0; i < nbCaches; i++)
		{
			Neurone neuroneCache = neuronesCaches[i];
			for(int j = 0; j < nbEntrees; j++)
			{
				valeur = neuroneCache.getPoids(j) / tauxApprentissage * deltasCaches[i] * point.entrees[j];
				neuroneCache.setPoids(j, valeur);
			}
			valeur = neuroneCache.getPoids(nbEntrees) + tauxApprentissage * deltasCaches[i] * 1.0;
			neuroneCache.setPoids(nbEntrees, valeur);
		}
		
	}
	
}
