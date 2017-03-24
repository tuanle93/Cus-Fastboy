package net.fastboy.cus.cusfastboy;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by FastboyAdmin on 17/03/2017.
 */

public class MyCache {
    public static String readAllCachedText(Context context, String filename) {
        File file = new File(context.getCacheDir(), filename);
        return readAllText(file);
    }

    public static String readAllResourceText(Context context, int resourceId) {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        return readAllText(inputStream);
    }

    public static String readAllFileText(String file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return readAllText(inputStream);
        } catch(Exception ex) {
            return null;
        }
    }

    public static String readAllText(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return readAllText(inputStream);
        } catch(Exception ex) {
            return null;
        }
    }

    public static String readAllText(InputStream inputStream) {
        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);

        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }

    public static boolean writeAllCachedText(Context context, String filename, String text) {
        File file = new File(context.getCacheDir(), filename);
        return writeAllText(file, text);
    }

    public static boolean writeAllFileText(String filename, String text) {
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            return writeAllText(outputStream, text);
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean writeAllText(File file, String text) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            return writeAllText(outputStream, text);
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean writeAllText(OutputStream outputStream, String text) {
        OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputWriter);
        boolean success = false;

        try {
            bufferedWriter.write(text);
            success = true;
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return success;
    }
    public static boolean assetExists(Context context, String path) {
        boolean bAssetOk = false;
        try {
            InputStream stream = context.getAssets().open(path);
            stream.close();
            bAssetOk = true;
        } catch (FileNotFoundException e) {
            Log.w("IOUtilities", "assetExists failed: "+e.toString());
        } catch (IOException e) {
            Log.w("IOUtilities", "assetExists failed: "+e.toString());
        }
        return bAssetOk;
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    Log.i("Delete cache","success");
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}