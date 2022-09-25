package fr.ulille.iut.agile.client_android;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //private static final String URL_CB = "https:/groupe-2.k8s.azae.eu/api/v1/interface";
    private static final String URL_CB = "http:/192.168.1.25:8080/api/v1/interface";

    private RequestQueue queue;
    private ExpandableListView expandableListView;
    private JSONArray object;
    private String[] parent;

    //exemple de niveau 3
    LinkedHashMap<String, String[]> thirdLevelq1 = new LinkedHashMap<>();

    //exemple de niveau 2
    List<String[]> secondLevel = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPhoto();
            }
        });
    }

    public void goToPhoto(){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }

    private void setUpAdapter() {
        //exemple d'ajout au 2nd niveau
        String[] q1 = new String[]{"test"};
        secondLevel.add(q1);
        //exemple d'ajout au 3e niveau
        thirdLevelq1.put(q1[0], new String[]{"description"});
        //thirdLevelq1.put(q1[1], des2);

        //exemple d'ajout à la treeview
        data.add(thirdLevelq1);
        expandableListView = (ExpandableListView) findViewById(R.id.expandible_listview);
        ThreeLevelListAdapter threeLevelListAdapterAdapter = new ThreeLevelListAdapter(this, parent, secondLevel, data);
        expandableListView.setAdapter(threeLevelListAdapterAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void doTest(View view) throws JSONException {
        final TextView tvResultat = findViewById(R.id.resultat);
        final TextView etSearch = findViewById(R.id.search_bar);

        StringRequest request = new StringRequest(Request.Method.GET, URL_CB + "/espece?desc=" + etSearch.getText().toString(),
                (response) -> {
                    tvResultat.setText(response);
                    try {
                        object = new JSONArray(response);
                        parent = arrayListToTab(filterIsDeclarable(object));
                        setUpAdapter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (error) -> tvResultat.setText(error.toString())) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        request.setTag("VOLLEY");
        queue.add(request);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll("VOLLEY");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String[] arrayListToTab (ArrayList<String> al){
        String [] tab = new String[al.size()];
        for(int i = 0; i < al.size(); i++){
            tab[i] = al.get(i);
        }
        return tab;
    }

    public ArrayList<String> filterIsDeclarable(JSONObject obj) throws JSONException {
        ArrayList<String> res = new ArrayList<>();
        JSONObject tmp = obj;
        if(obj.has("nomenclature")){
            tmp = obj.getJSONObject("nomenclature");
        }
        JSONArray children = tmp.getJSONArray("children");
        if(tmp.getBoolean("isDeclarable"))
            res.add(tmp.getString("code"));
        if(children != null && children.length() > 0)
            res.addAll(filterIsDeclarable(children));
        return res;
    }

    public ArrayList<String> filterIsDeclarable(JSONArray array) throws JSONException {
        ArrayList<String> res = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            res.addAll(filterIsDeclarable(array.getJSONObject(i)));
        }
        return res;
    }

    public String[] getParents(JSONArray array) throws JSONException {
        String[] res = new String[array.length()];
        for(int i = 0; i < array.length(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            obj = obj.getJSONObject("nomenclature");
            res[i] = obj.getString("code") + "\n desc = " + obj.getString("descFR");
        }
        return res;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String[] parents(JSONArray array) throws JSONException {
        String[] res = new String[array.length()];
        JSONObject[] tab = new JSONObject[array.length()];
        for(int i = 0; i < array.length(); i++){
            tab[i] = (JSONObject) array.get(i);
            tab[i] = tab[i].getJSONObject("nomenclature");
            res[i] = tab[i].getString("code")+tab[i].getString("descFR");
        }
        return res;
    }
}