package phoenix.hackfest.orderit.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import phoenix.hackfest.orderit.Models.FoodOrder;
import phoenix.ism.hackfest.orderit.R;

/**
 * Created by Jibin_ism on 04-Mar-16.
 */
public class CheckoutList extends LinearLayout {

    private Context mContext;
    private ArrayList<FoodOrder> foodOrders;
    private LinearLayout checkoutList;
    private TextView qtyView;
    private RupeeTextView amountView;
    private int qty;
    private double cost;

    public CheckoutList(Context context) {
        this(context, null);
    }

    public CheckoutList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public CheckoutList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void init() {
        inflate(mContext,R.layout.view_checkoutlist,this);

        checkoutList = (LinearLayout) findViewById(R.id.orderList);
        qtyView = (TextView) findViewById(R.id.qtyView);
        amountView = (RupeeTextView) findViewById(R.id.amountView);

        if(foodOrders==null){
            foodOrders = new ArrayList<>();
        }
        updateList();
    }

    public void updateList(){
        qty=0;
        cost=0;
        checkoutList.removeAllViews();
        for(int i=0;i<foodOrders.size();i++){
            //Log.e("CheckoutList","Order: "+i+" "+foodOrders.get(i).toString());
            CheckoutItem checkoutItem = new CheckoutItem(mContext, foodOrders.get(i),i+1);
            checkoutItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkoutList.addView(checkoutItem);
            qty+=foodOrders.get(i).getQty();
            cost+=foodOrders.get(i).getCost();
        }

        qtyView.setText(String.valueOf(qty));
        //Log.e("Checkqty",String.valueOf(qty)+" quantity"+checkoutList.getChildCount());
        amountView.setAmount(cost);
        requestLayout();
    }


    public ArrayList<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(ArrayList<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
        updateList();
    }

    public void addFoodOrder(FoodOrder foodOrder){
        if(this.foodOrders == null){
            foodOrders= new ArrayList<>();
        }
        foodOrders.add(foodOrder);
        //Log.e("Check Adding", "Food order: " + foodOrder.toString());
        for(FoodOrder foodOrder1: foodOrders){
            //Log.e("Adding check: ",foodOrder1.toString());
        }
        updateList();
    }

    public int getTotalQty(){
        return this.qty;
    }

    public double getTotalAmount(){
        return this.cost;
    }

    public class CheckoutItem extends TableRow {

        private Context mContext;
        private FoodOrder foodOrder;
        private int index;
        private TextView sNoView, itemView, qtyView;
        private RupeeTextView costView;


        public CheckoutItem(Context context) {
            super(context);
            init();
        }

        public CheckoutItem(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }


        public CheckoutItem(Context context, AttributeSet attrs, FoodOrder foodOrder, int index) {
            super(context, attrs);
            this.mContext = context;
            this.foodOrder = foodOrder;
            this.index = index;
            init();
        }
        public CheckoutItem(Context context, FoodOrder foodOrder, int index) {
            super(context);
            this.mContext = context;
            this.foodOrder = foodOrder;
            this.index = index;
            init();
        }

        public void init() {
            //Log.e("CheckListItem","Init");
            inflate(mContext, R.layout.checkoutlist_item, this);
            sNoView = (TextView) findViewById(R.id.indexView);
            itemView = (TextView) findViewById(R.id.orderView);
            qtyView = (TextView) findViewById(R.id.qtyView);
            costView = (RupeeTextView) findViewById(R.id.amountView);

            //Log.e("CheckListItem","Food order: "+foodOrder.toString());
            if(foodOrder!=null) {
                //Log.e("CheckListItem","foodorder not null");
                sNoView.setText(String.valueOf(this.index));
                itemView.setText(foodOrder.getItemName());
                qtyView.setText(String.valueOf(foodOrder.getQty()));
                costView.setAmount(foodOrder.getCost());
            }
        }

        public FoodOrder getFoodOrder() {
            return foodOrder;
        }

        public void setFoodOrder(FoodOrder foodOrder) {
            this.foodOrder = foodOrder;
            invalidate();
        }
    }

}
