package com.bizcall.wayto.mentebit13;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GraphReport extends AppCompatActivity {


   /* private Button mbtn1, mbtn2, mbtn3, mbtn4, mbtn5, mbtn6, mbtn7;
    ImageView imgBack;*/
   Thread thread;

    RadioGroup mRadioGroup;
    RadioButton rd_all, rd_date_range;
    LinearLayout linearLayoutData, linear_recycler_data;

    //......calender..........
    private Calendar datecalendar;
    DatePickerDialog.OnDateSetListener listener;
    TextView mtv_date_from, mtv_date_to, mButtonLoad;
    EditText edtDatefrom, edtDateto;
    String currentDate, finalDate,callDate;
    int totalCall;
    String stringdateTo, stringdateFrom;
    long timeout;
   AlertDialog alertDialog;

    private int day, month, year;
    Date datefrom,dateto;
    LinearLayout linearLayoutAll;

    // ........URL String.........
    String urlReportNo, table_data, urlReportDatewise,clientUrl,clientId;


    //.......Graph........
    BarChart mBarChart, chart;
    MarkerView mv;


    //.............Data fetch..........
    RequestQueue requestQueue;
    RecyclerView mRecyclerView_Table_data;
    AdapterGraph adapterGraph;
    ImageView imgBack,imgRefresh;
    Vibrator vibrator;

    UrlRequest urlRequest;

    //.................ArrayList...........
    ArrayList<DataGraph> mSampleDataArrayList_table;
    ArrayList<BarEntry> mBarEntryArrayList;
    ArrayList<String> mStringArrayList;
    ArrayList<BarEntry> arrayListFirstGraphX;
    ArrayList<String> arrayListFirstGraphY;

    ProgressDialog dialog;
    TextView txtExport,txtActivityName,txtGraphName;
    EditText edtExcelName;
    File directory;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

   // private ArrayList<DataExcelExport> mArrayList;
    TextView txtNotFound;
    String existname,activtyName;
    TextView txtDate,txtTotal,txtCallType;
    String id1;
    boolean success;
    LinearLayout linearSpinner,linearUnderSpinner;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_report);
        try{
            initialize();
            txtActivityName.setText(activtyName);
            txtGraphName.setText("Graph"+" "+activtyName);
            txtDate.setText(activtyName+" "+"Date");

            if(activtyName.equals("CallLog Report"))
            {
                txtTotal.setText("Total Call Time in Minutes");
            }
            else {
                txtTotal.setText("Total" + " " + activtyName);
            }

            final int id = mRadioGroup.getCheckedRadioButtonId();//to check which option is selected
            rd_all = findViewById(id);
            String rVal = rd_all.getText().toString();
            Log.d("Selected", rVal);
            if (rVal.contains("All")) {
                if(CheckInternetSpeed.checkInternet(GraphReport.this).contains("0")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("No Internet connection!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                    dialog.dismiss();

                                }
                            }).show();
                }
                else if(CheckInternetSpeed.checkInternet(GraphReport.this).contains("1")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("Slow Internet speed!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                else {
                    dialog = ProgressDialog.show(GraphReport.this, "", "Loading report", true);
                        newThreadInitilization(dialog);
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        public void onCancel(DialogInterface arg0) {
                            // TODO Auto-generated method stub
                            System.out.println("...cancel button is pressed");
                                dialog.dismiss();
                        }
                    });
                    //to check which activity is this and load all report according to selected activity
                    loadAllReport();
                }
                //refreshWhenLoading();
            } else {
                //if Date range option isselected set its layout visibility on
                if (linearLayoutData.getVisibility() == View.GONE) {
                    linearLayoutData.setVisibility(View.VISIBLE);
                    linear_recycler_data.setVisibility(View.GONE);
                }

            }
                imgRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(GraphReport.this,GraphReport.class);
                        intent.putExtra("ActivityName",activtyName);
                        startActivity(intent);
                        finish();
                    }
                });
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibrator.vibrate(100);
                    onBackPressed();
                }
            });

            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    rd_all = findViewById(checkedId);
                    onRadioButtonClicked();

                }
            });  //  mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

            txtExport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onExportClicked();
                    }
            });

            mtv_date_from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTxtDateFromClicked();
                    }
            });  //  mtv_date_from.setOnClickListener(new View.OnClickListener()

            mButtonLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                onBtnLoadClicked();

                }

            });   //  mButtonLoad.setOnClickListener(new View.OnClickListener()

            mtv_date_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTxtDateToClicked();
                }
            });    //mtv_date_to.setOnClickListener(new View.OnClickListener()
            }catch (Exception e)
            {
                Toast.makeText(GraphReport.this,"Errorcode-363 GraphReport onCreate "+e.toString(),Toast.LENGTH_SHORT).show();
                Log.d("ReportException", String.valueOf(e));
            }
    }//onCreate

    private void initialize() {
        sp=getSharedPreferences("Settings",Context.MODE_PRIVATE);
        editor=sp.edit();
        clientUrl=sp.getString("ClientUrl",null);
        clientId=sp.getString("ClientId",null);
        timeout=sp.getLong("TimeOut",0);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mRecyclerView_Table_data = findViewById(R.id.recycler_table);
        txtActivityName=findViewById(R.id.txtActivityName);
        txtGraphName=findViewById(R.id.txtGraphName);
        txtNotFound=findViewById(R.id.txtNotFound);
        linearLayoutAll=findViewById(R.id.linearData);
        txtDate=findViewById(R.id.txtDate);
        txtTotal=findViewById(R.id.txtTotal);
        imgRefresh=findViewById(R.id.imgRefresh);
        txtCallType=findViewById(R.id.txtCallType);
        id1 = sp.getString("Id", null).trim();
        linearSpinner=findViewById(R.id.linearSpinner);
        linearUnderSpinner=findViewById(R.id.linearUnderCounselor);
        linearUnderSpinner.setVisibility(View.VISIBLE);
        linearSpinner.setVisibility(View.GONE);

        //  mArrayList=new ArrayList<>();
        mRadioGroup = findViewById(R.id.radio);
        rd_all = findViewById(R.id.rd_btnn_all);
        rd_date_range = findViewById(R.id.rd_btnn_date_range);

        linearLayoutData = findViewById(R.id.linear_Load_data);
        linear_recycler_data = findViewById(R.id.linear_recycler);
        imgBack = findViewById(R.id.img_back);
        edtDatefrom = findViewById(R.id.edtDatefrom);
        edtDateto = findViewById(R.id.edtDateto);
        mButtonLoad = findViewById(R.id.btn_load);
        mtv_date_from = findViewById(R.id.tv_date_from);
        mtv_date_to = findViewById(R.id.tv_date_to);

        txtExport=findViewById(R.id.txtExport);
        edtExcelName=findViewById(R.id.edtExcelName);
        datecalendar = Calendar.getInstance();
        day = datecalendar.get(Calendar.DAY_OF_MONTH);
        year = datecalendar.get(Calendar.YEAR);
        month = datecalendar.get(Calendar.MONTH);
        datecalendar.add(Calendar.DAY_OF_MONTH, 0);

        datefrom=new Date();

        mBarEntryArrayList = new ArrayList<BarEntry>();
        mStringArrayList = new ArrayList<String>();
        mBarChart = (BarChart) findViewById(R.id.barchart);
        mBarChart.setTouchEnabled(true);
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(true);
        mBarChart.setMarkerView(mv);

        activtyName=getIntent().getStringExtra("ActivityName");
        editor.putString("ReportActivity",activtyName);
        editor.commit();
    }

    public void newThreadInitilization(final ProgressDialog dialog1)
    {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(timeout);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(dialog1.isShowing()) {
                                dialog1.dismiss();
                                Toast.makeText(GraphReport.this, "Connection aborted.", Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(Home.this, "Something is wrong, Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Log.d("TimeThread","cdvmklmv");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        System.out.println("...any key is pressed....");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("...BackButton is pressed...");
            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

        public void onTxtDateToClicked(){
        try {
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    finalDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                    try {
                        dateto = simpleDateFormat.parse(finalDate);
                        stringdateTo = simpleDateFormat.format(dateto);
                        edtDateto.setText(finalDate);
                        if (finalDate.length() == 0) {
                            edtDateto.setError("Select date");
                        } else {
                            edtDateto.setError(null);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };
            DatePickerDialog dpDialog = new DatePickerDialog(GraphReport.this, listener, year, month, day);
            dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 3 * 24 * 60 * 60);
            dpDialog.getDatePicker().setMinDate(datefrom.getTime());
            dpDialog.show();
        }catch (Exception e)
        {
            Toast.makeText(GraphReport.this,"Errorcode-364 GraphReport txtDateToClicked "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void onBtnLoadClicked(){
        try {
            String datefrom = edtDatefrom.getText().toString();
            String dateto = edtDateto.getText().toString();
            if (datefrom.length() == 0)
            {
                edtDatefrom.setError("Select date");


            }else if( dateto.length() == 0)
            {
                edtDateto.setError("Select date");
            }
            else
             {
                edtDatefrom.setError(null);
                edtDateto.setError(null);
                if (activtyName.contains("Call Report")) {
                    urlReportDatewise = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=302&DateFrom=" + stringdateFrom + "&DateTo=" + stringdateTo + "&ReportId=1";
                } else if (activtyName.contains("Message Report")) {
                    urlReportDatewise = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=302&DateFrom=" + stringdateFrom + "&DateTo=" + stringdateTo + "&ReportId=2";
                } else if (activtyName.contains("Status Report")) {
                    urlReportDatewise = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=306";
                } else if (activtyName.contains("Remark Report")) {
                    urlReportDatewise = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=302&DateFrom=" + stringdateFrom + "&DateTo=" + stringdateTo + "&ReportId=4";
                } else if (activtyName.contains("Point Report")) {
                    urlReportDatewise = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=302&DateFrom=" + stringdateFrom + "&DateTo=" + stringdateTo + "&ReportId=5";
                } else if (activtyName.contains("CallLog Report")) {
                    urlReportDatewise = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=302&DateFrom=" + stringdateFrom + "&DateTo=" + stringdateTo + "&ReportId=6";
                }
                Log.d("UrlReportDatewise", urlReportDatewise);
                if (CheckInternetSpeed.checkInternet(GraphReport.this).contains("0")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("No Internet connection!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                    dialog.dismiss();

                                }
                            }).show();
                } else if (CheckInternetSpeed.checkInternet(GraphReport.this).contains("1")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("Slow Internet speed!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {

                    dialog = ProgressDialog.show(GraphReport.this, "", "Loading Datewise Report", true);
                    newThreadInitilization(dialog);
                    //if date range option is selelcted to check report of particlar date
                    getDateWiseReport();
                }
            }
            //  refreshWhenLoading();


            if (linear_recycler_data.getVisibility() == View.VISIBLE) {
                linear_recycler_data.setVisibility(View.GONE);
            }
        }catch (Exception e)
        {
            Toast.makeText(GraphReport.this,"Errorcode-365 GraphReport btnLoadClicked "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void onTxtDateFromClicked(){
        try {
            listener = new DatePickerDialog.OnDateSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    currentDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        datefrom = simpleDateFormat.parse(currentDate);
                        stringdateFrom = simpleDateFormat.format(datefrom);
                        edtDatefrom.setText(currentDate);
                        if (currentDate.length() == 0) {
                            edtDatefrom.setError("Select date");
                        } else {
                            edtDatefrom.setError(null);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };
            DatePickerDialog dpDialog = new DatePickerDialog(GraphReport.this, listener, year, month, day);
            dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 3 * 24 * 60 * 60);
            dpDialog.show();
        }catch (Exception e){
            Toast.makeText(GraphReport.this,"Errorcode-366 GraphReport txtDataFromClicked "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void onExportClicked()
    {
        try {
            int count = 0;
            directory = new File(Environment.getExternalStorageDirectory() + "/Bizcall/Report");
            directory.mkdirs();

          /*  File sdCardRoot = Environment.getExternalStorageDirectory();
            File yourDir = new File(sdCardRoot, "Appli");
*/
            for (File f : directory.listFiles()) {
                if (f.isFile()) {
                    existname = f.getName();
                }
                if ((edtExcelName.getText() + ".xls").equals(existname)) {
                    // edtExcelName.setError("Record Exist");

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("File name already exist:")
                            .setMessage("Do you want to replace file?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with replace operation and save data o excel file
                                    saveExcelFile(edtExcelName.getText().toString() + ".xls");
                                    Toast.makeText(GraphReport.this, "File saved.", Toast.LENGTH_SHORT).show();
                                    edtExcelName.setError(null);
                                    dialog.dismiss();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    count++;
                }
            }

            if (edtExcelName.getText().length() == 0) {
                edtExcelName.setError("Please Enter Name");
            } else if (count > 0) {
                edtExcelName.setError("Record exist, Rename the record.");
            } else {
                saveExcelFile(edtExcelName.getText().toString() + ".xls");
                Toast.makeText(GraphReport.this, "File Generated Successfully.", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GraphReport.this);
                alertDialogBuilder.setTitle("Report saved at location:")
                        .setMessage("" + directory.getPath() + "/" + edtExcelName.getText().toString() + ".xls")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                dialog.dismiss();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        /* .setNegativeButton(android.R.string.no, null)*/
                        .show();
            }
        }catch (Exception e)
        {
            Toast.makeText(GraphReport.this,"Errorcode-367 GraphReport onExportClicked "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClicked()
    {
        try {
            String rVal = rd_all.getText().toString();
            Log.d("SelectedId", rVal);

            if (rVal.contains("All")) {
                if (CheckInternetSpeed.checkInternet(GraphReport.this).contains("0")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("No Internet connection!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                    dialog.dismiss();

                                }
                            }).show();
                } else if (CheckInternetSpeed.checkInternet(GraphReport.this).contains("1")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("Slow Internet speed!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    dialog = ProgressDialog.show(GraphReport.this, "", "Loading report", true);
                    newThreadInitilization(dialog);
                    loadAllReport();
                }
            } else {
                dialog.dismiss();
                if (linearLayoutData.getVisibility() == View.GONE) {
                    linearLayoutData.setVisibility(View.VISIBLE);
                    linear_recycler_data.setVisibility(View.GONE);
                    edtDatefrom.getText().clear();
                    edtDateto.getText().clear();
                }
            }
        }catch (Exception e)
        {
            Toast.makeText(GraphReport.this,"Errorcode-368 GraphReport onRadioBtnClicked "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshWhenLoading()
    {
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                if(dialog.isShowing()) {
                    Intent intent = new Intent(GraphReport.this, GraphReport.class);
                    //intent.putExtra("Activity",strActivity);
                    startActivity(intent);// when the task active then close the dialog
                    t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                }
            }
        }, 12000); // after 12 second (or 2000 miliseconds), the task will be active.

    }

public void loadAllReport()
{
    try {
        Log.d("Visibility", String.valueOf(linear_recycler_data.getVisibility()));
        linear_recycler_data.setVisibility(View.GONE);
        if (linear_recycler_data.getVisibility() == View.GONE) {
            linearLayoutData.setVisibility(View.GONE);
            linear_recycler_data.setVisibility(View.VISIBLE);

            if (linear_recycler_data.getVisibility() == View.VISIBLE) {

                //urlReportNo = clientUrl+"?clientid=" + clientId + "&CounselorID=2&caseid=301";
                if (activtyName.contains("Call Report")) {
                    editor.putString("ReportName", "Call");
                    editor.commit();
                    urlReportNo = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=301&ReportId=1";
                }else if (activtyName.contains("CallLog Report")) {
                    editor.putString("ReportName", "CallLog");
                    editor.commit();
                    urlReportNo = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=301&ReportId=6";
                }
                else if (activtyName.contains("Message Report")) {
                    editor.putString("ReportName", "Message");
                    editor.commit();
                    urlReportNo = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=301&ReportId=2";
                } else if (activtyName.contains("Status Report")) {
                    editor.putString("ReportName", "Status");
                    editor.commit();
                    urlReportNo = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=301&ReportId=3";
                } else if (activtyName.contains("Remark Report")) {
                    editor.putString("ReportName", "Remark");
                    editor.commit();
                    urlReportNo = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=301&ReportId=4";
                } else if (activtyName.contains("Point Report")) {
                    editor.putString("ReportName", "Point");
                    editor.commit();
                    urlReportNo = clientUrl + "?clientid=" + clientId + "&CounselorID=" + id1 + "&caseid=301&ReportId=5";
                }
                Log.d("UrlReportNo", urlReportNo);
                //  dialog=ProgressDialog.show(GraphReport.this,"","Loading report",true);
                if (CheckInternetSpeed.checkInternet(GraphReport.this).contains("0")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("No Internet connection!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                    dialog.dismiss();

                                }
                            }).show();
                } else if (CheckInternetSpeed.checkInternet(GraphReport.this).contains("1")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                    alertDialogBuilder.setTitle("Slow Internet speed!!!")
                            .setMessage("Can't do further process")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //insertIMEI();
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    //if All option is selected to get all reports
                    getAllReport();
                }
            }
        }
    }catch (Exception e)
    {
        Toast.makeText(GraphReport.this,"Errorcode-369 GraphReport loadAllReport "+e.toString(),Toast.LENGTH_SHORT).show();
    }
}


    private boolean saveExcelFile(String fileName) {
        try {
            String path;
            File dir;
            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                Log.e("Failed", "Storage not available or read only");
                return false;
            }
            success = false;

            //New Workbook
            Workbook workbook = new HSSFWorkbook();

            //Cell style for header row
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            //New Sheet
            Sheet sheet;
            sheet = workbook.createSheet(edtExcelName.getText().toString());

            // Generate column headings
            Row row1, row2, row3, row4;

            row1 = sheet.createRow(0);
            row2 = sheet.createRow(2);

            Cell cellTitle, cellDate, cellTime, cellType, cellBottom;

            cellDate = row2.createCell(0);
            cellDate.setCellValue("Date");
            cellDate.setCellStyle(cellStyle);

            int z = 3;

            switch (activtyName) {
                case "CallLog Report":
                    cellTitle = row1.createCell(0);
                    cellTitle.setCellValue("Counselor id - "+id1);
                    cellTitle.setCellStyle(cellStyle);

                    cellTime = row2.createCell(1);
                    cellTime.setCellValue("Calls Minutes");
                    cellTime.setCellStyle(cellStyle);

                    cellType = row2.createCell(2);
                    cellType.setCellValue("Call Type");
                    cellType.setCellStyle(cellStyle);

                    for (int k = 0; k < mSampleDataArrayList_table.size(); k++) {
                        row2 = sheet.createRow(z);
                        for (int i = 0; i < 2; i++) {
                            cellDate = row2.createCell(i);
                            cellDate.setCellValue(mSampleDataArrayList_table.get(k).getDate());
                            cellDate.setCellStyle(cellStyle1);
                        }
                        for (int j = 1; j < 2; j++) {
                            cellTime = row2.createCell(j);
                            cellTime.setCellValue(mSampleDataArrayList_table.get(k).getTime()+"");
                            cellTime.setCellStyle(cellStyle1);
                        }
                        for (int x = 2; x < 3; x++) {
                            cellType = row2.createCell(x);
                            cellType.setCellValue(mSampleDataArrayList_table.get(k).getCalltype());
                            cellType.setCellStyle(cellStyle1);
                        }
                        sheet.setColumnWidth(k, (15 * 350));
                        z++;
                    }
                    row3 = sheet.createRow(z+1);
                    cellBottom = row3.createCell(0);
                    cellBottom.setCellValue("Report created by : www.bizcallcrm.com");
                    cellBottom.setCellStyle(cellStyle1);

                    row4 = sheet.createRow(z+2);
                    cellBottom = row4.createCell(0);
                    cellBottom.setCellValue("Contact No. 8149900003");
                    cellBottom.setCellStyle(cellStyle1);
                    break;
                case "Call Report":
                    cellTitle = row1.createCell(0);
                    cellTitle.setCellValue("Counselor ID - "+id1);
                    cellTitle.setCellStyle(cellStyle);

                    cellTime = row2.createCell(1);
                    cellTime.setCellValue("Calls Report");
                    cellTime.setCellStyle(cellStyle);

                    for (int k = 0; k < mSampleDataArrayList_table.size(); k++) {
                        row2 = sheet.createRow(z);
                        for (int i = 0; i < 2; i++) {
                            cellDate = row2.createCell(i);
                            cellDate.setCellValue(mSampleDataArrayList_table.get(k).getDate());
                            cellDate.setCellStyle(cellStyle1);
                        }
                        for (int j = 1; j < 2; j++) {
                            cellTime = row2.createCell(j);
                            cellTime.setCellValue(mSampleDataArrayList_table.get(k).getTime()+"");
                            cellTime.setCellStyle(cellStyle1);
                        }
                        sheet.setColumnWidth(k, (15 * 350));
                        z++;
                    }
                    row3 = sheet.createRow(z+1);
                    cellBottom = row3.createCell(0);
                    cellBottom.setCellValue("Report created by : www.bizcallcrm.com");
                    cellBottom.setCellStyle(cellStyle1);

                    row4 = sheet.createRow(z+2);
                    cellBottom = row4.createCell(0);
                    cellBottom.setCellValue("Contact No. 8149900003");
                    cellBottom.setCellStyle(cellStyle1);
                    break;
                case "Message Report":
                    cellTitle = row1.createCell(0);
                    cellTitle.setCellValue("Counselor ID - "+id1);
                    cellTitle.setCellStyle(cellStyle);

                    cellTime = row2.createCell(1);
                    cellTime.setCellValue("Message Report");
                    cellTime.setCellStyle(cellStyle);

                    for (int k = 0; k < mSampleDataArrayList_table.size(); k++) {
                        row2 = sheet.createRow(z);
                        for (int i = 0; i < 2; i++) {
                            cellDate = row2.createCell(i);
                            cellDate.setCellValue(mSampleDataArrayList_table.get(k).getDate());
                            cellDate.setCellStyle(cellStyle1);
                        }
                        for (int j = 1; j < 2; j++) {
                            cellTime = row2.createCell(j);
                            cellTime.setCellValue(mSampleDataArrayList_table.get(k).getTime()+"");
                            cellTime.setCellStyle(cellStyle1);
                        }
                        sheet.setColumnWidth(k, (15 * 350));
                        z++;
                    }
                    row3 = sheet.createRow(z+1);
                    cellBottom = row3.createCell(0);
                    cellBottom.setCellValue("Report created by : www.bizcallcrm.com");
                    cellBottom.setCellStyle(cellStyle1);

                    row4 = sheet.createRow(z+2);
                    cellBottom = row4.createCell(0);
                    cellBottom.setCellValue("Contact No. 8149900003");
                    cellBottom.setCellStyle(cellStyle1);
                    break;
                case "Remark Report":
                    cellTitle = row1.createCell(0);
                    cellTitle.setCellValue("Counselor ID - "+id1);
                    cellTitle.setCellStyle(cellStyle);

                    cellTime = row2.createCell(1);
                    cellTime.setCellValue("Remark Report");
                    cellTime.setCellStyle(cellStyle);

                    for (int k = 0; k < mSampleDataArrayList_table.size(); k++) {
                        row2 = sheet.createRow(z);
                        for (int i = 0; i < 2; i++) {
                            cellDate = row2.createCell(i);
                            cellDate.setCellValue(mSampleDataArrayList_table.get(k).getDate());
                            cellDate.setCellStyle(cellStyle1);
                        }
                        for (int j = 1; j < 2; j++) {
                            cellTime = row2.createCell(j);
                            cellTime.setCellValue(mSampleDataArrayList_table.get(k).getTime()+"");
                            cellTime.setCellStyle(cellStyle1);
                        }
                        sheet.setColumnWidth(k, (15 * 350));
                        z++;
                    }
                    row3 = sheet.createRow(z+1);
                    cellBottom = row3.createCell(0);
                    cellBottom.setCellValue("Report created by : www.bizcallcrm.com");
                    cellBottom.setCellStyle(cellStyle1);

                    row4 = sheet.createRow(z+2);
                    cellBottom = row4.createCell(0);
                    cellBottom.setCellValue("Contact No. 8149900003");
                    cellBottom.setCellStyle(cellStyle1);
                    break;
                case "Point Report":
                    cellTitle = row1.createCell(0);
                    cellTitle.setCellValue("Counselor ID - "+id1);
                    cellTitle.setCellStyle(cellStyle);

                    cellTime = row2.createCell(1);
                    cellTime.setCellValue("Point Report");
                    cellTime.setCellStyle(cellStyle);

                    for (int k = 0; k < mSampleDataArrayList_table.size(); k++) {
                        row2 = sheet.createRow(z);
                        for (int i = 0; i < 2; i++) {
                            cellDate = row2.createCell(i);
                            cellDate.setCellValue(mSampleDataArrayList_table.get(k).getDate());
                            cellDate.setCellStyle(cellStyle1);
                        }
                        for (int j = 1; j < 2; j++) {
                            cellTime = row2.createCell(j);
                            cellTime.setCellValue(mSampleDataArrayList_table.get(k).getTime()+"");
                            cellTime.setCellStyle(cellStyle1);
                        }
                        sheet.setColumnWidth(k, (15 * 350));
                        z++;
                    }
                    row3 = sheet.createRow(z+1);
                    cellBottom = row3.createCell(0);
                    cellBottom.setCellValue("Report created by : www.bizcallcrm.com");
                    cellBottom.setCellStyle(cellStyle1);

                    row4 = sheet.createRow(z+2);
                    cellBottom = row4.createCell(0);
                    cellBottom.setCellValue("Contact No. 8149900003");
                    cellBottom.setCellStyle(cellStyle1);
                    break;
            }

            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bizcall/Report";
            dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                Log.w("FileUtils", "Writing file" + file);
                success = true;
            } catch (IOException e) {
                Log.w("FileUtils", "Error writing " + file, e);
            } catch (Exception e) {
                Log.w("FileUtils", "Failed to save file", e);
            } finally {
                try {
                    if (null != fileOutputStream)
                        fileOutputStream.close();
                } catch (Exception ex) {
                }
            }

        } catch (Exception e) {
            Toast.makeText(GraphReport.this, "Errorcode-370 GraphReport saveExcelFile " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return success;
    }//saveExcelFile


    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }//isExternalStorageReadOnly

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }//isExternalStorageAvailable

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


    public void getAllReport() {
                try {
                    Log.d("UrlReportNo", urlReportNo);
                    if(CheckServer.isServerReachable(GraphReport.this)) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlReportNo,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            dialog.dismiss();
                                            String res = String.valueOf(response);
                                            if (res.contains("[]")) {
                                                txtNotFound.setVisibility(View.VISIBLE);
                                                linearLayoutAll.setVisibility(View.GONE);
                                            } else {
                                                txtNotFound.setVisibility(View.GONE);
                                                linearLayoutAll.setVisibility(View.VISIBLE);

                                            }
                                            mSampleDataArrayList_table = new ArrayList<>();
                                            mSampleDataArrayList_table.clear();
                                            mBarEntryArrayList.clear();
                                            mStringArrayList.clear();
                                            //arrayListFirstGraphX.clear();
                                            //arrayListFirstGraphY.clear();
                                            JSONObject jsonObject = new JSONObject(response);
                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                                            Log.d("table data success", String.valueOf(response));
                                            for (int i = 0; i < jsonArray.length(); i++) {

                                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                String CallDate = jsonObject1.getString("ReportDate");

                                                int TotalCall = jsonObject1.getInt("TotalReportNo");
                                                String callType="";
                                                if(activtyName.equals("CallLog Report"))
                                                {
                                                    callType=jsonObject1.getString("cCallType");
                                                    txtCallType.setVisibility(View.VISIBLE);
                                                }

                                                Log.d("data fetch table", CallDate + " " + TotalCall + " ");

                                                DataGraph sampleData = new DataGraph(CallDate, TotalCall,callType);
                                                mSampleDataArrayList_table.add(sampleData);
                                                mBarEntryArrayList.add(new BarEntry(TotalCall, i));
                                                mStringArrayList.add(CallDate);

                                            }
                                            BarDataSet set1 = new BarDataSet(mBarEntryArrayList, " Data Value");
                                            set1.setColors(ColorTemplate.COLORFUL_COLORS);


                                            BarData data = new BarData(mStringArrayList, set1);
                                            mBarChart.setData(data);

                                            adapterGraph = new AdapterGraph(mSampleDataArrayList_table, getApplicationContext());
                                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                                            mRecyclerView_Table_data.setLayoutManager(manager);
                                            mRecyclerView_Table_data.setAdapter(adapterGraph);
                                            adapterGraph.notifyDataSetChanged();
                                        } catch (Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(GraphReport.this, "Errorcode-372 GraphReport getAllReportResponse " + e.toString(), Toast.LENGTH_SHORT).show();
                                            Log.d("Exception", String.valueOf(e));
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        if (error == null || error.networkResponse == null)
                                            return;
                                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                                        //get response body and parse with appropriate encoding
                                        if (error.networkResponse != null || error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof AuthFailureError || error instanceof ServerError || error instanceof NetworkError || error instanceof ParseError) {
                                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                                            alertDialogBuilder.setTitle("Network issue!!!")


                                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            dialog.dismiss();
                                                        }
                                                    }).show();
                                            dialog.dismiss();
                                            Toast.makeText(GraphReport.this, "Network issue", Toast.LENGTH_SHORT).show();
                                            // showCustomPopupMenu();
                                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                        }

                                    }
                                });
                        requestQueue.add(stringRequest);
                    }else {
                        if (dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                        alertDialogBuilder.setTitle("Network issue!!!!")
                                .setMessage("Try after some time!")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                        dialog.dismiss();

                                    }
                                }).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(GraphReport.this,"Errorcode-371 GraphReport getAllReport "+e.toString(),Toast.LENGTH_SHORT).show();
                }
    }//getAllReport

    public void getDateWiseReport() {
        try {
            if(CheckServer.isServerReachable(GraphReport.this)) {
                Log.d("UrlReportNo", urlReportDatewise);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlReportDatewise,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    dialog.dismiss();
                                    String res = String.valueOf(response);
                                    if (res.contains("[]")) {
                                        txtNotFound.setVisibility(View.VISIBLE);
                                        linearLayoutData.setVisibility(View.VISIBLE);
                                        linear_recycler_data.setVisibility(View.GONE);
                                    } else {
                                        txtNotFound.setVisibility(View.GONE);
                                        linearLayoutAll.setVisibility(View.VISIBLE);


                                        mSampleDataArrayList_table = new ArrayList<>();
                                        mSampleDataArrayList_table.clear();
                                        mBarEntryArrayList.clear();
                                        mStringArrayList.clear();
                                        //arrayListFirstGraphX.clear();
                                        //arrayListFirstGraphY.clear();
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        Log.d("table data success", String.valueOf(response));
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                            String calltype = "";
                                            if (activtyName.contains("Status Report")) {
                                                callDate = jsonObject1.getString("Status");

                                                totalCall = jsonObject1.getInt("Total No");
                                            } else {
                                                callDate = jsonObject1.getString("ReportDate");

                                                totalCall = jsonObject1.getInt("TotalReportNo");
                                                if (activtyName.equals("CallLog Report")) {
                                                    calltype = jsonObject1.getString("cCallType");
                                                    txtCallType.setVisibility(View.VISIBLE);
                                                }
                                            }

                                            Log.d("data fetch table", callDate + " " + totalCall + " ");
                                            mBarEntryArrayList.add(new BarEntry(totalCall, i));
                                            mStringArrayList.add(callDate);
                                            DataGraph sampleData = new DataGraph(callDate, totalCall, calltype);
                                            mSampleDataArrayList_table.add(sampleData);
                                            BarDataSet set1 = new BarDataSet(mBarEntryArrayList, " Data Value");
                                            set1.setColors(ColorTemplate.COLORFUL_COLORS);


                                            BarData data = new BarData(mStringArrayList, set1);
                                            mBarChart.setData(data);
                                            mBarChart.invalidate();
                                        }
                                        linear_recycler_data.setVisibility(View.VISIBLE);
                                        adapterGraph = new AdapterGraph(mSampleDataArrayList_table, getApplicationContext());
                                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                                        mRecyclerView_Table_data.setLayoutManager(manager);
                                        mRecyclerView_Table_data.setAdapter(adapterGraph);
                                        adapterGraph.notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(GraphReport.this, "Errorcode-374 GraphReport DatewiseReportResponse" + e.toString(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    Log.d("Exception", String.valueOf(e));
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error == null || error.networkResponse == null)
                                    return;
                                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                                //get response body and parse with appropriate encoding
                                if (error.networkResponse != null || error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof AuthFailureError || error instanceof ServerError || error instanceof NetworkError || error instanceof ParseError) {
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")


                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(GraphReport.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }

                            }
                        });
                requestQueue.add(stringRequest);
            }else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GraphReport.this);
                alertDialogBuilder.setTitle("Network issue!!!!")
                        .setMessage("Try after some time!")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                dialog.dismiss();

                            }
                        }).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(GraphReport.this,"Errorcode-373 GraphReport getDatewiseReport"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }//getDateWiseReport

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GraphReport.this, Home.class);
        intent.putExtra("Activity", "GraphReport");
        startActivity(intent);
        finish();
    }
}
