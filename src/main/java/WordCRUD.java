import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
    ArrayList<Word> list;
    Scanner s;
    final String fname = "Dictionary.txt";

    WordCRUD(Scanner s){
        list = new ArrayList<>();
        this.s = s;
    }

    @Override
    public Object add() {
        // TODO Auto-generated method stub
        System.out.print("=> 난이도(1,2,3) & 새 단어 입력 : ");
        int level = s.nextInt();
        String word = s.nextLine();

        System.out.print("뜻 입력 : ");
        String meaning = s.nextLine();

        return new Word(0, level, word, meaning);
    }
    public void addWord() {
        Word one = (Word)add();
        list.add(one);
        System.out.println("새 단어가 단어장에 추가되었습니다");
    }

    @Override
    public int update(Object obj) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(Object obj) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void selectOne(int id) {
        // TODO Auto-generated method stub

    }

    public void listAll() {
        System.out.println("--------------------------------");
        for(int i = 0; i < list.size(); i++) {
            System.out.print((i + 1) + " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("--------------------------------");
    }

    public ArrayList<Integer> listAll(String searchWord) {
        ArrayList<Integer> idlist = new ArrayList<>();
        int j = 0;
        System.out.println("--------------------------------");
        for(int i = 0; i < list.size(); i++) {
            String word = list.get(i).getWord();
            if(!word.contains(searchWord)) continue;
            System.out.print((j + 1) + " ");
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("--------------------------------");
        return idlist;
    }

    public ArrayList<Integer> listAll(int level) {
        ArrayList<Integer> idlist = new ArrayList<>();
        int j = 0;
        System.out.println("--------------------------------");
        for(int i = 0; i < list.size(); i++) {
            int l = list.get(i).getLevel();
            if(l != level) continue;
            System.out.print((j + 1) + " ");
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("--------------------------------");
        return idlist;
    }
//listAll을 overloading해서 같은 이름과 비슷한 기능을 가진 함수를 여러 개 만든 것.
// 파라미터로 다른 값을 주어 각기 상황에 사용
//override vs overload
//override => 상속받은 메소드의 내용을 변경
//overload => 이름은 같지만 시그니처(파라미터 수, 타입)는 다른 메소드를 중복으로 선언하는 것

    public void updateWord() {
        System.out.println("=> 수정할 단어 검색: ");
        String searchWord = s.next();
        ArrayList<Integer> idlist = this.listAll(searchWord);
        System.out.println("=> 수정할 번호 선택: ");
        int num = s.nextInt();
        s.nextLine();
        System.out.println("=> 뜻 입력: ");
        String meaning = s.nextLine();

        Word word = list.get(idlist.get(num - 1));
        //list의 index를 입력받은 숫자로 바로 바꾸는 것이 아니라 idlist로 접근하여 index를 바꾸어야 한다.
        //이 부분을 제대로 인지하지 못해 계속 오류가 남
        word.setMeaning(meaning);
        System.out.println("단어가 수정되었습니다.");
    }

    public void deleteWord() {
        System.out.println("=> 삭제할 단어 검색: ");
        String searchWord = s.next();
        ArrayList<Integer> idlist = this.listAll(searchWord);
        System.out.println("=> 삭제할 번호 선택: ");
        int num = s.nextInt();
        s.nextLine();
        System.out.println("정말로 삭제하시겠습니까?(y/n) ");
        String ans = s.nextLine();

        if(ans.equalsIgnoreCase("y")){
            list.remove((int)idlist.get(num - 1));
            System.out.println("단어가 삭제되었습니다.");
        }
        else System.out.println("취소되었습니다.");
    }

    public void loadFile(){
        try {
            int count = 0;
            BufferedReader br = new BufferedReader(new FileReader(fname));
            //BufferedReader, FileReader
            while (true) {
                String line;
                line = br.readLine();
                if(line == null) break;
                String[] data = line.split("\\|");
                int level = Integer.parseInt(data[0]);
                String word = data[1];
                String meaning = data[2];
                list.add(new Word(0, level, word, meaning));
                count++;
            }
            br.close();
            System.out.println("=>" + count + " 개 로딩 완료.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fname));
            //PrintWriter, FileWriter
            //왜 강의에서 여기는 BufferedWriter를 사용하지 않는 걸까 => BufferedWriter는 파라미터로 Writer만 받지만
            //PrintWriter는 File(String), OutputStream, Writer등을 받음.
            for(Word one : list){
                pw.write(one.toSaveString() + "\n");
            }
            pw.close();
            System.out.println("=> 파일 저장 완료.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void levelSearch() {
        System.out.println("원하는 레벨은? ");
        int level = s.nextInt();
        listAll(level);
    }

    public void search() {
        System.out.println("=> 검색할 단어: ");
        String searchWord = s.next();
        listAll(searchWord);
    }
}
