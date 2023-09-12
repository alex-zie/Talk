package com.alex.talk.utils;

import android.content.Context;

import com.alex.talk.model.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /**
     * @param n number associated with topic
     * @param context
     * @return List of questions of that topic
     * @throws IOException
     */
    public static List<Question> getQuestions(int n, Context context) throws IOException{
        String path = context.getExternalFilesDir(null).getPath() + File.separator;
        switch (n){
            case 0:
                path += "fragenLiebe.in";
                break;
            case 1:
                path += "fragenGefuehle.in";
                break;
            case 2:
                path += "fragenLeben.in";
                break;
            case 3:
                path += "fragenPhilo.in";
                break;
            case 4:
                path += "fragenPolitik.in";
                break;
            default:
                throw new RuntimeException("Invalid value!");
        }
        return readFile(path);
    }

    public static List<Question> readFile (String path) throws IOException {
        List<Question> questions = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        while ((str = br.readLine()) != null)
            questions.add(new Question(str));

        return questions;
    }

    public static void CopyFromAssetsToStorage(Context context, String SourceFile, String DestinationFile) throws IOException {
        InputStream IS = context.getAssets().open(SourceFile);
        OutputStream OS = new FileOutputStream(DestinationFile);
        CopyStream(IS, OS);
        OS.flush();
        OS.close();
        IS.close();
    }

    public static void CopyStream(InputStream Input, OutputStream Output) throws IOException { //Wozu ist das gut?
        byte[] buffer = new byte[5120];
        int length = Input.read(buffer);
        while (length > 0) {
            Output.write(buffer, 0, length);
            length = Input.read(buffer);
        }
    }
}
