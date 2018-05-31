import java.io.PrintWriter;
import java.util.Scanner;

public class GraphColoring {

    private int[][] adjacencyMatrix;
    private Node listHead;
    private Node currentNode;
    private int uncoloredNode;
    private int newColor;
    private int numNodes;
    private Scanner infile;
    private PrintWriter outfile;

    public GraphColoring (int in_numNodes, Scanner in_infile, PrintWriter in_outfile){
        numNodes=in_numNodes;
        infile = in_infile;
        outfile = in_outfile;
        //step 0
        listHead = new Node(-1);
        adjacencyMatrix = new int[numNodes+1][numNodes+1];
        newColor=0;
    }

    public void start(){
        //Step0, load matrix, print matrix, construct list.
        loadAdjacencyMatrix();
        printAdjacencyMatrix();
        constructNodeList(listHead);

        while (!allNodesAreColored()) {
            //step1:
            newColor++;
            currentNode = listHead.next;
            //step2
            do {
                if (currentNode.color == 0 && checkAdjacent(currentNode.id, newColor)) {
                    adjacencyMatrix[currentNode.id][currentNode.id] = newColor;
                    currentNode.color = newColor;
                }
                currentNode = currentNode.next;
            } while (currentNode != null);//step 3: repeat step2 until the end of node list
        }//step4: repeat step1-3 until all nodes are colored
        //step5 print adjacencyMatrix
        outfile.println("Number of colors used: "+newColor);
        printAdjacencyMatrix();

    }

    private void loadAdjacencyMatrix(){
        int i, j;
        while (infile.hasNext()){
            i=infile.nextInt();
            outfile.print(i+" ");
            j=infile.nextInt();
            outfile.println(j); //print input pairs to outfile to satisfy output requirement
            adjacencyMatrix[i][j]=1;
            adjacencyMatrix[j][i]=1;
        }
    }

    private void insertOneNodeInAscOrder (Node in_listhead, Node in_newNode){
        Node walker = in_listhead;
        if (in_listhead.next==null){
            in_listhead.next=in_newNode;
        }
        else {
            while (walker.next !=null && in_newNode.numEdges > walker.next.numEdges) {
                walker = walker.next;
            }
            in_newNode.next = walker.next;
            walker.next = in_newNode;
        }
    }

    private void constructNodeList (Node in_listhead) {
        Node node=null;
        for (int i=1; i<numNodes+1; i++){
            node = new Node(i); //make new node object for each number in matrix
            for (int j=1; j<numNodes+1; j++){
                //increment edge every time the value is 1
                if (adjacencyMatrix[i][j]==1){
                    node.numEdges++;
                }
            }
            //insert new node
            insertOneNodeInAscOrder(listHead, node);
        }
    }

    private boolean checkAdjacent (int in_nodeID, int in_color){
        boolean noAdjacentNodeHasTheColor=true;
        for (int i=1; i<numNodes+1; i++){
            if (adjacencyMatrix[in_nodeID][i]==1){
                if (adjacencyMatrix[i][i]==in_color){
                    noAdjacentNodeHasTheColor=false;
                }
            }
        }
        return noAdjacentNodeHasTheColor;
    }

    private boolean allNodesAreColored (){
        boolean allColored=true;
        for (int i=1; i<numNodes+1; i++){
            if (adjacencyMatrix[i][i]==0){
                allColored=false;
            }
        }
        return allColored;
    }

    private void printAdjacencyMatrix (){
        for (int i=1; i<numNodes+1; i++){
            for (int j=1; j<numNodes+1; j++){
                outfile.print(adjacencyMatrix[i][j]+" ");
            }
            outfile.println("");
        }
        outfile.println("");
    }
}
