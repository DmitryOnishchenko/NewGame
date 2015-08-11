package com.electdead.newgame.gamestate;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.components.AIComponent;
import com.electdead.newgame.gameobjects.components.GraphicsComponent;
import com.electdead.newgame.gameobjects.components.PhysicsComponent;
import com.electdead.newgame.gameobjects.components.SoldierAIComponent;
import com.electdead.newgame.gameobjects.components.UnitGraphicsComponent;
import com.electdead.newgame.gameobjects.components.UnitPhysicsComponent;
import com.electdead.newgame.main.MainApp;
import com.google.common.collect.TreeMultiset;


public class DevGameState extends AbstractGameState {
	public static HashSet<GameObject> gameObjects = new HashSet<>();
	public static HashSet<Unit> units = new HashSet<>();
	public static TreeMultiset<GameObject> renderObjects = TreeMultiset.create();
	
	private BufferedImage floorSprite = (BufferedImage) Assets.getProperties("Battle background").get("floor");
	private BufferedImage map = new BufferedImage(MainApp.WIDTH, MainApp.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	Graphics2D floor = (Graphics2D) map.getGraphics();
	
	private int testSpawnTimer;
	
	public DevGameState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
    public void init() {
		floor.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < 720 / 40; i++) {
			floor.drawImage(floorSprite, 0, i * 40, null);
		}
		
	    units.add(createDemoUnit("Human Soldier", 700, 520));
	    units.add(createDemoUnit("Human Soldier", 0, 520));
	    units.add(createDemoUnit("Orc Soldier", 800, 520));
    }
	
	public Unit createDemoUnit(String name, double x, double y) {
		HashMap<String, Object> props = Assets.getProperties(name);
		
		TypeObject type = (TypeObject) props.get("type");
		Unit obj = new Unit(name, type, x, y);
		
		AIComponent aic = new SoldierAIComponent(obj);
		GraphicsComponent gc = new UnitGraphicsComponent(obj);
		PhysicsComponent pc = new UnitPhysicsComponent(obj);
//		GraphicsComponent gc = new GraphicsComponent(obj);
		
		obj.setAIComponent(aic);
		obj.setPhysicsComponent(pc);
		obj.setGraphicsComponent(gc);
		
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
	    
	    for (Unit unit : units)
	    	if (!unit.delete) unit.update();
	    
	    if (++testSpawnTimer > 25) {
//	    	SWARM();
	    	testSpawnTimer = 0;
	    }
	    
	    for (Iterator<GameObject> iterator = gameObjects.iterator(); iterator.hasNext();) {
	        GameObject gameObject = iterator.next();
	        if (gameObject.delete)
	        	iterator.remove();
        }
	    
	    for (Iterator<Unit> iterator = units.iterator(); iterator.hasNext();) {
	        Unit unit = iterator.next();
	        if (unit.delete) {
	        	iterator.remove(); System.out.println(unit);	        	
	        }
        }
    }

	@Override
    public void render(Graphics2D g2, double deltaTime) {
		g2.drawImage(map, 0, 0, null);
		
		renderObjects.clear();
		renderObjects.addAll(gameObjects);
		renderObjects.addAll(units);
		
		for (GameObject obj : renderObjects)
			if (!obj.delete) obj.render(g2, deltaTime);
    }
	
	public void SWARM() {
		Random r = new Random();
    	GameObject humanSoldier = createDemoUnit("Human Soldier", -100, r.nextDouble() * 600 + 100);
    	gameObjects.add(humanSoldier);
    	
    	GameObject orcSoldier = createDemoUnit("Orc Soldier", 1280, r.nextDouble() * 600 + 100);
    	gameObjects.add(orcSoldier);
	}
}
