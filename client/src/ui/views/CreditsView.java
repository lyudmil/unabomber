package ui.views;

import unabomber.client.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CreditsView extends ListActivity{

	private String[] credits = new String[]{"Lyudmil Angelov", "Stefano Brivio", "Davide Gadioli", "Andrea Manfredi","Developed for the Videogame Design and Programming course - Prof. Pier Luca Lanzi and Dr. Daniele Loiacono - Facolta' di Ingegneria dell'Informazione - Dipartimento di Elettronica e Informazione - Politecnico di Milano"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, credits));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setFocusable(true);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
}
