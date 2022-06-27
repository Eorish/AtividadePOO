public class Aluno extends SQLClass{
    @Key
    int id;
    String nome;
    int idade;
    String cidade;
    String uf;

    Aluno(){
        this.setTableName("ALUNOS");
    }
}