package testZad;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Lee {
    List<Pair<Integer,Integer>> mainList = new ArrayList<>();
    List<Pair<Integer,Integer>> listPuti = new ArrayList<>();
    Pair<Integer,Integer> finishKord;
    boolean isInFind = true;
    boolean isFind = false;

    public Pair findStart(int[][] tab){
        Pair<Integer, Integer> pairRet = null;
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[i].length;j++){
                if(tab[i][j]==101) {
                    pairRet = new Pair<>(i,j);
                    break;
                }
            }
        }
        return pairRet;
    }

    public List<Pair> randStartFin(){
        List<Pair> list = new ArrayList<>();
        while(true) {
            int ranX = (int) (Math.random() * 10);
            int ranY = (int) (Math.random() * 10);
            int randFX = (int) (Math.random() * 10);
            int randFY =(int) (Math.random() * 10);
            if(ranX!=randFX&&randFY!=ranY) {
                list.add(new Pair(ranX,ranY));
                list.add(new Pair(randFX,randFY));
                break;
            }
        }
        return list;
    }
    public void findPath(Pair start, int[][] tab){
        mainList.add(start);
        int[][] tmpTab = new int[10][10];
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[i].length;j++) {
                tmpTab[i][j] = tab[i][j];
            }
        }
        int tmpPozX = 0;
        int tmpPozY = 0;
        while(mainList.size()>0 && isInFind){
            int pozX =mainList.get(0).getKey();
            int pozY =mainList.get(0).getValue();
            tmpPozX=pozX;
            tmpPozY=pozY;
            int liczba;
            //sprawdzanie czy kord nie jest startem
            if(!(pozX==(int)start.getKey()&&pozY==(int)start.getValue()))
                liczba = tmpTab[pozX][pozY];
            else liczba=0;
            //gorna, dolna, prawa il ewa koordynaty, zaczynaja fale
            if(isFindNumber(tmpTab,pozX+1,pozY)&&isFree(tmpTab,pozX+1,pozY)){
                tmpTab[pozX+1][pozY] = liczba+1;
                mainList.add(new Pair<>(pozX+1,pozY));
            }
            if(isFindNumber(tmpTab,pozX-1,pozY)&&isFree(tmpTab,pozX-1,pozY)){
                tmpTab[pozX-1][pozY] = liczba+1;
                mainList.add(new Pair<>(pozX-1,pozY));
            }
            if(isFindNumber(tmpTab,pozX,pozY-1)&&isFree(tmpTab,pozX,pozY-1)){
                tmpTab[pozX][pozY-1] = liczba+1;
                mainList.add(new Pair<>(pozX,pozY-1));
            }
            if(isFindNumber(tmpTab,pozX,pozY+1)&&isFree(tmpTab,pozX,pozY+1)){
                tmpTab[pozX][pozY+1] = liczba+1;
                mainList.add(new Pair<>(pozX,pozY+1));
            }
            mainList.remove(0);
        }
        isInFind= true;
        listPuti.add(new Pair<>(tmpPozX,tmpPozY));
        if(isFind)
            koordinaty(tmpTab, start);
        else System.out.println("Nie ma wyjscia!");
    }
    //wolna kordynata dla fali ("Wave")
    public boolean isFree(int[][] tab, int pozX, int pozY){
        boolean isIt=false;
        try {
            isIt = tab[pozX][pozY] == 0 && pozX <= 9 && pozY <= 9;
        }catch (ArrayIndexOutOfBoundsException ignored){}
        return isIt;
    }
    public boolean isFindNumber(int[][] tab, int pozX, int pozY){
        boolean isIt=false;
        try {
            if (tab[pozX][pozY] == 202) {
                System.out.println("You find the exit!");
                isIt = false;
                isInFind=false;
                isFind = true;
                finishKord = new Pair<>(pozX, pozY);
            }
            else isIt=true;
        }catch (ArrayIndexOutOfBoundsException e){}
        return isIt;
    }
    //koordynaty drogi wyjscia (od konca)
    public void koordinaty(int[][]tmp, Pair start){
        while(isInFind){
            int pozX = listPuti.get(listPuti.size()-1).getKey();
            int pozY = listPuti.get(listPuti.size()-1).getValue();
            int elem = tmp[pozX][pozY];
            if(elem==-1||elem==0 || (pozX==(int)start.getKey() && pozY==(int)start.getValue()))
                break;
            try {
                if (pozX+1<tmp.length&&tmp[pozX + 1][pozY] < elem)
                    listPuti.add(new Pair<>(pozX + 1, pozY));
                else if (pozX-1>=0&&tmp[pozX - 1][pozY] < elem)
                    listPuti.add(new Pair<>(pozX - 1, pozY));
                else if (pozY-1>=0&&tmp[pozX][pozY - 1] < elem)
                    listPuti.add(new Pair<>(pozX, pozY - 1));
                else if (pozY+1<tmp.length&&tmp[pozX][pozY + 1] < elem)
                    listPuti.add(new Pair<>(pozX, pozY + 1));
            }catch (ArrayIndexOutOfBoundsException e){}
        }
        System.out.print("("+(finishKord.getKey()+1) + "," + (finishKord.getValue()+1) + "),");
        for(Pair p: listPuti)
            System.out.print( "("+((int)p.getKey()+1) + "," + ((int)p.getValue()+1) + "),");
        System.out.print("("+((int)start.getKey()+1) + "," + ((int)start.getValue()+1) + "),");
    }

    public static void main(String[] args) {
        Lee lee= new Lee();
        int[][] tab = new int[10][10];
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[i].length;j++){
                tab[i][j]=(int)(Math.random()*-2);
            }
        }
        //101 - start
        //202 - finishKord
        List<Pair> pairs = lee.randStartFin();
        System.out.println(pairs);
        int startX =(int) pairs.get(0).getKey();
        int startY =(int) pairs.get(0).getValue();
        int finishX = (int)pairs.get(1).getKey();
        int finishY = (int)pairs.get(1).getValue();
        tab[startX][startY] = 101;
        tab[finishX][finishY] = 202;
        for(int i=0;i<tab.length;i++) {
            System.out.println();
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
        }
        System.out.println();
        Pair paitStart = lee.findStart(tab);
        lee.findPath(paitStart,tab);
    }
}


