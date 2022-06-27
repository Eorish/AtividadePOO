public class Carro extends SQLClass{
    @Key
    int id;
    String modelo;
    String marca;
    int ano;
    double valor;

    Carro(){
        this.setTableName("carros");
    };    
    
}
