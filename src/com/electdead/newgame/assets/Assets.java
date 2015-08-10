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
import com.electdead.newgame.gameobjects.components.GraphicsModel;
import com.electdead.newgame.gameobjects.components.PhysicsModel;
import com.electdead.newgame.main.MainApp;

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
		loadAssetsForUnit("Orc Soldier");
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
			double defaultSpeed		= getDouble(json, "defaultSpeed");
			double attackSpeed 		= getDouble(json, "attackSpeed");
			double attackRange 		= getDouble(json, "attackRange");
			int spawnPrice 			= getInt(json, "spawnPrice");
			int pricePerHead 		= getInt(json, "pricePerHead");
			
			double hitBoxWidth 		= getDouble(json, "hitBoxWidth");
			double hitBoxHeight 	= getDouble(json, "hitBoxHeight");
			int velocityX 			= getInt(json, "velocityX");
			int velocityY 			= getInt(json, "velocityY");
			
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
			
			PhysicsModel physModel = new PhysicsModel();
			physModel.setRace(race);
			physModel.setMaxHp(maxHp);
			physModel.setArmor(armor);
			physModel.setDefaultSpeed(defaultSpeed);
			physModel.setVelocityX(velocityX);
			physModel.setVelocityY(velocityY);
			physModel.setDamage(damage);
			physModel.setAttackSpeed(attackSpeed);
			physModel.setAttackRange(attackRange);
			physModel.setHitBoxWidth(hitBoxWidth);
			physModel.setHitBoxHeight(hitBoxHeight);
			physModel.setSpawnPrice(spawnPrice);
			physModel.setPricePerHead(pricePerHead);
			
			GraphicsModel graphModel = new GraphicsModel();
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
	
	public static double getDouble(JSONObject json, String name) {
		return ((Long) json.get(name)).doubleValue();
	}
}
