public class Usuario extends SQLClass {
    int id;
    String nome;
    String login;
    String senha;
    String data;
    int status;
    
    Usuario(){
        this.setTableName("usuarios");
    }
}
