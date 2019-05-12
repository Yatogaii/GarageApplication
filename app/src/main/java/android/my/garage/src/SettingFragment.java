package android.my.garage.src;

import android.my.garage.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends Fragment implements View.OnClickListener{
    final String TAG = "Setting";
    View accountSetting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        accountSetting = view.findViewById(R.id.accountSetting);
        accountSetting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accountSetting:
                Log.w(TAG, "onClick: 点击了账号view");
        }
    }
}
