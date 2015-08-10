package com.electdead.newgame.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;

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
			
			int maxHp		 		= ((Long) json.get("maxHp")).intValue();
			int armor 				= ((Long) json.get("armor")).intValue();
			int damage 				= ((Long) json.get("damage")).intValue();
			double defaultSpeed		= ((Long) json.get("defaultSpeed")).doubleValue();
			double attackSpeed 		= ((Long) json.get("attackSpeed")).doubleValue();
			double attackRange 		= ((Long) json.get("attackRange")).doubleValue();
			int spawnPrice 			= ((Long) json.get("spawnPrice")).intValue();
			int pricePerHead 		= ((Long) json.get("pricePerHead")).intValue();
			
			double hitBoxWidth 		= ((Long) json.get("hitBoxWidth")).doubleValue();
			double hitBoxHeight 	= ((Long) json.get("hitBoxHeight")).doubleValue();
			int velocityX 			= ((Long) json.get("velocityX")).intValue();
			int velocityY 			= ((Long) json.get("velocityY")).intValue();
			
			int widthSprite 		= ((Long) json.get("widthSprite")).intValue();
			int heightSprite 		= ((Long) json.get("heightSprite")).intValue();
			int baseLine 			= ((Long) json.get("baseLine")).intValue();
			int defaultSpriteIndex 	= ((Long) json.get("defaultSpriteIndex")).intValue();
			int moveSprites 		= ((Long) json.get("moveSprites")).intValue();
			int fightSprites 		= ((Long) json.get("fightSprites")).intValue();
			int dieSprites 			= ((Long) json.get("dieSprites")).intValue();
			int animationSpeedMs 	= ((Long) json.get("animationSpeedMs")).intValue();
			
			String spritesFile 		= (String) json.get("spritesFile");
			SpriteSheet spriteSheet = new SpriteSheet(
					ImageLoader.loadImage(MainApp.class, spritesFile));
			
			int total = spriteSheet.getWidth() / widthSprite;
			BufferedImage[] sprites = new BufferedImage[total];
			for (int i = 0; i < total; i++) {
				sprites[i] = spriteSheet.getTile(i * widthSprite, 0, widthSprite, heightSprite);
			}
			
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
			graphModel.setDefaultSpriteIndex(defaultSpriteIndex);
			graphModel.setMoveSprites(moveSprites);
			graphModel.setFightSprites(fightSprites);
			graphModel.setDieSprites(dieSprites);
			graphModel.setAnimationSpeedMs(animationSpeedMs);
			graphModel.setSprites(sprites);
			
			HashMap<String, Object> props = new HashMap<>();
			props.put(name, name);
			props.put("type", type);
			props.put("physModel", physModel);
			props.put("graphModel", graphModel);
			
			assets.put(name, props);
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	
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
}
