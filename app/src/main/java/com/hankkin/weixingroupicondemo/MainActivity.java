package com.hankkin.weixingroupicondemo;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.hankkin.weixingroupicondemo.model.BitmapBean;
import com.hankkin.weixingroupicondemo.utils.BitmapUtils;
import com.hankkin.weixingroupicondemo.utils.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView iv1,iv2,iv3,iv4;
    private List<BitmapBean> bitmapBeans1 = new ArrayList<>();
    private List<BitmapBean> bitmapBeans2 = new ArrayList<>();
    private List<BitmapBean> bitmapBeans3 = new ArrayList<>();
    private List<BitmapBean> bitmapBeans4 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        
        initData();
    }


    /**
     * 初始化数据
     * by Hankkin at:2015-11-19 21:59:03
     */
    private void initData(){
        /*获取四个图片数组*/
        bitmapBeans1 = getBitmapEntitys(1);
        bitmapBeans2 = getBitmapEntitys(2);
        bitmapBeans3 = getBitmapEntitys(3);
        bitmapBeans4 = getBitmapEntitys(4);
        /*压缩bitmap*/
        Bitmap[] bitmaps1 = {
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                getResources(), R.drawable.j), (int) bitmapBeans1
                .get(0).getWidth(), (int) bitmapBeans1.get(0).getWidth())};
        Bitmap[] bitmaps2 = {
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                getResources(), R.drawable.j), (int) bitmapBeans2
                .get(0).getWidth(), (int) bitmapBeans2.get(0).getWidth()),
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                getResources(), R.drawable.j), (int) bitmapBeans2
                .get(0).getWidth(), (int) bitmapBeans2.get(0).getWidth())};
        Bitmap[] bitmaps3 = {
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                getResources(), R.drawable.j), (int) bitmapBeans3
                .get(0).getWidth(), (int) bitmapBeans3.get(0).getWidth()),
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                getResources(), R.drawable.j), (int) bitmapBeans3
                .get(0).getWidth(), (int) bitmapBeans3.get(0).getWidth()),
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                        getResources(), R.drawable.j), (int) bitmapBeans3
                        .get(0).getWidth(), (int) bitmapBeans3.get(0).getWidth())};
        Bitmap[] bitmaps4 = {
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                        getResources(), R.drawable.j), (int) bitmapBeans4
                        .get(0).getWidth(), (int) bitmapBeans4.get(0).getWidth()),
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                        getResources(), R.drawable.j), (int) bitmapBeans4
                        .get(0).getWidth(), (int) bitmapBeans4.get(0).getWidth()),
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                        getResources(), R.drawable.j), (int) bitmapBeans4
                        .get(0).getWidth(), (int) bitmapBeans4.get(0).getWidth()),
                ThumbnailUtils.extractThumbnail(BitmapUtils.getScaleBitmap(
                        getResources(), R.drawable.j), (int) bitmapBeans4
                        .get(0).getWidth(), (int) bitmapBeans4.get(0).getWidth())};

        /*获取组合后的图片显示*/
        Bitmap combineBitmap1 = BitmapUtils.getCombineBitmaps(bitmapBeans1,bitmaps1);
        iv1.setImageBitmap(combineBitmap1);

        Bitmap combineBitmap2 = BitmapUtils.getCombineBitmaps(bitmapBeans2,bitmaps2);
        iv2.setImageBitmap(combineBitmap2);

        Bitmap combineBitmap3 = BitmapUtils.getCombineBitmaps(bitmapBeans3,bitmaps3);
        iv3.setImageBitmap(combineBitmap3);

        Bitmap combineBitmap4 = BitmapUtils.getCombineBitmaps(bitmapBeans4,bitmaps4);
        iv4.setImageBitmap(combineBitmap4);
    }


    /**
     * 获取图片数组实体
     * by Hankkin at:2015-11-19 22:00:55
     * @param count
     * @return
     */
    private List<BitmapBean> getBitmapEntitys(int count) {
        List<BitmapBean> mList = new ArrayList<>();
        String value = PropertiesUtil.readData(this, String.valueOf(count),
                R.raw.data);
        String[] arr1 = value.split(";");
        int length = arr1.length;
        for (int i = 0; i < length; i++) {
            String content = arr1[i];
            String[] arr2 = content.split(",");
            BitmapBean entity = null;
            for (int j = 0; j < arr2.length; j++) {
                entity = new BitmapBean();
                entity.setX(Float.valueOf(arr2[0]));
                entity.setY(Float.valueOf(arr2[1]));
                entity.setWidth(Float.valueOf(arr2[2]));
                entity.setHeight(Float.valueOf(arr2[3]));
            }
            mList.add(entity);
        }
        return mList;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
