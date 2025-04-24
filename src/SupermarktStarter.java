public class SupermarktStarter {

	public static void main(String[] args) {
			
		Datenbank datenbank = new Datenbank();
		Beliebtheitsgraph beliebtheitsgraph = new Beliebtheitsgraph(datenbank);
	    new Kassierer(datenbank, beliebtheitsgraph);
	    new LagermitarbeiterView(datenbank, beliebtheitsgraph);
	    new KundeView(datenbank);
	    new ProduktverwaltungsView(datenbank);
	        
	}

}
