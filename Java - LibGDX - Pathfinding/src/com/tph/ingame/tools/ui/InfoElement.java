package com.tph.ingame.tools.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tph.Resources;
import com.tph.ingame.tools.characters.Character;

public class InfoElement{

	private Window box;
	private VerticalGroup group;
	private Image image;
	private Label infos,titre,lvl;
	
	public InfoElement() {

		Skin skin = Resources.getManager().get("ui/menuSkin.json");
		
		BitmapFont font= Resources.getFont("font/euphorigenic.ttf", 20, Color.WHITE);
		LabelStyle style=new LabelStyle(font,Color.WHITE);
		
		box=new Window("", skin,"mod");
		group      = new VerticalGroup  (); 
		image      = new Image ();
		infos      = new Label("", skin );
		titre	   = new Label("", skin );
		lvl	   	   = new Label("", skin );

		infos.setStyle(style);
		lvl  .setStyle(style);
		titre.setStyle(style);
                                      
		box.pad(10);
		group.addActor(infos);
		box.add(image).left().width(64).height(64).space(10);
		box.add(titre).left();
		box.add(lvl).spaceLeft(20).row();
		box.add(group).colspan(2);
		
	}
	public void setElement(Character element){
		
		if( element == null )
			return;
		
		if(element.getLogo_img()!=null)
			image.setDrawable(new TextureRegionDrawable(element.getLogo_img()));

		image.setSize(64,64);
		titre.setText(element.getNom()+" - "+element.getStats().getUrl());
		String info="";
		info+=" Hp "+element.getStats().getHp()+" / "+element.getStats().getHP_MAX();
		info+="\n"+element.getDescription();
		
		infos.setText(info);
		lvl.setText(" - lvl "+element.getStats().getLvl());
	}
	public Window getBox() {
		return box;
	}

}
