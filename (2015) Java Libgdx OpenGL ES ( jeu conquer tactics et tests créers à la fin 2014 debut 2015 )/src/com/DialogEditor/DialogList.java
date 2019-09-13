package com.DialogEditor;

import com.badlogic.gdx.utils.Array;


public class DialogList {
	protected Array<Dialogue> liste;
	public DialogList(){
		liste=new Array<Dialogue>();
	}
	

	public String getNom(int i) {
		return liste.get(i).getNom();
	}

	public String getPhrase(int i) {
		return liste.get(i).getPhrase();
	}
	public Array<Dialogue> Liste(){
		return liste;
	}
	public void Add(Dialogue d){
		liste.add(d);
	}
	public int size(){
		return liste.size;
	}
	public Dialogue get(int i){
		return liste.get(i);
	}

}