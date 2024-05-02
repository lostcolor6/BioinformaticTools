package ReadWriteTools;

public class maintest {
    public static void main(String[] args) {


        String[][] data = Read.readandSplitatEach("D:\\GIT\\BioinformaticTools\\ReadWriteTools\\data.txt", ";");
        System.out.println(data[0][0] + " "); //first cell
        Modify.overwriteCell(data, 0,0, "TEST"); //changing first cell
        System.out.println(data[0][0] + " ");
        Write.write(data,"D:\\GIT\\BioinformaticTools\\ReadWriteTools\\data2.txt", "neue Headerline", ";" ); //wirting a new file with the changed cell and a new path


    }
}
