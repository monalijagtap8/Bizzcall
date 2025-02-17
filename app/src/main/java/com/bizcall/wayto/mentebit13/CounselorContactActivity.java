package com.bizcall.wayto.mentebit13;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CounselorContactActivity extends FragmentActivity {

    static final int TIME_DIALOG_ID = 1111;
    int pos=0;
    long timeout;
    int clickedcount=0;//Getdetail click count only 1 click allowed
    String fetchedDataFrom="";//Response cdatafrom DataRefmaster
    String fetchedAllocatedTo="";// response counselor allocated to
    String fetchedCreatedDate="";//respont dtcreated date
    long dsec;//Recording second duration
    Date dt1;//Reminder date
    Thread thread;
    String clienturl="",cid1="";//Final Query URL
    String totalcount="";//check total phone no count
    String phone1="";
    String phone2="";
    String clickedButton="";
    ArrayList<String> arrayListStatusId;

    int eventid;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String status11="",country="",statusid,remark,enteredRemark,remarkOnCall;
    String msg, text1, smbl, mbl, sms, clientid, name, course,  email, activityName, remarks, path1,id1, parentno,counselorname;
    String adrs, city, state1, pincode,updatedStatus,dateReminder,checkNumber;
    Intent intent;
    int status1;
    int reminderFlag=0;

    UrlRequest urlRequest;
    public static String sr_no;
    String uploadFilePath = "";
    String uploadFileName = "",url;
    String upLoadServerUri = null;
    String strNotificatio;
    int serverResponseCode = 0;
    RequestQueue requestQueue;
    String id,formno,firstname,lastname,fathername,mothername,dob,passport,mobile,addrs,paddresss,permanentcity,permanentstate,permanentpin,parentmobile,college,schoolboard,tenthpercentage,tenthyear,tenthmarks,tenthoutof,twelthpercentage,twelthyear,twelthmarks,twelthoutof,neetyear,neetmarks,physics,chemistry,biology,pcb,aggregate,dtcreateddate,cWebsite;
    ProgressDialog dialog,dialog2,dialog3;
    AlertDialog alertDialog,alertDialog1;

    TextView txtName, txtTime,txtReallocationTitle,txtReportTitle,txtClientEntry,txtOnlineFormTitle;
    TextView txtCalledNumber,txtDateTime,txtDuration,txtNoCallDetails;
    TextView txtDateView,txtReminderTitle,txtDiamond;
    public static TextView txtSelectedCname, txtSelecteCId, txtSelctedSrNo;//for displaying info
    TextView txtSrno, txtCName, txtCourse, txtMobile,txtCurrentStatus,txtRemark,txtCoin,btnCancel;
    TextView edtRemarks, btnReallocate;

    ImageView imgBack, imgClock,imgCoin;
    ImageView imgCalender;
    ImageView imgAddContact;
    ImageView imgRefresh,imgDone;

    LinearLayout linearReport,linearUrl;
    public static LinearLayout linearReallocateTo, linearSelectDate, linearSelectTime;
    LinearLayout linearOnlineForm,linearReminder,linearCallDetails,linearConfirmCall,linearCallDuration,linearCall;
    LinearLayout linearViewDetails,linearContactDetails;
    LinearLayout linearReallocation;

    Button btnUpdateStatus,btnupdateRemarks,btnReminder;
    Button btnCall, btnMsg, btnMail, btnDetails;
    Button btnOk,btnViewDetails,btnUpdateCountryCourse;
    public static Button btnGetDetails;

    Spinner spinnerStatus, spinnerMobile,spinnerCourse,spinnerCountry;

    ArrayList<DataNotification> arrayListNotification;
    ArrayList<String> arrayList1;
    ArrayList<DataListCounselor> arrayListCounselorDetails;
    ArrayList<DataCallDetails> arrayListCallDetails;
    ArrayList<DataOnlineFormDetails> arrayListOnlineForm;
    ArrayList<String> arrayListCountry,arrayListCourse;


    DataListCounselor dataListCounselor;
    RecyclerView recyclerCounselorDetails;

    int year, day, month;
    String time11, date11, selectedmbl, allocatedDate, newAllocatedTo,totalcoins,act1,strSelectedDate,strSelectedTime,sr_onlinelead;
    private int hour;
    private int minute;
    Date dt22,dt33;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Vibrator vibrator;
    Calendar myCalendar,calendar;


    DataCallDetails dataCallDetails;
    String newDTime,refname,datafrom;
    View confirmCall;
    WifiManager wifiManager;
    NetworkInfo info;

    RecyclerView recyclerOnlineFormDetails;
    int statusupdate=0,remarkupdate=0,temp=0;

    AdapterOnlineFormDetails adapterOnlineFormDetails;

    EditText edtCountry,edtCourse;


    //used to select time from timepicker to set reminder
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute);
        }
    };
    //used to select date from datepicker to set reminder
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                     myCalendar = Calendar.getInstance();
                    //arg0.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    myCalendar.set(Calendar.YEAR, arg1);
                    myCalendar.set(Calendar.MONTH, arg2);
                    myCalendar.set(Calendar.DAY_OF_MONTH, arg3);
                    String myFormat = "yyyy/MM/dd"; //Change as you need
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                    txtDateView.setText(sdf.format(myCalendar.getTime()));
                    day = arg1;
                    month = arg2;
                    year = arg3;
                    showDate(day, month + 1, year);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor_contact);
        try{
            initialize();
            spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if(spinnerCountry.getSelectedItem().toString().equals("Others"))
                    {
                        edtCountry.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(spinnerCourse.getSelectedItem().toString().equals("Others"))
                    {
                        edtCourse.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            upLoadServerUri =clienturl+"?clientid=" + clientid + "&caseid=4";
          //  imgNotification=findViewById(R.id.imgNotification);

            ViewDetails();
            imgDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 imgDoneClicked();
                }
            });
            imgRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(CounselorContactActivity.this,CounselorContactActivity.class);
                    intent.putExtra("ActivityName",activityName);
                    startActivity(intent);
                }
            });
            imgAddContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //This is used to save contact to phone
                  onImgCreateContactClicked();
                }

                });

            btnUpdateCountryCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("0")) {
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                    else if(CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("1")) {
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                        temp=0;
                        String country1=spinnerCountry.getSelectedItem().toString();
                        if(country1.equals("Others"))
                        {
                            country1=edtCountry.getText().toString();
                            if(country1.length()==0)
                            {
                                temp=1;
                                edtCountry.setError("Invalid country");
                            }
                        }
                        String course1=spinnerCourse.getSelectedItem().toString();
                        if(course1.equals("Others"))
                        {
                            course1=edtCourse.getText().toString();
                            if(course1.length()==0)
                            {
                                temp=1;
                                edtCourse.setError("Invalid course");
                            }
                        }

                        if(temp==0) {
                            dialog2 = ProgressDialog.show(CounselorContactActivity.this, "", "Updating course and country", false, true);
                            newThreadInitilization(dialog2);
                            //To update country and course of client
                            updateCountryCourse(country1, course1, sr_no);
                        }
                    }
                }
            });

        imgCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                startActivity(new Intent(CounselorContactActivity.this,PointCollectionDetails.class));
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("Remarks"));
        tabLayout.addTab(tabLayout.newTab().setText("Status"));
        tabLayout.addTab(tabLayout.newTab().setText("Call"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
        txtOnlineFormTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                if(linearOnlineForm.getVisibility()==View.GONE)
                {
                    linearOnlineForm.setVisibility(View.VISIBLE);
                }
                else
                {
                    linearOnlineForm.setVisibility(View.GONE);
                }

            }
        });
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        edtRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remarkOnCall=edtRemarks.getText().toString();
                editor.putString("RemarkOnCall",remarkOnCall);
                editor.commit();
                Log.d("RemarkOnCall",remarkOnCall);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtReminderTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                vibrator.vibrate(100);
                if(linearReminder.getVisibility()==View.GONE)
                {
                    linearReminder.setVisibility(View.VISIBLE);
                }
                else
                {
                    linearReminder.setVisibility(View.GONE);
                }
            }
        });
        txtClientEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                startActivity(new Intent(CounselorContactActivity.this,NewClientEntry.class));
            }
        });

        txtReallocationTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              vibrator.vibrate(100);
                if(linearReallocation.getVisibility()==View.GONE)
                {
                    linearReallocation.setVisibility(View.VISIBLE);
                    linearReallocateTo.setVisibility(View.GONE);
                    txtSelecteCId.setText("");
                    txtSelectedCname.setText("");
                    txtSelctedSrNo.setText("");
                }
                else
                {
                    linearReallocation.setVisibility(View.GONE);
                }
                }
        });

        txtReportTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                if(linearReport.getVisibility()==View.GONE)
                {
                    linearReport.setVisibility(View.VISIBLE);
                }
                else
                {
                    linearReport.setVisibility(View.GONE);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               vibrator.vibrate(100);
                onBackPressed();
            }
        });
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBtnReminderClicked();
               }
        });
        btnReallocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                newAllocatedTo = sp.getString("SCID", null);
                if(newAllocatedTo.length()!=0) {
                    dialog=ProgressDialog.show(CounselorContactActivity.this,"","Reallocating",true);
                   newThreadInitilization(dialog);
                   //code to reallocate client to new counselor
                    setReallocation();
                }
                else
                {
                    Toast.makeText(CounselorContactActivity.this,"Try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                linearReallocateTo.setVisibility(View.GONE);
            }
        });
        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getStatus1();
                vibrator.vibrate(100);
                if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("0")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                } else if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("1")) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                    dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Updating status...", true);
                    newThreadInitilization(dialog);
                    //code to insert new status value
                    statusBackup();
                }
            }
        });
        btnupdateRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                enteredRemark=edtRemarks.getText().toString();
                enteredRemark=enteredRemark.replaceAll("'","");
                if(enteredRemark.length()==0)
                {
                    edtRemarks.setError("Please enter remark");
                }
                else
                {
                   // dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Updating remark...", true);
                    if(CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("0")) {
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                    else if(CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("1")) {
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                        //code to insert remark valueto database
                        remarkBackup(sr_no, id1, enteredRemark);
                    }
                }
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBtnCallClicked();
                }});

        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                editor.putString("CName", name);
                editor.putString("SelectedMobile", selectedmbl);
                editor.putString("ParentNo",parentno);
                editor.commit();
               Intent intent1= new Intent(CounselorContactActivity.this,MessageActivity.class);
               intent1.putExtra("Activity","CounselorContact");
                startActivity(intent1);
                 }
        });
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
               // eventid=4;
               // insertPointCollection(eventid);
                Intent intent=new Intent(CounselorContactActivity.this,MailActiivity.class);
                intent.putExtra("Email",email);
                startActivity(intent);
               /* Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                try {
                    // context.startActivity(i);
                    // context.startActivity(i);
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(CounselorContactActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                startActivity(new Intent(CounselorContactActivity.this,DetailsActivity.class));
                           }
        });

        linearSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
        linearSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        }catch (Exception e)
        {
          // dialog.dismiss();
            Toast.makeText(CounselorContactActivity.this,"Errorcode-163 CounselorCountact onCreate "+e.toString(),Toast.LENGTH_SHORT).show();

            Log.d("Exception", String.valueOf(e));
        }
    }//onCreate

    public void newThreadInitilization(final ProgressDialog dialog1)
    {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(timeout);
                   // dialog1.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(dialog1.isShowing()) {
                                dialog1.dismiss();
                                Toast.makeText(CounselorContactActivity.this, "Connection Aborted", Toast.LENGTH_SHORT).show();
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
    public void initialize()
    {
        info=getInfo(getApplicationContext());
        btnViewDetails=findViewById(R.id.btnViewdetails);
        linearViewDetails=findViewById(R.id.linearViewDetails);
        linearContactDetails=findViewById(R.id.linearContactDetails);
        txtClientEntry=findViewById(R.id.txtNewClientEntry);
        txtCoin=findViewById(R.id.txtCoin);
        imgCoin=findViewById(R.id.imgCoin);
        txtDiamond=findViewById(R.id.txtDiamond);
        txtSelctedSrNo = findViewById(R.id.txtSelectedSrno);
        txtSelecteCId = findViewById(R.id.txtSelectedCounselorId);
        txtSelectedCname = findViewById(R.id.txtSelectedCName);
        linearReallocateTo = findViewById(R.id.linearReallocateTo);
        linearSelectDate = findViewById(R.id.linearSelectDate);
        linearSelectTime = findViewById(R.id.linearSelectTime);
        linearReport=findViewById(R.id.linearReport);
        txtReportTitle=findViewById(R.id.txtReportTitle);
        txtCName = findViewById(R.id.txtCandidateName1);
        txtCourse = findViewById(R.id.txtCourse1);
        txtSrno = findViewById(R.id.txtSrNo1);
        btnCall = findViewById(R.id.btnCall);
        btnMsg = findViewById(R.id.btnMessage);
        btnMail = findViewById(R.id.btnMail);
        btnDetails = findViewById(R.id.btnDetails);
        spinnerStatus = findViewById(R.id.spinnerStatus11);
        imgBack = findViewById(R.id.img_back);
        imgCalender = findViewById(R.id.imgCalender);
        txtTime = findViewById(R.id.txtSelectTime);
        imgClock = findViewById(R.id.imgClock);
        txtDateView = findViewById(R.id.txtSelectedDate);
        btnReminder = findViewById(R.id.btnReminder);
        spinnerMobile = findViewById(R.id.spinnerMobile);
        btnReallocate = findViewById(R.id.btnReallocate);
        btnCancel = findViewById(R.id.btnCancel1);
        txtReallocationTitle=findViewById(R.id.txtReallocationTitle);
        linearReallocation=findViewById(R.id.linearReallocation);
        txtCurrentStatus=findViewById(R.id.txtCurrentStatus);
        txtRemark=findViewById(R.id.txtRemarks);
        //txtSrno=findViewById(R.id.txtSerialNo1);
        // txtName=findViewById(R.id.txtCName);
        // txtMobile=findViewById(R.id.txtMobNumber);
        //txtPath=findViewById(R.id.txtPath1);
        edtRemarks = findViewById(R.id.edtRemarks);
        txtReminderTitle=findViewById(R.id.txtReminderTitle);
        linearReminder=findViewById(R.id.linearReminder);
        btnUpdateStatus = findViewById(R.id.btnUpdateStatus);
        btnupdateRemarks=findViewById(R.id.btnUpdateRemark);
        imgAddContact=findViewById(R.id.imgAddContact);
        imgRefresh=findViewById(R.id.imgRefresh);
        imgDone=findViewById(R.id.imgDone);
        arrayList1 = new ArrayList<>();
        recyclerCounselorDetails = findViewById(R.id.recyclerCounselorDetails);
        linearOnlineForm=findViewById(R.id.linearOnlineForm);
        txtOnlineFormTitle=findViewById(R.id.txtOnlineFormTitle);
        spinnerCourse=findViewById(R.id.spinnerCourse);
        spinnerCountry=findViewById(R.id.spinnerCountry);
        edtCountry=findViewById(R.id.edtCountry);
        edtCourse=findViewById(R.id.edtCourse);
        btnUpdateCountryCourse=findViewById(R.id.btnUpdateCountryCourse);
        recyclerOnlineFormDetails=findViewById(R.id.recyclerOnlineFormDetails);
        arrayListCounselorDetails = new ArrayList<>();
        dt1=new Date();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

            /*scheduleClient = new ScheduleClient(CounselorContactActivity.this);
            scheduleClient.doBindService();*/

        txtCoin.setText(totalcoins);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        requestQueue=Volley.newRequestQueue(CounselorContactActivity.this);
        sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        id1 = sp.getString("Id", null);
        id1 = id1.replaceAll(" ", "");
        clientid = sp.getString("ClientId", null);
        clienturl=sp.getString("ClientUrl",null);
        counselorname=sp.getString("Name",null);
        timeout=sp.getLong("TimeOut",0);
        //timeout=sp.getLong("TimeOut",0);
        Log.d("ClientId", clientid);
        totalcoins=sp.getString("TotalCoin",null);
        editor = sp.edit();
        arrayListStatusId=new ArrayList<>();
        arrayListNotification=new ArrayList<>();

        activityName = getIntent().getStringExtra("ActivityName");
        Log.d("ActivityName",activityName);
        sr_no = sp.getString("SelectedSrNo", null);

        act1=sp.getString("ActivityContact",null);
        Log.d("Act1", act1+activityName+sr_no);

        arrayListCountry=new ArrayList<>();
        arrayListCourse=new ArrayList<>();

        arrayListCountry.add("Select");
        arrayListCountry.add("NA");
        arrayListCountry.add("Ukraine");
        arrayListCountry.add("Russia");
        arrayListCountry.add("Armenia");
        arrayListCountry.add("USA");
        arrayListCountry.add("Poland");
        arrayListCountry.add("Canada");
        arrayListCountry.add("Belarus");
        arrayListCountry.add("China");
        arrayListCountry.add("India");
        arrayListCountry.add("Ireland");
        arrayListCountry.add("Australia");
        arrayListCountry.add("Georgia");
        arrayListCountry.add("KYRGYZSTAN");
        arrayListCountry.add("KAZAKHSTAN");
        arrayListCountry.add("Germany");
        arrayListCountry.add("Nepal");
        arrayListCountry.add("Philippines");
        arrayListCountry.add("UK");
        arrayListCountry.add("Bangladesh");
        arrayListCountry.add("Others");

        arrayListCourse.add("SelectCourse");
        arrayListCourse.add("NA");
        arrayListCourse.add("MBBS");
        arrayListCourse.add("MBA");
        arrayListCourse.add("Engineering");
        arrayListCourse.add("Bsc Nursing");
        arrayListCourse.add("Msc Nursing");
        arrayListCourse.add("BBA");
        arrayListCourse.add("BCA");
        arrayListCourse.add("MCA");
        arrayListCourse.add("Others");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CounselorContactActivity.this, R.layout.spinner_item1, arrayListCountry);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(CounselorContactActivity.this, R.layout.spinner_item1, arrayListCourse);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(arrayAdapter1);
        spinnerCountry.setSelection(0);
        spinnerCourse.setSelection(0);
    }//close initialize
    public void imgDoneClicked()
    {
        if(CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("0")) {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        else if(CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("1")) {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            dialog2=ProgressDialog.show(CounselorContactActivity.this,"","Checking SrNo exist in DocumentMaster or not",false,true);
            newThreadInitilization(dialog2);
            // If client is confirmed,check that his entry alredy there in database or not.
            checkIfExistInDocMaster(sr_no);
        }
    }//close imgDoneClicked
    public void updateCountryCourse(String country,String course,String srno)
    {
        // String callId=sp.getString("SelectedCallingId",null);
        try {
            if (CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=165&Country="+country+"&Course="+course+"&SrNo="+srno;
                Log.d("CountryCourseUrl", url);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    dialog2.dismiss();
                                    Log.d("CountryCourseResponse", response);
                                    if (response.contains("Data updated successfully")) {

                                        Toast.makeText(CounselorContactActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                                        //  edtRemarks.setText(sp.getString("RemarkOnCall",null));

                                    } else {

                                        Toast.makeText(CounselorContactActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                                    }
                                    //   Log.d("Size**", String.valueOf(arrayList.size()));

                                } catch (Exception e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-170 CounselorContact insertViewDetailsResponse " + e.toString(), Toast.LENGTH_SHORT).show();
                                    dialog2.dismiss();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog2.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }
                            }
                        });
                requestQueue.add(stringRequest);
            } else {
                dialog2.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }
        catch (Exception e)
        {
            Log.d("ExcInsertViewDetails",e.toString());
            Toast.makeText(CounselorContactActivity.this,"Errorcode-169 CounselorContact insertViewDetails "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }//close updateCountryCourse
    public void ViewDetails() {
        if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("0")) {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        } else if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("1")) {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Getting Counselor Details", true);
            newThreadInitilization(dialog);
            if (activityName.equals("OnlineLead") || activityName.contains("IVRMissed") || activityName.equals("RefreshOnlineLead") || activityName.equals("ContactOnlineLead")) {
                // if (activityName.contains("AdapterOnlineLead")) {
                Log.d("SerialNo", sr_no);

                //if activity name is onlinelead then this function is used to get details of client b passing his serial no
                url = clienturl + "?clientid=" + clientid + "&caseid=60&RefNo=" + sr_no;
                getCounselorData(url);
                // getOnlineLeadRefno(sr_no);
                // }
            } else if (activityName.contains("OpenLeads")) {
                // if (activityName.contains("AdapterOnlineLead")) {
                Log.d("SerialNo", sr_no);
                //datafrom = sp.getString("DtaFrom", null);
                refname = sp.getString("DataRefName", null);
                //if activity name is openlead then this function is used to get details of client b passing his serial no
                url = clienturl + "?clientid=" + clientid + "&caseid=76&nSrNo=" + sr_no;
                getCounselorData(url);
                // getOpenLeadRefNo(sr_no);
                // }
            } else if (activityName.contains("FormFilled")) {
                //if activity name is formfilled then this function is used to get details of client b passing his serial no
                url = clienturl + "?clientid=" + clientid + "&caseid=83&ID=" + sr_no;
                getCounselorData(url);
                //getSrDetailsOnlineForm(sr_no);
                if (txtOnlineFormTitle.getVisibility() == View.GONE) {
                    txtOnlineFormTitle.setVisibility(View.VISIBLE);
                } else {
                    txtOnlineFormTitle.setVisibility(View.GONE);
                }
                spinnerStatus.setVisibility(View.GONE);
                btnUpdateStatus.setVisibility(View.GONE);
                edtRemarks.setVisibility(View.GONE);
                btnupdateRemarks.setVisibility(View.GONE);
                btnDetails.setVisibility(View.GONE);
                txtCurrentStatus.setVisibility(View.GONE);
                txtRemark.setVisibility(View.GONE);
                txtReallocationTitle.setVisibility(View.GONE);
            } else if (activityName.equals("AdminReportDetails")) {
                cid1 = getIntent().getStringExtra("CID");
                // this function is used to get details of client by passing his serial no
                String url = clienturl + "?clientid=" + clientid + "&caseid=32&nSrNo=" + sr_no + "&cCounselorID=" + cid1;
                getCounselorData(url);
            } else {
                // datafrom = sp.getString("DtaFrom", null);
                refname = sp.getString("DataRefName", null);
                // this function is used to get details of client by passing his serial no
                String url = clienturl + "?clientid=" + clientid + "&caseid=32&nSrNo=" + sr_no + "&cCounselorID=" + id1;
                getCounselorData(url);
            }


           /* dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Inserting View Details", true);
            newThreadInitilization(dialog);
            //this function is used to insert that who opened this activity to datebase.
            insertViewDetails();*/
        }
    }
    //close viewDeails
    public void checkIfExistInDocMaster(final String srno) {
        try {

            url = clienturl + "?clientid=" + clientid + "&caseid=80&SrNo=" + srno + "&CounselorId=" + id1;
            Log.d("CheckDocMAsterUrl", url);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (dialog2.isShowing()) {
                                dialog2.dismiss();
                            }
                            Log.d("CheckSrNoResponse", response);
                            try {
                                // arrayListTotal.clear();
                                JSONObject jsonObject = new JSONObject(response);
                                // Log.d("Json",jsonObject.toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    totalcount = jsonObject1.getString("total");
                                }
                                if (totalcount.equals("0")) {
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Is this client confirmed?")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(CounselorContactActivity.this, NewClientEntry.class);
                                                    if (activityName.equals("FormFilled")) {
                                                        intent.putExtra("Activity", "FormFilled");
                                                    } else {
                                                        intent.putExtra("Activity", "Contact");
                                                    }

                                                    intent.putExtra("SrNo", srno);
                                                    startActivity(intent);
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    dialog2.dismiss();
                                                }
                                            }).setCancelable(false)
                                            .show();
                                } else {
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle(srno + " is already exist in DocumentMaster")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).setCancelable(false)
                                            .show();
                                }


                            } catch (JSONException e) {
                                Toast.makeText(CounselorContactActivity.this,"Errorcode-165 CounselorContact checkIfExistInDocMasterResponse "+e.toString(),Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                                Log.d("CheckPhoneException", String.valueOf(e));
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
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                alertDialogBuilder.setTitle("Network issue!!!")


                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        }).show();
                                dialog2.dismiss();
                                Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                // showCustomPopupMenu();
                                Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                            }
                        }
                    });
            requestQueue.add(stringRequest);
        }catch (Exception e)
        {
            Toast.makeText(CounselorContactActivity.this,"Errorcode-164 CounselorContact checkIfExistInDocMaster "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }//close checkifExistInDocMaster
    public void onBtnCallClicked()
    {
        try {
        //first check if previous activity name is Online leads
            if (act1.contains("OnlineLead")||act1.contains("RefreshOnlineLead")||act1.contains("ContactOnlineLead")) {
                Log.d("Entered", "Greaterthan60OnlineLead");

                //  dialog=ProgressDialog.show(CounselorContactActivity.this,"","Loading",true);
                //if yes then allocate that lead to counselor
                insertOnlineLead(sr_no, id1);
            }
            //check if activity name is OpenLeads
            else if(activityName.contains("OpenLeads")||activityName.contains("RefreshOpenLead")||activityName.contains("ContactOpenLead"))
            {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                alertDialogBuilder.setTitle("Open lead allocated successfully")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                showCallAlert();
                                dialog.dismiss();
                            }
                        }).setCancelable(false)
                        .show();
                Log.d("Entered", "Greaterthan60OpenLead");
                updateOpenLeads(sr_no);
            }
            else {
               showCallAlert();
            }
        }catch (Exception e)
        {
            Log.d("ExcGetDetails",e.toString());
            Toast.makeText(CounselorContactActivity.this,"Errorcode-165 CounselorContact BtnCallClicked "+e.toString(),Toast.LENGTH_SHORT).show();        }
    }//close btnCallClicked
    public void showCallAlert()
    {
        clickedButton = "Call";
        vibrator.vibrate(100);
        editor.putString("Sr.No", sr_no);
        editor.putString("CName", name);
        editor.putString("SelectedMobile", selectedmbl);
        editor.commit();
        // Toast.makeText(context,"SrNo:"+dataCounselor1.getSr_no(),Toast.LENGTH_SHORT).show();
        LayoutInflater li = LayoutInflater.from(CounselorContactActivity.this);
        //Creating a view to get the dialog box
        confirmCall = li.inflate(R.layout.layout_confirm_call, null);
        //confirmCall.setClipToOutline(true);
        final EditText edtCalledNumber = confirmCall.findViewById(R.id.edtCallNumber);
        linearCall = confirmCall.findViewById(R.id.linearCall1);
        linearUrl = confirmCall.findViewById(R.id.linearUrl);
        TextView txtYes = confirmCall.findViewById(R.id.txtYes);
        TextView txtNo =  confirmCall.findViewById(R.id.txtNo);
        txtCalledNumber = confirmCall.findViewById(R.id.txtCallingNumber);
        txtDateTime = confirmCall.findViewById(R.id.txtDateTime);
        txtDuration = confirmCall.findViewById(R.id.txtDuration);
        linearCallDetails = confirmCall.findViewById(R.id.linearCallDetails);
        linearConfirmCall = confirmCall.findViewById(R.id.linearConfirmCall);
        btnGetDetails = confirmCall.findViewById(R.id.btnGetDetails);
        btnOk = confirmCall.findViewById(R.id.btnOk);
        linearCallDuration = confirmCall.findViewById(R.id.linearCallDuration);
        txtNoCallDetails = confirmCall.findViewById(R.id.txtNoCallMade);
        TextView txtSrno = confirmCall.findViewById(R.id.txtSrno);
        TextView txtName = confirmCall.findViewById(R.id.txtName);
        final TextView txtUrl = confirmCall.findViewById(R.id.txtUrl);
        // TextView txtRefname=confirmCall.findViewById(R.id.txtRefNo);
        TextView txtActivityName = confirmCall.findViewById(R.id.txtActivity);

        if (activityName.equals("FormFilled")) {
            txtUrl.setText("https://bizcallcrm.com/admission/studentdetails.php?id=" + sr_no);
            linearUrl.setVisibility(View.VISIBLE);
        } else {
            linearUrl.setVisibility(View.GONE);
        }
        if (activityName.contains("OpenLeads") || activityName.contains("CounselorData")) {
            if (refname.equals("Admission form")) {
                txtUrl.setText("http://anilsahasrabuddhe.in/admission/studentdetails.php?id=" + datafrom);
                linearUrl.setVisibility(View.VISIBLE);
            }
        } else {
            linearUrl.setVisibility(View.GONE);
        }
        txtUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txtUrl.getText().toString()));
                startActivity(browserIntent);
            }
        });

        txtSrno.setText(sr_no);
        txtName.setText(txtCName.getText().toString());
        txtActivityName.setText(activityName);

        final AlertDialog.Builder alert = new AlertDialog.Builder(CounselorContactActivity.this);
        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmCall);
        //Creating an alert dialog
        alertDialog1 = alert.create();
        alertDialog1.show();
        alertDialog1.setCancelable(false);

        edtCalledNumber.setText( selectedmbl + "");
        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton = "Confirm";
                vibrator.vibrate(100);
                phone1 = edtCalledNumber.getText().toString().trim();
                phone1 = phone1.replaceAll(" ", "");
                // Log.d("Phone1", phone1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //code to check internet connection quality
                    getConnectionQuality(clickedButton);
                }
            }
        });
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                alertDialog1.dismiss();
            }
        });

        btnGetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on click of this button,call details get inserted to database
                onBtntnGetDetailsClicked();
            }
        });
    }//close showCallAlter

    public void onBtntnGetDetailsClicked()
    {
        try {
            Calendar calendar1 = Calendar.getInstance();
            dt33 = new Date();
            dt33 = calendar1.getTime();
            long diffInMs = dt33.getTime() - dt22.getTime();
            long diffInSec1 = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
            Log.d("TImeDiff", String.valueOf(diffInSec1));
            if (diffInSec1 <= 1) {
                clickedcount = 0;
            }
            Log.d("ClickedCount", String.valueOf(clickedcount));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (manager.getMode() == AudioManager.MODE_IN_CALL) {
                    clickedcount = 0;
                    // linearCall.setVisibility(View.GONE);
                    Toast.makeText(CounselorContactActivity.this, "Can't get call details while on call", Toast.LENGTH_SHORT).show();
                } else {
                    clickedcount++;
                    if (clickedcount == 1) {
                        RecordService recordService=new RecordService(CounselorContactActivity.this);
                        if(recordService.mStartRecording){
                        recordService.onStop();}
                        //linearCall.setVisibility(View.VISIBLE);
                        clickedButton = "GetDetails";
                        getConnectionQuality(clickedButton);
                        // refreshWhenLoading();
                    }
                    else {
                        clickedcount=0;
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(CounselorContactActivity.this,"Errorcode-166 CounselorContact BtnGetDetails clicked "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }//close onBtnGetDetailsClicked
    public void onBtnReminderClicked() {
        try {
            reminderFlag=0;
            vibrator.vibrate(100);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String time1 = txtTime.getText().toString();
            dateReminder = txtDateView.getText().toString();
            if(time1.contains("-Select"))
            {
                reminderFlag=1;
                Toast.makeText(CounselorContactActivity.this,"Select time",Toast.LENGTH_SHORT).show();
            }
            if(dateReminder.contains("-Select"))
            {
                reminderFlag=1;
                Toast.makeText(CounselorContactActivity.this,"Select date",Toast.LENGTH_SHORT).show();
            }
            if(reminderFlag==0) {
                try {
                    dt1 = simpleDateFormat.parse(dateReminder);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String date12 = dateReminder + " " + "00:00:00.000000";
                try {
                    date11 = URLEncoder.encode(date12, "UTF-8");
                    time11 = URLEncoder.encode(time1, "UTF-8");
                    Log.d("EncodedDate", date11);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //alert to confirm set reminder that if reminder for same time has already been set and if click on ok this will overrite new reminder.
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                alertDialogBuilder.setTitle("This will clear all previous reminder  ")
                        .setMessage("Do you want to set new reminder?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                dialog.dismiss();
                                if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("0")) {
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("No Internet connection!!!")
                                            .setMessage("Can't do further process")

                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();

                                                }
                                            }).show();
                                } else if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("1")) {
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                                    //insert reminder entry to database
                                    setReminder0ifAny();
                                }
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(false)
                        .show();
            }
        }catch (Exception e)
        {
            Toast.makeText(CounselorContactActivity.this,"Errorcode-167 CounselorContact BtnReminder clicked "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }//close btnReminderClicked

    public void onImgCreateContactClicked()
    {
        try {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
            alertDialogBuilder.setTitle("Create Contact")
                    .setMessage("Do you want to save contact? ")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            if (ContextCompat.checkSelfPermission(CounselorContactActivity.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(CounselorContactActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
                            } else {

                                ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                                        .build());

                                //----------------------- Names ------------------------
                                if (name != null) {
                                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE,
                                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                            .withValue(
                                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                                    "Bizcall_" + sr_no + "_" + name).build());
                                }

                                //----------------------- Mobile Number ------------------
                                if (mbl != null) {
                                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE,
                                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mbl)
                                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                            .build());
                                }
                                if (parentno != null && !parentno.equals("NA")) {
                                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE,
                                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, parentno)
                                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                                            .build());
                                }
                                //----------------------- Organization -----------------------
                                if (!course.equals("")) {
                                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE,
                                                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                                            .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, sr_no + "_" + course)
                                            .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                                            .build());
                                }

                                // Asking the Contact provider to create a new contact
                                try {
                                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                                    Toast.makeText(CounselorContactActivity.this, "Contact Created Successfully", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.d("CreateContactExc", String.valueOf(e));
                                    // Toast.makeText(CounselorContactActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            // setReminder0ifAny();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    })
                    .show();
            //  Toast.makeText(CounselorContactActivity.this, "Contact Created Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Toast.makeText(CounselorContactActivity.this,"Errorcode-168 CounselorContact CreateContact clicked "+e.toString(),Toast.LENGTH_SHORT).show();        }
    }//close onImgCreateContactClicked

    public void insertViewDetails()
    {
        // String callId=sp.getString("SelectedCallingId",null);
        try {
            if (CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=73&CounselorId=" + id1 + "&SrNo=" + sr_no + "&ActivityName=" + activityName;
                Log.d("ViewDetailsUrl", url);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("ViewDetailsResponse", response);
                                    if (response.contains("Data inserted successfully")) {
                                        dialog.dismiss();
                                        Toast.makeText(CounselorContactActivity.this, "Details inserted successfully", Toast.LENGTH_SHORT).show();
                                        //  edtRemarks.setText(sp.getString("RemarkOnCall",null));
                                        linearContactDetails.setVisibility(View.VISIBLE);
                                        linearViewDetails.setVisibility(View.GONE);
                                        dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Getting Counselor Details", true);
                                        newThreadInitilization(dialog);
                                        if (activityName.equals("OnlineLead") ||activityName.contains("IVRMissed")|| activityName.equals("RefreshOnlineLead") || activityName.equals("ContactOnlineLead")) {
                                            // if (activityName.contains("AdapterOnlineLead")) {
                                            Log.d("SerialNo", sr_no);

                                          //if activity name is onlinelead then this function is used to get details of client b passing his serial no
                                            url = clienturl + "?clientid=" + clientid + "&caseid=60&RefNo=" + sr_no;
                                            getCounselorData(url);
                                           // getOnlineLeadRefno(sr_no);
                                            // }
                                        } else if (activityName.contains("OpenLeads")) {
                                            // if (activityName.contains("AdapterOnlineLead")) {
                                            Log.d("SerialNo", sr_no);
                                            //datafrom = sp.getString("DtaFrom", null);
                                            refname = sp.getString("DataRefName", null);
                                            //if activity name is openlead then this function is used to get details of client b passing his serial no
                                            url = clienturl + "?clientid=" + clientid + "&caseid=76&nSrNo=" + sr_no;
                                            getCounselorData(url);
                                            // getOpenLeadRefNo(sr_no);
                                            // }
                                        } else if (activityName.contains("FormFilled")) {
                                            //if activity name is formfilled then this function is used to get details of client b passing his serial no
                                            url = clienturl + "?clientid=" + clientid + "&caseid=83&ID=" + sr_no;
                                            getCounselorData(url);
                                            //getSrDetailsOnlineForm(sr_no);
                                            if (txtOnlineFormTitle.getVisibility() == View.GONE) {
                                                txtOnlineFormTitle.setVisibility(View.VISIBLE);
                                            } else {
                                                txtOnlineFormTitle.setVisibility(View.GONE);
                                            }
                                            spinnerStatus.setVisibility(View.GONE);
                                            btnUpdateStatus.setVisibility(View.GONE);
                                            edtRemarks.setVisibility(View.GONE);
                                            btnupdateRemarks.setVisibility(View.GONE);
                                            btnDetails.setVisibility(View.GONE);
                                            txtCurrentStatus.setVisibility(View.GONE);
                                            txtRemark.setVisibility(View.GONE);
                                            txtReallocationTitle.setVisibility(View.GONE);
                                        }
                                        else if(activityName.equals("AdminReportDetails"))
                                        {
                                             cid1=getIntent().getStringExtra("CID");
                                            // this function is used to get details of client by passing his serial no
                                            String url=clienturl+"?clientid=" + clientid + "&caseid=32&nSrNo="+sr_no+"&cCounselorID="+cid1;
                                            getCounselorData(url);
                                        }
                                        else {
                                            // datafrom = sp.getString("DtaFrom", null);
                                            refname = sp.getString("DataRefName", null);
                                            // this function is used to get details of client by passing his serial no
                                            String url=clienturl+"?clientid=" + clientid + "&caseid=32&nSrNo="+sr_no+"&cCounselorID="+id1;
                                            getCounselorData(url);
                                        }

                                    } else {
                                        linearContactDetails.setVisibility(View.GONE);
                                        linearViewDetails.setVisibility(View.VISIBLE);
                                        dialog.dismiss();
                                        Toast.makeText(CounselorContactActivity.this, "Details not inserted", Toast.LENGTH_SHORT).show();
                                    }
                                    //   Log.d("Size**", String.valueOf(arrayList.size()));

                                } catch (Exception e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-170 CounselorContact insertViewDetailsResponse " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }
                            }
                        });
                requestQueue.add(stringRequest);
            } else {
                dialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }
        catch (Exception e)
        {
            Log.d("ExcInsertViewDetails",e.toString());
            Toast.makeText(CounselorContactActivity.this,"Errorcode-169 CounselorContact insertViewDetails "+e.toString(),Toast.LENGTH_SHORT).show();
        }
       }//close insertViewDetails

    @Override
    public void onBackPressed()
    {
        try {
          //  dialog=new ProgressDialog(CounselorContactActivity.this);
            if(linearContactDetails.getVisibility()==View.VISIBLE) {
                remarkupdate=sp.getInt("RemarkUpdate",0);
                statusupdate=sp.getInt("StatusUpdate",0);
                country=spinnerCountry.getSelectedItem().toString();
                course=spinnerCourse.getSelectedItem().toString();
                if(remarkupdate==1&&statusupdate==1)
                {
                    if(status11.equals("About To Confirm")||status11.equals("Confirm")||status11.equals("Interested final"))
                    {
                        if(!country.contains("Select")) {
                        if(!course.contains("Select"))
                        {
                        editor = sp.edit();
                        editor.putString("SelectedSrNo", "");
                        editor.putInt("RemarkUpdate", 0);
                        editor.putInt("StatusUpdate", 0);
                        editor.commit();

                        if (act1.contains("AdapterSearch")) {
                            Intent intent = new Intent(CounselorContactActivity.this, Home.class);
                            intent.putExtra("Activity", "CounsellorContact");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("Home")) {
                            Intent intent = new Intent(CounselorContactActivity.this, Home.class);
                            intent.putExtra("Activity", "CounsellorContact");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("Allocation")) {
                            intent = new Intent(CounselorContactActivity.this, AllocationReport.class);
                            intent.putExtra("Activity", "Allocation");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("OLActivity")) {
                            intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                            intent.putExtra("ActivityLeads", "ContactOnlineLead");
                            startActivity(intent);
                            finish();
                        } /*else if (act1.contains("IVRMissed")) {
                        intent = new Intent(CounselorContactActivity.this, IVRMissedTollFree.class);
                        intent.putExtra("ActivityLeads", "ContactIVRMissed");
                        startActivity(intent);
                    }*/ else if (act1.contains("OpenLeads")) {
                            intent = new Intent(CounselorContactActivity.this, OpenLeads.class);
                            intent.putExtra("Activity", "ContactOpenLeads");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("ConvertedOnlineLead")) {
                            intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                            intent.putExtra("ActivityLeads", "ContactConvertedOnlineLead");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("ReportDetails")) {
                            intent = new Intent(CounselorContactActivity.this, ReportDetails.class);
                            intent.putExtra("Activity", "Report Details");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("ReminderActivity")) {
                            LayoutInflater li = LayoutInflater.from(CounselorContactActivity.this);
                            //Creating a view to get the dialog box
                            View confirmCall = li.inflate(R.layout.layout_confirm_call, null);
                            //confirmCall.setClipToOutline(true);
                            EditText editText = confirmCall.findViewById(R.id.edtCallNumber);
                            editText.setVisibility(View.GONE);
                            TextView txtYes = (TextView) confirmCall.findViewById(R.id.txtYes);
                            TextView txtTitle = confirmCall.findViewById(R.id.txtConfirmDelete);
                            TextView txtNo = (TextView) confirmCall.findViewById(R.id.txtNo);
                            txtTitle.setText("Do you want to clear reminder?");
                            AlertDialog.Builder alert = new AlertDialog.Builder(CounselorContactActivity.this);
                            //Adding our dialog box to the view of alert dialog
                            alert.setView(confirmCall);
                            //Creating an alert dialog
                            alertDialog = alert.create();
                            alertDialog.show();

                            txtYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Toast.makeText(CounselorContactActivity.this,"Call"+String.valueOf(call),Toast.LENGTH_SHORT).show();
                                    dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Clearing reminder...", true);
                                    clearReminder();
                                    // refreshWhenLoading();
                                    startActivity(new Intent(CounselorContactActivity.this, ReminderActivity.class));
                                    finish();
                                }
                            });

                            txtNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    startActivity(new Intent(CounselorContactActivity.this, ReminderActivity.class));
                                    finish();
                                }
                            });

                        } else if (activityName.contains("OnlineLead")) {
                            intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                            intent.putExtra("ActivityLeads", "ContactOnlineLead");
                            startActivity(intent);
                            finish();
                        } else if (activityName.contains("FormFilled")) {
                            Intent intent = new Intent(CounselorContactActivity.this, FormFilled.class);
                            // intent.putExtra("Activity","CounsellorContact");
                            startActivity(intent);
                            finish();
                        } else if (activityName.contains("AdminReportDetails")) {
                            super.onBackPressed();
                        } else {
                            intent = new Intent(CounselorContactActivity.this, CounsellorData.class);
                            intent.putExtra("Activity", "CounselorData");
                            startActivity(intent);
                            finish();
                        }
                        }else {
                            Toast.makeText(CounselorContactActivity.this,"Update course first",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CounselorContactActivity.this,"Update country first",Toast.LENGTH_SHORT).show();
                    }
                }else {
                        editor = sp.edit();
                        editor.putString("SelectedSrNo", "");
                        editor.putInt("RemarkUpdate", 0);
                        editor.putInt("StatusUpdate", 0);
                        editor.commit();

                        if (act1.contains("AdapterSearch")) {
                            Intent intent = new Intent(CounselorContactActivity.this, Home.class);
                            intent.putExtra("Activity", "CounsellorContact");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("Home")) {
                            Intent intent = new Intent(CounselorContactActivity.this, Home.class);
                            intent.putExtra("Activity", "CounsellorContact");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("Allocation")) {
                            intent = new Intent(CounselorContactActivity.this, AllocationReport.class);
                            intent.putExtra("Activity", "Allocation");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("OLActivity")) {
                            intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                            intent.putExtra("ActivityLeads", "ContactOnlineLead");
                            startActivity(intent);
                            finish();
                        } /*else if (act1.contains("IVRMissed")) {
                        intent = new Intent(CounselorContactActivity.this, IVRMissedTollFree.class);
                        intent.putExtra("ActivityLeads", "ContactIVRMissed");
                        startActivity(intent);
                    }*/ else if (act1.contains("OpenLeads")) {
                            intent = new Intent(CounselorContactActivity.this, OpenLeads.class);
                            intent.putExtra("Activity", "ContactOpenLeads");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("ConvertedOnlineLead")) {
                            intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                            intent.putExtra("ActivityLeads", "ContactConvertedOnlineLead");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("ReportDetails")) {
                            intent = new Intent(CounselorContactActivity.this, ReportDetails.class);
                            intent.putExtra("Activity", "Report Details");
                            startActivity(intent);
                            finish();
                        } else if (act1.contains("ReminderActivity")) {
                            LayoutInflater li = LayoutInflater.from(CounselorContactActivity.this);
                            //Creating a view to get the dialog box
                            View confirmCall = li.inflate(R.layout.layout_confirm_call, null);
                            //confirmCall.setClipToOutline(true);
                            EditText editText = confirmCall.findViewById(R.id.edtCallNumber);
                            editText.setVisibility(View.GONE);
                            TextView txtYes = (TextView) confirmCall.findViewById(R.id.txtYes);
                            TextView txtTitle = confirmCall.findViewById(R.id.txtConfirmDelete);
                            TextView txtNo = (TextView) confirmCall.findViewById(R.id.txtNo);
                            txtTitle.setText("Do you want to clear reminder?");
                            AlertDialog.Builder alert = new AlertDialog.Builder(CounselorContactActivity.this);
                            //Adding our dialog box to the view of alert dialog
                            alert.setView(confirmCall);
                            //Creating an alert dialog
                            alertDialog = alert.create();
                            alertDialog.show();

                            txtYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Toast.makeText(CounselorContactActivity.this,"Call"+String.valueOf(call),Toast.LENGTH_SHORT).show();
                                    dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Clearing reminder...", true);
                                    clearReminder();
                                    // refreshWhenLoading();
                                    startActivity(new Intent(CounselorContactActivity.this, ReminderActivity.class));
                                    finish();
                                }
                            });

                            txtNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    startActivity(new Intent(CounselorContactActivity.this, ReminderActivity.class));
                                    finish();
                                }
                            });

                        } else if (activityName.contains("OnlineLead")) {
                            intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                            intent.putExtra("ActivityLeads", "ContactOnlineLead");
                            startActivity(intent);
                            finish();
                        } else if (activityName.contains("FormFilled")) {
                            Intent intent = new Intent(CounselorContactActivity.this, FormFilled.class);
                            // intent.putExtra("Activity","CounsellorContact");
                            startActivity(intent);
                            finish();
                        } else if (activityName.contains("AdminReportDetails")) {
                            super.onBackPressed();
                        } else {
                            intent = new Intent(CounselorContactActivity.this, CounsellorData.class);
                            intent.putExtra("Activity", "CounselorData");
                            startActivity(intent);
                            finish();
                        }
                    }
                }else {
                    Toast.makeText(CounselorContactActivity.this,"Update status and remarks first",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                editor = sp.edit();
                editor.putString("SelectedSrNo", "");
                editor.commit();
                if (act1.contains("AdapterSearch")) {
                    Intent intent = new Intent(CounselorContactActivity.this, Home.class);
                    intent.putExtra("Activity", "CounsellorContact");
                    startActivity(intent);
                } else if (act1.contains("Home")) {
                    Intent intent = new Intent(CounselorContactActivity.this, Home.class);
                    intent.putExtra("Activity", "CounsellorContact");
                    startActivity(intent);
                } else if (act1.contains("Allocation")) {
                    intent = new Intent(CounselorContactActivity.this, AllocationReport.class);
                    intent.putExtra("Activity", "Allocation");
                    startActivity(intent);
                } else if (act1.contains("OLActivity")) {
                    intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                    intent.putExtra("ActivityLeads", "ContactOnlineLead");
                    startActivity(intent);
                } /*else if (act1.contains("IVRMissed")) {
                    intent = new Intent(CounselorContactActivity.this, IVRMissedTollFree.class);
                    intent.putExtra("ActivityLeads", "ContactIVRMissed");
                    startActivity(intent);
                } */else if (act1.contains("OpenLeads")) {
                    intent = new Intent(CounselorContactActivity.this, OpenLeads.class);
                    intent.putExtra("Activity", "ContactOpenLeads");
                    startActivity(intent);
                } else if (act1.contains("ConvertedOnlineLead")) {
                    intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                    intent.putExtra("ActivityLeads", "ContactConvertedOnlineLead");
                    startActivity(intent);
                } else if (act1.contains("ReportDetails")) {
                    intent = new Intent(CounselorContactActivity.this, ReportDetails.class);
                    intent.putExtra("Activity", "Report Details");
                    startActivity(intent);
                } else if (act1.contains("ReminderActivity")) {
                    LayoutInflater li = LayoutInflater.from(CounselorContactActivity.this);
                    //Creating a view to get the dialog box
                    View confirmCall = li.inflate(R.layout.layout_confirm_call, null);
                    //confirmCall.setClipToOutline(true);
                    EditText editText = confirmCall.findViewById(R.id.edtCallNumber);
                    editText.setVisibility(View.GONE);
                    TextView txtYes = (TextView) confirmCall.findViewById(R.id.txtYes);
                    TextView txtTitle = confirmCall.findViewById(R.id.txtConfirmDelete);
                    TextView txtNo = (TextView) confirmCall.findViewById(R.id.txtNo);
                    txtTitle.setText("Do you want to clear reminder?");
                    AlertDialog.Builder alert = new AlertDialog.Builder(CounselorContactActivity.this);
                    //Adding our dialog box to the view of alert dialog
                    alert.setView(confirmCall);
                    //Creating an alert dialog
                    alertDialog = alert.create();
                    alertDialog.show();

                    txtYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
            // Toast.makeText(CounselorContactActivity.this,"Call"+String.valueOf(call),Toast.LENGTH_SHORT).show();
                            dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Clearing reminder...", true);

                            clearReminder();
                            // refreshWhenLoading();
                            startActivity(new Intent(CounselorContactActivity.this, ReminderActivity.class));
                        }
                    });

                    txtNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            startActivity(new Intent(CounselorContactActivity.this, ReminderActivity.class));
                        }
                    });

                } else if (activityName.contains("OnlineLead")) {
                    intent = new Intent(CounselorContactActivity.this, OnlineLead.class);
                    intent.putExtra("ActivityLeads", "ContactOnlineLead");
                    startActivity(intent);
                } else if (activityName.contains("FormFilled")) {
                    Intent intent = new Intent(CounselorContactActivity.this, FormFilled.class);
                    // intent.putExtra("Activity","CounsellorContact");
                    startActivity(intent);
                } else if (activityName.contains("AdminReportDetails")) {
                    super.onBackPressed();
                } else {
                    intent = new Intent(CounselorContactActivity.this, CounsellorData.class);
                    intent.putExtra("Activity", "CounselorData");
                    startActivity(intent);
                }
            }
        }catch (Exception e)
        {
            Log.d("ExcOBackpress", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-171 CounselorContact onBackpressed "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getConnectionQuality(String clkButton) {
        try {
            if (!CheckInternet.checkInternet(CounselorContactActivity.this)) {
            clickedcount=0;
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
            alertDialogBuilder.setTitle("No Internet connection!!!")
                    .setMessage("Can't do further process")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).show();
            //  Toast.makeText(context, "Service Unavailable", Toast.LENGTH_SHORT).show();
        }
        else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            int numberOfLevels = 5;
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
            Log.d("Level", String.valueOf(level));
            if (level == 2||level==1) {
                clickedcount=0;
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                alertDialogBuilder.setTitle("Slow Internet speed!!!")
                        .setMessage("Can't do further process")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                        clickedcount=0;
                                dialog.dismiss();

                            }
                        })
                        .show();

              //  Toast.makeText(context, "Wifi POOR", Toast.LENGTH_SHORT).show();
            }/* else if (level == 3) {
                Toast.makeText(context, "Wifi MODERATE", Toast.LENGTH_SHORT).show();
            }*/ else if (level == 4||level == 5||level==3) {

                        callProcess();


                // Toast.makeText(context, "Wifi GOOD", Toast.LENGTH_SHORT).show();
            } /*else if (level == 5) {
                Toast.makeText(context, "Wifi EXCELLENT", Toast.LENGTH_SHORT).show();
            }*//* else {
                Toast.makeText(context, "Wifi Slow", Toast.LENGTH_SHORT).show();
            }*/
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkClass = getNetworkClass(getNetworkType(CounselorContactActivity.this));
            Log.d("ClassNET", String.valueOf(networkClass));
            if (networkClass == 1) {
                clickedcount=0;
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                alertDialogBuilder.setTitle("Slow Internet speed!!!")
                        .setMessage("Can't do further process")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //insertIMEI();
                                        /*edtName.setText("");
                                        edtPassword.setText("");*/
                                        clickedcount=0;
                                dialog.dismiss();
                                }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        /* .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();

                             }
                         })*/
                        .show();
             //   Toast.makeText(context, "Mobile Data POOR", Toast.LENGTH_SHORT).show();
            } else if (networkClass == 2||networkClass == 3) {
                    callProcess();
                //  Toast.makeText(context, "Mobile Data GOOD", Toast.LENGTH_SHORT).show();
            }
        }
        }catch (Exception e)
        {
            Log.d("ExcConnectionQuality", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-172 CounselorContact getConnectionQuality "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        return "UNKNOWN";

    }

    public NetworkInfo getInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    public int getNetworkClass(int networkType) {
        try {
            return getNetworkClassReflect(networkType);
        } catch (Exception ignored) {
        }

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case 16: // TelephonyManager.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 1;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case 17: // TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                return 2;
            case TelephonyManager.NETWORK_TYPE_LTE:
            case 18: // TelephonyManager.NETWORK_TYPE_IWLAN:
                return 3;
            default:
                return 0;
        }
    }

    private int getNetworkClassReflect(int networkType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getNetworkClass = TelephonyManager.class.getDeclaredMethod("getNetworkClass", int.class);
        if (!getNetworkClass.isAccessible()) {
            getNetworkClass.setAccessible(true);
        }
        return (Integer) getNetworkClass.invoke(null, networkType);
    }

    public static int getNetworkType(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType();
    }
    public void callProcess()
    {
        try {
            if(clickedButton.contains("Confirm"))
        {
            //int pos = viewHolder.getAdapterPosition();
            // DataCounselor dataCounselor1 = arrayList.get(pos);
                makeCall();
        }
        else {
            if(act1.contains("OnlineLead")||act1.contains("RefreshOnlineLead")||act1.contains("ContactOnlineLead"))
            {
                checkNumber="GetDetails";
                checkPhoneNumber(phone2);
            }
            else {
                dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading call details", true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getCallLogDetails();
                }
            }
        }
        }catch (Exception e)
        {
            Log.d("ExcCallProcess", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-173 CounselorContact callProcess "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void makeCall(){
        try {
            phone2 = phone1.substring((phone1.length()) - 10);
            phone2 = phone2.replaceAll(" ", "");
            Log.d("Nmber", phone2);
            if (act1.equals("OnlineLead")) {

                checkNumber = "ConfirmCall";
                dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Checking number in database", true);
                checkPhoneNumber(phone2);
                //  refreshWhenLoading();
            } else {
                eventid = 2;

                // dialog=ProgressDialog.show(CounselorContactActivity.this,"","Loading",true);
                insertPointCollection(eventid);
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone1));
                // call=call+2;

                //Toast.makeText(CounselorContactActivity.this,"Call"+String.valueOf(call),Toast.LENGTH_SHORT).show();

                //intent.setAction("android.intent.action.CALL");
                if (ContextCompat.checkSelfPermission(CounselorContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) CounselorContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    clickedcount = 0;
                    calendar = Calendar.getInstance();
                    linearConfirmCall.setVisibility(View.GONE);
                    linearCallDetails.setVisibility(View.VISIBLE);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    dt22 = new Date();
                    dt22 = calendar.getTime();
                    // txtAppNo.setText(edtCallNo.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    // SimpleDateFormat sdf1=new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    String dtime = calendar.getTime() + "";
                    Log.d("DTime", dtime);
                    // newDTime=sdf.format(dtime);
                    Date date = calendar.getTime();
                    newDTime = sdf.format(date);
                    txtDateTime.setText(newDTime);
                    txtCalledNumber.setText(phone2);
                    Log.d("apptime", phone2 + "/" + newDTime);
                    startActivity(intent);
                }
            }
        }catch (Exception e)
        {
            Log.d("ExcMakeCall", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-174 CounselorContact makeCall "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void clearReminder()
    {
        try {
            String callId=sp.getString("SelectedCallingId",null);
            url=clienturl+"?clientid=" + clientid + "&caseid=35&cUpdatedBy=" +id1+"&nSrNo=" + sr_no+"&nCallingId="+callId;
        Log.d("ClearReminderUrl", url);
            if(CheckInternet.checkInternet(CounselorContactActivity.this))
            {
          StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {

                          Log.d("*******", response.toString());
                          try {
                              dialog.dismiss();

                              Log.d("ClearReminderResonse", response);
                              if (response.contains("Row updated successfully"))
                              {
                                  //dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);
                                  eventid=14;
                                  insertPointCollection(eventid);
                                  Toast.makeText(CounselorContactActivity.this, "Cleared reminder", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(CounselorContactActivity.this, "Reminder not cleared", Toast.LENGTH_SHORT).show();
                              }
                          }catch (Exception e)
                          {
                              Toast.makeText(CounselorContactActivity.this,"Errorcode-176 CounselorContact clearReminderResponse "+e.toString(),Toast.LENGTH_SHORT).show();                              dialog.dismiss();
                              Log.d("ClearReminderException", String.valueOf(e));
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
                          if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                              android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                              alertDialogBuilder.setTitle("Network issue!!!")


                                      // Specifying a listener allows you to take an action before dismissing the dialog.
                                      // The dialog is automatically dismissed when a dialog button is clicked.
                                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                          public void onClick(DialogInterface dialog, int which) {

                                              dialog.dismiss();
                                          }
                                      }).show();
                                 dialog.dismiss();
                              Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                              // showCustomPopupMenu();
                              Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                          }

                      }
                  });
          requestQueue.add(stringRequest);
    }else {
        dialog.dismiss();
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }catch (Exception e)
        {
            Log.d("ExcClearReminder", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-175 CounselorContact clearReminder "+e.toString(),Toast.LENGTH_SHORT).show();
        }

    }


    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {
try{
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "P.M.";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "A.M.";
        } else if (hours == 12)
            timeSet = "P.M.";
        else
            timeSet = "A.M.";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        // String aTime = new StringBuilder().append(hours).append(" ").append(timeSet).toString();
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        txtTime.setText(aTime);
}catch (Exception e)
{
    Toast.makeText(CounselorContactActivity.this,"Errorcode-177 CounselorContact updateTime "+e.toString(),Toast.LENGTH_SHORT).show();}
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        try {

        showDialog(999);
        Toast.makeText(CounselorContactActivity.this, "ca",
                Toast.LENGTH_SHORT)
                .show();
        }catch (Exception e)
        {
            Toast.makeText(CounselorContactActivity.this,"Errorcode-178 CounselorContact setDate "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcSetDate", String.valueOf(e));
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        try {

            if (id == 999) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        myDateListener, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return datePickerDialog;
            }
            if (id == TIME_DIALOG_ID) {

                // set time picker as current time
                TimePickerDialog timePickerDialog=new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);

                return timePickerDialog;
            }
        }catch (Exception e)
        {
            Log.d("ExcCreateDialog", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-179 CounselorContact createDialog "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        try{
        txtDateView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        }catch (Exception e)
        {
            Toast.makeText(CounselorContactActivity.this,"Errorcode-180 CounselorContact showDate "+e.toString(),Toast.LENGTH_SHORT).show();                Log.d("ExcShowDate", String.valueOf(e));
        }
    }


    public void insertPointCollection(int eid)
    {
        try {
       // String callId=sp.getString("SelectedCallingId",null);
        url=clienturl+"?clientid=" + clientid + "&caseid=36&nCounsellorId=" + id1 + "&nEventId="+eid;
        Log.d("PointCollectionUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this))
        {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.d("PointCollectionResponse", response);
                            if (response.contains("Data inserted successfully")) {
                                Toast.makeText(CounselorContactActivity.this, "Point inserted successfully", Toast.LENGTH_SHORT).show();
                              //  dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Inserting point...", true);
                                getPointCollection();
                            } else {
                                Toast.makeText(CounselorContactActivity.this, "Point not inserted", Toast.LENGTH_SHORT).show();
                            }
                            //   Log.d("Size**", String.valueOf(arrayList.size()));

                        }catch (Exception e)
                        {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-182 CounselorContact PointCollectionResponse "+e.toString(),Toast.LENGTH_SHORT).show();                            dialog.dismiss();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);

    }else {
        dialog.dismiss();
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
    } }catch (Exception e)
        {
            Log.d("ExcInsertPoint", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-181 CounselorContact insertPointCollection "+e.toString(),Toast.LENGTH_SHORT).show();        }

    }

    public void checkPhoneNumber(final String number) {
        try {
            if (CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=70&CounselorID=" + id1 + "&PhoneNumber=" + number;
                Log.d("CheckNumberUrl", url);
                if (CheckInternet.checkInternet(CounselorContactActivity.this)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                    Log.d("CheckNumberResponse", response);
                                    try {
                                        // arrayListTotal.clear();
                                        JSONObject jsonObject = new JSONObject(response);
                                        // Log.d("Json",jsonObject.toString());
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                            totalcount = jsonObject1.getString("mobilecount");
                                        }
                                        if (totalcount.equals("0")) {
                                            if (checkNumber.equals("GetDetails")) {
                                                dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading call details", true);

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    getCallLogDetails();
                                                }
                                            } else {
                                                eventid = 2;
                                                insertPointCollection(eventid);
                                                intent = new Intent(Intent.ACTION_CALL);
                                                intent.setData(Uri.parse("tel:" + phone1));
                                                // call=call+2;

                                                //Toast.makeText(CounselorContactActivity.this,"Call"+String.valueOf(call),Toast.LENGTH_SHORT).show();

                                                //intent.setAction("android.intent.action.CALL");
                                                if (ContextCompat.checkSelfPermission(CounselorContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions((Activity) CounselorContactActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                                } else {
                                                    calendar = Calendar.getInstance();
                                                    clickedcount = 0;

                                                    linearConfirmCall.setVisibility(View.GONE);
                                                    linearCallDetails.setVisibility(View.VISIBLE);

                                                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                                    dt22 = new Date();
                                                    dt22 = calendar.getTime();
                                                    // txtAppNo.setText(edtCallNo.getText().toString());
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                                    // SimpleDateFormat sdf1=new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                                    String dtime = calendar.getTime() + "";
                                                    Log.d("DTime", dtime);
                                                    // newDTime=sdf.format(dtime);
                                                    Date date = calendar.getTime();
                                                    newDTime = sdf.format(date);
                                                    txtDateTime.setText(newDTime);
                                                    txtCalledNumber.setText(phone2);
                                                    Log.d("apptime", phone2 + "/" + newDTime);
                                                    startActivity(intent);

                                                }
                                            }
                                        } else {
                                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                            alertDialogBuilder.setTitle("This number is allocated to someone else")
                                                    .setMessage("You cannot make a call")

                                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(CounselorContactActivity.this, "Errorcode-184 CounselorContact checkPhoneResponse " + e.toString(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Log.d("CheckPhoneException", String.valueOf(e));
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
                                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                        alertDialogBuilder.setTitle("Network issue!!!")


                                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                                // The dialog is automatically dismissed when a dialog button is clicked.
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                        dialog.dismiss();
                                        Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                        // showCustomPopupMenu();
                                        Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                    }

                                }
                            });
                    requestQueue.add(stringRequest);
                } else {
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            }else
                {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }
    catch (Exception e)
        {
            Log.d("ExcCheckPhone", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-183 CounselorContact checkPhone "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCallLogDetails() {
        try {
            //dialog=ProgressDialog.show(CounselorContactActivity.this,"","Loading",true);
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        arrayListCallDetails = new ArrayList<>();
        while (managedCursor.moveToNext())
        {
            String callPhoneno = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String strdateFormated = String.valueOf(callDayTime);
           String callDuration = managedCursor.getString(duration);
           String  finalCallType = null;
            Log.d("logtime", strdateFormated+" / "+callPhoneno);

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
            //Date date1=strdateFormated;
            newDTime=sdf.format(callDayTime);

            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    finalCallType = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    finalCallType = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    finalCallType = "MISSED";
                    break;
            }
            dataCallDetails = new DataCallDetails(callPhoneno, finalCallType, newDTime, callDuration);
            arrayListCallDetails.add(dataCallDetails);
        }
        Log.d("callarraysize", arrayListCallDetails.size() + "");

        for (int i = 0; i < arrayListCallDetails.size(); i++)
        {

            String clno = arrayListCallDetails.get(i).getmCallMobileNo();
            String cltype = arrayListCallDetails.get(i).getmCallType();
            final String cldate = arrayListCallDetails.get(i).getmCallDate();
            final String clduration = arrayListCallDetails.get(i).getmCallDuration();

       //     Log.d("logdata", clno+" / "+phone2);
            // String newNo = clno.substring(clno.length()-10);
         //   Log.d("qqq",phone2+" "+txtDateTime.getText());
           /* if(activityName.equals("PhoneStateReceiver"))
            {
                phone2=selectedmbl;

            }*/
           // Log.d("PhoneStateNumber",phone2);

            if (clno.contains(phone2) && cldate.equals(txtDateTime.getText().toString()) ){
                    dialog.dismiss();
                Log.d("logdata1", clno+" / "+cldate+""+phone2);

                txtCalledNumber.setText(clno);
                txtDuration.setText(clduration);
                txtDateTime.setText(cldate);
                linearCallDuration.setVisibility(View.VISIBLE);
                       //txtCallType.setText(cltype);
                dsec= Long.parseLong(clduration);
                txtNoCallDetails.setVisibility(View.GONE);
                btnOk.setVisibility(View.GONE);
                linearConfirmCall.setVisibility(View.GONE);
                linearCallDetails.setVisibility(View.VISIBLE);
              insertCallInfo(clduration, cldate);
                }
                else {
                if(linearCallDuration.getVisibility()==View.GONE) {
                    dialog.dismiss();
                    txtNoCallDetails.setVisibility(View.VISIBLE);
                    btnOk.setVisibility(View.VISIBLE);
                    linearConfirmCall.setVisibility(View.GONE);
                    linearCallDetails.setVisibility(View.GONE);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog1.dismiss();
                        }
                    });
                }
            }
        }
        }catch (Exception e)
        {
            Log.d("ExcCallLogDetails", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-185 CounselorContact callLogDetails "+e.toString(),Toast.LENGTH_SHORT).show();        }
    }
        public void callUploadFile(){
                    try {

                        new Thread(new Runnable() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                       // dialog.dismiss();
                                    }
                                });
                                uploadFile(uploadFilePath + "" + uploadFileName);
                            }
                        }).start();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(CounselorContactActivity.this,"Errorcode-186 CounselorContact uploadFile "+e.toString(),Toast.LENGTH_SHORT).show();                        Log.d("UplaodException", String.valueOf(e));
                    }
                }
        public void getPointCollection()
        {
        try {
            url=clienturl+"?clientid=" + clientid + "&caseid=37&nCounsellorId=" + id1;
        Log.d("CoinUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this)) {
            if (CheckServer.isServerReachable(CounselorContactActivity.this)) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                      /*  if (dialog.isShowing()) {
                            dialog.dismiss();
                        }*/
                                    Log.d("CoinResponse", response);

                                    // arrayListTotal.clear();
                                    JSONObject jsonObject = new JSONObject(response);
                                    // Log.d("Json",jsonObject.toString());
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String totalcoin = jsonObject1.getString("Total Coin");


                                        if(totalcoin.equals(null)) {
                                           totalcoin="0";
                                        }
                                        txtCoin.setText(totalcoin);
                                        editor = sp.edit();
                                        editor.putString("TotalCoin", totalcoin);
                                        editor.commit();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-187 CounselorContact checkIfExistInDocMaster " + e.toString(), Toast.LENGTH_SHORT).show();                            //dialog.dismiss();
                                    Log.d("PointCollectException", String.valueOf(e));
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")


                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    //  dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }

                            }
                        });
                requestQueue.add(stringRequest);
            } else {
                // dialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }else {
            dialog.dismiss();
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Log.d("ExcGetPoint", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-186 CounselorContact getPointCollection "+e.toString(),Toast.LENGTH_SHORT).show();
        }

}
public void showIncomingAlert()
{
    editor.putString("Sr.No", sr_no);
    editor.putString("CName", name);
    editor.putString("SelectedMobile", selectedmbl);
    editor.commit();
    // Toast.makeText(context,"SrNo:"+dataCounselor1.getSr_no(),Toast.LENGTH_SHORT).show();
    LayoutInflater li = LayoutInflater.from(CounselorContactActivity.this);
    //Creating a view to get the dialog box
    confirmCall = li.inflate(R.layout.layout_confirm_call, null);
    //confirmCall.setClipToOutline(true);
    final EditText edtCalledNumber = confirmCall.findViewById(R.id.edtCallNumber);
    linearCall = confirmCall.findViewById(R.id.linearCall1);
    linearUrl = confirmCall.findViewById(R.id.linearUrl);
    TextView txtYes = (TextView) confirmCall.findViewById(R.id.txtYes);
    TextView txtNo = (TextView) confirmCall.findViewById(R.id.txtNo);
    txtCalledNumber = confirmCall.findViewById(R.id.txtCallingNumber);
    txtDateTime = confirmCall.findViewById(R.id.txtDateTime);
    txtDuration = confirmCall.findViewById(R.id.txtDuration);
    linearCallDetails = confirmCall.findViewById(R.id.linearCallDetails);
    linearConfirmCall = confirmCall.findViewById(R.id.linearConfirmCall);
    btnGetDetails = confirmCall.findViewById(R.id.btnGetDetails);
    btnOk = confirmCall.findViewById(R.id.btnOk);
    linearCallDuration = confirmCall.findViewById(R.id.linearCallDuration);
    txtNoCallDetails = confirmCall.findViewById(R.id.txtNoCallMade);
    TextView txtSrno = confirmCall.findViewById(R.id.txtSrno);
    TextView txtName = confirmCall.findViewById(R.id.txtName);
    final TextView txtUrl = confirmCall.findViewById(R.id.txtUrl);
    // TextView txtRefname=confirmCall.findViewById(R.id.txtRefNo);
    TextView txtActivityName = confirmCall.findViewById(R.id.txtActivity);
    // LinearLayout linearRefName=confirmCall.findViewById(R.id.linearRefNo);
    // edtRemark=confirmCall.findViewById(R.id.edtRemarks);

    txtUrl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txtUrl.getText().toString()));
            startActivity(browserIntent);
        }
    });

    txtSrno.setText(sr_no);
    txtName.setText(txtCName.getText().toString());
    txtActivityName.setText(activityName);
    final AlertDialog.Builder alert = new AlertDialog.Builder(CounselorContactActivity.this);
    //Adding our dialog box to the view of alert dialog
    alert.setView(confirmCall);
    //Creating an alert dialog
    alertDialog1 = alert.create();
    alertDialog1.show();
    alertDialog1.setCancelable(false);
    linearConfirmCall.setVisibility(View.GONE);
    linearCallDetails.setVisibility(View.VISIBLE);
    calendar = Calendar.getInstance();
    linearConfirmCall.setVisibility(View.GONE);
    linearCallDetails.setVisibility(View.VISIBLE);
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    dt22 = new Date();
    dt22 = calendar.getTime();
    // txtAppNo.setText(edtCallNo.getText().toString());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    // SimpleDateFormat sdf1=new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String dtime = calendar.getTime() + "";
    Log.d("DTime", dtime);
    // newDTime=sdf.format(dtime);
    Date date = calendar.getTime();
    newDTime = sdf.format(date);
    txtDateTime.setText(newDTime);
    txtCalledNumber.setText(selectedmbl);
    btnGetDetails.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBtntnGetDetailsClicked();
        }
    });

}
public void getCounselorDetails()
{
        try {
            url=clienturl+"?clientid=" + clientid + "&caseid=30";
        Log.d("CounselorUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this))
        {
            if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                Log.d("CounselorResponse1", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("Json", jsonObject.toString());
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String id = String.valueOf(jsonObject1.getInt("cCounselorID"));
                                        String name = jsonObject1.getString("cCounselorName");
                                        dataListCounselor = new DataListCounselor(id, name);
                                        arrayListCounselorDetails.add(dataListCounselor);
                                    }
                                    AdapterListCounselor adapterListCounselor = new AdapterListCounselor(CounselorContactActivity.this, arrayListCounselorDetails);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(CounselorContactActivity.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    recyclerCounselorDetails.setLayoutManager(layoutManager);
                                    recyclerCounselorDetails.setAdapter(adapterListCounselor);
                                    adapterListCounselor.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-189 CounselorContact counselorDetailsResponse " + e.toString(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    Log.d("CounselorDetailExceptio", String.valueOf(e));
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")

                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }
                            }
                        });
                requestQueue.add(stringRequest);
            }else
            {
                dialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                alertDialogBuilder.setTitle("Network issue!!!!")
                        .setMessage("Try after some time!")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).show();
            }
    }else {
        dialog.dismiss();
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }catch (Exception e)
        {
            Toast.makeText(CounselorContactActivity.this,"Errorcode-188 CounselorContact getCounselorDetails "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcCounselorDetails", String.valueOf(e));
        }
    }
    public  void getOnlineLeadRefno(String serialno)
    {
        try {
        if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
            url = clienturl + "?clientid=" + clientid + "&caseid=60&RefNo=" + serialno;
            Log.d("OnlineRefUrl", url);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("OnlineLeaREfResponse", response);
                                getCounselorDetails();
                                getPointCollection();
                                dialog.dismiss();
                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("Json", jsonObject.toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.d("Length", String.valueOf(jsonArray.length()));

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String sr_no = String.valueOf(jsonObject1.getInt("nSrNo"));
                                    name = jsonObject1.getString("cCandidateName");
                                    course = jsonObject1.getString("cCourse");
                                    mbl = jsonObject1.getString("cMobile");
                                    adrs = jsonObject1.getString("cAddressLine");
                                    city = jsonObject1.getString("cCity");
                                    state1 = jsonObject1.getString("cState");
                                    pincode = jsonObject1.getString("cPinCode");
                                    parentno = jsonObject1.getString("cParantNo");
                                    email = jsonObject1.getString("cEmail");
                                    fetchedDataFrom = jsonObject1.getString("cDataFrom");
                                    fetchedAllocatedTo = jsonObject1.getString("AllocatedTo");
                                    allocatedDate = jsonObject1.getString("AllocationDate");
                                    statusid = jsonObject1.getString("CurrentStatus");
                                    remark = jsonObject1.getString("cRemarks");
                                    fetchedCreatedDate = jsonObject1.getString("dtCreatedDate");
                                    status11 = jsonObject1.getString("cStatus");
                                    country=jsonObject1.getString("cCountry");
                                    // DataCounselor dataCounselor=new DataCounselor("1",sr_no,name,course,mbl,parentno,email,allocatedDate,adrs,
                                    // city,state1,pincode,statusid,status11,remark);
                                    //  arrayListOnlineLead.add(dataCounselor);
                                    editor.putString("SelectedMobile", mbl);
                                    editor.putString("SelectedName", name);
                                    editor.commit();
                                    txtCName.setText(name);
                                    txtSrno.setText(sr_no);
                                    txtCourse.setText(course);
                                    txtCurrentStatus.setText(status11);
                                    txtRemark.setText(remark);
                                    ArrayList<String> arrayListMbl = new ArrayList<>();

                                    arrayListMbl.add(mbl);
                                    arrayListMbl.add(parentno);

                                    if(!country.equals(null))
                                    {
                                        for(int i1=0;i1<arrayListCountry.size();i1++) {
                                            if(arrayListCountry.get(i1).contains(country)) {
                                                spinnerCountry.setSelection(i1);
                                            }
                                        }
                                    }
                                    if(!course.equals(null))
                                    {
                                        for(int i1=0;i1<arrayListCourse.size();i1++) {
                                            if(arrayListCourse.get(i1).contains(course)) {
                                                spinnerCourse.setSelection(i1);
                                            }
                                        }
                                    }

                                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CounselorContactActivity.this, R.layout.spinner_item1, arrayListMbl);
                                    //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerMobile.setAdapter(arrayAdapter);
                                    spinnerMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            selectedmbl = spinnerMobile.getSelectedItem().toString().trim();
                                            selectedmbl = selectedmbl.replaceAll(" ", "");
                                            // selectedmbl =  "+91"+ selectedmbl;
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            spinnerMobile.setSelection(0);
                                        }
                                    });
                                }
                                //dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);
                                if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("0")) {
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                                } else if (CheckInternetSpeed.checkInternet(CounselorContactActivity.this).contains("1")) {
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
                                    getStatus1();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(CounselorContactActivity.this, "Errorcode-191 CounselorContact onlineLeadRefResponse " + e.toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Log.d("OnlineLeadRefException", String.valueOf(e));
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
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                alertDialogBuilder.setTitle("Network issue!!!")


                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        }).show();
                                dialog.dismiss();
                                Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                // showCustomPopupMenu();
                                Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                            }

                        }
                    });
            requestQueue.add(stringRequest);
        }else {
            if(dialog.isShowing()){
            dialog.dismiss();
            }
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Log.d("ExcOnlineLeadRefno", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-190 CounselorContact getOnlinLeadRefNo "+e.toString(),Toast.LENGTH_SHORT).show();        }

            }
    public void insertOnlineLead(String srno,String cid)
    { try {
        if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
            url = clienturl + "?clientid=" + clientid + "&caseid=61&RefNo=" + srno + "&CounselorId=" + cid;
            Log.d("InsertOnlineUrl", url);
            if (CheckInternet.checkInternet(CounselorContactActivity.this)) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //Log.d("*******", response.toString());
                                try {
                                    Log.d("InsertOnlineResponse", response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("Json", jsonObject.toString());

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        sr_onlinelead = jsonObject1.getString("nSRNO");
                                    }
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Online lead inserted successfully")
                                            .setMessage("Serial no is:" + sr_onlinelead)

                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(CounselorContactActivity.this, CounselorContactActivity.class);
                                                    intent.putExtra("ActivityName", "OLActivity");
                                                    editor.putString("SelectedSrNo", sr_onlinelead);
                                                    editor.putString("ActivityContact", "OLActivity");
                                                    editor.commit();
                                                    startActivity(intent);
                                                    //getCounselorData(sr_onlinelead,id1);
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                    updateOnlineLead(sr_no);


                                    //   Log.d("Size**", String.valueOf(arrayList.size()));
                                } catch (Exception e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-193 CounselorContact insertOnlineLead " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")


                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }
                            }
                        });
                requestQueue.add(stringRequest);
            } else {
                dialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }else {
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-192 CounselorContact insertOnlineLead "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void updateOnlineLead(String sr)
    {
        try {
            if(CheckServer.isServerReachable(CounselorContactActivity.this))
            {
        url=clienturl + "?clientid=" + clientid + "&caseid=66&RefNo=" + sr;
        Log.d("UpdateOnlineUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this))
        {
             StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("*******", response.toString());
                        try {
                            dialog.dismiss();

                            Log.d("updateOnlineRes", response);
                            if (response.contains("Row updated successfully")) {
                                // getOnlineLeadRefno(sr_onlinelead);
                                Toast.makeText(CounselorContactActivity.this, "Online lead updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CounselorContactActivity.this, "Online lead not updated ", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-197 CounselorContact updateOnlineLeadResponse "+e.toString(),Toast.LENGTH_SHORT).show();                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error == null || error.networkResponse == null)
                            return;
                        //get response body and parse with appropriate encoding
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")

                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);
    }else {
        dialog.dismiss();
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }else {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }
        catch (Exception e)
        {
            Toast.makeText(CounselorContactActivity.this,"Errorcode-196 CounselorContact updateOnlineLead "+e.toString(),Toast.LENGTH_SHORT).show();        }
    }
    public void updateOpenLeads(String sr)
    {
        try {
            url=clienturl + "?clientid=" + clientid + "&caseid=77&nSrNo=" + sr+"&CounselorID="+id1;
        Log.d("UpdateOpenLeadUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                           // Log.d("*******", response.toString());
                            try {
                                dialog.dismiss();
                                Log.d("updateOpenLeadRes", response);
                                if (response.contains("Row updated successfully")) {

                                    // getOnlineLeadRefno(sr_onlinelead);
                                    Toast.makeText(CounselorContactActivity.this, "Open lead updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CounselorContactActivity.this, "Open lead not updated ", Toast.LENGTH_SHORT).show();
                                }
                                }
                                catch (Exception e)
                                 {
                                     Toast.makeText(CounselorContactActivity.this,"Errorcode-196 CounselorContact openLeadUpdateResponse "+e.toString(),Toast.LENGTH_SHORT).show();                                 }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (error == null || error.networkResponse == null)
                                return;
                            //get response body and parse with appropriate encoding
                            if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                alertDialogBuilder.setTitle("Network issue!!!")

                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        }).show();
                                dialog.dismiss();
                                Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                                // showCustomPopupMenu();
                                Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                            }

                        }
                    });
            requestQueue.add(stringRequest);
        }else {
            dialog.dismiss();
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
    }catch (Exception e)
    {
        Toast.makeText(CounselorContactActivity.this,"Errorcode-195 CounselorContact updateOpenLead "+e.toString(),Toast.LENGTH_SHORT).show();
        Log.d("ExcUpdateOepnLLead", String.valueOf(e));
    }

    }

    public void getCounselorData(String url) {
        try {
            if(CheckServer.isServerReachable(CounselorContactActivity.this))
            {

        Log.d("CounselorUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this))
        {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("FetchedResponse", response);
                        try {
                            getCounselorDetails();
                            getPointCollection();
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Json", jsonObject.toString());

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("Length", String.valueOf(jsonArray.length()));
                            if(jsonArray.length()==0)
                            {
                                if(act1.contains("AdapterSearch"))
                                {
                                    intent=new Intent(CounselorContactActivity.this,SearchAllActivity.class);
                                    intent.putExtra("Activity",activityName);
                                    startActivity(intent);
                                }
                                else if(act1.contains("OnlineLead"))
                                {
                                    intent=new Intent(CounselorContactActivity.this,OnlineLead.class);
                                    intent.putExtra("Activity",activityName);
                                    startActivity(intent);
                                }
                                else {
                                    intent = new Intent(CounselorContactActivity.this, CounsellorData.class);
                                    intent.putExtra("Activity","CounselorData");
                                    startActivity(intent);
                                    //  startActivity(new Intent(CounselorContactActivity.this, CounsellorData.class));
                                }

                                Toast.makeText(CounselorContactActivity.this, "This candidate is allocated to someone else", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    //String id = String.valueOf(jsonObject1.getInt("cCounselorID"));
                                    datafrom = jsonObject1.getString("refNo");
                                    name = jsonObject1.getString("cCandidateName");
                                    course = jsonObject1.getString("cCourse");
                                    mbl = jsonObject1.getString("cMobile");
                                    adrs = jsonObject1.getString("cAddressLine");
                                    city = jsonObject1.getString("cCity");
                                    state1 = jsonObject1.getString("cState");
                                    pincode = jsonObject1.getString("cPinCode");
                                    parentno = jsonObject1.getString("cParantNo");
                                    email = jsonObject1.getString("cEmail");
                                    fetchedDataFrom = jsonObject1.getString("cDataFrom");
                                    fetchedAllocatedTo = jsonObject1.getString("AllocatedTo");
                                    allocatedDate = jsonObject1.getString("AllocationDate");
                                    statusid = jsonObject1.getString("CurrentStatus");
                                    remark = jsonObject1.getString("cRemarks");
                                    fetchedCreatedDate = jsonObject1.getString("dtCreatedDate");
                                    status11 = jsonObject1.getString("cStatus");
                                    country=jsonObject1.getString("cCountry");

                                    editor.putString("SelectedMobile", mbl);
                                    editor.putString("SelectedParentNo", parentno);
                                    editor.putString("SelectedName", name);
                                    editor.putString("SelectedCourse", course);
                                    // editor.putString("SelectedSrNo", dataCounselor.getSr_no());
                                    editor.putString("SelectedEmail", email);
                                    editor.putString("AllocatedDate", allocatedDate);
                                    editor.putString("SelectedAddress", adrs);
                                    editor.putString("SelectedCity", city);
                                    editor.putString("SelectedState", state1);
                                    editor.putString("SelectedPinCode", pincode);
                                    editor.putString("SelectedStatus", status11);
                                    editor.putString("SelectedStatusId", statusid);
                                    editor.putString("SelectedRemark", remark);
                                    editor.commit();
                                }

                                //  allocatedDate = allocatedDate.substring(allocatedDate.indexOf("date") + 6, allocatedDate.indexOf(","));
                                Log.d("Date!!!!", allocatedDate);
                                txtCName.setText(name);
                                txtSrno.setText(sr_no);
                                txtCourse.setText(course);
                                txtCurrentStatus.setText(status11);
                                txtRemark.setText(remark);
                                ArrayList<String> arrayListMbl = new ArrayList<>();
                                arrayListMbl.add(mbl);
                                arrayListMbl.add(parentno);
                                if(!country.equals(null))
                                {
                                    for(int i1=0;i1<arrayListCountry.size();i1++) {
                                        if(arrayListCountry.get(i1).contains(country)) {
                                            spinnerCountry.setSelection(i1);
                                        }
                                    }
                                }
                                if(!course.equals(null))
                                {
                                    for(int i1=0;i1<arrayListCourse.size();i1++) {
                                        if(arrayListCourse.get(i1).contains(course)) {
                                            spinnerCourse.setSelection(i1);
                                        }
                                    }
                                }


                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CounselorContactActivity.this, R.layout.spinner_item1, arrayListMbl);
                                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerMobile.setAdapter(arrayAdapter);
                                spinnerMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedmbl = spinnerMobile.getSelectedItem().toString();
                                        //selectedmbl = "+91" + selectedmbl;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        spinnerMobile.setSelection(0);
                                    }
                                });

                                //  dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);

                                getStatus1();
                            }


                        } catch (JSONException e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-199 CounselorContact counselorDataResponse "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);
    }else {
        dialog.dismiss();
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
        alertDialogBuilder.setTitle("No Internet connection!!!")
                .setMessage("Can't do further process")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).show();
    }
        }else {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-198 CounselorContact getCounselorDats "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcCounselorData", String.valueOf(e));
        }
    }
    public void getOpenLeadRefNo(String serialno) {
        try {
            if (CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=76&nSrNo=" + serialno;
                Log.d("OpenLeadRefNoUrl", url);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("OpenLeadRefNoResponse", response);
                                try {
                                    getCounselorDetails();
                                    getPointCollection();
                                    dialog.dismiss();
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("Json", jsonObject.toString());

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    Log.d("Length", String.valueOf(jsonArray.length()));
                                    if (jsonArray.length() == 0) {
                                        if (act1.contains("OnlineLead")) {
                                            startActivity(new Intent(CounselorContactActivity.this, OnlineLead.class));
                                        }else if(act1.contains("AdapterSearch"))
                                        {
                                            intent = new Intent(CounselorContactActivity.this, SearchAllActivity.class);
                                            intent.putExtra("Activity", "CounselorData");
                                            startActivity(intent);
                                        }
                                        else {
                                            intent = new Intent(CounselorContactActivity.this, CounsellorData.class);
                                            intent.putExtra("Activity", "CounselorData");
                                            startActivity(intent);
                                            //  startActivity(new Intent(CounselorContactActivity.this, CounsellorData.class));
                                            Toast.makeText(CounselorContactActivity.this, "This candidate is allocated to someone else", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                            //String id = String.valueOf(jsonObject1.getInt("cCounselorID"));
                                            datafrom = jsonObject1.getString("refNo");
                                            name = jsonObject1.getString("cCandidateName");
                                            course = jsonObject1.getString("cCourse");
                                            mbl = jsonObject1.getString("cMobile");
                                            adrs = jsonObject1.getString("cAddressLine");
                                            city = jsonObject1.getString("cCity");
                                            state1 = jsonObject1.getString("cState");
                                            pincode = jsonObject1.getString("cPinCode");
                                            parentno = jsonObject1.getString("cParantNo");
                                            email = jsonObject1.getString("cEmail");
                                            fetchedDataFrom = jsonObject1.getString("cDataFrom");
                                            fetchedAllocatedTo = jsonObject1.getString("AllocatedTo");
                                            allocatedDate = jsonObject1.getString("AllocationDate");
                                            statusid = jsonObject1.getString("CurrentStatus");
                                            remark = jsonObject1.getString("cRemarks");
                                            fetchedCreatedDate = jsonObject1.getString("dtCreatedDate");
                                            status11 = jsonObject1.getString("cStatus");
                                            country=jsonObject1.getString("cCountry");

                                            editor.putString("SelectedMobile", mbl);
                                            editor.putString("SelectedParentNo", parentno);
                                            editor.putString("SelectedName", name);
                                            editor.putString("SelectedCourse", course);
                                            // editor.putString("SelectedSrNo", dataCounselor.getSr_no());
                                            editor.putString("SelectedEmail", email);
                                            editor.putString("AllocatedDate", allocatedDate);
                                            editor.putString("SelectedAddress", adrs);
                                            editor.putString("SelectedCity", city);
                                            editor.putString("SelectedState", state1);
                                            editor.putString("SelectedPinCode", pincode);
                                            editor.putString("SelectedStatus", status11);
                                            editor.putString("SelectedStatusId", statusid);
                                            editor.putString("SelectedRemark", remark);
                                            editor.commit();
                                        }

                                        //  allocatedDate = allocatedDate.substring(allocatedDate.indexOf("date") + 6, allocatedDate.indexOf(","));
                                        Log.d("Date!!!!", allocatedDate);
                                        txtCName.setText(name);
                                        txtSrno.setText(sr_no);
                                        txtCourse.setText(course);
                                        txtCurrentStatus.setText(status11);
                                        txtRemark.setText(remark);
                                        ArrayList<String> arrayListMbl = new ArrayList<>();
                                        arrayListMbl.add(mbl);
                                        arrayListMbl.add(parentno);

                                        if(!country.equals(null))
                                        {
                                            for(int i1=0;i1<arrayListCountry.size();i1++) {
                                                if(arrayListCountry.get(i1).contains(country)) {
                                                    spinnerCountry.setSelection(i1);
                                                }
                                            }
                                        }
                                        if(!course.equals(null))
                                        {
                                            for(int i1=0;i1<arrayListCourse.size();i1++) {
                                                if(arrayListCourse.get(i1).contains(course)) {
                                                    spinnerCourse.setSelection(i1);
                                                }
                                            }
                                        }

                                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CounselorContactActivity.this, R.layout.spinner_item1, arrayListMbl);
                                        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinnerMobile.setAdapter(arrayAdapter);
                                        spinnerMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                selectedmbl = spinnerMobile.getSelectedItem().toString();
                                                //selectedmbl = "+91" + selectedmbl;
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {
                                                spinnerMobile.setSelection(0);
                                            }
                                        });
                                    }

                                    //  dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);

                                    getStatus1();

                                } catch (JSONException e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-201 CounselorContact openLeadRefNo " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")


                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }

                            }
                        });
                requestQueue.add(stringRequest);
            }else
            {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-200 CounselorContact getOpenLeadRefNo "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcOpenLeadRefNo", String.valueOf(e));
        }
    }

    public void getSrDetailsOnlineForm(String serialno) {
        try {
            if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=83&ID=" + serialno;
                Log.d("OpenLeadRefNoUrl", url);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("OFSrDetailsResponse", response);
                                try {
                                    arrayListOnlineForm = new ArrayList<>();
                                    getCounselorDetails();
                                    getPointCollection();
                                    dialog.dismiss();
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("Json", jsonObject.toString());

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    Log.d("Length", String.valueOf(jsonArray.length()));
                               /* if(jsonArray.length()==0)
                                {
                                    if(act1.contains("OnlineLead"))
                                    {
                                        startActivity(new Intent(CounselorContactActivity.this,OnlineLead.class));
                                    }
                                    else {
                                        intent = new Intent(CounselorContactActivity.this, CounsellorData.class);
                                        intent.putExtra("Activity","CounselorData");
                                        startActivity(intent);
                                        //  startActivity(new Intent(CounselorContactActivity.this, CounsellorData.class));
                                        Toast.makeText(CounselorContactActivity.this, "This candidate is allocated to someone else", Toast.LENGTH_SHORT).show();
                                    }
                                }*/
                                    //else {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        id = String.valueOf(jsonObject1.getInt("id"));
                                        formno = jsonObject1.getString("formno");
                                        firstname = jsonObject1.getString("firstname");
                                        lastname = jsonObject1.getString("lastname");
                                        fathername = jsonObject1.getString("fathername");
                                        mothername = jsonObject1.getString("mothername");
                                        dob = jsonObject1.getString("dob");
                                        passport = jsonObject1.getString("passport");
                                        mbl = jsonObject1.getString("mobile");
                                        addrs = jsonObject1.getString("address");
                                        city = jsonObject1.getString("city");
                                        state1 = jsonObject1.getString("state");
                                        pincode = jsonObject1.getString("pincode");
                                        email = jsonObject1.getString("email");
                                        paddresss = jsonObject1.getString("PermanentAddress");
                                        permanentcity = jsonObject1.getString("permanentcity");
                                        permanentstate = jsonObject1.getString("permanentstate");
                                        permanentpin = jsonObject1.getString("permanentpin");
                                        parentmobile = jsonObject1.getString("parentmobile");
                                        college = jsonObject1.getString("college");
                                        schoolboard = jsonObject1.getString("schoolboard");
                                        tenthpercentage = jsonObject1.getString("tenthpercentage");
                                        tenthyear = jsonObject1.getString("tenthyear");
                                        tenthmarks = jsonObject1.getString("tenthmarks");
                                        tenthoutof = jsonObject1.getString("tenthoutof");
                                        twelthpercentage = jsonObject1.getString("twelthpercentage");
                                        twelthyear = jsonObject1.getString("twelthyear");
                                        twelthmarks = jsonObject1.getString("twelthmarks");
                                        twelthoutof = jsonObject1.getString("twelthoutof");
                                        neetyear = jsonObject1.getString("neetyear");
                                        neetmarks = jsonObject1.getString("neetmarks");
                                        physics = jsonObject1.getString("physics");
                                        chemistry = jsonObject1.getString("chemistry");
                                        biology = jsonObject1.getString("biology");
                                        pcb = jsonObject1.getString("pcb");
                                        aggregate = jsonObject1.getString("aggregate");
                                        dtcreateddate = jsonObject1.getString("dtcreateddate");
                                        dtcreateddate = dtcreateddate.substring(9, dtcreateddate.lastIndexOf(" "));
                                        Log.d("Cdate", dtcreateddate);
                                        cWebsite = jsonObject1.getString("cWebsite");
                                        DataOnlineFormDetails dataOnlineFormDetails = new DataOnlineFormDetails(id, formno, firstname, lastname, fathername, mothername, dob, passport, addrs, mobile, city, state1, pincode, paddresss, permanentcity, permanentstate,
                                                permanentpin, parentmobile, email, college, schoolboard, tenthpercentage, tenthyear, tenthmarks, tenthoutof, twelthpercentage, twelthyear, twelthmarks, twelthoutof,
                                                neetmarks, neetyear, physics, chemistry, biology, pcb, aggregate, dtcreateddate, cWebsite);
                                        arrayListOnlineForm.add(dataOnlineFormDetails);
                                        editor.putString("SelectedMobile", mbl);
                                        editor.putString("SelectedName", firstname + " " + lastname);
                                        editor.commit();
                                    }
                                    adapterOnlineFormDetails = new AdapterOnlineFormDetails(CounselorContactActivity.this, arrayListOnlineForm);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(CounselorContactActivity.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    recyclerOnlineFormDetails.setLayoutManager(layoutManager);
                                    recyclerOnlineFormDetails.setAdapter(adapterOnlineFormDetails);
                                    //  allocatedDate = allocatedDate.substring(allocatedDate.indexOf("date") + 6, allocatedDate.indexOf(","));
                                    //     Log.d("Date!!!!", allocatedDate);
                                    txtCName.setText(firstname);
                                    txtSrno.setText(id);
                                    txtCourse.setText(cWebsite);
                                    //txtCurrentStatus.setText(status11);
                                    // txtRemark.setText(remark);
                                    ArrayList<String> arrayListMbl = new ArrayList<>();
                                    arrayListMbl.add(mbl);
                                    arrayListMbl.add(parentmobile);
                                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CounselorContactActivity.this, R.layout.spinner_item1, arrayListMbl);
                                    //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerMobile.setAdapter(arrayAdapter);
                                    spinnerMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            selectedmbl = spinnerMobile.getSelectedItem().toString();
                                            //selectedmbl = "+91" + selectedmbl;
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            spinnerMobile.setSelection(0);
                                        }
                                    });
                                } catch (JSONException e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-203 CounselorContact SrDetailsOnlineFormResponse " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }

                            }
                        });
                requestQueue.add(stringRequest);
            }else
            {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-202 CounselorContact getSrDetailsOnlineForm "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcSrDetails", String.valueOf(e));
        }

    }
/*public  void checkDatabaselist(String Srno,String CounselorID)
{
    urlRequest = UrlRequest.getObject();
    urlRequest.setContext(getApplicationContext());
    String url=clienturl+"?clientid=" + clientid + "&caseid=32&nSrNo="+serialno+"&cCounselorID="+cid;
    urlRequest.setUrl(url);
    Log.d("FetchedCounselorUrl", url);
}*/

    public void getStatus1() {
        try {
            if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=2";
                Log.d("StatusUrl", url);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("StatusResponse1", response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("Json", jsonObject.toString());
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String status1 = jsonObject1.getString("cStatus");
                                        statusid = jsonObject1.getString("nStatusID");
                                        Log.d("Status11", status1);
                                        //StatusInfo statusInfo=new StatusInfo(status1,statusid);
                                        arrayListStatusId.add(statusid);
                                        // Log.d("Json33333",statusInfo.toString());
                                        //arrayList.add(statusInfo);
                                        arrayList1.add(status1);
                                        // Log.d("Json11111",arrayList1.toString());
                                    }
                                    String setselected = status11;

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CounselorContactActivity.this, R.layout.spinner_item1, arrayList1);
                                    //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerStatus.setAdapter(arrayAdapter);

                                    for (int i = 0; i < arrayList1.size(); i++) {
                                        if (arrayList1.get(i).contains(status11)) {
                                            spinnerStatus.setSelection(i);
                                        }
                                    }
                                    if(activityName.contains("OLActivity"))
                                    {
                                        showCallAlert();
                                    }
                                   /* Log.d("Size**", String.valueOf(arrayList1.size()));
                                    if(activityName.equals("PhoneStateReceiver"))
                                    {
                                        showIncomingAlert();
                                    }*/
                                } catch (JSONException e) {
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-205 CounselorContact statusResponse " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")


                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }

                            }
                        });
                requestQueue.add(stringRequest);
            }else {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-204 CounselorContact getStatus1 "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcStatus", String.valueOf(e));
        }
    }

    public void updateStatus(int strStatus)
    {
        try {
            if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=5&nSrNo=" + sr_no + "&CurrentStatus=" + strStatus;
                Log.d("UStatusUrl", url);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    dialog.dismiss();
                                    updatedStatus = spinnerStatus.getSelectedItem().toString();
                                    Log.d("UStatus", updatedStatus);
                                    txtCurrentStatus.setText(updatedStatus);
                                    Log.d("UpdatedStatus", response);
                                    if (response.contains("Row updated successfully")) {
                                        // statusBackup();
                                        statusupdate=1;
                                        editor=sp.edit();
                                        editor.putInt("StatusUpdate",statusupdate);
                                        editor.commit();

                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                   /* txtRemark.setText(remarks);
                    edtRemarks.setText("");*/
                                        eventid = 6;
                                        // dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);

                                        insertPointCollection(eventid);
                    /*editor.putInt("UpdateStatus",updateStatus);
                    editor.putInt("Remark1",remark1);
                    editor.commit();*/
                                        if (updatedStatus.contains("CONFIRM SUCCESS")) {
                                            // dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);
                                            eventid = 7;
                                            insertPointCollection(eventid);
                       /* editor.putInt("ConfirmSuccess",confirmstatus);
                        editor.commit();*/
                                        }
                                        Toast.makeText(CounselorContactActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CounselorContactActivity.this, "Status not updated successfully", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Errorcode-207 CounselorContact updateStatusResponse " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                }

                            }
                        });
                requestQueue.add(stringRequest);
            }else {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-206 CounselorContact updateStatus "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcUpdateStatus", String.valueOf(e));
        }
    }

    public void statusBackup()
    {
        try {
            if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
                status1 = Integer.parseInt(arrayListStatusId.get(pos));
                // updatedStatus=spinnerStatus.getSelectedItem().toString();
                Log.d("updateStatus", String.valueOf(status1));
                url = clienturl + "?clientid=" + clientid + "&caseid=19&SrNo=" + sr_no + "&StatusId=" + status1 + "&CounsellorId=" + id1;
                Log.d("StatusBackupUr;", url);
                if (CheckInternet.checkInternet(CounselorContactActivity.this)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("*******", response.toString());
                                    try {
                                        //flag2 = 1;
                                        Log.d("StatusBackup", response);
                                        if (response.contains("Data inserted successfully")) {
                                            //code to update status
                                            updateStatus(status1);
                                            Toast.makeText(CounselorContactActivity.this, "Status inserted successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(CounselorContactActivity.this, "Status not inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(CounselorContactActivity.this, "Errorcode-209 CounselorContact statusBackupResponse " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                        alertDialogBuilder.setTitle("Network issue!!!")


                                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                                // The dialog is automatically dismissed when a dialog button is clicked.
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                        dialog.dismiss();
                                        Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                        // showCustomPopupMenu();
                                        Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                    }

                                }
                            });
                    requestQueue.add(stringRequest);
                } else {
                    dialog.dismiss();
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            }else {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-208 CounselorContact statusBackup "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcStatusBackup", String.valueOf(e));
        }
    }
    public void remarkBackup(String strSrNo, String strCounsellorId, final String strRemarks) {
        try{
            if(CheckServer.isServerReachable(CounselorContactActivity.this)) {
                url = clienturl + "?clientid=" + clientid + "&caseid=20&SrNo=" + strSrNo + "&sRemarks=" + strRemarks + "&CounsellorId=" + strCounsellorId;
                Log.d("RemarkBackupUrl", url);
                if (CheckInternet.checkInternet(CounselorContactActivity.this)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("RemarkBackup", response);
                                        if (response.contains("Data inserted successfully")) {
                   /* Intent intent = getIntent();
                    finish();
                    startActivity(intent);*/
                                            txtRemark.setText(strRemarks);
                                            edtRemarks.setText("");
                                            dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Updating remarks", true);
                                            newThreadInitilization(dialog);
                                            //code to update remark value to database
                                            updateRemarks(strRemarks);
                                            // refreshWhenLoading();
                                            Toast.makeText(CounselorContactActivity.this, "Remark inserted successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(CounselorContactActivity.this, "Remark not inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(CounselorContactActivity.this, "Errorcode-211 CounselorContact remarkBackupResponse " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                        alertDialogBuilder.setTitle("Network issue!!!")


                                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                                // The dialog is automatically dismissed when a dialog button is clicked.
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                        dialog.dismiss();
                                        Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                        // showCustomPopupMenu();
                                        Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                    }

                                }
                            });
                    requestQueue.add(stringRequest);
                } else {
                    dialog.dismiss();
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            }else {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-210 CounselorContact remarkBackup "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcRemarkBackup", String.valueOf(e));
        }
    }
    public void updateRemarks(String strRemark) {
        try{
            if(CheckServer.isServerReachable(CounselorContactActivity.this))
            {
        url=clienturl+"?clientid=" + clientid + "&caseid=44&SrNo=" + sr_no + "&sRemarks=" + strRemark;
        Log.d("URemarkUrl", url);
       if(CheckInternet.checkInternet(CounselorContactActivity.this))
       {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("*******", response.toString());
                        try {
                            dialog.dismiss();
                            txtRemark.setText(enteredRemark);
                            edtRemarks.setText("");
                            Log.d("RemarkUpdateResponse", response);
                            if (response.contains("Row updated successfully")) {
                                remarkupdate=1;
                                editor=sp.edit();
                                editor.putInt("RemarkUpdate",remarkupdate);
                                editor.commit();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                Toast.makeText(CounselorContactActivity.this, "Remark updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CounselorContactActivity.this, "Remark not updated", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-213 CounselorContact updateRemarkResponse "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);
       }else {
           dialog.dismiss();
           android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
       } }else
           {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-212 CounselorContact updateRemarks "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcUpdateRemark", String.valueOf(e));
        }

    }


    public void setReminder() {
        try{

        url=clienturl+"?clientid=" + clientid + "&caseid=26&nSrNo=" + sr_no +  "&cCounselorID=" + id1 +"&dtCallDateTime=" + date11 + "&cCallTime=" + time11 + "&nStatus=" + String.valueOf(status1) + "&cUpdatedBy=" + id1;
        Log.d("ReminderUrl", url);
       if(CheckInternet.checkInternet(CounselorContactActivity.this))
       {
           if(CheckServer.isServerReachable(CounselorContactActivity.this))
           {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("*******", response.toString());
                        try {
                            dialog.dismiss();
                            Log.d("ReminderResponse", response);
                            if (response.contains("Data inserted successfully"))
                            {
                                eventid=9;
                                // dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);

                                insertPointCollection(eventid);
                                Toast.makeText(CounselorContactActivity.this, "Reminder set successfully", Toast.LENGTH_SHORT).show();
                                strNotificatio="Reminder for "+sr_no+" at "+dateReminder+" "+txtTime.getText().toString();
                                //   setNotification(strNotificatio,sr_no,id1);
                                Calendar c = Calendar.getInstance();

                                // c.set(year, month, day);
                                //c.set(Calendar.HOUR_OF_DAY, 0);
                                // c.set(year,month,day,hour,minute);
                                String myFormat = "dd/MM/yyyy"; //Change as you need
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                                strSelectedDate=sdf.format(myCalendar.getTime());


                                String strTime = ( hour + ":" + minute);

                                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                                Date date2 = null;
                                try {
                                    date2 = sdfTime.parse(strTime);
                                    strSelectedTime = sdfTime.format(date2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Log.d("DateTime",strSelectedDate+" "+strSelectedTime);
                                int mon=Integer.parseInt(strSelectedDate.substring(3,5));
                                mon=mon-1;
                                c.set(Integer.parseInt(strSelectedDate.substring(6,10)),mon,
                                        Integer.parseInt(strSelectedDate.substring(0,2)),Integer.parseInt(strSelectedTime.substring(0,2)),
                                        Integer.parseInt(strSelectedTime.substring(3,5)));


                                Intent intent = new Intent(Intent.ACTION_INSERT)
                                        .setType("vnd.android.cursor.item/event")
                                        .putExtra(CalendarContract.Events.TITLE, strNotificatio)
                                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, c.getTimeInMillis());
                                //.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendarDateend.getTimeInMillis());
                                startActivity(intent);


                            } else {
                                Toast.makeText(CounselorContactActivity.this, "Reminder not set successfully", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-215 CounselorContact setReminderResponse "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);
       }else {
           dialog.dismiss();
           android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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

       }}else {
           if(dialog.isShowing()) {
               dialog.dismiss();
           }
           android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-214 CounselorContact setReminder "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcSetReminder", String.valueOf(e));
        }
    }
    public void setNotification(String strNotification,String strSrNo,String strCounselorID)
    {
        try{
        url=clienturl+"?clientid=" + clientid + "&caseid=62&SrNo=" + strSrNo +  "&CounselorId=" + strCounselorID +"&Notification=" + strNotification;
        Log.d("NotificationUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this))
        {
            if(CheckServer.isServerReachable(CounselorContactActivity.this))
            {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("*******", response.toString());
                        try {
                            dialog.dismiss();
                            Log.d("NotificationResponse", response);
                            if (response.contains("Data inserted successfully"))
                            {

                                Toast.makeText(CounselorContactActivity.this, "Notification set", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(CounselorContactActivity.this, "Noification not set", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-217 CounselorContact setNotification "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);
        }else {
            dialog.dismiss();
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        } }else {
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-216 CounselorContact setNotification "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcSetNotifjcation", String.valueOf(e));
        }

    }
    public void setReminder0ifAny() {
        try{
        url=clienturl+"?clientid=" + clientid + "&caseid=48&nSrNo=" + sr_no +  "&cCounselorID=" + id1;
        Log.d("ReminderUrl", url);
      if(CheckInternet.checkInternet(CounselorContactActivity.this))
      {
          if(CheckServer.isServerReachable(CounselorContactActivity.this)){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("*******", response.toString());
                        try {
                            dialog.dismiss();
                            Log.d("ReminderResponse", response);
                            if (response.contains("Row updated successfully"))
                            {
                                // eventid=9;
                                dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);
                                setReminder();

                            } else {
                                //  Toast.makeText(CounselorContactActivity.this, "Reminder not set successfully", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-219 CounselorContact setReminder0Response "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);
      }else {
          dialog.dismiss();
          android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
      }}else {
          if(dialog.isShowing()) {
              dialog.dismiss();
          }
          android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Log.d("ExcsetReminderIfAny", String.valueOf(e));
            Toast.makeText(CounselorContactActivity.this,"Errorcode-218 CounselorContact setReminder0IfAny "+e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    public void setSmsEntry()
    {
        urlRequest = UrlRequest.getObject();
        urlRequest.setContext(getApplicationContext());
        urlRequest.setUrl(clienturl+"?clientid="+ clientid+"&caseid=27&nSrNo=" + sr_no + "&cMobileNo=" + smbl + "&cCounselorID=" + id1 + "&cSms=" + msg);
        Log.d("SMSUrl", clienturl+"?clientid=" + clientid+"&caseid=27&nSrNo=" + sr_no + "&cMobileNo=" + smbl + "&cCounselorID=" + id1 + "&cSms=" + msg);
        urlRequest.getResponse(new ServerCallback() {
            @Override
            public void onSuccess(String response) throws JSONException {
                dialog.dismiss();
                Log.d("SMSResponse", response);
                if (response.contains("Data inserted successfully")) {

                    Toast.makeText(CounselorContactActivity.this, "message inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CounselorContactActivity.this, "message not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setReallocation() {
        try{
        url=clienturl+"?clientid=" + clientid+"&caseid=28&nSrNo=" + sr_no + "&nCurrentStatus=" + status1 + "&nPreAllocatedTo=" + id1 + "&nNewAllocatedTo=" + newAllocatedTo + "&dtPreAllocationDate=" + allocatedDate;
        Log.d("ReallocationUrl", url);
        if(CheckInternet.checkInternet(CounselorContactActivity.this))
        {
            if(CheckServer.isServerReachable(CounselorContactActivity.this)){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("*******", response.toString());
                        try {
                            dialog.dismiss();
                            Log.d("ReallocationResponse", response);
                            if (response.contains("Data inserted successfully"))
                            {
                                eventid=10;
                                // dialog = ProgressDialog.show(CounselorContactActivity.this, "", "Loading...", true);

                                insertPointCollection(eventid);
                                setReallocateDatabaselist();
                                // Toast.makeText(CounselorContactActivity.this,"Reallocation:"+String.valueOf(reallocation),Toast.LENGTH_SHORT).show();
                                Toast.makeText(CounselorContactActivity.this, "Reallocated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CounselorContactActivity.this, "Not Reallocated successfully", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-221 CounselorContact ReallocationResponse "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }
                    }
                });
        requestQueue.add(stringRequest);
        }else {
            dialog.dismiss();
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
        }}else {
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-220 CounselorContact setReallocation "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcSetReallocation", String.valueOf(e));
        }
    }

    public void setReallocateDatabaselist()
    {
        try{
        status1 = Integer.parseInt(arrayListStatusId.get(pos));
        Log.d("updateStatus", String.valueOf(status1));
        // status=status.replaceAll(" ","");
        remarks = edtRemarks.getText().toString();
        url=clienturl+"?clientid=" + clientid+"&caseid=29&nSrNo=" + sr_no + "&cCounselorID=" + newAllocatedTo;
        Log.d("SetReallocateUrl", url);
       if(CheckInternet.checkInternet(CounselorContactActivity.this))
       {
           if(CheckServer.isServerReachable(CounselorContactActivity.this))
           {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("*******", response.toString());
                        try {
                            dialog.dismiss();
                            Log.d("SetReallocationRes", response);
                            if (response.contains("Row updated successfully"))
                            {

                                // dialog=ProgressDialog.show(CounselorContactActivity.this,"","Getting counselor data",true);
                                String url=clienturl+"?clientid=" + clientid + "&caseid=32&nSrNo="+sr_no+"&cCounselorID="+id1;
                                getCounselorData(url);
                                // refreshWhenLoading();
                                linearReallocateTo.setVisibility(View.GONE);
                                txtSelecteCId.setText("");
                                txtSelectedCname.setText("");
                                txtSelctedSrNo.setText("");
                                strNotificatio=sr_no+" is reallocated by "+counselorname;
                                //dialog=ProgressDialog.show(CounselorContactActivity.this,"","Settting notification",true);
                                setNotification(strNotificatio,sr_no,newAllocatedTo);

                                Toast.makeText(CounselorContactActivity.this, "Databaselist updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CounselorContactActivity.this, "Databaselist not updated successfully", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CounselorContactActivity.this,"Errorcode-223 CounselorContact ReallocateDatabaselistResponse "+e.toString(),Toast.LENGTH_SHORT).show();
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
                        if (error.networkResponse != null||error instanceof TimeoutError ||error instanceof NoConnectionError ||error instanceof AuthFailureError ||error instanceof ServerError ||error instanceof NetworkError ||error instanceof ParseError) {
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                            alertDialogBuilder.setTitle("Network issue!!!")


                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).show();
                            dialog.dismiss();
                            Toast.makeText(CounselorContactActivity.this,"Network issue",Toast.LENGTH_SHORT).show();
                            // showCustomPopupMenu();
                            Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                        }

                    }
                });
        requestQueue.add(stringRequest);
       }else {
           dialog.dismiss();
           android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
           alertDialogBuilder.setTitle("No Internet connection!!!")
                   .setMessage("Can't do further process")

                   // Specifying a listener allows you to take an action before dismissing the dialog.
                   // The dialog is automatically dismissed when a dialog button is clicked.
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {

                           dialog.dismiss();

                       }
                   }).show();
       }}else
       {
           if(dialog.isShowing()) {
               dialog.dismiss();
           }
           android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Toast.makeText(CounselorContactActivity.this,"Errorcode-222 CounselorContact setReallocateDatabaselist "+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("ExcReallocatedDB", String.valueOf(e));
        }
    }
    public void insertCallInfo(String dur,String datetime)
    {
        try {
            sp=getSharedPreferences("Settings",Context.MODE_PRIVATE);
            path1 = sp.getString("RecordedFile", null);
            Log.d("RecPath1", path1);
            uploadFilePath = path1.substring(path1.indexOf("/storage"), path1.indexOf("CallRecording/") + 14);
            uploadFileName = path1.substring(path1.indexOf("CallRecording/") + 14);
          //  callDate = uploadFileName.substring(7, 17);

            //callDate="1_12_2018_";
            /*if (callDate.endsWith("_")) {
                callDate = callDate.substring(0, callDate.length() - 1);
            }
            Log.d("CallDate", callDate);*/
        }catch (Exception e) {
            Log.d("ExpPath",e.toString());
          //  Toast.makeText(CounselorContactActivity.this,"Exception in path"+e.toString(),Toast.LENGTH_SHORT).show();
        }
        try{
        String url = clienturl + "?clientid=" + clientid + "&caseid=6&nSrNo=" + sr_no + "&cFileName=" + uploadFileName + "&cMobileNo=" + mbl + "&cCallDuration=" + dur + "&cCallDate=" + datetime + "&nCounselorID=" + id1;
            Log.d("InsertCallUrl", url);
            if (CheckInternet.checkInternet(CounselorContactActivity.this)) {
                if(CheckServer.isServerReachable(CounselorContactActivity.this))
                {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("*******", response.toString());
                                try {
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                    Log.d("InsertedCallInfo", response);
                                    if (response.contains("Data inserted successfully")) {

                                        if (dsec > 0 && dsec <9) {
                                            Log.d("Entered", "Greaterthan20");
                                            eventid = 11;
                                            // dialog=ProgressDialog.show(CounselorContactActivity.this,"","Loading",true);
                                            insertPointCollection(eventid);
                                        }
                                        if (dsec >= 10)
                                        { eventid = 12;
                                            Log.d("Entered", "Greaterthan60");
                                            //dialog=ProgressDialog.show(CounselorContactActivity.this,"","Loading",true);
                                            insertPointCollection(eventid);
                                            Log.d("ActivityName",act1);
                                           /* if (act1.contains("OnlineLead")||act1.contains("RefreshOnlineLead")||act1.contains("ContactOnlineLead")) {
                                                Log.d("Entered", "Greaterthan60OnlineLead");

                                                //  dialog=ProgressDialog.show(CounselorContactActivity.this,"","Loading",true);
                                                insertOnlineLead(sr_no, id1);
                                            }
                                            else if(activityName.contains("OpenLeads")||activityName.contains("RefreshOpenLead")||activityName.contains("ContactOpenLead"))
                                                {
                                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                                    alertDialogBuilder.setTitle("Open lead allocated successfully")
                                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                                              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                   *//* Intent intent = new Intent(CounselorContactActivity.this, CounselorContactActivity.class);
                                                                    intent.putExtra("ActivityName", "OLActivity");
                                                                    editor.putString("SelectedSrNo", sr_onlinelead);
                                                                    editor.putString("ActivityContact", "OLActivity");
                                                                    editor.commit();
                                                                    startActivity(intent);*//*
                                                                    //getCounselorData(sr_onlinelead,id1);
                                                                    dialog.dismiss();
                                                                }
                                                            }).setCancelable(false)
                                                            .show();
                                                    Log.d("Entered", "Greaterthan60OpenLead");
                                                    updateOpenLeads(sr_no);
                                                }*/
                                        }
                                        Log.d("ActOnlineLeadName",activityName);
                                        alertDialog1.dismiss();
                                        if (!activityName.equals("OnlineLead")) {
                                            if(!activityName.contains("OpenLead"))
                                            {
                                                    Log.d("Entered", "RefreshWhenNotOnlineLead");
                                                    Intent intent = new Intent(CounselorContactActivity.this, CounselorContactActivity.class);
                                                    if (activityName.equals("OLActivity")) {
                                                    intent.putExtra("ActivityName", "OLA");
                                            } else if(activityName.contains("AdminReportDetails")) {
                                                        intent.putExtra("CID",cid1);
                                                        intent.putExtra("ActivityName","AdminReportDetails");
                                            }
                                            else
                                            {
                                                    intent.putExtra("ActivityName", activityName);
                                            }
                                                    startActivity(intent);

                                            }
                                        }

                                        try {
                                            if (path1 != null && !path1.isEmpty() && !path1.equals("null")) {
                                                callUploadFile();
                                            }
                                        }catch (Exception e) {
                                            Toast.makeText(CounselorContactActivity.this,"Exception while uploading file",Toast.LENGTH_SHORT).show();
                                        }
                                        //   linearReport.setVisibility(View.GONE);

                                        Toast.makeText(CounselorContactActivity.this, "Call info inserted successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CounselorContactActivity.this, "Call info not inserted", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(CounselorContactActivity.this,"Errorcode-225 CounselorContact insertCallInfoResponse "+e.toString(),Toast.LENGTH_SHORT).show();
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
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
                                    alertDialogBuilder.setTitle("Network issue!!!")


                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    dialog.dismiss();
                                    Toast.makeText(CounselorContactActivity.this, "Network issue", Toast.LENGTH_SHORT).show();
                                    // showCustomPopupMenu();
                                    Log.e("Volley", "Error.HTTP Status Code:" + error.networkResponse.statusCode);
                                } }
                        });
                requestQueue.add(stringRequest);
            } else {
                dialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            }}else {
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(CounselorContactActivity.this);
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
            Log.d("ExcInsertCallInfo",e.toString());
            Toast.makeText(CounselorContactActivity.this,"Errorcode-224 CounselorContact insertCallInfo "+e.toString(),Toast.LENGTH_SHORT).show();        }
    }


    public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.exists()) {
            dialog.dismiss();

            Log.d("uploadFileNotExist", "Source File not exist :"
                    + uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(CounselorContactActivity.this, "Source File not exist :"
                            + uploadFilePath + "" + uploadFileName, Toast.LENGTH_SHORT).show();
                }
            });

            return 0;
        } else {
            try {
               // dialog.dismiss();

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename=" + fileName + "" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);
                /*if(serverResponseCode==409)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SplashAfterCall.this, "File already exist.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }*/

                if (serverResponseCode == 200) {
                   /* if(dialog.isShowing()) {
                        dialog.dismiss();
                    }*/
                    runOnUiThread(new Runnable() {
                        public void run() {
                            editor=sp.edit();
                            editor.putString("RecordedFile","");
                            editor.commit();
                    Toast.makeText(CounselorContactActivity.this, "File Upload Complete.",
                            Toast.LENGTH_SHORT).show();

                    }
                    });
                    }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        // messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(CounselorContactActivity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Uploadfiletoserver", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        // messageText.setText("Got Exception : see logcat ");
                      //  Toast.makeText(CounselorContactActivity.this, "Got Exception : see logcat ",
                        //        Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("Exception", "Exception : "
                        + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }//end uploadfile
}
