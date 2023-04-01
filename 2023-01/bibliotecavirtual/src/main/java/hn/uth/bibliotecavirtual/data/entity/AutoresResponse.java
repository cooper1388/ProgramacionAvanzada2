package hn.uth.bibliotecavirtual.data.entity;

import java.util.List;

public class AutoresResponse {
	private int numFound;
	private int start;
	private boolean numFoundExact;
	private List<Autor> docs;
	
	public int getNumFound() {
		return numFound;
	}
	public void setNumFound(int numFound) {
		this.numFound = numFound;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public boolean isNumFoundExact() {
		return numFoundExact;
	}
	public void setNumFoundExact(boolean numFoundExact) {
		this.numFoundExact = numFoundExact;
	}
	public List<Autor> getDocs() {
		return docs;
	}
	public void setDocs(List<Autor> docs) {
		this.docs = docs;
	}
	
	
}
