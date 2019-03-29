import acm.program.*;
import acm.graphics.*; //importing packages.
/* Exercice 2
		DrawHouseOfUsher */
public class DrawHouseOfUsher extends GraphicsProgram {
   public void run() {
	   build_house();
		draw_last_shapes();
	    
}
private void draw_popular_shape(double x,double y,double rect_width,double rect_height,double triangle_height){
	GRect rect = new GRect(x, y, rect_width, rect_height);
	add(rect);
	add(new GLine(x,y,x+rect.getWidth()/2,y-triangle_height));
	add(new GLine(x+rect.getWidth()/2,y-triangle_height,x+rect.getWidth(),y));
	
	
	
}
private void draw_last_shapes(){
	/* It draws the last three shapes */
	int total_widht=getWidth();
	int i=1;
	while(i!=4){
	int x_pos = total_widht-i*97 ; //spacing between shapes is 97.
	int y_pos = getHeight()-(SMALL_RECT_HEIGHT+25); //spacing from the xx axis is 25.
	draw_popular_shape(x_pos,y_pos,SMALL_RECT_WIDTH,SMALL_RECT_HEIGHT,SMALL_TRIANGLE_HEIGHT); 
	i+=1;
	
	}
}
private void build_house(){
	double door_x = (20+HOUSE_LEFT_RIGHT_RECT_WIDTH+HOUSE_CENTER_RECT_WIDTH/2.0)-HOUSE_DOOR_WIDTH/2;
	int y_pos = getHeight()-25 ;//spacing from the xx axis is 25.
	draw_popular_shape(20,y_pos-HOUSE_LEFT_RIGHT_HEIGHT, HOUSE_LEFT_RIGHT_RECT_WIDTH, HOUSE_LEFT_RIGHT_HEIGHT, HOUSE_LEFT_RIGHT_TRIANGLE_HEIGHT); //Left and right shapes
	draw_popular_shape(20+HOUSE_LEFT_RIGHT_RECT_WIDTH, y_pos-HOUSE_CENTER_RECT_HEIGHT,HOUSE_CENTER_RECT_WIDTH, HOUSE_CENTER_RECT_HEIGHT, HOUSE_CENTER_TRIANGLE_HEIGHT); //Center
	draw_popular_shape(20+HOUSE_LEFT_RIGHT_RECT_WIDTH+HOUSE_CENTER_RECT_WIDTH, y_pos-HOUSE_LEFT_RIGHT_HEIGHT, HOUSE_LEFT_RIGHT_RECT_WIDTH,HOUSE_LEFT_RIGHT_HEIGHT, HOUSE_LEFT_RIGHT_TRIANGLE_HEIGHT);
	draw_popular_shape(door_x,y_pos-HOUSE_DOOR_HEIGHT,HOUSE_DOOR_WIDTH,HOUSE_DOOR_HEIGHT,HOUSE_DOOR_TRIANGLE_HEIGHT);
	add(new GOval(door_x-30-HOUSE_CENTER_RECT_WIDTH/11, getHeight()-(HOUSE_CENTER_RECT_HEIGHT/fi+25)-30, 30.0, 30.0) );
	add(new GOval(door_x+HOUSE_DOOR_WIDTH+HOUSE_CENTER_RECT_WIDTH/10, getHeight()-(HOUSE_CENTER_RECT_HEIGHT/fi+25)-30, 30.0, 30.0) );
	
}
private static final int SMALL_RECT_WIDTH=30,SMALL_RECT_HEIGHT=140,SMALL_TRIANGLE_HEIGHT=70;
private static final int HOUSE_LEFT_RIGHT_RECT_WIDTH=75,HOUSE_CENTER_RECT_WIDTH=190,HOUSE_LEFT_RIGHT_HEIGHT=280;
private static final int HOUSE_CENTER_RECT_HEIGHT=SMALL_RECT_HEIGHT+SMALL_TRIANGLE_HEIGHT; //It is changing dynamicly.
private static final int HOUSE_LEFT_RIGHT_TRIANGLE_HEIGHT=60,HOUSE_CENTER_TRIANGLE_HEIGHT=105;
private static final int HOUSE_DOOR_WIDTH=36,HOUSE_DOOR_HEIGHT=55,HOUSE_DOOR_TRIANGLE_HEIGHT=20;
private static final double fi=1.618033988749; //Because mathematics is fun!

}
