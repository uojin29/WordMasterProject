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
            for(Word one : list){
                pw.write(one.toSaveString() + "\n");
            }
            pw.close();
            System.out.println("=> 파일 저장 완료.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
