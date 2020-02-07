import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Tar {
    @Option(names = "-d", description = "descending")
    boolean desc;

    @Option(names = "-s", description = "string")
    boolean st;

    @Option(names = "-i", description = "integer")
    boolean in;

    @Option(names = "-a", description = "ascending")
    boolean as;

    @Parameters(paramLabel = "FILE", description = "first file-output file,two ore more files to sorting")
    File[] files;
}

public class Main {

    private static void strsort(File[] inFiles, boolean des) throws IOException {
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        FileReader fr1 = new FileReader(inFiles[1]);
        Scanner scan1 = new Scanner(fr1);
        while (scan1.hasNextLine()) {
            list1.add(scan1.next());
        }
        for (int f = 2; f < inFiles.length; f++) {
            ArrayList<String> list2 = new ArrayList<>();
            ArrayList<String> list3 = new ArrayList<>();
            FileReader fr2 = new FileReader(inFiles[f]);
            Scanner scan2 = new Scanner(fr2);
            while (scan2.hasNextLine()) {
                list2.add(scan2.next());
            }

            int n = list1.size() + list2.size(), i = 0, j = 0;
            for (int k = 0; k < n; k++) {

                if (i > list1.size() - 1) {
                    String a = list2.get(j);
                    list3.add(a);
                    j++;
                } else if (j > list2.size() - 1) {
                    String a = list1.get(i);
                    list3.add(a);
                    i++;
                } else if (list1.get(i).compareTo(list2.get(j)) < 0) {
                    String a = list1.get(i);
                    list3.add(a);
                    i++;
                } else {
                    String b = list2.get(j);
                    list3.add(b);
                    j++;
                }
            }
            fr2.close();
            list1 = list3;
        }
        fr1.close();

        for (int o = list1.size() - 1; o >= 0; o--)
            list4.add(list1.get(o));

        Writer writer = new FileWriter(inFiles[0]);
        if (des) {
            for (String line : list4) {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
            writer.flush();
        } else {
            for (String line : list1) {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
            writer.flush();
        }
    }

    private static void intsort(File[] inFiles, boolean des) throws IOException {
        try {
            ArrayList<Integer> list1 = new ArrayList<>();
            ArrayList<Integer> list4 = new ArrayList<>();
            FileReader fr1 = new FileReader(inFiles[1]);
            Scanner scan1 = new Scanner(fr1);
            while (scan1.hasNextLine()) {
                list1.add(scan1.nextInt());
            }

            for (int f = 2; f < inFiles.length; f++) {
                ArrayList<Integer> list2 = new ArrayList<>();
                ArrayList<Integer> list3 = new ArrayList<>();
                FileReader fr2 = new FileReader(inFiles[f]);
                Scanner scan2 = new Scanner(fr2);
                while (scan2.hasNextLine()) {
                    list2.add(scan2.nextInt());
                }

                int n = list1.size() + list2.size(), i = 0, j = 0;
                for (int k = 0; k < n; k++) {

                    if (i > list1.size() - 1) {
                        int a = list2.get(j);
                        list3.add(a);
                        j++;
                    } else if (j > list2.size() - 1) {
                        int a = list1.get(i);
                        list3.add(a);
                        i++;
                    } else if (list1.get(i) < list2.get(j)) {
                        int a = list1.get(i);
                        list3.add(a);
                        i++;
                    } else {
                        int b = list2.get(j);
                        list3.add(b);
                        j++;
                    }
                }
                fr2.close();
                list1 = list3;
            }
            fr1.close();
            for (int o = list1.size() - 1; o >= 0; o--)
                list4.add(list1.get(o));

            Writer writer = new FileWriter(inFiles[0]);
            if (des) {
                for (Integer line : list4) {
                    writer.write(line.toString());
                    writer.write(System.getProperty("line.separator"));
                }
            } else {
                for (Integer line : list1) {
                    writer.write(line.toString());
                    writer.write(System.getProperty("line.separator"));
                }
            }
            writer.flush();
        } catch (NoSuchElementException e) {
            System.out.println("Incorrect file format");
        }
    }

    public static void main(String[] args) throws IOException {
        Tar tar = new Tar();
        new CommandLine(tar).parseArgs(args);
        if (tar.files.length<2)
            System.out.println("Need minimum 2 files");
        else {

            if (tar.st)
                strsort(tar.files, tar.desc);
            else
                intsort(tar.files, tar.desc);
        }
    }
}