package game.scripts;

import embgine.core.Base;
import embgine.core.Block;
import embgine.core.Index;
import embgine.core.Scene;
import embgine.core.components.HitBox;
import embgine.core.components.TilRenderer;
import embgine.core.elements.GameObject;
import embgine.core.elements.Map;
import embgine.core.scripts.ObjectScript;
import embgine.core.scripts.Script;
import embgine.graphics.Transform;
import embgine.graphics.Window;

public class PlayerScript extends ObjectScript{
	
	public static final int W = 87;
	public static final int A = 65;
	public static final int S = 83;
	public static final int D = 68;
	
	private Window input;
	private Map map;
	
	private double animTimer;
	private int animFrame;
	
	private float jumpSpeed;
	private float speed;
	private float grav;
	
	private float xMax;
	private float yMax;
	
	private float xVel;
	private float yVel;
	private boolean air;
	
	private boolean aPress;
	private boolean dPress;
	
	private static int width;
	
	//the amount the player is actually moving this frame
	private float frameX;
	private float frameY;
	
	public void start(Object... params) {
		
		width = 16;
		
		Index ind = scene.getIndex();
		
		input = ind.getWindow();

		animFrame = 0;
		
		xMax = 7 * Index.TILE;
		yMax = 20 * Index.TILE;
		
		jumpSpeed = 15 * Index.TILE;
		speed = 0.5f * Index.TILE;
		
		grav = 0.5f * Index.TILE;
		air = true;
		
	}

	@Override
	public void update() {
		
		Transform t = parent.getTransform();
		float x = t.getXScale() * 1.001f;
		float y = t.getXScale() * 1.001f;
		t.setScale(x, y);
		
		getInput();
		gravity();
		velocityCheck();
		collisionCheck();
		move();
		animUpdate();
	}
	
	//collects information about what the player is inputting
	private void getInput() {
		if(input.keyPressed(A)) {
			aPress = true;
			xVel -= speed;
		}else {
			aPress = false;
		}
		if(input.keyPressed(D)) {
			dPress = true;
			xVel += speed;
		}else {
			dPress = false;
		}
		
		if(input.keyPressed(W)) {
			if(!air) {
				yVel = -jumpSpeed;
			}
		}
	}
	
	//sets yVelocity based on gravity
	private void gravity() {
		air = true;
		yVel += grav;
	}
	
	//makes sure the player doesn't break the laws of physics
	private void velocityCheck() {
		
		if(!aPress) {
			if(xVel < 0){
				xVel += speed * 1.5f;
				if(xVel > 0) {
					xVel = 0;
				}
			}
		}
		
		if(!dPress) {
			if(xVel > 0){
				xVel -= speed * 1.5f;
				if(xVel < 0) {
					xVel = 0;
				}
			}
		}
		
		if(xVel > xMax) {
			xVel = xMax;
		}else if(xVel < -xMax) {
			xVel = -xMax;
		}
		
		if(yVel > yMax) {
			yVel = yMax;
		}else if(yVel < -yMax) {
			yVel = -yMax;
		}
		
		frameX = (float)(xVel*Base.time);
		
		frameY = (float)(yVel*Base.time);
	}
	
	private int superRound(float a) {
		if(a%1 <= 0.5) {
			return (int) a;
		}else {
			return (int) (a + 1);
		}
	}
	
	//stops the player from moving if they're gonna hit a wall
	private void collisionCheck() {
		
		HitBox collBox = (HitBox) parent.getComponent(1);
		
		float nowX = parent.getTransform().getX();
		float nowY = parent.getTransform().getY();
		
		int upMapY = map.accessY(collBox.getUp());
		int midMapY = map.accessY(nowY);
		int downMapY = map.accessY(collBox.getDown());
		
		
		int leftMapX = map.accessX(collBox.getLeft());
		int midMapX = map.accessX(nowX);
		int rightMapX = map.accessX(collBox.getRight());
		
		System.out.println(upMapY + " " + downMapY);
		
		float limit;
	
		//down collision
		if(frameY >= 0) {
			for(int j = upMapY; j <= downMapY; ++j) {
				limit = ( (Math.round( (nowY / Index.TILE) + 0.5f) * Index.TILE ) - collBox.getJustDown() );
				for(int i = leftMapX; i <= rightMapX; ++i) {
					Block b = map.access(i, j);
					if(b != null && b.isSolid()) {
						if(nowY + frameY > limit) {
							air = false;
							yVel = 0;
							frameY = (limit - nowY);
							break;
						}
					}
				}
				
			}
		}
		
		/*
		//right collision
		limit = ( (midX + 0.5f) * Index.TILE ) - collBox.right;
		for(int i = upY; i <= downY; ++i) {
			Block b = map.access(midX + 1, i);
			if(b != null && b.isSolid()) {
				if(nowX + frameX > limit) {
					xVel = 0;
					frameX = (limit - nowX);
					break;
				}
			}
		}
		
		//up collision
		limit = ( (midY - 0.5f) * Index.TILE ) - collBox.up;
		for(int i = leftX; i <= rightX; ++i) {
			Block b = map.access(i, midY - 1);
			if(b != null && b.isSolid()) {
				if(nowY + frameY < limit) {
					yVel = 0;
					frameY = (limit - nowY);
					break;
				}
			}
		}
		
		//left collision
		limit = ( (midX - 0.5f) * Index.TILE ) - collBox.left;
		for(int i = upY; i <= downY; ++i) {
			Block b = map.access(midX - 1, i);
			if(b != null && b.isSolid()) {
				if(nowX + frameX < limit) {
					xVel = 0;
					frameX = (limit - nowX);
					break;
				}
			}
		}
		*/
	}
	
	//actually moves the player
	private void move() {
		parent.getTransform().move(frameX, frameY);
	}
	
	private void animUpdate() {
		
		animTimer += Base.time;
		
		if(xVel > 0) {
			parent.getTransform().setWidth(width);
			animFrame = (int)(animTimer * 7 % 3);
			animTimer %= 3;
		}else if(xVel < 0){
			parent.getTransform().setWidth(-width);
			animFrame = (int)(animTimer * 7 % 3);
			animTimer %= 3;
		}else {
			animFrame = 0;
		}
		
		((TilRenderer)parent.getComponent(0)).setFrame(animFrame);
	}

	public void setMap(Map m) {
		map = m;
	}
}
