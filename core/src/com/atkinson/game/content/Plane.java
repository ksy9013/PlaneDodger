/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atkinson.game.content;

import com.atkinson.game.engine.BaseActor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;

/**
 *
 * @author seonyoung.kim
 */
public class Plane extends BaseActor{
   private ArrayList<String> lives = new ArrayList<String>();
    public Plane(float x, float y, Stage s) {
        super(x, y, s);
        
        String[] filenames = {"planeGreen0.png", "planeGreen1.png",
                               "planeGreen2.png", "planeGreen1.png"};
        
        loadAnimationFromFiles(filenames, 0.1f, true);
        setMaxSpeed(800);
        setBoundaryPolygon(8);          
//        String[] lives = {"smallGreenPlane.png", "smallGreenPlane.png", "smallGreenPlane.png"};
        
    }


    
    @Override
    public void act(float dt) {
        super.act(dt);
        
        // simulate force of gravity
        setAcceleration(800);
        accelerateAtAngle(270);
        applyPhysics(dt);
        
        // stop plane from passing through the ground
        for(BaseActor g: BaseActor.getList(this.getStage(), "com.atkinson.game.content.Ground")) {
            if(this.overlaps(g)) {
                setSpeed(0);
                preventOverlap(g);
            }
        }
        
        // stop plane from moving past top of screen
        if(getY() + getHeight() > getWorldBounds().height) {
            setSpeed(0);
            boundToWorld();
        }
    }
    
    public void boost() {
        setSpeed(300);
        setMotionAngle(90);
    }
    public void shoot() {
        if(getStage() == null) 
            return;
        
        Bullet bullet = new Bullet(0, 0, this.getStage());
        bullet.centerAtActor(this);
        bullet.setRotation(this.getRotation());
        bullet.setMotionAngle(this.getRotation());
    }   
}
