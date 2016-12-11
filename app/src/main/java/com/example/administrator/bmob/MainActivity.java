package com.example.administrator.bmob;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

        import cn.bmob.v3.Bmob;
        import cn.bmob.v3.BmobBatch;
        import cn.bmob.v3.BmobObject;
        import cn.bmob.v3.BmobQuery;
        import cn.bmob.v3.datatype.BatchResult;
        import cn.bmob.v3.exception.BmobException;
        import cn.bmob.v3.listener.FindListener;
        import cn.bmob.v3.listener.QueryListListener;
        import cn.bmob.v3.listener.QueryListener;
        import cn.bmob.v3.listener.SaveListener;
        import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {
    private Button btinto;
    private Button btget;
    private Button btchange;
    private Button btdes;
    private Button btmoreinto;
    private Button btmorechange;
    private Button btmoredes;
    private Button btgetmore;
    private Button bttiaojian;
    private String obid[] = new String[50];
    String id1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "6896e16e217c1c52472beaccfdf22827");
        btinto = (Button) findViewById(R.id.buttonstart);
        btget = (Button) findViewById(R.id.buttonget);
        btchange = (Button) findViewById(R.id.buttonchange);
        btdes= (Button) findViewById(R.id.buttondes);
        btmoreinto= (Button) findViewById(R.id.btmoreinto);
        btmorechange= (Button) findViewById(R.id.btmorechange);
        btmoredes= (Button) findViewById(R.id.btmoredes);
        btgetmore = (Button) findViewById(R.id.btgetmore);
        bttiaojian = (Button) findViewById(R.id.bttiaojian);
        //插入数据
        btinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student s = new Student();
                s.setName("张三");
                s.setAge(18);
                s.setAddress("北京");
                s.setBelongclass(1503);
                s.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Log.i("插入数据", "数据插入成功");
                            id1=s;
                        } else {
                            Log.i("插入数据", "数据插入失败");
                        }
                    }
                });
            }
        });

        //得到数据
        btget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Student> change = new BmobQuery<Student>();
                change.getObject(id1, new QueryListener<Student>() {
                    @Override
                    public void done(Student student, BmobException e) {
                        if (e == null) {
                            Log.i("得到数据", "数据查找成功"+" name: "+student.getName()+" age: "+student.getAge()+
                                    " address: "+student.getAddress()+" class: "+student.getBelongclass());
                        } else {
                            Log.i("得到数据", "数据查找失败");
                        }
                    }
                });
            }
        });

        //修改
        btchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student s = new Student();
                s.setObjectId(id1);
                s.setName("李四");
                s.setBelongclass(1000);
                s.setAge(19);
                s.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("修改数据", "数据修改成功");
                        } else {
                            Log.i("修改数据", "数据修改失败");
                        }
                    }
                });
            }
        });

        //删除数据
        btdes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student s = new Student();
                s.setObjectId(id1);
                s.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("删除数据", "删除数据成功");
                        } else {
                            Log.i("删除数据", "删除数据失败");
                        }
                    }
                });
            }
        });

        //批量增加数据
        btmoreinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BmobObject> students = new ArrayList<BmobObject>();
                for(int i=0;i<50;i++){
                    Student s = new Student("张三",i,"北京",i);
                    students.add(s);
                }
                new BmobBatch().insertBatch(students).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {
                        if (e == null) {
                            for (int i = 0; i < 50; i++) {
                                BatchResult s = list.get(i);
                                BmobException o = s.getError();
                                if (o == null) {
                                    Log.i("批量增加数据", "第" + i + "个数据增加成功");
                                    obid[i] = s.getObjectId();
                                } else {
                                    Log.i("批量增加数据", "第" + i + "个数据增加失败");
                                }
                            }
                        } else {
                            Log.i("批量增加数据", "数据增加失败"+e.getMessage()+"   "+e.getErrorCode());
                        }
                    }
                });
            }
        });

        //批量更新数据
        btmorechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BmobObject> students = new ArrayList<BmobObject>();
                for(int i=0,j=0;i<5;i++){
                    Student s = new Student();
                    s.setObjectId(obid[j]);
                    s.setName("李四");
                    s.setAddress("上海");
                    s.setAge(19);
                    s.setBelongclass(1000);
                    students.add(s);
                    j+=5;
                }
                new BmobBatch().updateBatch(students).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {
                        if (e == null) {
                            for (int i = 0; i < 5; i++) {
                                BatchResult r = list.get(i);
                                BmobException e1 = r.getError();
                                if (e1 == null) {
                                    Log.i("批量更新数据", "第" + i + "个数据更新成功");
                                } else {
                                    Log.i("批量更新数据", "第" + i + "个数据更新失败");
                                }
                            }
                        } else {
                            Log.i("批量更新数据", "数据更新失败"+e.getMessage()+"   "+e.getErrorCode());
                        }
                    }
                });
            }
        });

        //批量删除数据
        btmoredes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BmobObject> students = new ArrayList<BmobObject>();
                for(int i=0,j=49;i<5;i++) {
                    Student s = new Student();
                    s.setObjectId(obid[j]);
                    j-=5;
                    students.add(s);
                }
                new BmobBatch().deleteBatch(students).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                        if (e == null) {
                            for(int q=0;q<5;q++){
                                BatchResult t = list.get(q);
                                BmobException e1 = t.getError();
                                if (e1 == null) {
                                    Log.i("批量删除数据", "第" + q + "个数据删除成功");
                                } else {
                                    Log.i("批量删除数据", "第" + q + "个数据更新失败");
                                }
                            }
                        } else {

                            Log.i("批量删除数据", "数据删除失败"+e.getMessage()+"   "+e.getErrorCode());
                        }
                    }
                });
            }
        });

        //批量查找数据
        btgetmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Student> query = new BmobQuery<Student>();
                query.addWhereEqualTo("name", "李四");//在数据中查找到name为李四的所有数据信息
                query.setLimit(50);//设置查找的条数，默认为10
                query.setLimit(50);
                query.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> list, BmobException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "数据查询成功" + list.size() + "个", Toast.LENGTH_LONG).show();
                            for (Student s : list) {
                                Log.i("查询数据", "age: " + s.getAge() + " class: " + s.getBelongclass() + " address: " + s.getAddress());
                            }
                        } else {
                            Log.i("批量查找数据", "数据查找失败"+e.getMessage()+"   "+e.getErrorCode());
                        }
                    }
                });
            }
        });

        //符合多重条件的查询
        bttiaojian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Student> query = new BmobQuery<Student>();
                query.addWhereNotEqualTo("name", "张三");//查找姓名不是张三的
                query.addWhereGreaterThan("age", 20);//查找年龄在20岁以上的
                query.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> list, BmobException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "数据查询成功"+list.size()+"个", Toast.LENGTH_LONG).show();
                            for (Student s : list) {
                                Log.i("查询数据", "姓名：" + s.getName() + " age: " + s.getAge() + " class: " + s.getBelongclass() + " address: " + s.getAddress());
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "数据查询失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
