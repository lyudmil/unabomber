package ui;


import engine.GameEngine.GameStatus;
import unabomber.client.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MatchResult extends Activity {
	
	LinearLayout mLinearLayout;
	
	public static GameStatus gameStatus = GameStatus.FINISHEDWIN;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	   
	    
	    
	    // Create a LinearLayout in which to add the ImageView
	    mLinearLayout = new LinearLayout(this);

	    // Instantiate an ImageView and define its properties
	    ImageView i = new ImageView(this);
	    
	    
	    
	    //switch background image dpending on the gameStatus
	    switch (gameStatus) {
		case FINISHEDWIN:
			i.setImageResource(R.drawable.youwin);
			break;

		case FINISHEDLOSE:
			i.setImageResource(R.drawable.youlose);
			break;
			
		case FINISHEDKILLED:
			i.setImageResource(R.drawable.youlosekilled);
			break;
			
			
		case FINISHEDJAILED:
			i.setImageResource(R.drawable.youlosejail);
			break;
			
		default:
			i.setImageResource(R.drawable.youlose);
			break;
		}
	    
	    
	    i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
	    i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

	    // Add the ImageView to the layout and set the layout as the content view
	    mLinearLayout.addView(i);
	    setContentView(mLinearLayout);
	    
	    
	    
	}

}
