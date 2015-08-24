package com.andressantibanez.android.quota;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class QuotaLayout extends LinearLayout{

    public static final String TAG = QuotaLayout.class.getSimpleName();

    private static final int DEFAULT_COMPLIED_COLOR = R.color.default_complied_color;
    private static final int DEFAULT_TOTAL_COLOR = R.color.default_total_color;

    /**
     * Variables
     */
    private String mTitle;
    private double mCompliedAmount;
    private double mTotalAmount;
    private double mCompliedPercentage;
    private int mCompliedColor;
    private int mTotalColor;


    /**
     * Controls
     */
    private TextView mTitleView;
    private TextView mCompliedPercentageView;
    private View mCompliedProgressView;
    private View mTotalView;
    private TextView mCompliedAmountView;
    private TextView mTotalAmountView;

    public QuotaLayout(Context context) {
        super(context, null);
    }

    public QuotaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public QuotaLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.quota_layout, this, true);

        //Init variables
        mTitle = "";
        mCompliedAmount = 20;
        mTotalAmount = 100;

        //Get controls
        mTitleView = (TextView) findViewById(R.id.quota_title);
        mCompliedPercentageView = (TextView) findViewById(R.id.quota_percentage);
        mCompliedProgressView = findViewById(R.id.complied_progress_view);
        mTotalView = findViewById(R.id.total_amount_view);
        mCompliedAmountView = (TextView) findViewById(R.id.complied_amount);
        mTotalAmountView = (TextView) findViewById(R.id.total_amount);

        //Check custom attributes
        if(attrs != null ) {

            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.QuotaLayout, 0, 0);
            try {

                mTitle = a.getString(R.styleable.QuotaLayout_quotaTitle);

                mCompliedColor = a.getColor(R.styleable.QuotaLayout_quotaCompliedColor, getResources().getColor(DEFAULT_COMPLIED_COLOR));
                mTotalColor = a.getColor(R.styleable.QuotaLayout_quotaTotalColor, getResources().getColor(DEFAULT_TOTAL_COLOR));

                mCompliedAmount = a.getFloat(R.styleable.QuotaLayout_quotaCompliedAmount, 0);
                mTotalAmount = a.getFloat(R.styleable.QuotaLayout_quotaTotalAmount, 0);

            } finally {
                a.recycle();
            }
        }

        //Update view
        updateUi();
    }


    /**
     * Setters
     */
    public void setTitle(String title) {
        mTitle = title;
        updateUi();
    }

    public void setAmounts(double compliedAmount, double totalAmount) {
        mCompliedAmount = compliedAmount;
        mTotalAmount = totalAmount;
        updateUi();
    }

    public void setCompliedAmount(double compliedAmount) {
        setAmounts(compliedAmount, mTotalAmount);
    }

    public void setTotalAmount(double totalAmount) {
        setAmounts(mCompliedAmount, totalAmount);
    }


    /**
     * Ui methods
     */
    private void updateUi() {
        //Set title
        mTitleView.setText(mTitle);

        //Set style
        mCompliedProgressView.setBackgroundColor(mCompliedColor);
        mTotalView.setBackgroundColor(mTotalColor);

        //Set amounts
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##", decimalFormatSymbols);

        mCompliedAmountView.setText("$ " + decimalFormat.format(mCompliedAmount));
        mTotalAmountView.setText("$ " + decimalFormat.format(mTotalAmount));

        //Set percentage
        if(mCompliedAmount == 0 && mTotalAmount == 0)
            mCompliedPercentage = 0;

        if(mCompliedAmount > 0 && mTotalAmount == 0)
            mCompliedPercentage = 100;

        if(mCompliedAmount > 0 && mTotalAmount > 0) {
            mCompliedPercentage = (mCompliedAmount/mTotalAmount * 100);
        }

        decimalFormat = new DecimalFormat("#", decimalFormatSymbols);
        mCompliedPercentageView.setText(decimalFormat.format(mCompliedPercentage) + "%");
        if(mCompliedPercentage > 100) {
            mTotalView.setVisibility(View.GONE);
        } else {
            //Calculate new weights
            float compliedProgressViewWeight = (float) mCompliedPercentage;
            float totalViewWeight = 100 - compliedProgressViewWeight;

            //Update progress and total weight
            LayoutParams compliedProgressViewLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, compliedProgressViewWeight);
            mCompliedProgressView.setLayoutParams(compliedProgressViewLayoutParams);

            LayoutParams totalViewLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, totalViewWeight);
            mTotalView.setLayoutParams(totalViewLayoutParams);
        }
    }


    /**
     * Configuration change handling
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState savedState = new SavedState(superState);
        savedState.setTitle(mTitle);
        savedState.setCompliedAmount(mCompliedAmount);
        savedState.setTotalAmount(mTotalAmount);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        mTitle = savedState.getTitle();
        mCompliedAmount = savedState.getCompliedAmount();
        mTotalAmount = savedState.getTotalAmount();

        updateUi();
    }

    protected static class SavedState extends BaseSavedState {
        String mTitle;
        double mCompliedAmount;
        double mTotalAmount;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in) {
            super(in);
            mTitle = in.readString();
            mCompliedAmount = in.readDouble();
            mTotalAmount = in.readDouble();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(mTitle);
            out.writeDouble(mCompliedAmount);
            out.writeDouble(mTotalAmount);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        /**
         * Setters
         */
        public void setTitle(String title) {
            mTitle = title;
        }

        public void setCompliedAmount(double compliedAmount) {
            mCompliedAmount = compliedAmount;
        }

        public void setTotalAmount(double totalAmount) {
            mTotalAmount = totalAmount;
        }


        /**
         * Getters
         */
        public String getTitle() {
            return mTitle;
        }
        public double getCompliedAmount() {
            return mCompliedAmount;
        }

        public double getTotalAmount() {
            return mTotalAmount;
        }
    }
}
