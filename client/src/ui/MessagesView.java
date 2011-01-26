package ui;

import java.util.ArrayList;

import unabomber.client.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import engine.PlayerMessage;





public class MessagesView extends ListActivity{
	private ArrayList<String> playerIdMessage= new ArrayList<String>();




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		Bundle extras = getIntent().getExtras(); 
		
		if(extras !=null){
			ArrayList<PlayerMessage> messages = (ArrayList<PlayerMessage>) extras.get("messages"); 

			for(int i=0;i<messages.size();i++){
				playerIdMessage.add((String.valueOf((messages.get(i)).getSender()) + ": " + (messages.get(i).getMessage())));
			}

		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, playerIdMessage));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setFocusable(true);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	

	}

}
