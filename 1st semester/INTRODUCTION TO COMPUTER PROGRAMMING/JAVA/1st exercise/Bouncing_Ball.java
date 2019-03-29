import acm.program.*;
import acm.graphics.*;
import java.awt.*; // Import this for the ball's color. ( Abstract windows toolkit )
/* Exercice 2
		Bouncing_Ball */
public class Bouncing_Ball extends GraphicsProgram {
   public void run() { 
   GOval ball = new GOval(START_POS_X,START_POS_Y,BALL_SIZE,BALL_SIZE); //It's a circle with radius=BALL_SIZE/2
   ball.setFilled(true);
   ball.setFillColor(Color.RED);
   add(ball);
   double dx= (getWidth() - BALL_SIZE)/130;
   double dy= (getHeight() - BALL_SIZE)/130;
   while(true){
	   ball.move(dx,dy);
	   pause(10); // 10 ms per move.I don't think a person's eye can catch this!
	   if ((ball.getX() >= getWidth() - BALL_SIZE) || (ball.getX()<=0)) {
		   dx = -dx;
		
		   
	   }if ((ball.getY() >= getHeight()-BALL_SIZE) || (ball.getY() <= 0)){
		   dy=-dy;
	   
   }
}
   }
private static final int BALL_SIZE = 70, START_POS_X=60, START_POS_Y=200 ;
}
