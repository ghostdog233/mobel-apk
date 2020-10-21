package com.spicytomato.helloworld.cacluate;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spicytomato.helloworld.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class calculator_Activity extends AppCompatActivity {
    private static final int TOBINARY = R.id.toBinary;
    final int ONE = R.id.one_button;//后面的数为一个整性变量
    final int TWO = R.id.two_button;
    final int THREE = R.id.three_button;
    final int FOUR = R.id.four_button;
    final int FIVE = R.id.five_button;
    final int SIX = R.id.six_button;
    final int SEVEN = R.id.seven_button;
    final int EIGHT = R.id.eight_button;
    final int NINE = R.id.nine_button;
    final int ZERO = R.id.zero_button;
    final int POINT = R.id.point_button;
    final int AC = R.id.AC_button;
    final int RESULT = R.id.result_button;
    final int POSITIVE = R.id.positive_button;
    final int ADD = R.id.add_button;
    final int SUBTRACT = R.id.subtract_button;
    final int DIVIDE = R.id.divide_button;
    final int MULTIPLY = R.id.multiply_button;
    final int REMAINDER = R.id.remainder_button;
    final int SIN = R.id.sin;
    final int COS = R.id.cos;
    final int GEN = R.id.gen;
    final int FACTORIAL = R.id.factorial;

    private Button mButtonRemainder;
    private Button mButtonDivide;
    private Button mButtonAdd;
    private Button mButtonMultiply;
    private Button mButtonSubtract;
    private Button mButtonResult;
    private Button mButtonOne;
    private Button mButtonTwo;
    private Button mButtonThree;
    private Button mButtonFour;
    private Button mButtonFive;
    private Button mButtonSix;
    private Button mButtonSeven;
    private Button mButtonEight;
    private Button mButtonNine;
    private TextView mTextViewResult;
    private Button mButtonAC;
    private Button mButtonPositive;
    private List<BigDecimal> mDoubleList;
    private int mLastCalculate;
    private boolean mIsClear = true;
    private Button mButtonZero;
    private Button mButtonPoint;
    private Boolean mIsAC;
    private int mNowCalculate;
    private Button mButtonSin;
    private Button mButtonCos;
    private Button mButtonGen;
    private Button mButtonFactorial;
    private Button mButtonToBinary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//调用父类Activity的onCreate()方法
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.calculator_activity);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayout gridLayout = findViewById(R.id.gridlayout);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            int column = gridLayout.getColumnCount();
            int row = gridLayout.getRowCount();
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                if (gridLayout.getChildAt(i).getId() != R.id.result_textview) {
                    Button button = (Button) gridLayout.getChildAt(i);
                    button.setWidth(screenWidth / column);
                    button.setHeight(screenHeight / row);
                } else {
                    TextView textView = (TextView) gridLayout.getChildAt(i);
                    textView.setWidth(screenWidth / column);
                    textView.setHeight(screenHeight / row);
                }
            }
        }
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    public void initView(){
        mButtonAC = findViewById(R.id.AC_button);//绑定按钮
        mButtonRemainder = findViewById(R.id.remainder_button);
        mButtonDivide = findViewById(R.id.divide_button);
        mButtonAdd = findViewById(R.id.add_button);
        mButtonMultiply = findViewById(R.id.multiply_button);
        mButtonSubtract = findViewById(R.id.subtract_button);
        mButtonResult = findViewById(R.id.result_button);
        mButtonPositive = findViewById(R.id.positive_button);
        mButtonOne = findViewById(R.id.one_button);
        mButtonTwo = findViewById(R.id.two_button);
        mButtonThree = findViewById(R.id.three_button);
        mButtonFour = findViewById(R.id.four_button);
        mButtonFive = findViewById(R.id.five_button);
        mButtonSix = findViewById(R.id.six_button);
        mButtonSeven = findViewById(R.id.seven_button);
        mButtonEight = findViewById(R.id.eight_button);
        mButtonNine = findViewById(R.id.nine_button);
        mButtonZero = findViewById(R.id.zero_button);
        mButtonPoint = findViewById(R.id.point_button);
        mTextViewResult = findViewById(R.id.result_textview);
        mDoubleList = new ArrayList<>();


        OperateListener operateListener = new OperateListener();
        NumberListener numberListener = new NumberListener();

        mButtonAC.setOnClickListener(operateListener);//匿名内部类
        mButtonRemainder.setOnClickListener(operateListener);
        mButtonDivide.setOnClickListener(operateListener);
        mButtonAdd.setOnClickListener(operateListener);
        mButtonMultiply.setOnClickListener(operateListener);
        mButtonSubtract.setOnClickListener(operateListener);
        mButtonResult.setOnClickListener(operateListener);
        mButtonRemainder.setOnClickListener(operateListener);

        mButtonPositive.setOnClickListener(numberListener);

        mButtonOne.setOnClickListener(numberListener);
        mButtonTwo.setOnClickListener(numberListener);
        mButtonThree.setOnClickListener(numberListener);
        mButtonFour.setOnClickListener(numberListener);
        mButtonFive.setOnClickListener(numberListener);
        mButtonSix.setOnClickListener(numberListener);
        mButtonSeven.setOnClickListener(numberListener);
        mButtonEight.setOnClickListener(numberListener);
        mButtonNine.setOnClickListener(numberListener);
        mButtonZero.setOnClickListener(numberListener);
        mButtonPoint.setOnClickListener(numberListener);


        mDoubleList.add(0, new BigDecimal("0"));

        mTextViewResult.setText("0");
        mTextViewResult.setEnabled(false);//设置这个控件不可用
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("help")
                    .setMessage("this is a calculator")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create().show();
            return true;
        }else if (item.getItemId() == R.id.exit){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出")
                    .setMessage("是否要退出")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.calculator_activity);


        initView();

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayout gridLayout = findViewById(R.id.gridlayout);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            int column = gridLayout.getColumnCount();
            int row = gridLayout.getRowCount();
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                if (gridLayout.getChildAt(i).getId() != R.id.result_textview) {
                    Button button = (Button) gridLayout.getChildAt(i);
                    button.setWidth(screenWidth / column);
                    button.setHeight(screenHeight / row);
                } else {
                    TextView textView = (TextView) gridLayout.getChildAt(i);
                    textView.setWidth(screenWidth / column);
                    textView.setHeight(screenHeight / row);
                }
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mButtonSin = findViewById(R.id.sin);
            mButtonCos = findViewById(R.id.cos);
            mButtonGen = findViewById(R.id.gen);
            mButtonFactorial = findViewById(R.id.factorial);
            mButtonToBinary = findViewById(R.id.toBinary);
            OperateListener operateListener = new OperateListener();
            mButtonSin.setOnClickListener(operateListener);
            mButtonCos.setOnClickListener(operateListener);
            mButtonGen.setOnClickListener(operateListener);
            mButtonFactorial.setOnClickListener(operateListener);
            mButtonToBinary.setOnClickListener(operateListener);
        }

    }

    private class NumberListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case ONE:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "1");
                    else {
                        mTextViewResult.setText("1");
                        mIsClear = false;
                    }
                    break;
                case TWO:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "2");
                    else {
                        mTextViewResult.setText("2");
                        mIsClear = false;
                    }
                    break;
                case THREE:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "3");
                    else {
                        mTextViewResult.setText("3");
                        mIsClear = false;
                    }
                    break;
                case FOUR:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "4");
                    else {
                        mTextViewResult.setText("4");
                        mIsClear = false;
                    }
                    break;
                case FIVE:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "5");
                    else {
                        mTextViewResult.setText("5");
                        mIsClear = false;
                    }
                    break;
                case SIX:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "6");
                    else {
                        mTextViewResult.setText("6");
                        mIsClear = false;
                    }
                    break;
                case SEVEN:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "7");
                    else {
                        mTextViewResult.setText("7");
                        mIsClear = false;
                    }
                    break;
                case EIGHT:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "8");
                    else {
                        mTextViewResult.setText("8");
                        mIsClear = false;
                    }
                    break;
                case NINE:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "9");
                    else {
                        mTextViewResult.setText("9");
                        mIsClear = false;
                    }
                    break;
                case ZERO:
                    if (!mIsClear)
                        mTextViewResult.setText(mTextViewResult.getText() + "0");
                    else {
                        mTextViewResult.setText("0");
                        mIsClear = false;
                    }
                    break;
                case POSITIVE:
                    String old = mTextViewResult.getText().toString();
                    if (old.charAt(0) == '-') {
                        String newS = (String) old.subSequence(1, mTextViewResult.length());
                        mTextViewResult.setText(newS);
                    } else {
                        mTextViewResult.setText("-" + old);
                    }
                    break;
                case POINT:
                    if (!mTextViewResult.getText().toString().matches("[0-9]{1,}.[0-9]*+")) {
                        Log.d("mTextViewResult", "ok");
                        mTextViewResult.setText(mTextViewResult.getText().toString() + ".");
                        mIsClear = false;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private class OperateListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mTextViewResult.getText().toString() != null && mTextViewResult.getText().toString().length() != 0) {
                String content = mTextViewResult.getText().toString();
                BigDecimal num = new BigDecimal(content);
                switch (v.getId()) {
                    case ADD:
                        mNowCalculate = 0;
                        mDoubleList.add(0, num);
                        mIsClear = true;
                        break;
                    case SUBTRACT:
                        mNowCalculate = 1;
                        mDoubleList.add(0, num);
                        mIsClear = true;
                        break;
                    case MULTIPLY:
                        mNowCalculate = 2;
                        mDoubleList.add(0, num);
                        mIsClear = true;
                        break;
                    case DIVIDE:
                        mNowCalculate = 3;
                        mDoubleList.add(0, num);
                        mIsClear = true;
                        break;
                    case REMAINDER:
                        mNowCalculate = 6;
                        mDoubleList.add(0, num);
                        mIsClear = true;
                        break;
                    case AC:
                        getResult(5, 5);
                        mIsClear = true;
                        break;
                    case RESULT:
                        getResult(mLastCalculate, mNowCalculate);
                        mNowCalculate = 0;
                        break;
                    case SIN:
                        mTextViewResult.setText(String.valueOf(Math.sin(Double.parseDouble(content))));
                        mIsClear = true;
                        break;
                    case COS:
                        mTextViewResult.setText(String.valueOf(Math.cos(Double.parseDouble(content))));
                        mIsClear = true;
                        break;
                    case GEN:
                        mTextViewResult.setText(String.valueOf(Math.sqrt(Double.parseDouble(content))));
                        mIsClear = true;
                        break;
                    case FACTORIAL:
                        int result = 1;
                        for (int i = 1; i <= Double.parseDouble(content); i++) {
                            result = i * result;
                        }
                        mTextViewResult.setText(String.valueOf(result));
                        mIsClear = true;
                        break;
                    case  TOBINARY:
                        mTextViewResult.setText(Integer.toBinaryString(Integer.parseInt(content)));
                        break;
                    default:
                        break;

                }
            }
        }
    }


    private void getResult(int lastCalculate, int nowCalculate) {
        if (mTextViewResult.getText().toString() != null && mTextViewResult.getText().toString().length() != 0) {
            String content = mTextViewResult.getText().toString();
            BigDecimal num = new BigDecimal(content);
            switch (nowCalculate) {
                case 0:
                    BigDecimal bigDecimal = mDoubleList.get(0).add(num);
                    mTextViewResult.setText(bigDecimal.toString());
                    mDoubleList.clear();
                    mDoubleList.add(0, bigDecimal);
                    break;
                case 1:
                    BigDecimal bigDecimal1 = mDoubleList.get(0).subtract(num);
                    mTextViewResult.setText(bigDecimal1.toString());
                    mDoubleList.clear();
                    mDoubleList.add(0, bigDecimal1);
                    break;
                case 2:
                    BigDecimal bigDecimal2 = mDoubleList.get(0).multiply(num);
                    mTextViewResult.setText(bigDecimal2.toString());
                    mDoubleList.clear();
                    mDoubleList.add(0, bigDecimal2);
                    break;
                case 3:
                    if (!num.multiply(new BigDecimal("8")).toString().equals(new String("0"))) {
                        BigDecimal bigDecimal3 = mDoubleList.get(0).divide(num);
                        mTextViewResult.setText(bigDecimal3.toString());
                        mDoubleList.clear();
                        mDoubleList.add(0, bigDecimal3);
                    } else {
                        Toast.makeText(this, "被除数不能为0", Toast.LENGTH_LONG).show();
                        mDoubleList.clear();
                        mDoubleList.add(0, num);
                    }

                    break;
                case 4:
                    mTextViewResult.setText(mDoubleList.get(0).toString());
                    break;
                case 5:
                    mDoubleList.clear();
                    mDoubleList.add(0, new BigDecimal("0"));
                    mTextViewResult.setText("0");
                    mIsAC = true;
                    break;
                case 6:
                    BigDecimal bigDecimal4 = mDoubleList.get(0).remainder(num);
                    mTextViewResult.setText(bigDecimal4.toString());
                    mDoubleList.clear();
                    mDoubleList.add(0, bigDecimal4);
                    break;
                default:
                    break;
            }
        }
        mIsClear = true;
    }
}
