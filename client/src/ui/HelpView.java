package ui;

import unabomber.client.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HelpView extends ListActivity{

	private String[] roles = new String[]{"Regular citizen: if you’re a regular citizen your goal is to survive long enough for the unabombers to be caught. You need to avoid getting murdered, but collect useful information so that the police can track the criminals down faster. You'll be able to track your own movement as well as the movement of all other players in the game. You therefore need to observe the game and communicate to the policeman any relevant information such as who you've seen near the site of an explosion. It is in your best interest to do so, as you only win if all the perpetrators are caught.", "Policeman: if you’re the policeman, your objective is to catch the unabombers. Like all other players, you can also track your movements and the movements of others. However, you are the only player who always survives bombings. With that power comes the responsibility of catching the perpetrator. You try to identify suspects and build your case against them by observing the game and talking to witnesses. Once you have reasonable suspicion who the criminal is, you can choose to send them to prison. Keep in mind that if you get the wrong guy, they are out of the game and the unabomber comes that much closer to winning.", "Unabomber: your goal is to kill as many of the citizens as possible without getting caught by the police. Like the ordinary citizen, you can track your movement and the movement of everyone else. However, you have the added ability to place bombs at locations of your choosing. Once you've placed a bomb, you have the option to detonate it at any point in time and kill all citizens within its range. Keep in mind that policemen always survive and it is a good idea to cover your tracks so they don't find out who you are and send you to prison. It is also useful to remember that citizens are potential witnesses that can testify against you, so if you suspect you’ve been exposed, it’s better to get to those witnesses before they talk to the police."};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, roles));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setFocusable(true);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


	}

}



