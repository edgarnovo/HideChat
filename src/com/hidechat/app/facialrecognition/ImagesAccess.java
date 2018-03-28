package com.hidechat.app.facialrecognition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.util.Log;

public class ImagesAccess {

	private String mPath;
	private ArrayList<Image> theList = new ArrayList<Image>();

	public ImagesAccess(String mPath) {
		this.mPath = mPath;
	}

	public boolean isEmpty() {
		return !(theList.size() > 0);
	}

	public void add(String s, int n) {
		theList.add(new Image(s, n));
	}

	public String get(int i) {
		Iterator<Image> Ilabel = theList.iterator();
		while (Ilabel.hasNext()) {
			Image l = Ilabel.next();
			if (l.getTheNum() == i)
				return l.getTheLabel();
		}
		return "";
	}

	public int get(String s) {
		Iterator<Image> Ilabel = theList.iterator();
		while (Ilabel.hasNext()) {
			Image l = Ilabel.next();
			if (l.getTheLabel().equalsIgnoreCase(s))
				return l.getTheNum();
		}
		return -1;
	}

	public void Save() {
		try {
			File f = new File(mPath + "faces.txt");
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			Iterator<Image> Ilabel = theList.iterator();
			while (Ilabel.hasNext()) {
				Image l = Ilabel.next();
				bw.write(l.getTheLabel() + "," + l.getTheNum());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("error", e.getMessage() + " " + e.getCause());
			e.printStackTrace();
		}

	}

	public void Read() {
		try {

			FileInputStream fstream = new FileInputStream(mPath + "faces.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream));

			String strLine;
			theList = new ArrayList<Image>();
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(strLine, ",");
				String s = tokens.nextToken();
				String sn = tokens.nextToken();

				theList.add(new Image(s, Integer.parseInt(sn)));
			}
			br.close();
			fstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int max() {
		int m = 0;
		Iterator<Image> Ilabel = theList.iterator();
		while (Ilabel.hasNext()) {
			Image l = Ilabel.next();
			if (l.getTheNum() > m)
				m = l.getTheNum();
		}
		return m;
	}

	
	private class Image {
		private int theNum;
		private String theLabel;
		
		public Image(String label, int num) {
			this.theLabel = label;
			this.theNum = num;
		}

		public int getTheNum() {
			return theNum;
		}

		public String getTheLabel() {
			return theLabel;
		}
	}
}
