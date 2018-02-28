package com.example.ormlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.Date;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    private StringBuffer contentBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        contentBuffer = new StringBuffer();
        initData();
        initView();
    }

    // 初始化数据
    private void initData() {
        // 添加用户数据
        UserBean userData = new UserBean("张三", '1', new Date(), "北京");
        new UserDao(MainActivity.this).insert(userData);
        // 添加文章数据
        ArticleBean articleData = new ArticleBean("标题", "内容内容内容内容内容内容", userData);
        new ArticleDao(MainActivity.this).insert(articleData);
    }

    // 初始化视图
    private void initView() {
        // 从数据库中根据ID取出文章信息
        ArticleBean articleBean = new ArticleDao(MainActivity.this).queryById(1);
        contentBuffer.append(articleBean.toString());
        // 根据取出的用户id查询用户信息
        UserBean userBean = new UserDao(MainActivity.this).queryById(articleBean.getUser().getId());
        contentBuffer.append("\n\n" + userBean.toString());
        // 从用户信息中取出关联的文章列表信息
        ForeignCollection<ArticleBean> articles = userBean.getArticles();
        Iterator<ArticleBean> iterator = articles.iterator();
        contentBuffer.append("\n\n");
        while (iterator.hasNext()) {
            ArticleBean article = iterator.next();
            contentBuffer.append(article.toString() + "\n");
        }
        // 设置TextView的文本
        tv.setText(contentBuffer.toString());
    }
}
