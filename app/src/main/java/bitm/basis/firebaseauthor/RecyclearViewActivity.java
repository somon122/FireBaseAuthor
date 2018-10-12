package bitm.basis.firebaseauthor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclearViewActivity extends AppCompatActivity implements RecyclerViewAdapter.ClickListener{

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    EditText nameET,timeET;
    String name,time;
    ArrayList<RViewData>dataList;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclear_view);

        nameET = findViewById(R.id.recyclerName_id);
        timeET = findViewById(R.id.recyclerTime_id);
        recyclerView1 = findViewById(R.id.recyclerView1_id);
        recyclerView2 = findViewById(R.id.recyclerView2_id);

        dataList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this,dataList,this);

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL ,false);
        recyclerView1.setLayoutManager(manager1);
        recyclerView1.setAdapter(adapter);

 RecyclerView.LayoutManager manager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL ,false);
        recyclerView2.setLayoutManager(manager2);
        recyclerView2.setAdapter(adapter);



    }

    public void SaveTime(View view) {

        name = nameET.getText().toString();
        time = timeET.getText().toString();

        RViewData viewData = new RViewData(name,time);
        dataList.add(viewData);
        adapter.DataUpdate(dataList);

    }

    @Override
    public void onClick(RViewData viewData) {
       startActivity(new Intent(RecyclearViewActivity.this,MainActivity.class));

    }
}
