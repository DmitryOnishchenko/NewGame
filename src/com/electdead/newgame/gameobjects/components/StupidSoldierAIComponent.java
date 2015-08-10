package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.GameObjectState;
import com.electdead.newgame.gamestate.DevGameState;

public class StupidSoldierAIComponent {
	private GameObject obj;
	private GameObjectState state;
	
	public StupidSoldierAIComponent(GameObject obj) {
		this.obj = obj;
		state = GameObjectState.MOVE;
	}
	
	public GameObjectState getState() {
		return state;
	} 
	
	public void update() {
		PhysicsComponent pc = obj.getPhys();
		
		if (findEnemy()) {
			state = GameObjectState.FIGHT;
		} else {
			state = GameObjectState.MOVE;
		}
		
		
		state.update(obj);
	}
	
	public boolean findEnemy() {
			for (GameObject enemy : DevGameState.gameObjects) {
				if (physModel.getRace() != enemy.getPhys().getRace() &&
					attackBox.intersects(enemy.getPhys().hitBox)) {
					target = enemy;
					state = GameObjectState.FIGHT;
//					velocityX = 0;
				}
			}
		}
	}
}
