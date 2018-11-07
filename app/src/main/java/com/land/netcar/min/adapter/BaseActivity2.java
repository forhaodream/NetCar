package com.land.netcar.min.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;

public class BaseActivity2 extends AppCompatActivity {

    private Person p1;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);

        ArrayMap<Integer,String> arrayMap = new ArrayMap<>();

        Person p = new Person();
        p.name = "雪松";
        p.age = 24;

        Person p1 = null;

        try {
            p1 = (Person)p.clone();
            p1.name = "冀帅朋";
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


        Log.e(this.getClass().getSimpleName(),p.toString());
        if(p1!=null)
        Log.e(this.getClass().getSimpleName(),p1.toString());


    }

    class Person implements Cloneable {
        public String name;
        public int age;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
