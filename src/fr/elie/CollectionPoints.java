package fr.elie;

import java.util.ArrayList;
import java.util.Random;

public class CollectionPoints {
	
	protected PointND[] ptsApprentissage;
	protected PointND[] ptsGeneralisation;
	
	public CollectionPoints(String[] _contenu, int _nbSorties, double _ratioAprentissage)
	{
		
		// lecture du fichier
		int nbLignes = _contenu.length;
		ArrayList<PointND> points = new ArrayList<>();
		for(int i = 0; i < nbLignes; i++)
		{
			points.add(new PointND(_contenu[i], _nbSorties));
		}
		
		// Création des points d'apprentissage
		int nbPtsApprentissage = (int) (nbLignes * _ratioAprentissage);
		ptsApprentissage = new PointND[nbPtsApprentissage];
		Random generateur = new Random();
		
		for(int i = 0; i < nbPtsApprentissage; i++)
		{
			int index = generateur.nextInt(points.size());
			ptsApprentissage[i] = points.get(index);
			points.remove(index);
		}
		
		// Création des points de généralisation
		ptsGeneralisation = (PointND[]) points.toArray(new PointND[points.size()]);
		
		
		
	}
	
	public PointND[] getPtsApprentissage() {
		return ptsApprentissage;
	}
	public PointND[] getPtsGeneralisation() {
		return ptsGeneralisation;
	}

}
