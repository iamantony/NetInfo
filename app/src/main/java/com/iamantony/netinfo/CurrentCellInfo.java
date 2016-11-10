package com.iamantony.netinfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

public final class CurrentCellInfo extends LinearLayout {

    public static final String TAG = "CurrentCellInfo";

    private TextView m_countryCode;
    private TextView m_countryName;
    private ImageView m_countryFlag;
    private TextView m_operatorCode;
    private TextView m_operatorName;
    private TextView m_cellTypeName;
    private TextView m_cellLAC;
    private TextView m_cellCID;

    public CurrentCellInfo(Context context) {
        super(context);
        Init();
    }

    public CurrentCellInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public CurrentCellInfo(Context context, AttributeSet attrs,
                           int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    private void Init() {
        LayoutInflater layoutInflater =
                (LayoutInflater)getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.current_cell_info, this, true);

        m_countryCode = (TextView)findViewById(R.id.cci_countryCodeTV);
        m_countryName = (TextView)findViewById(R.id.cci_countryNameTV);
        m_countryFlag = (ImageView)findViewById(R.id.cci_countryFlagIV);
        m_operatorCode = (TextView)findViewById(R.id.cci_operatorCodeTV);
        m_operatorName = (TextView)findViewById(R.id.cci_operatorNameTV);
        m_cellTypeName = (TextView)findViewById(R.id.cci_cellTypeCodeTV);
        m_cellLAC = (TextView)findViewById(R.id.cci_cellLACCodeTV);
        m_cellCID = (TextView)findViewById(R.id.cci_cellCIDCodeTV);
    }

    // TODO: how to set info?
    public void SetCountryInfo() {

    }

}

