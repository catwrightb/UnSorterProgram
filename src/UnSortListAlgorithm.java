import java.io.*;
import java.util.ArrayList;

public class UnSortListAlgorithm<E> implements Runnable{
    //queue implementation of Josephus Problem to create a unsorted version of a txt file
    private int gap;
    private File input;
    private File output;
    private int unSortedCount = 0;

    public UnSortListAlgorithm(int gap, File input, File output) {
        this.gap = gap;
        this.output = output;
        this.input = input;
    }

    public UnSortListAlgorithm(int gap, File input) {
        this.gap = gap;
        this.input = input;
    }

    public UnSortListAlgorithm(){

    }

    public void getFile() throws FileNotFoundException {
        ArrayList<String> strings = new ArrayList<>();
        LinkedQueue<String> integerLinkedQueue = new LinkedQueue<>();

        UnSortListAlgorithm list = new UnSortListAlgorithm();

        try{
            BufferedReader inStream = new BufferedReader(new FileReader(input));
            String line = "";

            while ((line = inStream.readLine()) != null){
                //add string lines to the arrayList
                strings.add(line);
            }

        }catch (Exception e){
            System.out.println(e);
        }

        int gap = this.gap;

        for (int i = 0; i < strings.size(); i++) {
            integerLinkedQueue.enqueue(strings.get(i));
        }

        list.JosephusProblem(integerLinkedQueue, gap);

        if (integerLinkedQueue.isEmpty()){
            System.out.println("list is empty");
        }
        else{
            System.out.println("list not empty");
        }

    }

    public void JosephusProblem(LinkedQueue<E> list, int gap){
        try{
            E element;
            int counter = 1;
            unSortedCount++;
            File tempStorage = new File("./resources/UnSorted/"+unSortedCount+"UnSorted.txt");
            BufferedWriter outStream = new BufferedWriter(new FileWriter(tempStorage, false));

            while (!list.isEmpty()){
                if (counter == gap){

                    if (!list.isEmpty()){
                        String s = (String) list.first();
                        outStream.append(s).append("\n");
                        list.dequeue();
                        counter = 1;
                    }
                }
                else{
                    element = list.first();
                    list.dequeue();
                    list.enqueue(element);
                    counter ++;
                }
            }
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            getFile();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}