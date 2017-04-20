package com.tsaruka;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
/**
 *
 * @author Pradyumn Agrawal coderbond007
 */

public class UserFileHandling {
    public static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws java.lang.Exception{
        new UserFileHandling().run();
        out.close();
    }
    void run()
    {
    	
    }
    public int nextInt() throws IOException {
        return Integer.parseInt(in.readLine());
    }
}