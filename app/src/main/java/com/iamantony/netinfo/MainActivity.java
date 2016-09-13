package com.iamantony.netinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.gsm.GsmCellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String TAG = "NetInfo";

    TelephonyManager m_telManager = null;
    Button m_refreshButton = null;
    TextView m_textView = null;
    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_telManager =  (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        m_refreshButton = (Button)findViewById(R.id.button);
        m_refreshButton.setOnClickListener(MainActivity.this);

        m_textView = (TextView)findViewById(R.id.textView);

        CollectInfo();
    }

    @Override
    public void onClick(View v)
    {
        CollectInfo();
    }

    private void CollectInfo()
    {
        StringBuilder telephonyInfo = new StringBuilder();
        Log.i(TAG, "Counter: " + counter);
        telephonyInfo.append("======= Counter: " + counter + " =========\r\n");
        counter++;

        if (m_telManager == null)
        {
            Log.i(TAG, "No TelephonyManager");
            if (m_textView != null)
            {
                m_textView.setText(telephonyInfo.toString());
            }

            return;
        }

        Log.i(TAG, "Get cellular operator MNC and MCC");
        String netOperator = m_telManager.getNetworkOperator();
        if (netOperator != null && !netOperator.isEmpty())
        {
            String mcc = m_telManager.getNetworkOperator().substring(0, 3);
            String mnc = m_telManager.getNetworkOperator().substring(mcc.length());

            Log.i(TAG, "MCC: " + mcc + ", MNC: " + mnc);

            telephonyInfo.append("MCC: " + mcc + ", MNC: " + mnc + "\r\n");
        }
        else
        {
            Log.i(TAG, "No info about network operator");
            telephonyInfo.append("----- No getNetworkOperator()\r\n");
        }

        Log.i(TAG, "Call getCellLocation()");
        CellLocation cellLoc = m_telManager.getCellLocation();
        int phoneType = m_telManager.getPhoneType();
        if (cellLoc != null && phoneType == TelephonyManager.PHONE_TYPE_GSM)
        {
            GsmCellLocation location = (GsmCellLocation)cellLoc;
            int lac = location.getLac();
            int cid = location.getCid();
            int psc = location.getPsc();

            Log.i(TAG, "getCellLocation() GSM. LAC: " + lac + ", CID: " + cid + ", PSC: " + psc);

            telephonyInfo.append("----- GSM Cell Location" + "\r\n");
            telephonyInfo.append("LAC: " + lac + ", CID: " + cid + ", PSC: " + psc + "\r\n");
        }
        else if (cellLoc != null && phoneType == TelephonyManager.PHONE_TYPE_CDMA)
        {
            CdmaCellLocation location = (CdmaCellLocation)cellLoc;
            int bsid = location.getBaseStationId();
            int lat = location.getBaseStationLatitude();
            int lon = location.getBaseStationLongitude();
            int nid = location.getBaseStationLongitude();
            int sid = location.getBaseStationLongitude();

            Log.i(TAG, "getCellLocation() CDMA. BSID: " + bsid + ", NID: " + nid +
                    ", SID: " + sid + ", LAT: " + lat + ", LON: " + lon);

            telephonyInfo.append("----- CDMA Cell Location" + "\r\n");
            telephonyInfo.append("BSID: " + bsid + ", NID: " + nid + ", SID: " + sid +
                    ", LAT: " + lat + ", LON: " + lon + "\r\n");
        }
        else
        {
            Log.i(TAG, "No getCellLocation()");
            telephonyInfo.append("----- No getCellLocation()\r\n");
        }

        Log.i(TAG, "Call getNeighboringCellInfo()");
        List<NeighboringCellInfo> nciList = m_telManager.getNeighboringCellInfo();
        if (!nciList.isEmpty())
        {
            telephonyInfo.append("----- Neighboring CellInfo" + "\r\n");
            int i = 1;
            for (NeighboringCellInfo cellInfo : nciList)
            {
                int cid = cellInfo.getCid();
                int lac = cellInfo.getLac();
                int psc = cellInfo.getPsc();
                int rssi = cellInfo.getRssi();

                Log.i(TAG, "getNeighboringCellInfo(): i: " + i + ", CID: " + cid +
                        ", LAC: " + lac + ", PSC: " + psc + ", RSSI: " + rssi);

                telephonyInfo.append("No. " + i + ": " + "CID: " + cid +
                        ", LAC: " + lac + ", PSC: " + psc + ", RSSI: " + rssi + "\r\n");
                i++;
            }
        }
        else
        {
            Log.i(TAG, "No getNeighboringCellInfo()");
            telephonyInfo.append("----- No getNeighboringCellInfo()" + "\r\n");
        }

        Log.i(TAG, "Call getAllCellInfo()");
        List<CellInfo> ciList = m_telManager.getAllCellInfo();
        if (ciList != null && !ciList.isEmpty())
        {
            telephonyInfo.append("----- AllCellInfo Section" + "\r\n");
            int i = 1;
            for (CellInfo cellInfo : ciList)
            {
                Log.i(TAG, "getAllCellInfo(): i=" + i + ", info=" + cellInfo.toString());
                telephonyInfo.append("No." + i + ": " + cellInfo.toString() + "\r\n");
                i++;
            }
        }
        else
        {
            Log.i(TAG, "No getAllCellInfo()");
            telephonyInfo.append("----- No getAllCellInfo()" + "\r\n");
        }

        if (m_textView != null)
        {
            m_textView.setText(telephonyInfo.toString());
        }
    }
}
