package com.example.dell.android.Application;

import com.example.dell.android.ImageLoader.PicassoImageLoader;
//import com.imnjh.imagepicker.PickerConfig;
//import com.imnjh.imagepicker.SImagePicker;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.youth.xframe.XFrame;

import org.litepal.LitePalApplication;

public class myApplication extends LitePalApplication {
    @Override
    public void onCreate()
    {
        super.onCreate();

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        XFrame.initXImageLoader(new GlideImageLoader(getApplicationContext()));
        XFrame.initXLog()//初始化XLog
                .setTag("Test")//设置全局tag
                .setShowThreadInfo(false)//是否开启线程信息显示，默认true
                .setDebug(false);//是否显示日志，默认true，发布时最好关闭

    }
}
