package com.DialogEditor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.tph.Resources;

public class SceneEditor implements Screen {
	
	protected Table  table;
	protected Skin skin;
	protected DialogFile file;
	protected String src;
	protected Array<VerticalGroup> content;
	protected Array<TextArea> texts;
	protected Array<CheckBox> tabs;
	protected Array<TextButton> next;

	public SceneEditor(String src,DialogFile file){
		this.file=file;
		this.src=src;

		skin=Resources.getManager().get("ui/menuSkin.json");
	}
	@Override
	public void show() {
		table=new Table();
		next=new Array<TextButton>();
		VerticalGroup tabsGroup=new VerticalGroup();

		final ButtonGroup<CheckBox> radiogroup=new ButtonGroup<CheckBox>();
		tabs=new Array<CheckBox>();
		texts=new Array<TextArea>();
		
		for(int i=0;i<file.getNb_persos();i++)
					tabs.add(new CheckBox(file.getPersos().get(i),skin));
		for(CheckBox s:tabs){
			tabsGroup.addActor(s);
			radiogroup.add(s);
		}
		
		TextButton retour=new TextButton("Back", skin);
		retour.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new EditorScreen());
			}
		});
		tabsGroup.space(50);
		tabsGroup.addActor(retour);
		
		
		content=new Array<VerticalGroup>();

		for(int i=0;i<file.getNb_persos();i++){
			content.add(new VerticalGroup());
			setupStack(i,content.get(i),file.getPersos().get(i));
		}
		
		final Stack stack=new Stack();
		for(VerticalGroup v:content)
				stack.add(v);

		ChangeListener tab_listener = new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                
            	for(int i=0;i<content.size;i++)
            	content.get(i).setVisible(tabs.get(i).isChecked());
				
            }
        };
		for(CheckBox tab:tabs)
			tab.addListener(tab_listener);
		
    	for(int i=0;i<content.size;i++)
        	content.get(i).setVisible(tabs.get(i).isChecked());
    	
    	table.addListener(new InputListener(){
    		@Override
    		public boolean keyUp(InputEvent event, int keycode) {
    			if(keycode==Keys.CONTROL_LEFT ||keycode==Keys.CONTROL_RIGHT || keycode==Keys.ALT_RIGHT){
    				for(int i=0;i<tabs.size;i++)
						if(tabs.get(i).isChecked())
                        {
								file.getDialog().Add( new Dialogue(""+tabs.get(i).getText(),texts.get(i).getText(),file.getImages().get(i) ));
								texts.get(i).setText("");
								texts.get(i).setCursorPosition(file.getPersos().get(i).length()+3);
				    	}
				}
    			if(keycode==Keys.TAB){
    				int n=0;
					for(int i=0;i<tabs.size;i++)
								if(tabs.get(i).isChecked())
											n=(i+1)%tabs .size;
					tabs.get(n).setChecked(true);
					Resources.stage.setKeyboardFocus(texts.get(n));
					texts.get(n).setCursorPosition(tabs.get(n).getText().length()+3);
    			}
    			return true;
    		}
    	});
    	

    	tabsGroup.left();
    	table.setFillParent(true);
		table.pad(50);
		table.add(tabsGroup).expandX().fill().space(50);
		table.add(stack).maxWidth(600).expandX().fill().center();
		
		Resources.stage.clear();
		Resources.stage.addActor(table);
		Gdx.input.setInputProcessor(Resources.stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		Gdx.gl20.glClearColor( 0f, .5f, 0.5f, 1 );
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		Resources.getStage().act(delta);
		//table.debug();
		Resources.getStage().draw();
	}

	@Override
	public void resize(int width, int height) {
		Resources.stage.getViewport().update(width,height);
	}
	
	private void setupStack(final int id,VerticalGroup vgroup, String name) {
		texts.add(new TextArea("",skin));
		texts.get(id).scaleBy(.5f);
		texts.get(id).setMessageText(name+"'s Dialog Here...");
		texts.get(id).setPrefRows(10);
		texts.get(id).setTextFieldFilter(new TextFieldFilter() {
			
			@Override
			public boolean acceptChar(TextField textField, char c) {
				if(c=='\n')
				{
					Resources.stage.setKeyboardFocus(table);
					return false;
				}
				return true;
			}
		});
		
		next.add(new TextButton("Next", skin));
		TextButton save=new TextButton("Save", skin);
		next.get(id).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			file.getDialog().Add( new Dialogue(""+tabs.get(id).getText(),texts.get(id).getText(),file.getImages().get(id) ) );
			texts.get(id).setText("");
			
				
			}
		});
		save.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SceneEditor.saveFile(file.getDialog(),src);
			}
		});
		texts.get(0).getLines();
		HorizontalGroup buttons=new HorizontalGroup();
		buttons.space(50).addActor(next.get(id));
		buttons.addActor(save);
		buttons.fill();
		vgroup.addActor(texts.get(id));
		vgroup.addActor(buttons);
		vgroup.fill();
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	public static void saveFile(DialogList dialogList,String src){

		FileHandle file=Gdx.files.local("Dialog/"+src+".json");
		
		Json json=new Json();
		file.writeString(Base64Coder.encodeString(json.toJson(dialogList)),false);
	}

}






















/**

		TextArea img=new TextArea("", skin,"small"),src=new TextArea("", skin,"small"),text=new TextArea("", skin,"small");
		Label l_img=new Label("img", skin,"small"),l_src=new Label("folder", skin,"small");
		
		CheckBox create=new CheckBox("Create", skin),delete=new CheckBox("Delete", skin);
		
		ButtonGroup stat=new ButtonGroup();
		stat.add(create);
		stat.add(delete);
		
		TextButton confirm=new TextButton("Confirm",skin);
		confirm.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		HorizontalGroup left=new HorizontalGroup();
		left.space(15).addActor(l_img);left.addActor(img);

		HorizontalGroup right=new HorizontalGroup();
		right.space(15).addActor(l_src);right.addActor(src);
		
		img.setMessageText("ex: p1.png");
		src.setMessageText("ex:dialog/d1");
		text.setMessageText("dialog text");
		text.setPrefRows(7);
		
		HorizontalGroup radio=new HorizontalGroup();
		radio.space(15).addActor(create);
		radio.addActor(delete);
		
		VerticalGroup l_right=new VerticalGroup();
		l_right.space(20).addActor(right);
		l_right.addActor(radio);
		l_right.addActor(confirm);
		
		table.setFillParent(true);
		table.add(left).space(100).top();
		table.add(l_right).spaceBottom(100).top().row();
		table.add(text).colspan(2).fill(true);
		





**/