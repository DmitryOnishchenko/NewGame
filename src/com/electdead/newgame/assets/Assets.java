package com.electdead.newgame.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.electdead.newgame.gameobjects.Race;
import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.gameobjects.components.UnitGraphicsModel;
import com.electdead.newgame.gameobjects.components.UnitPhysicsModel;
import com.electdead.newgame.main.MainApp;
import com.electdead.newgame.physics.Vector2F;

public class Assets {
	private static final HashMap<String, HashMap<String, Object>> assets = new HashMap<>();
	private static final String UNITS_PROPERTIES = "/res/units/properties/";
	
	private Assets() {}
	
	static {
		/* Properties for Battle background */
		HashMap<String, Object> battleBackgroundProperties = new HashMap<>();
		BufferedImage background = ImageLoader.loadImage(MainApp.class, "/res/background/battle_panel_background.png");
		BufferedImage floor = ImageLoader.loadImage(MainApp.class, "/res/background/floor.png");
		battleBackgroundProperties.put("background", background);
		battleBackgroundProperties.put("floor", floor);
		assets.put("Battle background", battleBackgroundProperties);
		
		loadAssetsForUnit("Human Soldier");
		loadAssetsForUnit("Human Archer");
		loadAssetsForUnit("Orc Soldier");
		loadAssetsForUnit("Orc Archer");
	}
	
	public static HashMap<String, Object> getProperties(String key) {
		return assets.get(key);
	}
	
	private static void loadAssetsForUnit(String unitName) {
		try {
			/* Load from .json */
			URL url = MainApp.class.getResource(UNITS_PROPERTIES + unitName + ".json");
			File file = new File(url.toURI());
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(new FileReader(file));
			
			String name 			= (String) json.get("name");
			TypeObject type 		= TypeObject.valueOf((String) json.get("type"));
			
			Race race 				= Race.valueOf((String) json.get("race"));
			int maxHp		 		= getInt(json, "maxHp");
			int armor 				= getInt(json, "armor");
			int damage 				= getInt(json, "damage");
			float defaultSpeed		= getFloat(json, "defaultSpeed");
			float attackSpeed 		= getFloat(json, "attackSpeedMs");
			float attackRange 		= getFloat(json, "attackRange");
			int spawnPrice 			= getInt(json, "spawnPrice");
			int pricePerHead 		= getInt(json, "pricePerHead");
			
			float hitBoxWidth 		= getFloat(json, "hitBoxWidth");
			float hitBoxHeight 		= getFloat(json, "hitBoxHeight");
			float velocityX 		= getFloat(json, "velocityX");
			float velocityY 		= getFloat(json, "velocityY");
			Vector2F dir 			= new Vector2F(velocityX, velocityY);
			
			int widthSprite 		= getInt(json, "widthSprite");
			int heightSprite 		= getInt(json, "heightSprite");
			int baseLine 			= getInt(json, "baseLine");
			int animationSpeedMs 	= getInt(json, "animationSpeedMs");
			
			String spritesFile 		= (String) json.get("spritesFile");
			SpriteSheet spriteSheet = new SpriteSheet(
					ImageLoader.loadImage(MainApp.class, spritesFile));
			
			int total = spriteSheet.getWidth() / widthSprite;
			BufferedImage[] sprites = new BufferedImage[total];
			for (int i = 0; i < total; i++) {
				sprites[i] = spriteSheet.getTile(i * widthSprite, 0, widthSprite, heightSprite);
			}
			
			JSONArray moveArray = (JSONArray) json.get("moveSprites");
			JSONArray fightArray = (JSONArray) json.get("fightSprites");
			JSONArray dieArray = (JSONArray) json.get("dieSprites");
			
			BufferedImage[] moveSprites = Arrays.copyOfRange(
					sprites, ((Long) moveArray.get(0)).intValue(), ((Long) moveArray.get(1)).intValue());
			BufferedImage[] fightSprites = Arrays.copyOfRange(
					sprites, ((Long) fightArray.get(0)).intValue(), ((Long) fightArray.get(1)).intValue());
			BufferedImage[] dieSprites = Arrays.copyOfRange(
					sprites, ((Long) dieArray.get(0)).intValue(), ((Long) dieArray.get(1)).intValue());
			
			UnitPhysicsModel physModel = new UnitPhysicsModel();
			physModel.setRace(race);
			physModel.setMaxHp(maxHp);
			physModel.setArmor(armor);
			physModel.setDefaultSpeed(defaultSpeed);
			physModel.setDir(dir);
//			physModel.setVelocityX(velocityX);
//			physModel.setVelocityY(velocityY);
			physModel.setDamage(damage);
			physModel.setAttackSpeed(attackSpeed);
			physModel.setAttackRange(attackRange);
			physModel.setHitBoxWidth(hitBoxWidth);
			physModel.setHitBoxHeight(hitBoxHeight);
			physModel.setSpawnPrice(spawnPrice);
			physModel.setPricePerHead(pricePerHead);
			
			UnitGraphicsModel graphModel = new UnitGraphicsModel();
			graphModel.setWidthSprite(widthSprite);
			graphModel.setHeightSprite(heightSprite);
			graphModel.setBaseLine(baseLine);
			graphModel.setMoveSprites(moveSprites);
			graphModel.setFightSprites(fightSprites);
			graphModel.setDieSprites(dieSprites);
			graphModel.setAnimationSpeed(animationSpeedMs);
			
			HashMap<String, Object> props = new HashMap<>();
			props.put(name, name);
			props.put("type", type);
			props.put("physicsModel", physModel);
			props.put("graphicsModel", graphModel);
			
			assets.put(name, props);
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	
	public static int getInt(JSONObject json, String name) {
		return ((Long) json.get(name)).intValue();
	}
	
	public static float getFloat(JSONObject json, String name) {
		return ((Long) json.get(name)).floatValue();
	}
}
