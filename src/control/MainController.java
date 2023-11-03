package control;

import model.File;
import model.List;

/**
 * Created by Jean-Pierre on 05.11.2016.
 */
public class MainController {

    private List<File>[] allShelves;    //Ein Array, das Objekte der Klasse Liste verwaltet, die wiederum Objekte der Klasse File verwalten.

    public MainController(){
        allShelves = new List[2];
        allShelves[0] = new List<File>(); //Beachtet die unterschiedliche Instanziierung! Was bedeutet das?
        allShelves[1] = new List<>();
        createFiles();
    }

    /**
     * Die Akten eines Regals werden vollständig ausgelesen.
     * @param index Regalnummer
     * @return String-Array mit den Familiennamen
     */
    public String[] showShelfContent(int index){
        //COMPLETE 03: Ausgabe der Inhalte
        List<File> list = allShelves[index];
        if(list.isEmpty()){
            return new String[0];
        }
        int count = 0;
        list.toFirst();
        while(list.hasAccess()){
            list.next();
            count++;
        }
        list.toFirst();
        String[] output = new String[count];
        for (int i = 0; i < count; i++) {
            if(list.hasAccess()){
                output[i] = list.getContent().getName();
                list.next();
            }
        }
        quickSort(output,0,output.length - 1);
        return output;
    }

    /**
     * Ein Regal wird nach Familiennamen aufsteigend sortiert.
     * @param index Regalnummer des Regals, das sortiert werden soll.
     * @return true, falls die Sortierung geklappt hat, sonst false.
     */
    public boolean sort(int index){
        //COMPLETE 07: Sortieren einer Liste.
        String[] names = showShelfContent(index);
        quickSort(names,0,names.length - 1);
        return true;
    }
    public static void quickSort(String[] array, int start, int end){
        if(end <= start){
            return;
        }
        int i = start - 1;
        int pivot = end;
        for (int j = start; j < pivot; j++) {
            if(array[j].compareTo(array[pivot]) < 0){
                i++;
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        i++;
        String temp = array[i];
        array[i] = array[pivot];
        array[pivot] = temp;
        pivot = i;


        quickSort(array, start, pivot - 1);
        quickSort(array, pivot + 1, end);

    }
    /**
     * Die gesammte Aktensammlung eines Regals wird zur Aktensammlung eines anderen Regals gestellt.
     * @param from Regalnummer, aus dem die Akten genommen werden. Danach sind in diesem Regal keine Akten mehr.
     * @param to Regalnummer, in das die Akten gestellt werden.
     * @return true, falls alles funktionierte, sonst false.
     */
    public boolean appendFromTo(int from, int to){
        //COMPLETE 04: Die Objekte einer Liste an eine andere anhängen und dabei die erste Liste leeren.
        List<File> fromList = allShelves[from];
        List<File> toList = allShelves[to];
        toList.concat(fromList);
        return true;
    }

    /**
     * Es wird eine neue Akte erstellt und einem bestimmten Regal hinzugefügt.
     * @param index Regalnummer
     * @param name Name der Familie
     * @param phoneNumber Telefonnummer der Familie
     * @return true, falls das Hinzufügen geklappt hat, sonst false.
     */
    public boolean appendANewFile(int index, String name, String phoneNumber){
        //COMPLETE 02: Hinzufügen einer neuen Akte am Ende der Liste.
        List<File> list = allShelves[index];
        list.append(new File(name, phoneNumber));
        return true;
    }

    /**
     * Es wird eine neue Akte in ein Regal eingefügt. Funktioniert nur dann sinnvoll, wenn das Regal vorher bereits nach Namen sortiert wurde.
     * @param index Regalnummer, in das die neue Akte einsortiert werden soll.
     * @param name Name der Familie
     * @param phoneNumber Telefonnummer der Familie
     * @return true, falls das Einfügen geklappt hat, sonst false.
     */
    public boolean insertANewFile(int index, String name, String phoneNumber){
        //COMPLETE 08: Einfügen einer neuen Akte an die richtige Stelle innerhalb der Liste
        sort(index);
        List<File> list = allShelves[index];
        list.append(new File(name,phoneNumber));
        sort(index);
        return false;
    }

    /**
     * Es wird nach einer Akte gesucht.
     * @param name Familienname, nach dem gesucht werden soll.
     * @return Zahlen-Array der Länge 2. Bei Index 0 wird das Regal, bei Index 1 die Position der Akte angegeben. Sollte das Element - also die Akte zum Namen - nicht gefunden werden, wird {-1,-1} zurückgegeben.
     */
    public int[] search(String name){
        //COMPLETE 05: Suchen in einer Liste.
        for (int i = 0; i < allShelves.length; i++) {
            List<File> list = allShelves[i];
            if(list.isEmpty()){
                continue;
            }
            int count = 0;
            list.toFirst();
            while(list.hasAccess()){
                list.next();
                count++;
            }
            list.toFirst();
            for (int j = 0; j < count; j++) {
                if(list.hasAccess()){
                    if(list.getContent().getName().equals(name))
                        return new int[]{i,j};
                    list.next();
                }
            }
        }
        return new int[]{-1,-1};
    }

    /**
     * Eine Akte wird entfernt. Dabei werden die enthaltenen Informationen ausgelesen und zurückgegeben.
     * @param shelfIndex Regalnummer, aus dem die Akte entfernt wird.
     * @param fileIndex Aktennummer, die entfernt werden soll.
     * @return String-Array der Länge 2. Index 0 = Name, Indedx 1 = Telefonnummer.
     */
    public String[] remove(int shelfIndex, int fileIndex){
        //COMPLETE 06: Entfernen aus einer Liste.
        List<File> list = allShelves[shelfIndex];
        int i = 0;
        list.toFirst();
        while (list.hasAccess()){
            if(i == fileIndex){
                String[] output =  new String[]{list.getContent().getName(),list.getContent().getPhoneNumber()};
                list.remove();
                return output;
            }

            list.next();
            i++;
        }
        return new String[]{"Nicht vorhanden","Nicht vorhanden"};
    }

    /**
     * Es werden 14 zufällige Akten angelegt und zufällig den Regalen hinzugefügt.
     */
    private void createFiles(){
        for(int i = 0; i < 14; i++){
            int shelfIndex = (int)(Math.random()*allShelves.length);

            int nameLength = (int)(Math.random()*5)+3;
            String name = "";
            for(int j = 0; j < nameLength; j++){
                name = name + (char) ('A' + (int)(Math.random()*26));
            }

            int phoneLength = (int)(Math.random()*2)+8;
            String phone = "0";
            for (int k = 1; k < phoneLength; k++){
                phone = phone + (int)(Math.random()*10);
            }

            appendANewFile(shelfIndex,name,phone);
        }
    }
}
