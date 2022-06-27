public class Produto extends SQLClass{            
    @Key
    int id;

    String descricao;
    double preco;        

    Produto(){
        this.setTableName("produtos");        
    }
}
