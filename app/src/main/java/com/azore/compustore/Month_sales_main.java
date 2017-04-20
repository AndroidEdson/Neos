package com.azore.compustore;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.graphics.Rect;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azore.compustore.fiuady.db.Inventory;
import com.azore.compustore.fiuady.db.Meses;
import com.azore.compustore.fiuady.db.Products;
import com.azore.compustore.fiuady.db.SalesMonth;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armín on 19/04/2017.
 */


public class Month_sales_main extends AppCompatActivity{

    public class MesesAdapter extends RecyclerView.Adapter<MesesAdapter.MyViewHolder>{

        private Context mContext;
        private List<Meses> mesesList;

        public class MyViewHolder extends RecyclerView.ViewHolder{
            private TextView title, count, total;
            private ImageView thumbnail, overflow;


            public MyViewHolder(View view) {
                super(view);
                title= (TextView) view.findViewById(R.id.id_title);
                total= (TextView) view.findViewById(R.id.txt_ganancia);
                count= (TextView) view.findViewById(R.id.txt_vendidas);

                thumbnail= (ImageView)view.findViewById(R.id.thumbnail);
                overflow= (ImageView)view.findViewById(R.id.overflow);
            }
        }

        public MesesAdapter(Context mContext, List<Meses> mesesList){
            this.mContext=mContext;
            this.mesesList=mesesList;
        }





        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.album_card,parent,false);
            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Meses meses = mesesList.get(position);
            final int aux= position;

            holder.title.setText(meses.getName());
            holder.total.setText(Double.toString((double) meses.getGanancia()/100));
            holder.count.setText(String.valueOf(meses.getNum()));

            Glide.with(mContext).load(meses.getThumbanail()).into(holder.thumbnail); 

            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), info_month_sales.class);
                    intent.putExtra(info_month_sales.EXTRA_DATE_BEGIN, mes_begin.get(position));
                    intent.putExtra(info_month_sales.EXTRA_DATE_END, mes_ending.get(position));

                    startActivityForResult(intent, request_code);

                    // Toast.makeText(getApplicationContext(), String.valueOf(aux), Toast.LENGTH_SHORT).show();

                }
            });


            holder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(), String.valueOf(aux), Toast.LENGTH_SHORT).show();

                    //showPopupMenu(holder.overflow);
                }


            });

        }

        private void showPopupMenu(View view) {
            PopupMenu popup= new PopupMenu(mContext,view);
            MenuInflater inflater= popup.getMenuInflater();
            inflater.inflate(R.menu.menu4, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
            popup.show();
        }

        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

            public MyMenuItemClickListener(){

            }

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.test_menu:
                        Toast.makeText(getApplicationContext(), "WORKS", Toast.LENGTH_SHORT).show();
                        return true;

                }

                return false;
            }

        }

        @Override
        public int getItemCount() {
        return  mesesList.size();
        }
    }







    //*********************************************************
    private RecyclerView recyclerView;
    private MesesAdapter adapter;
    private Inventory inventory;
    private List<Meses> mesList;
    private List<SalesMonth> salesMonthList;
    private Spinner spinner;
    private int PosicionSpinner;
     ArrayAdapter<String> spinner_adapter;
    List<String> mes_begin;
    List<String> mes_ending;
    int request_code=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_sales_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mesList= new ArrayList<>();


        adapter= new MesesAdapter(this, mesList);

        RecyclerView.LayoutManager mlayoutManager= new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        spinner = (Spinner) findViewById(R.id.spinner_anio);

        spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);

        int  siglo =2000;
        for (int i=0; i<30; i++){
            int anio = siglo+i;
            spinner_adapter.add(String.valueOf(anio));
        }

        spinner.setAdapter(spinner_adapter);

        //Toast.makeText(getApplicationContext(),  spinner_adapter.getItem(position), Toast.LENGTH_SHORT ).show();



        prepareAlbums();

        try{
            Glide.with(this).load(R.drawable.texture).into((ImageView)findViewById(R.id.image_new));

        }catch (Exception e){
            e.printStackTrace();
        }



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // int j = Integer.valueOf(spinner_adapter.getItem(position));
                //Toast.makeText(getApplicationContext(), categoriesProduct.get(position).getDescription(), Toast.LENGTH_SHORT).show();

                 PosicionSpinner = position;
                spinner_adapter.getItem(position);
                prepareAlbums();

                adapter= new MesesAdapter(getApplicationContext(), mesList);
                recyclerView.setAdapter(adapter);
               // Toast.makeText(getApplicationContext(), String.valueOf(mesList.get(0).getNum())+"  "+String.valueOf(salesMonthList.get(0).getCount()) , Toast.LENGTH_SHORT).show();

                 //Toast.makeText(getApplicationContext(),mes_begin.get(0)+"  "+ mes_ending.get(0) , Toast.LENGTH_SHORT).show();



                //Toast.makeText(getApplicationContext(),  spinner_adapter.getItem(position), Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//
            }
        });


    }

    private void initCollapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbar=
                (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
         boolean isShow= false;
            int scrollRange=-1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange== -1){
                    scrollRange= appBarLayout.getTotalScrollRange();
                }
                if (scrollRange+ verticalOffset== 0){
                    collapsingToolbar.setTitle("Card View");
                    isShow=true;
                }else if (isShow){
                    collapsingToolbar.setTitle(" ");
                    isShow=false;
                }

            }

        });
    }


private void prepareAlbums(){
    int[] covers = new int[]{
            R.drawable.ic_calendar,
            R.drawable.ic_action_search,
            R.drawable.ic_laptop_chromebook
    };


    String year_spinner= spinner_adapter.getItem(PosicionSpinner);
    salesMonthList= new ArrayList<>();
    mes_begin = new ArrayList<>();
    mes_ending = new ArrayList<>();
    mesList= new ArrayList<>();

    //ENERO
    mes_begin.add(year_spinner+"-01-01");
    mes_ending.add(year_spinner+"-01-31");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(0), mes_ending.get(0)));
    //FEBRERO
    mes_begin.add(year_spinner+"-02-01");
    mes_ending.add(year_spinner+"-02-29");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(1), mes_ending.get(1)));
    //MARZO
    mes_begin.add(year_spinner+"-03-01");
    mes_ending.add(year_spinner+"-03-31");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(2), mes_ending.get(2)));

    //ABRIL
    mes_begin.add(year_spinner+"-04-01");
    mes_ending.add(year_spinner+"-04-30");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(3), mes_ending.get(3)));

    //MAYO
    mes_begin.add(year_spinner+"-05-01");
    mes_ending.add(year_spinner+"-05-31");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(4), mes_ending.get(4)));

    //JUNIO
    mes_begin.add(year_spinner+"-06-01");
    mes_ending.add(year_spinner+"-06-30");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(5), mes_ending.get(5)));

    //JULIO
    mes_begin.add(year_spinner+"-07-01");
    mes_ending.add(year_spinner+"-07-31");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(6), mes_ending.get(6)));

    //AGOSTO
    mes_begin.add(year_spinner+"-08-01");
    mes_ending.add(year_spinner+"-08-31");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(7), mes_ending.get(7)));

    //SEPTIEMBRE
    mes_begin.add(year_spinner+"-09-01");
    mes_ending.add(year_spinner+"-09-30");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(8), mes_ending.get(8)));

    //OCTUBRE
    mes_begin.add(year_spinner+"-10-01");
    mes_ending.add(year_spinner+"-10-31");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(9), mes_ending.get(9)));

    //NOVIEMBRE
    mes_begin.add(year_spinner+"-11-01");
    mes_ending.add(year_spinner+"-11-30");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(10), mes_ending.get(10)));

    //DICIEMBRE
    mes_begin.add(year_spinner+"-12-01");
    mes_ending.add(year_spinner+"-12-31");
    salesMonthList.add(inventory.getSalesMonth(mes_begin.get(11), mes_ending.get(11)));



    Meses a = new Meses("Enero", salesMonthList.get(0).getCount() , salesMonthList.get(0).getGanancia() ,covers[0]);
    mesList.add(a);

     a = new Meses("Febrero", salesMonthList.get(1).getCount(), salesMonthList.get(1).getGanancia() ,covers[0]);
    mesList.add(a);

    a = new Meses("Marzo", salesMonthList.get(2).getCount(), salesMonthList.get(2).getGanancia() ,covers[0]);
    mesList.add(a);

    a = new Meses("Abril", salesMonthList.get(3).getCount(),salesMonthList.get(3).getGanancia() , covers[0]);
    mesList.add(a);

    a = new Meses("Mayo", salesMonthList.get(4).getCount(), salesMonthList.get(4).getGanancia() ,covers[0]);
    mesList.add(a);

    a = new Meses("Junio", salesMonthList.get(5).getCount(), salesMonthList.get(5).getGanancia() ,covers[0]);
    mesList.add(a);

    a = new Meses("Julio", salesMonthList.get(6).getCount(),salesMonthList.get(6).getGanancia() , covers[0]);
    mesList.add(a);

    a = new Meses("Agosto", salesMonthList.get(7).getCount(),salesMonthList.get(7).getGanancia() , covers[0]);
    mesList.add(a);

    a = new Meses("Septiembre", salesMonthList.get(8).getCount(),salesMonthList.get(8).getGanancia() , covers[0]);
    mesList.add(a);

    a = new Meses("Octubre", salesMonthList.get(9).getCount(), salesMonthList.get(9).getGanancia() ,covers[0]);
    mesList.add(a);

    a = new Meses("Noviembre", salesMonthList.get(10).getCount(), salesMonthList.get(10).getGanancia() ,covers[0]);
    mesList.add(a);

    a = new Meses("Diciembre", salesMonthList.get(11).getCount(),salesMonthList.get(11).getGanancia() , covers[0]);
    mesList.add(a);

  adapter.notifyDataSetChanged();

}

private  class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
    private int spanCount;
    private int spacing ;
    private boolean includeEdge;


    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if(includeEdge){
            outRect.left= spacing-column*spacing/spanCount;
            outRect.right= (column+1)* spacing/spanCount;


            if(position< spanCount){
                outRect.top=spacing;
            }
            outRect.bottom=spacing;
        }else{
            outRect.left= column *spacing/spanCount ;
            outRect.right=spacing-(column+1)*spacing/spanCount;
            if (position >= spanCount){
                outRect.top=spacing;
            }
        }

    }
}


private  int dpToPx(int dp){
    Resources r =getResources();
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
}


}
