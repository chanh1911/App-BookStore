package com.tvt.nhosm2bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    ListView lvtim;
    EditText key;
    Connection connection;
    ArrayList<Book> books;
    Button tim;

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_search, container, false);
        AnhXa();
        books=new ArrayList<Book>();

        tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConSQL c=new ConSQL();
                connection=c.conclass();
                try {
                    String sqlstatement = "Select * from CreateBook where NameBook like '%"+key.getText()+"%'";
                    Toast.makeText(getActivity(),"SQL :"+sqlstatement,Toast.LENGTH_SHORT).show();

                    Statement smt = connection.createStatement();
                    ResultSet set = smt.executeQuery(sqlstatement);
                        while (set.next()){

                            books.add(new Book(set.getInt("ID"),set.getString("NameBook"),set.getString("NameTG"),set.getString("IDTG"),set.getString("ViewBook"),set.getString("Tim"),set.getString("Content"),set.getBytes("IMG"),set.getString("DayCreate")));

                        }
                        VietAdapter anadapter =new VietAdapter(
                                getActivity(),
                                R.layout.show_book,
                                books
                        );
                        lvtim.setAdapter(anadapter);
                        lvtim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent =new Intent(getActivity(),DetailBook.class);
                                String id= String.valueOf(books.get(i).ID);
                                intent.putExtra("Book",id);
                                startActivity(intent);
                            }
                        });

                }
                catch (Exception e){
                    Log.e("Error: ",e.getMessage());
                }

            }
        });



        return view;
    }

    public  void AnhXa(){
        lvtim=(ListView) view.findViewById(R.id.lvsearch) ;
        key=(EditText) view.findViewById(R.id.key);
        tim=(Button) view.findViewById(R.id.tim);
    }

}