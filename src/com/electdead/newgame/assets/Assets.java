package com.electdead.newgame.assets;

import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.gameobjects.units.Race;
import com.electdead.newgame.graphics.UnitGraphicsModel;
import com.electdead.newgame.main.MainApp;
import com.electdead.newgame.physics.UnitPhysicsModel;
import com.electdead.newgame.physics.Vector2F;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Assets {
    private static final HashMap<String, HashMap<String, Object>> assets = new HashMap<>();
    private static final String UNITS_PROPERTIES = "/res/units/properties/";

    private Assets() {}

    static {
		/* Properties for Battle background */
        HashMap<String, Object> battleBackgroundProperties = new HashMap<>();
        BufferedImage background = ImageUtils.loadImage(MainApp.class, "/res/background/battle_panel_background.png");
        BufferedImage floor = ImageUtils.loadImage(MainApp.class, "/res/background/floor.png");
        battleBackgroundProperties.put("background", background);
        battleBackgroundProperties.put("floor", floor);
        assets.put("Battle background", battleBackgroundProperties);
		
		/* Projectiles */
        HashMap<String, Object> projectiles = new HashMap<>();
        BufferedImage woodenArrow = ImageUtils.loadImage(MainApp.class, "/res/projectiles/wooden_arrow.png");
        projectiles.put("woodenArrow", woodenArrow);
        assets.put("projectiles", projectiles);
		
		/* Units */
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
            InputStream in = MainApp.class.getResourceAsStream(UNITS_PROPERTIES + unitName + ".json");
            InputStreamReader rin = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(rin);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(br);

            String name 			= (String) json.get("name");
            TypeObject type 		= TypeObject.valueOf((String) json.get("type"));

            Race race 				= Race.valueOf((String) json.get("race"));
            int maxHp		 		= getInt(json, "maxHp");
            int armor 				= getInt(json, "armor");
            int damage 				= getInt(json, "damage");
            float defaultSpeed		= getFloat(json, "defaultSpeed");
            float attackSpeed 		= getFloat(json, "attackSpeedMs");
            float attackRange 		= getFloat(json, "attackRange");
            float searchRange 		= getFloat(json, "searchRange");
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

            BufferedImage[] moveSprites = getSprites(json, "move");
            BufferedImage[] fightSprites = getSprites(json, "fight");
            BufferedImage[] dieSprites = getSprites(json, "die");

            UnitPhysicsModel physModel = new UnitPhysicsModel();
            physModel.setRace(race);
            physModel.setMaxHp(maxHp);
            physModel.setArmor(armor);
            physModel.setDefaultSpeed(defaultSpeed);
            physModel.setDir(dir);
            physModel.setDamage(damage);
            physModel.setAttackSpeed(attackSpeed);
            physModel.setAttackRange(attackRange);
            physModel.setSearchRange(searchRange);
            physModel.setHitBoxWidth(hitBoxWidth);
            physModel.setHitBoxHeight(hitBoxHeight);
            physModel.setSpawnPrice(spawnPrice);
            physModel.setPricePerHead(pricePerHead);

            UnitGraphicsModel graphModel = new UnitGraphicsModel();
            graphModel.setWidthSprite(widthSprite);
            graphModel.setHeightSprite(heightSprite);
            graphModel.setBaseLine(baseLine);
            graphModel.setMoveSpritesRight(moveSprites);
            graphModel.setFightSpritesRight(fightSprites);
            graphModel.setDieSpritesRight(dieSprites);
            graphModel.setAnimationSpeed(animationSpeedMs);

            HashMap<String, Object> props = new HashMap<>();
            props.put(name, name);
            props.put("type", type);
            props.put("physicsModel", physModel);
            props.put("graphicsModel", graphModel);

            assets.put(name, props);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private static int getInt(JSONObject json, String name) {
        return ((Long) json.get(name)).intValue();
    }

    private static float getFloat(JSONObject json, String name) {
        return ((Long) json.get(name)).floatValue();
    }

    private static BufferedImage[] getSprites(JSONObject json, String name) {
        String spritesFile = (String) json.get(name + "SpritesFile");
        SpriteSheet spriteSheet = new SpriteSheet(ImageUtils.loadImage(MainApp.class, spritesFile));

        int width = spriteSheet.getWidth();
        int height = spriteSheet.getHeight();
        int total = width / height;

        JSONArray array = (JSONArray) json.get(name + "SpritesIndex");
        int start = ((Long) array.get(0)).intValue();
        int end = ((Long) array.get(1)).intValue();
        int widthSprite = ((Long) json.get("widthSprite")).intValue();
        int heightSprite = ((Long) json.get("heightSprite")).intValue();

        BufferedImage[] sprites = new BufferedImage[total];
        for (int i = start; i < end; i++) {
            sprites[i] = spriteSheet.getTile(i * widthSprite, 0, widthSprite, heightSprite);
        }

        return sprites;
    }
}
