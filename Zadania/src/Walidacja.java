
public class Walidacja {
    public static void main(String[] args) {
        String email []={"adam22@gmail.com","bbb%wp.pl","*%@onet.pl"};
        String haslo []={"Pawel98","B1G00s12#!","%12345678"};

        for(int i=0; i<3;i++)
            if(email[i].matches("")){
                System.out.println(email[i]+" dobrze");
            }else{
                System.out.println(email[i]+" zle");
            }

        for(int i=0; i<3;i++)
            if(haslo[i].matches("")){
                System.out.println(haslo[i]+" dobrze");
            }else{
                System.out.println(haslo[i]+" zle");
            }
    }
}
