package myapp.com.weatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String str="Kanpur";
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    ImageView iv;
    EditText et;
    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        tv4=(TextView)findViewById(R.id.textView4);
        tv5=(TextView)findViewById(R.id.textView6);
        tv6=(TextView)findViewById(R.id.textView7);
        iv=(ImageView)findViewById(R.id.imageView);
        et=(EditText)findViewById(R.id.editText2);
        bt1=(Button)findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=et.getText().toString().trim();
                if(TextUtils.isEmpty(s)){
                    Toast.makeText(MainActivity.this, "city name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if(s.charAt(0)>91){
                    char ch[]=s.toCharArray();
                    ch[0]=(char)((int)ch[0]-32);
                    s=new String(ch);
                }
                str=s;
                new MyTask().execute();

            }
        });
        new MyTask().execute();
    }
    public class MyTask extends AsyncTask<String ,Void,Void>{

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("Please Wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL("http://api.apixu.com/v1/current.json?key=f1afde3c53264b3cb3271733170707&q="+str);
                InputStream stream = url.openStream();
                DataInputStream din = new DataInputStream(stream);
                String s = "", res = "";
                while ((s = din.readLine()) != null) {
                    res += s;
                }
                JSONObject jsonObject = new JSONObject(res);
                JSONObject jsonObject1 = jsonObject.getJSONObject("location");
                JSONObject jsonObject2 = jsonObject.getJSONObject("current");
                c = jsonObject1.getString("name");
                r = jsonObject1.getString("region");
                cn = jsonObject1.getString("country");
                lt = jsonObject1.getString("localtime");
                lu = jsonObject2.getString("last_updated");
                tc = jsonObject2.getString("temp_c");
                JSONObject jsonObject3 = jsonObject2.getJSONObject("condition");
                text = jsonObject3.getString("text");


            }catch(Exception e)
            {
               // Toast.makeText(MainActivity.this, "Cannot find city..", Toast.LENGTH_SHORT).show();
                //Log.d("Error is -->",e+"---------------------------------------");
            }

            return null;
        }
        String text,tc,lu,lt,cn,r,c;
        Drawable d;

        @Override
        protected void onPostExecute(Void vVoid) {
            pd.dismiss();
           try{
               tv1.setText(lu);
               tv2.setText(c);
               tv3.setText(r);
               tv4.setText(cn);
               tv5.setText(tc);
               tv6.setText(text);
               iv.setImageDrawable(d);
           }catch(Exception e){

           }
        }
    }
}
