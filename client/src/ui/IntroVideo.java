package ui;

import ui.dialogs.Dialogs;
import unabomber.client.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class IntroVideo extends Activity {

	private String path = "C4Labs.as.swf";
    private VideoView mVideoView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);

        mVideoView = (VideoView) findViewById(R.id.videoView);


        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(
                    IntroVideo.this,
                    "Please edit VideoViewDemo Activity, and set path"
                            + " variable to your media file URL/path",
                    Toast.LENGTH_LONG).show();

        } else {

            /*
             * Alternatively,for streaming media you can use
             * mVideoView.setVideoURI(Uri.parse(URLstring));
             */
            mVideoView.setVideoPath(path);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();

        }
		
	}
}