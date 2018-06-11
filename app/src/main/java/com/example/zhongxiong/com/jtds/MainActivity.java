package com.example.zhongxiong.com.jtds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends Activity {

    private String UserName = "zhongxiong";
    private String Password = "123456";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectTask dt = new ConnectTask();
        dt.execute();

    }

    class ConnectTask extends AsyncTask<Integer, Integer, String> {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        @Override
        protected void onPreExecute() {
            //第一个执行方法
            Toast.makeText(getApplicationContext(), "执行成功！！", Toast.LENGTH_SHORT).show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            //建立于数据库的连接


            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:jtds:sqlserver://10.110.32.207:1433/test", UserName, Password);
                Log.d("cghjgjhon", "" + con);
                testConnection(con);//测试数据库连接

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return "执行完毕";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        public void testConnection(Connection con) throws java.sql.SQLException {


            try {
                String sql = "SELECT * FROM AddressList ";
                preparedStatement = con.prepareStatement(sql);

                rs = preparedStatement.executeQuery();

                //con.commit();
               /* Statement stmt = con.createStatement();//创建Statement  "BookNo"
                final ResultSet rs = stmt.executeQuery(sql);//ResultSet类似Cursor*/

                while (rs.next()) {
                    Log.e("&&&&&", rs.getString(1));
                    Log.e("&&&&&", rs.getString(2));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(getApplicationContext(), rs.getString(1), Toast.LENGTH_SHORT).show();
                            } catch (SQLException e) {
                                Toast.makeText(getApplicationContext(), "错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //Toast.makeText(getApplicationContext(),rs.getString("Bookname"),Toast.LENGTH_SHORT).show();
                }
                rs.close();
                preparedStatement.close();
                //   stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage().toString());
            } finally {
                if (con != null)
                    try {
                        con.close();
                    } catch (SQLException e) {
                    }
            }
        }
    }

}