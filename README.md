# ImageEditor
仿QQ图片编辑器，根据手势在图片上画矩形，圆，箭头，铅笔，擦除功能</br>
新的项目中需要做一个类似QQ截图随手势编辑图片的功能</br></br>
1.绘制矩形框</br></br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/0253F9CFE860517F25660C364DA22E04.jpg)</br></br>
2.绘制圆</br></br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/E762357186E9CD1566E8E6BBC692812B.jpg)</br></br>
3.绘制箭头</br></br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/58D93297EA25BDD5183C8F0D6C4F72CD.jpg)</br></br>
4.铅笔画线</br></br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/3019C33D656C73C8C151146F20C0EC52.jpg)</br></br>
5.文字输入</br></br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/D376FC2237A9A297FB67505F420D3C41.jpg)</br></br>
6.可以撤回上一个绘制的path</br></br>

7.用法：</br>
</br>
  代码中直接new</br>
 surfce = new CustomSurfaceView(this, url, false);</br>
 </br>
 传入不同参数实现绘制</br>
 </br>
case R.id.bianjia_0:</br>
    surfce.setState(0);//绘制矩形</br>
    break;</br>
case R.id.bianjia_1:</br>
    surfce.setState(1);//绘制圆</br>
    break;</br>
case R.id.bianjia_2:</br>
    surfce.setState(2);//绘制箭头:</br>
    break;</br>
case R.id.bianjia_3:</br>
    surfce.setState(3);//铅笔绘制</br>
    break;</br>
case R.id.bianjia_4:</br>
    surfce.setState(4);//输入文字</br>
    break;</br>
case R.id.bianjia_5:</br>
    surfce.revocation();//擦除上一步</br>
    break;</br>
case R.id.bianjia_6:</br>
    Bitmap bm = surfce.getBitmap();//获取最终的图片bitmap</br>
    break;</br>
