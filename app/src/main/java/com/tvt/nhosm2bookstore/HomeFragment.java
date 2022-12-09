package com.tvt.nhosm2bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    View view;
    ImageView img;
    ImageSlider imageSlider;
    public RecyclerView rcvCategory;
    Connection connection;
    public categoryAdapter categoryAdapter;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {

        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view= inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider=view.findViewById(R.id.img);

        rcvCategory =(RecyclerView) view.findViewById(R.id.rcv_category);
        categoryAdapter=new categoryAdapter(getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        img=(ImageView) view.findViewById(R.id.imageView);


        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);



        initUI();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),Profile.class);
                startActivity(intent);

            }
        });

        return view;


    }




    public List<category> getListCategory(){
        List<category>listCategory =new ArrayList<>();

        String sqlstatement = "Select Top 10 * \n" +
                "from CreateBook\n" +
                "order by DayCreate DESC";
        String sqllistBookView="Select Top 10 * \n" +
                "from CreateBook\n" +
                "order by ViewBook DESC";
        String sqllistBookTop="Select Top 10 * \n" +
                "from CreateBook\n" +
                "order by Tim DESC";



        listCategory.add(new category("Truyện mới",squery(sqlstatement)) );
        listCategory.add(new category("Nhiều người đọc nhất",squery(sqllistBookView)) );
        listCategory.add(new category("Top 10",squery(sqllistBookTop)) );


        return listCategory;
    }

    public  List<Book> squery( String sql){
        ConSQL c=new ConSQL();
        connection=c.conclass();
        List<Book> listBook=new ArrayList<>();

        try {
            String sqlstatement = sql;

            Statement smt = connection.createStatement();
            ResultSet set = smt.executeQuery(sqlstatement);

            while (set.next()){
                listBook.add(new Book(set.getInt("ID"),set.getString("NameBook"),set.getString("NameTG"),set.getString("IDTG"),set.getString("ViewBook"),set.getString("Tim"),set.getString("Content"),set.getBytes("IMG"),set.getString("DayCreate")));
            }
            connection.close();
        }

        catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }

        return listBook;



    }

    private void initUI() {

        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.img, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.img_1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.img_2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.img_3, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.img_4, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(categoryAdapter!=null){
            categoryAdapter.release();
        }
    }

}