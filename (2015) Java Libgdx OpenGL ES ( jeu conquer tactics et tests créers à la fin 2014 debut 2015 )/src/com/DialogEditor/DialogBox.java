package com.DialogEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.tph.Resources;


public class DialogBox{ 
	

    protected static Window window;
    protected float textlength=0,textspeed=50; //SPEED
    protected Label label;
    protected Skin skin;
    protected Dialogue p;
    protected int begin=0;
	String[] lines;
	protected byte[] c;
	
	public DialogBox(final String text,Skin skin,Dialogue p){
		this.skin=skin;
		window=new Window(text,skin);
		this.p=p;
		window.pad(20);
		window.setWidth(Gdx.graphics.getWidth()*.75f);
	}
	public boolean Next(Dialogue px){
		window.clear();
		textlength=0;
		this.p=px;
		Texture t=Resources.getManager().get(p.getImage()+".png", Texture.class);
		Image m=new Image(t);
		VerticalGroup texts=new VerticalGroup();
		window.add(m).width(128).height(128).left();
		label=new Label("\n\n\n\n",skin);
        label.setStyle(new Label.LabelStyle(Resources.getFont("font/koren.ttf",22, Color.WHITE),Color.WHITE));
		texts.addActor(label);
		texts.left().pad(10);
		window.add(texts).width((Gdx.graphics.getWidth()*.75f)-150);
		c=p.getPhrase().getBytes();
		
		lines = p.getPhrase().split("\r\n|\r|\n");
	
		return true;
	}
	public Window getWindow(){
		return window;
	}
	public boolean update(float delta){
		int length=(int)textlength;
		String sum="";
		//sum=p.getPhrase();		
		if(lines.length-3<begin)
		{
			
			sum=sumOf(lines,begin,lines.length);
			if(length>=sum.length())
				return true;
		}
		else
		{
			sum=sumOf(lines,begin,begin+3);
			if(length>=sum.length())
			{
				begin++;

				textlength=sumOf(lines,begin,begin+2).length();
			}
			
		}
		
		if(length>=sum.length())
			return true;
			textlength+=textspeed*delta;
		label.setText(p.getNom()+": "+"\n"+sum.substring(0,length));
		
		return false;
	}
	public String sumOf(String[] chaine, int from,int to){
		String somme="";
		for(int i=from;i<to;i++)
			somme+=(chaine[i]+"\n");
		return somme;
	}
	
}
