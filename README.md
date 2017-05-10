# ImageEditor
仿QQ图片编辑器，根据手势在图片上画矩形，圆，箭头，铅笔，擦除功能</br>
1.矩形框</br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/0253F9CFE860517F25660C364DA22E04.jpg)</br>
2.圆</br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/E762357186E9CD1566E8E6BBC692812B.jpg)</br>
3.箭头</br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/58D93297EA25BDD5183C8F0D6C4F72CD.jpg)</br>
4.铅笔</br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/3019C33D656C73C8C151146F20C0EC52.jpg)</br>
5.文字输入</br>
![image](https://github.com/yangzhidan/ImageEditor/blob/master/resultPic/D376FC2237A9A297FB67505F420D3C41.jpg)</br>
6.可以撤回上一个绘制的path</br>

7.用法：</br>
  不居中直接new</br>
 surfce = new CustomSurfaceView(this, url, false);</br>
 传入不同参数实现绘制</br>
            case R.id.bianjia_0:
                surfce.setState(0);//绘制矩形
                break;
            case R.id.bianjia_1:
                surfce.setState(1);//绘制圆
                break;
            case R.id.bianjia_2:
                surfce.setState(2);//绘制箭头
                break;
            case R.id.bianjia_3:
                surfce.setState(3);//铅笔绘制
                break;
            case R.id.bianjia_4:
                surfce.setState(4);//输入文字
                break;
            case R.id.bianjia_5:
                surfce.revocation();//擦除上一步
                break;
            case R.id.bianjia_6:
                Bitmap bm = surfce.getBitmap();//获取最终的图片bitmap
