package com.electdead.newgame.gameobjects.actions;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.ai.AIComponent;

public class MoveAction extends Action {

	public MoveAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
		super(aiComponent, unit, needFullAnimation);
	}

	@Override
	public void execute() {
		float shiftX 		= unit.currentSpeed * unit.dir.x;		
		unit.pos.x 			+= shiftX;
		unit.hitBox.x 		+= shiftX;
		unit.attackBox.x 	+= shiftX;
		
		float shiftY 		= unit.currentSpeed * unit.dir.y;
		unit.pos.y 			+= shiftY;
		unit.hitBox.y 		+= shiftY;
		unit.attackBox.y 	+= shiftY;
	}
	
	@Override
	public void animationFinished() {}
}
