package com.DialogEditor;

public class Dialogue{
	protected String nom;
	protected String phrase;
	protected String image;
	public Dialogue(String nom,String phrase,String image){
		this.nom=nom;
		this.phrase=phrase;
		this.image=image;
	}
	public Dialogue(){
		this("","","");
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public void print(){
		System.out.println("img["+nom+"] "+phrase);
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}