package ui.views;

import java.util.ArrayList;

import unabomber.client.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import engine.PlayerMessage;

public class MessagesView extends ListActivity{
	private ArrayList<String> messageList= new ArrayList<String>();

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras(); 
		
		if(extras !=null){
			ArrayList<PlayerMessage> messages = (ArrayList<PlayerMessage>) extras.get("messages"); 

			for(PlayerMessage message : messages) {
				messageList.add(message.getSender() + ": " + message.getContent());
			}
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, messageList));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setFocusable(true);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

}
