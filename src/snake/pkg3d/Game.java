
package snake.pkg3d;

import java.util.ArrayList;
import processing.core.*;


public class Game extends PApplet{
    
    PImage logo,points;
    int bs = 25;
    ArrayList<PVector> positions = new ArrayList<PVector>();
    PVector apple = new PVector();
    PVector direction = new PVector();
    
    public static void main(String[] args) {
        PApplet.main(new String[]{snake.pkg3d.Game.class.getName()});
    }
    
    @Override
    public void settings(){
        size(1080,1000,P3D);
    }
    @Override
    public void setup(){
        frameRate(15);
        logo = loadImage("C:\\Users\\Dani\\Documents\\NetBeansProjects\\Snake 3D\\src\\snake\\pkg3d\\logoBackground.jpg");
        points = loadImage("C:\\Users\\Dani\\Documents\\NetBeansProjects\\Snake 3D\\src\\snake\\pkg3d\\pointsBackground.jpg");
        
        camera(-250,-350,150,100,50,0,0,1,0);
        
        initSnake();
        spawnApple();
        
    }
    @Override
    public void draw(){
        background(255);
        translate(width/2,height/2,-200);
        rotateY(1);
        
        drawMap();
        drawSnake();
        drawApple();
        
        moveSnake();
        eat();
        checkDead();
    }
    
    void drawMap(){
        //Dibujamos el cubo
        noFill();
        box(700);
        
        pushMatrix();
        translate(350,0,0);
        rotateY((float) -1.57);
        image(logo, -350, -350);
        
        translate(350,0,350);
        rotateY((float)-1.57);
        image(points,-350,-350);
        textSize(35);
        fill(0);
        text(-positions.get(0).x,-200,30,20);
        text(-positions.get(0).y,-200,130,20);
        text(-positions.get(0).z,-200,230,20);      
        text(-apple.x,100,30,20);
        text(-apple.y,100,130,20);
        text(-apple.z,100,230,20);
        
        
        translate(0,350,350);
        fill(0,196,204);
        rotateY((float)3.14);
        box(700,(float)0.1,700);
        textSize(500);
        fill(255);
        rotateY((float)-1.5707);
        rotateX((float)1.5707);
        
        if(positions.size()<10){
            text(positions.size(),-150,180,(float)0.1);
        }else{
            text(positions.size(),-340,180,(float)0.1);
        }
        
        popMatrix();
    }
    
    void initSnake(){
        positions.clear();
        positions.add(new PVector(1,0,0));
        positions.add(new PVector(2,0,0));
        positions.add(new PVector(3,0,0));
        positions.add(new PVector(4,0,0));
        direction.set(-1,0,0);
    }
    
    void drawSnake(){
        for(int i = 0;i < positions.size();i++){
            float x = positions.get(i).x;
            float y = positions.get(i).y;
            float z = positions.get(i).z;
            
            
            pushMatrix();
            translate(x*bs,y*bs,z*bs);
            fill(100,100,200);
            box(25);
            popMatrix();
        }
    }
    
    void drawApple(){
        pushMatrix();
        translate(apple.x*bs,apple.y*bs,apple.z*bs);
        fill(200,50,50);
        box(25);
        popMatrix();
    }
    
    void spawnApple(){
        int x = (int)random(-14,14);
        int y = (int)random(-14,14);
        int z = (int)random(-14,14);
        
        apple.set(x,y,z);
    }
    
    void moveSnake(){
        PVector nextPosition = new PVector(positions.get(0).x + direction.x, positions.get(0).y + direction.y, positions.get(0).z + direction.z);
        positions.add(0,nextPosition);
        
        //positions.size()-1 re refeire a la ultima posicion del array
        positions.remove(positions.size()-1);
    }
    
    void eat(){
        PVector headPosition = positions.get(0);
        if(headPosition.equals(apple)){
            positions.add(positions.get(positions.size()-1));
            spawnApple();
        }
    }
    
    void checkDead(){
        PVector head = positions.get(0);
        //Si se sale del mapa, hacemos que vuelva a spawnear
        if(head.x > 14){
            pushMatrix();
            translate(350,0,0);
            fill(220,10,10);
            box(1,700,700);
            popMatrix();
            initSnake();
        }
        if(head.x < -14){
            pushMatrix();
            translate(-350,0,0);
            fill(220,10,10);
            box(1,700,700);
            popMatrix();
            initSnake();
        }
        if(head.y > 14){
            pushMatrix();
            translate(0,350,0);
            fill(220,10,10);
            box(700,1,700);
            popMatrix();
            initSnake();
        }
        if(head.y < -14){
            pushMatrix();
            translate(0,-350,0);
            fill(220,10,10);
            box(700,1,700);
            popMatrix();
            initSnake();
        }
        if(head.z > 14){
            pushMatrix();
            translate(0,0,350);
            fill(220,10,10);
            box(700,700,1);
            popMatrix();
            initSnake();
        }
        if(head.z < -14){
            pushMatrix();
            translate(0,0,-350);
            fill(220,10,10);
            box(700,700,1);
            popMatrix();
            initSnake();
        }
        
        
        //Si se choca consigo misma
        head = positions.get(0);
        for(int i = 2;i < positions.size();i++){
            if(head.equals(positions.get(i))){
                initSnake();
            }
        }
    }
    
    
    @Override
    public void mouseClicked(){
        if(mouseButton == LEFT && direction.y == 0){
            direction.set(0,-1,0);
        } 
        else if(mouseButton == RIGHT && direction.y == 0){
            direction.set(0,1,0);
        }
    }
    
    @Override
    public void keyPressed(){
        if(key == 'w' && direction.x == 0){
            direction.set(1,0,0);
        }
        else if(key == 's' && direction.x == 0){
            direction.set(-1,0,0);
        }
        else if(key == 'a' && direction.z == 0){
            direction.set(0,0,-1);
        }
        else if(key == 'd' && direction.z == 0){
            direction.set(0,0,1);
        }
    }
    
    
    
}
