package com.liang.databinding;

import android.database.sqlite.SQLiteOpenHelper;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.liang.databinding.databinding.ActivityUserBinding;
import com.liang.databinding.db.RemoteAppInfo;
import com.liang.databinding.db.RemoteDBHelper;
import com.liang.databinding.db.RemoteDBUtil;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user);
        binding.setUser(new User("liang","liangliang"));
        binding.setVariable(BR.user,new User("set","variable"));
        binding.firstName.setText("nimeia");

        binding.setPresenter(new Presenter());

    }

    public class Presenter {
        public void onTextChanged(CharSequence s, int start, int before, int count){
            User user = new User(s.toString(),"last");
            binding.setUser(user);
        }
        public void onClick(View view) {
            Toast.makeText(UserActivity.this,"onClick",Toast.LENGTH_LONG).show();
        }

        /**
         * 数据回传
         */
        public void setOnClick(User user) {
            Toast.makeText(UserActivity.this,user.getFirstName() + user.getLastName(),Toast.LENGTH_LONG).show();
        }
    }






    public void mainTest(SQLiteOpenHelper helper, List<RemoteAppInfo> list) {
        for (RemoteAppInfo remoteAppInfo : list) {
            RemoteDBUtil.insert(helper, remoteAppInfo);
        }

        RemoteDBUtil.insert(helper, list);

        RemoteDBUtil.insertBySql(helper,list);
    }

}
