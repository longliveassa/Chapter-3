package com.example.chapter3.homework;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class Ch3Ex2Activity extends AppCompatActivity {

    private View target;
    private View startColorPicker;
    private View endColorPicker;
    private Button durationSelector;
    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3ex2);

        target = findViewById(R.id.target);
        startColorPicker = findViewById(R.id.start_color_picker);
        endColorPicker = findViewById(R.id.end_color_picker);
        durationSelector = findViewById(R.id.duration_selector);

        startColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(Ch3Ex2Activity.this);
                picker.setColor(getBackgroundColor(startColorPicker));
                picker.enableAutoClose();
                picker.setCallback(new ColorPickerCallback() {
//                    picker结束后的回调，设置要干啥
                    @Override
                    public void onColorChosen(int color) {
                        onStartColorChanged(color);
                    }
                });
                picker.show();
            }
        });

        endColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(Ch3Ex2Activity.this);
                picker.setColor(getBackgroundColor(endColorPicker));
                picker.enableAutoClose();
                picker.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        onEndColorChanged(color);
                    }
                });
                picker.show();
            }
        });

        durationSelector.setText(String.valueOf(1000));
        durationSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(Ch3Ex2Activity.this)
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(getString(R.string.duration_hint), durationSelector.getText(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                onDurationChanged(input.toString());
                            }
                        })
                        .show();
            }
        });
        resetTargetAnimation();
    }

    private void onStartColorChanged(int color) {
        startColorPicker.setBackgroundColor(color);
        resetTargetAnimation();
    }

    private void onEndColorChanged(int color) {
        endColorPicker.setBackgroundColor(color);
        resetTargetAnimation();
    }

    private void onDurationChanged(String input) {
        boolean isValid = true;
        try {
            int duration = Integer.parseInt(input);
            if (duration < 100 || duration > 10000) {
                isValid = false;
            }
        } catch (Throwable e) {
            isValid = false;
        }
        if (isValid) {
            durationSelector.setText(input);
            resetTargetAnimation();
        } else {
            Toast.makeText(Ch3Ex2Activity.this, R.string.invalid_duration, Toast.LENGTH_LONG).show();
        }
    }

    private int getBackgroundColor(View view) {
        Drawable bg = view.getBackground();
        if (bg instanceof ColorDrawable) {
            return ((ColorDrawable) bg).getColor();
        }
        return Color.WHITE;
    }

    private void resetTargetAnimation() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }

   // 创建 PropertyValuesHolder 对象，分别用于 scaleX 和 scaleY 动画
//PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
//PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 2f);
//
//// 创建单个 ObjectAnimator 使用两个 PropertyValuesHolders


        // 在这里实现了一个 ObjectAnimator，对 target 控件的背景色进行修改
        // 可以思考下，这里为什么要使用 ofArgb，而不是 ofInt 呢？
        ObjectAnimator animator1 = ObjectAnimator.ofArgb(target,
                "backgroundColor",
                getBackgroundColor(startColorPicker),
                getBackgroundColor(endColorPicker));

        // TODO ex2-1：实现另一个 ObjectAnimator，对 target 控件的大小进行缩放，从 1 到 2 循环
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(target, "scaleX",1f, 2f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(target, "scaleY",1f, 2f);
// TODO ex2-2：实现另一个 ObjectAnimator，对 target 控件的透明度进行修改，从 1 到 0.5f 循环
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(target, "alpha", 1f, 0.5f);

//        配置
        configureAnimator(animatorX);
        configureAnimator(animatorY);
        configureAnimator(animator1);
        configureAnimator(animator2);
// TODO ex2-3: 将上面创建的其他 ObjectAnimator 都添加到 AnimatorSet 中
        animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animator1);
        animatorSet.playTogether(animator1, animator2, animatorX,animatorY);
        animatorSet.start();
    }
    private void configureAnimator(ObjectAnimator animator) {
//        配置动画效果：INFINITE 并且 REVERSE，间隔时间为durationSelector的数
        animator.setDuration(Integer.parseInt(durationSelector.getText().toString()));
        animator.setRepeatCount(android.animation.ValueAnimator.INFINITE);
        animator.setRepeatMode(android.animation.ValueAnimator.REVERSE);
    }
}
