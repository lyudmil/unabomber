package ui.views;

import unabomber.client.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InfoView extends ListActivity{

	private String[] info = new String[]{"Help", "Credits"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, info));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setFocusable(true);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position){
				case 0:
					Intent help = new Intent(InfoView.this, HelpView.class );
					startActivity(help);
					return;

				case 1:
					Intent credits = new Intent(InfoView.this, CreditsView.class);
					startActivity(credits);
					return;
				}

			}
		});
	}

}



