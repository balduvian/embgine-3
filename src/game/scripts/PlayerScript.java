package game.scripts;

import embgine.core.Base;
import embgine.core.Block;
import embgine.core.Index;
import embgine.core.Scene;
import embgine.core.components.CliRenderer;
import embgine.core.components.HitBox;
import embgine.core.components.TilRenderer;
import embgine.core.elements.Element;
import embgine.core.elements.GameObject;
import embgine.core.elements.Map;
import embgine.core.scripts.ObjectScript;
import embgine.core.scripts.Script;
import embgine.graphics.Camera;
import embgine.graphics.Sound;
import embgine.graphics.Transform;
import embgine.graphics.Window;
import embgine.graphics.shapes.Shape;
import game.master.CrushyMaster;

public class PlayerScript extends ObjectScript{
	
	public static final int Q = 81;
	public static final int W = 87;
	public static final int A = 65;
	public static final int S = 83;
	public static final int D = 68;
	
	private Window input;
	
	private double animTimer;
	private int animFrame;
	private int animRow;
	private boolean lastFacing;
	
	private float jumpSpeed;
	private float speed;
	
	private float xMax;
	private float yMax;
	
	private float xVel;
	private float yVel;
	private boolean air;
	
	private boolean wPress;
	private boolean aPress;
	private boolean dPress;
	private double jumpTimer;
	private double jumpTime;
	
	private Map onMap;
	private Camera camera;
	
	//the amount the player is actually moving this frame
	private float frameX;
	private float frameY;
	
	private boolean collRight;
	private boolean collLeft;
	
	private boolean winState;
	private int winFloor;
	
	private int spikeType;
	
	private double jumpBufferTimer;
	private boolean bufferedJump;
	
	public void start(Object... params) {
		
		Index ind = scene.getIndex();
		
		input = ind.getWindow();

		animFrame = 0;
		animRow = 0;
		lastFacing = true;
		
		xMax = 7 * Index.TILE;
		yMax = 20 * Index.TILE;
		
		jumpSpeed = 9.3f * Index.TILE;
		speed = 0.5f * Index.TILE;
		
		air = true;
		
		jumpTimer = 0;
		jumpTime = 0.1;
		
		camera = scene.getIndex().getCamera();
		
		winState = false;
		
		spikeType = ind.getBlockLoader("Spike").getType();
		
		jumpBufferTimer = 0;
		bufferedJump = false;
	}

	@Override
	public void update() {
		if(onMap != null) {
			parent.getTransform().moveX(onMap.getMove());
		}
	
		if(!winState) {
			getInput();
			gravity();
			velocityCheck();
			collisionCheck();
			move();
			animUpdate();
		}else {
			winUpdate();
		}
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
		
		if(jumpTimer > 0) {
			if(input.keyPressed(W)) {
				yVel = -jumpSpeed;
			}else {
				jumpTimer = 0;
			}
			jumpTimer -= Base.time;
		}else {
			if(!bufferedJump) {
				if(input.keyPressed(W)) {
					if(!wPress) {
						if(!air) {
							yVel = -jumpSpeed;
							jumpTimer = jumpTime;
							scene.sound("jump.wav", 1, false);
							bufferedJump = false;
						}else {
							jumpBufferTimer = 0.1;
							bufferedJump = true;
						}
						wPress = true;
					}
				}
			}else {
				if(!air) {
					yVel = -jumpSpeed;
					jumpTimer = jumpTime;
					scene.sound("jump.wav", 1, false);
					bufferedJump = false;
				}
			}
		}
		
		if(!input.keyPressed(W)) {
			wPress = false;
		}
		
		if(jumpBufferTimer > 0) {
			jumpBufferTimer -= Base.time;
		}else {
			bufferedJump = false;
		}
		
	}
	
	//sets yVelocity based on gravity
	private void gravity() {
		air = true;
		yVel += CrushyMaster.GRAVITY;
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
	
	//stops the player from moving if they're gonna hit a wall
	private void collisionCheck() {
		
		HitBox collBox = (HitBox) parent.getComponent(1);
		
		Element[] maps = scene.getCurrentMaps();
		int len = maps.length;
		
		//System.out.println(parent.getTransform().getX() + " " + parent.getTransform().getY());
		
		int nowX = parent.getTransform().getX();
		int nowY = parent.getTransform().getY();
		
		int limit;
		
		collRight = false;
		collLeft = false;
		
		boolean touchingSpike = false;
		
		for(int m = 0; m < len; ++m) {
			Map map = (Map)maps[m];
			
			int upMapY = map.accessY(collBox.getUp());
			int downMapY = map.accessY(collBox.getDown());
			
			//System.out.println(downMapY);
			
			//System.out.println(collBox.getRight() + " " + map.accessX(collBox.getRight()) );
			int leftMapX = map.accessX(collBox.getLeft());
			int rightMapX = map.accessX(collBox.getRight());
		
			//System.out.println(nowY);
			
			down: for(int j = upMapY + 1; j <= downMapY + 1; ++j) {
				limit = map.positionY(j) - 1 - collBox.getJustDown();
				for(int i = leftMapX; i <= rightMapX; ++i) {
					Block b = map.access(i, j);
					if(b != null) {
						if(b.isSolid()) {
							if(nowY + frameY > limit) {
								//System.out.println("colid");
								onMap = map;
								air = false;
								yVel = 0;
								frameY = limit - nowY;
								if(b.getType() == spikeType) {
									touchingSpike = true;
								}
								break down;
							}
						}
					}
				}
			}
		
			up: for(int j = upMapY - 1; j <= downMapY - 1; ++j) {
				limit = map.positionY(j) + 16 - collBox.getJustUp();
				for(int i = leftMapX; i <= rightMapX; ++i) {
					Block b = map.access(i, j);
					if(b != null) {
						if(b.isSolid()) {
							if(nowY + frameY < limit) {
								yVel = 0;
								frameY = limit - nowY;
								if(b.getType() == spikeType) {
									touchingSpike = true;
								}
								break up;
							}
						}
					}
				}
			}
		
			right: for(int j = leftMapX + 1; j <= rightMapX + 1; ++j) {
				limit = map.positionX(j) - collBox.getJustRight() - 2;
				for(int i = upMapY; i <= downMapY; ++i) {
					Block b = map.access(j, i);
					if(b != null) {
						if(b.isSolid()) {
							if(nowX + frameX > limit) {
								xVel = 0;
								frameX = limit - nowX;
								collRight = true;
								if(b.getType() == spikeType) {
									touchingSpike = true;
								}
								break right;
							}
						}
					}
				}
			}
		
			left: for(int j = leftMapX - 1; j <= rightMapX; ++j) {
				limit = map.positionX(j) + 17 - collBox.getJustLeft();
				for(int i = upMapY; i <= downMapY; ++i) {
					Block b = map.access(j, i);
					if(b != null) {
						if(b.isSolid()) {
							if(nowX + frameX < limit) {
								xVel = 0;
								frameX = limit - nowX;
								collLeft = true;
								if(b.getType() == spikeType) {
									touchingSpike = true;
								}
								break left;
							}
						}
					}
				}
			}
			
		}
		
		if((collLeft && collRight) || touchingSpike) {
			scene.sound("crush.wav", 0.25f, false);
			((GameScript)scene.getScript()).die();
		}
	}
	
	//actually moves the player
	private void move() {
		parent.getTransform().move(frameX, frameY);
	}
	
	private void animUpdate() {
		
		if(air) {
			if(yVel < 0) {
				animFrame = 0;
			}else {
				animFrame = 1;
			}
			if(xVel > 0) {
				animRow = 2;
				lastFacing = true;
			}else if(xVel < 0){
				animRow = 3;
				lastFacing = false;
			}else {
				if(lastFacing) {
					animRow = 2;
				}else {
					animRow = 3;
				}
			}
		}else {
			if(xVel > 0) {
				animRow = 0;
				animFrame = (int)(animTimer * 7 % 3);
				animTimer %= 3;
				lastFacing = true;
			}else if(xVel < 0){
				animRow = 1;
				animFrame = (int)(animTimer * 7 % 3);
				animTimer %= 3;
				lastFacing = false;
			}else {
				animFrame = 0;
				if(lastFacing) {
					animRow = 0;
				}else {
					animRow = 1;
				}
			}
		}
		
		animTimer += Base.time;
		
		((CliRenderer)parent.getComponent(0)).setFrame(animFrame, animRow);
		((CliRenderer)parent.getComponent(0)).setPlane(0, 0, 0, 0);
		Shape.RECT.getTransform().set(new Transform(-2, 0, -16, 16));
		((CliRenderer)parent.getComponent(0)).setView(Shape.RECT.getMatrix());
		
	}
	
	private void winUpdate() {
		xVel = this.xMax / 2f;
		yVel += CrushyMaster.GRAVITY;
		
		frameY = (float)(yVel*Base.time);
		
		HitBox collBox = (HitBox) parent.getComponent(1);
		
		Element[] maps = scene.getCurrentMaps();
		int len = maps.length;
		
		int nowX = parent.getTransform().getX();
		int nowY = parent.getTransform().getY();
		
		for(int m = 0; m < len; ++m) {
			Map map = (Map)maps[m];
			
			int upMapY = map.accessY(collBox.getUp());
			int downMapY = map.accessY(collBox.getDown());
			
			//System.out.println(downMapY);
			
			//System.out.println(collBox.getRight() + " " + map.accessX(collBox.getRight()) );
			int leftMapX = map.accessX(collBox.getLeft());
			int rightMapX = map.accessX(collBox.getRight());
			
			down: for(int j = upMapY + 1; j <= downMapY + 1; ++j) {
				int limit = map.positionY(j) - 1 - collBox.getJustDown();
				for(int i = leftMapX; i <= rightMapX; ++i) {
					Block b = map.access(i, j);
					if(b != null && b.isSolid()) {
						if(nowY + frameY > limit) {
							//System.out.println("colid");
							onMap = map;
							air = false;
							yVel = 0;
							frameY = limit - nowY;
							break down;
						}
					}
				}
			}
		
		}
		
		parent.getTransform().move((float)(xVel * Base.time), frameY);
		
		((CliRenderer)parent.getComponent(0)).setPlane(1, 0, 0, 2);
		
		animRow = 0;
		animFrame = (int)(animTimer * 7 % 3);
		animTimer %= 3;
		
		animTimer += Base.time;
		
		((CliRenderer)parent.getComponent(0)).setFrame(animFrame, 0);
		Shape.RECT.getTransform().set(new Transform(-2, 0, -16, 16));
		((CliRenderer)parent.getComponent(0)).setView(Shape.RECT.getMatrix());
		
	}
	
	public void setWin(boolean w, int f) {
		winState = w;
		winFloor = f;
	}
	
}
