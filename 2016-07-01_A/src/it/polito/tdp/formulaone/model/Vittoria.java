package it.polito.tdp.formulaone.model;

public class Vittoria {
	
	private int id1;
	private int id2;
	private int numeroVittore;
	public Vittoria(int id1, int id2, int numeroVittore) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.numeroVittore = numeroVittore;
	}
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public int getNumeroVittore() {
		return numeroVittore;
	}
	public void setNumeroVittore(int numeroVittore) {
		this.numeroVittore = numeroVittore;
	}
	

}
