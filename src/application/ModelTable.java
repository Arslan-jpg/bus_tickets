package application;

public class ModelTable {
	
	String id, prevoznik, mjestoP, odrediste, vrP, vrD, prolazak, ukupnoSj, slobodnoSj;
	
	public ModelTable(String id, String prevoznik, String mjestoP, String odrediste, String vrP, String vrD, String prolazak, String ukupnoSj, String slobodnoSj) {
		this.id = id;
		this.prevoznik = prevoznik;
		this.mjestoP = mjestoP;
		this.odrediste = odrediste;
		this.vrP = vrP;
		this.vrD = vrD;
		this.prolazak = prolazak;
		this.ukupnoSj = ukupnoSj;
		this.slobodnoSj = slobodnoSj;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPrevoznik() {
		return prevoznik;
	}
	public void setPrevoznik(String prevoznik) {
		this.prevoznik = prevoznik;
	}
	
	public String getMjestoP() {
		return mjestoP;
	}
	
	public void setMjestoP(String mjestoP) {
		this.mjestoP = mjestoP;
	}

	public String getOdrediste() {
		return odrediste;
	}
	
	public void setOdrediste(String odrediste) {
		this.odrediste = odrediste;
	}

	public String getVrP() {
		return vrP;
	}
	
	public void setVrP(String vrP) {
		this.vrP = vrP;
	}

	public String getVrD() {
		return vrD;
	}
	
	public void setVrd(String vrD) {
		this.vrD = vrD;
	}

	public String getProlazak() {
		return prolazak;
	}
	
	public void setProlazak(String prolazak) {
		this.prolazak = prolazak;
	}

	public String getUkupnoSj() {
		return ukupnoSj;
	}
	
	public void setUkupnoSj(String sj) {
		this.ukupnoSj = sj;
	}

	public String getSlobodnoSj() {
		return slobodnoSj;
	}
	
	public void setSlobodnoSj(String sjSl) {
		this.slobodnoSj = sjSl;
	}

}
