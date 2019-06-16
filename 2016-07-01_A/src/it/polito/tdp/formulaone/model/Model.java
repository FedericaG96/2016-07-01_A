package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	FormulaOneDAO dao;
	Graph<Driver, DefaultWeightedEdge> grafo ;
	Map<Integer, Driver> idMapDrivers;
	List<Driver> drivers;
	List<Vittoria> vittorie;
	
	//ricorsione
	List<Driver> ottima ;
	int tassoMin;
	
	public Model() {
		dao = new FormulaOneDAO();
		grafo = new SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		idMapDrivers = new HashMap<Integer, Driver>();
		
	}

	public List<Integer> getYears() {
		return dao.getAllYearsOfRace();
	}

	public void creaGrafo(Integer anno) {
		
		drivers =  dao.getDriver(anno);
		for(Driver d : drivers) {
			idMapDrivers.put(d.getDriverId(), d);
		}
		 
		vittorie = dao.getVittorie(anno);
		for(Vittoria v: vittorie) {
			Driver d1 = idMapDrivers.get(v.getId1());
			Driver d2 = idMapDrivers.get(v.getId2());
			Graphs.addEdgeWithVertices(grafo, d1, d2, v.getNumeroVittore());
		}
	}

	public Driver getPilotaMigliore() {
		Driver best = null;
		int bestRis = Integer.MIN_VALUE;
		
		for(Driver d1 : grafo.vertexSet()) {
			if(this.risultato(d1)> bestRis) {
				best = d1;
				bestRis = this.risultato(d1);
			}
		}
		return best;
	}

	private int risultato(Driver d1) {
		int peso = 0;
		
		for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(d1)) {
			peso += grafo.getEdgeWeight(e);
		}
		for(DefaultWeightedEdge e : grafo.incomingEdgesOf(d1)) {
			peso -= grafo.getEdgeWeight(e);
		}
		
		return peso;
	}

	public List<Driver> trovaDreamTeam(int dimensioneTeam) {
		ottima = new ArrayList<>();
		
		tassoMin = Integer.MAX_VALUE;
		
		List<Driver> parziale = new ArrayList<>();
		
		recursive(0,parziale, dimensioneTeam);
		
		return ottima;
}
	
	/**
	 * In ingresso ricevo il {@code team} parziale composto da {@code passo} elementi.
	 * La variabile {@code passo} parte da 0.
	 * Il caso terminale è quanto {@code passo==K}, ed in quel caso va calcolato il 
	 * tasso di sconfitta.
	 * Altrimenti, si procede ricorsivamente ad aggiungere 
	 * un nuovo vertice (il passo+1-esimo), scegliendolo tra i vertici
	 * non ancora presenti nel {@code team}.
	 * 
	 * @param passo
	 * @param team
	 * @param K
	 */

	private void recursive(int livello, List<Driver> parziale, Integer dimensioneTeam) {
		
		if(livello==dimensioneTeam) {
			int tasso = this.calcolaTasso(parziale);
			
			if(tasso<tassoMin) {
				tassoMin = tasso;
				ottima = new ArrayList<>(parziale);
			}
			
		} else {
		
		List<Driver> candidati = new ArrayList<>(grafo.vertexSet());
		candidati.removeAll(parziale);
		
		for(Driver d : candidati) {
			
				parziale.add(d);
				this.recursive(livello + 1, parziale, dimensioneTeam);
				parziale.remove(d);
			}
		}
	}

	private int calcolaTasso(List<Driver> parziale) {
		int tasso = 0;
				
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			if(!parziale.contains(grafo.getEdgeSource(e)) && parziale.contains(grafo.getEdgeTarget(e))) {
				tasso += (int)grafo.getEdgeWeight(e);
			}
		}
		
		return tasso;
	}


}
