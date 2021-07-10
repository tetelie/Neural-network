package fr.elie;

public class PointND {
	
	public double[] entrees;
	public double[] sorties;
	
	public PointND(String str, int _nbSorties)
	{
		String[] contenu = str.split("\t");
		entrees = new double[contenu.length - _nbSorties];
		for(int i = 0; i < entrees.length; i++)
		{
			entrees[i] = Double.parseDouble(contenu[i]);
		}
		sorties = new double[_nbSorties];
		for(int i = 0; i < _nbSorties; i++)
		{
			sorties[i] = Double.parseDouble(contenu[entrees.length + i]);
		}
	}

}
