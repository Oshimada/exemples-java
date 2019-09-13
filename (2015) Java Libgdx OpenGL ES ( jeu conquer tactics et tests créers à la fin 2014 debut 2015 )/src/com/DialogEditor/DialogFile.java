package com.DialogEditor;

import com.badlogic.gdx.utils.Array;

public class DialogFile	 {

	protected Array<String> persos;
	Array<String> images;
	protected int nb_persos;
	protected DialogList dialog;
	
	public DialogFile(int nb_persos,Array<String> persos,Array<String> images){
		this.persos= persos;
		this.nb_persos=nb_persos;
		this.images=images;
		dialog=new DialogList();
	}
	
	
	public DialogList getDialog() {
		return dialog;
	}


	public void setDialog(DialogList dialog) {
		this.dialog = dialog;
	}


	public Array<String> getPersos() {
		return persos;
	}




	public void setPersos(Array<String> persos) {
		this.persos = persos;
	}




	public int getNb_persos() {
		return nb_persos;
	}




	public void setNb_persos(int nb_persos) {
		this.nb_persos = nb_persos;
	}


	public Array<String> getImages() {
		return images;
	}


	public void setImages(Array<String> images) {
		this.images = images;
	}
	
}
