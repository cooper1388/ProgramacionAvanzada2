package hn.uth.bibliotecavirtual.data.entity;

import java.time.LocalDate;

public class Libro {

	private String key;
    private String autor;
    private String obraMasCelebre;
    private LocalDate fechaNacimiento;
    private Integer cantidadLibros;

    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getObraMasCelebre() {
        return obraMasCelebre;
    }
    public void setObraMasCelebre(String obraMasCelebre) {
        this.obraMasCelebre = obraMasCelebre;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public Integer getCantidadLibros() {
        return cantidadLibros;
    }
    public void setCantidadLibros(Integer cantidadLibros) {
        this.cantidadLibros = cantidadLibros;
    }
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
