package com.electdead.newgame.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.gameobjects.components.AIComponent;
import com.electdead.newgame.gameobjects.components.GraphicsComponentOld;
import com.electdead.newgame.gameobjects.components.PhysicsComponentOld;
import com.electdead.newgame.gameobjects.components.SoldierAIComponent;
import com.electdead.newgame.main.MainApp;
import com.google.common.collect.TreeMultiset;


public class DevGameState extends AbstractGameState {
	public static HashSet<GameObject> gameObjects = new HashSet<>();
	public static TreeMultiset<GameObject> renderObjects = TreeMultiset.create();
	
	private BufferedImage floor = (BufferedImage) Assets.getProperties("Battle background").get("floor");
	
	private int testSpawnTimer;
	
	public DevGameState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
    public void init() {
	    gameObjects.add(createDemoUnit("Human Soldier", 100, 520));
	    gameObjects.add(createDemoUnit("Orc Soldier", 1280, 520));
    }
	
	public GameObject createDemoUnit(String name, double x, double y) {
		HashMap<String, Object> props = Assets.getProperties(name);
		
		TypeObject type = (TypeObject) props.get("type");
		GameObject obj = new GameObject(name, type, x, y);
		
		AIComponent aic = new SoldierAIComponent();
//		PhysicsComponentOld pc = new PhysicsComponentOld(obj);
//		GraphicsComponent gc = new GraphicsComponent(obj);
		
		obj.setAIComponent(aic);
		
		return obj;
	}

	@Override
    public void processInput() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update() {
	    for (GameObject obj : gameObjects)
	    	if (!obj.delete) obj.update();
	    
	    if (++testSpawnTimer > 25) {
//	    	SWARM();
	    	
	    	testSpawnTimer = 0;
	    }
	    
	    for (Iterator<GameObject> iterator = gameObjects.iterator(); iterator.hasNext();) {
	        GameObject gameObject = iterator.next();
	        if (gameObject.delete)
	        	iterator.remove();
        }
    }

	@Override
    public void render(Graphics2D g2, double deltaTime) {
		for (int i = 0; i < 720 / 40; i++) {
			g2.drawImage(floor, 0, i * 40, null);
		}
		
		renderObjects.clear();
		renderObjects.addAll(gameObjects);
		
		for (GameObject obj : renderObjects)
			if (!obj.delete) obj.render(g2, deltaTime);
    }
	
	public void SWARM() {
		Random r = new Random();
    	GameObject humanSoldier = createDemoUnit("Human Soldier", -100, r.nextDouble() * 600 + 100);
    	gameObjects.add(humanSoldier);
    	
    	GameObject orcSoldier = createDemoUnit("Orc Soldier", MainApp.WIDTH, r.nextDouble() * 600 + 100);
    	gameObjects.add(orcSoldier);
	}
}
