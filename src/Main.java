import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner infile = null;
        PrintWriter outfile = null;
        int numNodes=0;

        if (args.length<2){
            System.out.println("Please specify input.txt and output.txt files and try again.");
            System.exit(1);
        }

        try {
            infile = new Scanner(new FileReader(args[0]));
            outfile = new PrintWriter(args[1]);
            System.out.println("Open files:"+args[0]+" "+args[1]+" OK");
            numNodes = infile.nextInt();
        }  catch (IOException e) {
            System.out.println("problems opening files. please try again");
            System.exit(1);
        }



        GraphColoring graphColoring = new GraphColoring(numNodes, infile, outfile);
        graphColoring.start();

        infile.close();
        outfile.close();
    }
}
