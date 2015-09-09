package com.electdead.newgame.gameobjects.units.actions;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIComponent;

public class MoveAction extends Action {

	public MoveAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
		super(aiComponent, unit, needFullAnimation);
	}

	@Override
	public void execute() {
		if (!unit.isAlive()) {
			return;
		}
		
		float shiftX 		= unit.currentSpeed * unit.dir.x;		
		unit.pos.x 			+= shiftX;
		unit.hitBox.x 		+= shiftX;
		unit.attackBox.x 	+= shiftX;
		unit.searchCircle.x += shiftX;
		
		float shiftY 		= unit.currentSpeed * unit.dir.y;
		unit.pos.y 			+= shiftY;
		unit.hitBox.y 		+= shiftY;
		unit.attackBox.y 	+= shiftY;
		unit.searchCircle.y += shiftY;
	}
	
	@Override
	public void animationFinished() {}
}
