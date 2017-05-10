package yangzhidan.imageeditor;

import android.app.Application;
import android.content.Context;

/**
 * Created by tederen on 2017/4/18.
 */

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }

    public static App getApplication() {
        return app;
    }
    public static Context getAppContext() {
        return app.getApplicationContext();
    }
}
