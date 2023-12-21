import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static DecimalFormat df1 = new DecimalFormat("0.00");
    public static Scanner input_number = new Scanner(System.in);
    public static Scanner input_text = new Scanner(System.in);

    public static ArrayList<String> produtos = new ArrayList<String>();
    public static ArrayList<Double> preco = new ArrayList<Double>();
    public static String vendas = "";
    public static double saldo_total = 0;

    public static void main(String[] args) {

        int opcao;

        do{

            exibirMenu();
            opcao = input_number.nextInt();

            limpa();

            switch (opcao){
                case 1: registarProduto(); break;
                case 2:
                    if(!zeroRegistos()){
                        editarProduto();
                    }
                    else {
                        System.out.println("Não existem produtos registados!");
                        press_enter();
                    }
                    break;
                case 3:
                    if(!zeroRegistos()){
                        pesquisarPorPrecos();
                    }
                    else {
                        System.out.println("Não existem produtos registados!");
                        press_enter();
                    }
                    break;
                case 4:
                    if(!zeroRegistos()){
                        limpa();
                        listarProdutos();
                        press_enter();
                    }
                    else {
                        System.out.println("Não existem produtos registados!");
                        press_enter();
                    }
                    break;
                case 5:
                    if(!zeroRegistos()){
                        registarVenda();
                    }
                    else {
                        System.out.println("Não existem produtos registados!");
                        press_enter();
                    }
                    break;
                case 6:
                    if(!vendas.equalsIgnoreCase("")){
                        listarVendas();
                    }
                    else {
                        System.out.println("Não existem vendas registadas!");
                        press_enter();
                    }
                    break;
                case 7:
                    if(!zeroRegistos()){
                        apagarProduto();
                    }
                    else {
                        System.out.println("Não existem produtos registados!");
                        press_enter();
                    }
                    break;
                case 0:
                    limpa(); System.out.println("A Sair"); break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }while(opcao!=0);

    }

    public static void exibirMenu(){
        limpa();
        System.out.println("== Mercado ==\n");

        System.out.println("1 - Registar produto.");
        System.out.println("2 - Editar produto.");
        System.out.println("3 - Procurar produto por baliza de preços.");
        System.out.println("4 - Listar todos os produtos.\n");
        System.out.println("5 - Registar venda.");
        System.out.println("6 - Listar todas as vendas.\n");
        System.out.println("7 - Apagar Produto.\n");
        System.out.println("0 - Sair.\n");
        System.out.println("Opção:");
    }

    public static void registarProduto(){
        limpa();
        String temp_produto;
        double temp_preco;

        System.out.println(" --- Registar produto ---\n");
        System.out.println("Digite o novo produto a ser registado:");
        temp_produto = input_text.nextLine();
        System.out.println("Digite o preço:");
        temp_preco = input_number.nextDouble();

        if(produtoJaExiste(temp_produto) || temp_preco <= 0){
            System.out.println("Erro! Nome de produto já existente ou preço inválido.\n");
            press_enter();
        }
        else{
            produtos.add(temp_produto);
            preco.add(temp_preco);

            System.out.println("Sucesso!\n");
            press_enter();
        }
    }

    public static void editarProduto(){
        int pos;
        String insert_produto;
        double insert_preco;

        limpa();
        System.out.println("--- Editar Produto ---\n");

        listarProdutos();

        System.out.println();
        System.out.println("Qual a posição a editar? (Entre 1 e " + produtos.size() +")");
        pos = input_number.nextInt();

        if(pos > 0 && pos <= produtos.size()){
            System.out.println("Insira o nome do novo produto:");
            insert_produto = input_text.nextLine();

            System.out.println("Insira o preço:");
            insert_preco = input_number.nextDouble();

            if(produtoJaExiste(insert_produto) || insert_preco <= 0){
                System.out.println("Erro! Nome de produto já existente ou preço inválido.\n");
                press_enter();
            }
            else{
                produtos.set(pos-1, insert_produto);
                preco.set(pos-1, insert_preco);

                System.out.println("Sucesso!\n");
                press_enter();
            }
        }
        else{
            System.out.println("Erro! Posição inválida.\n");
            press_enter();
        }
    }

    public static void pesquisarPorPrecos(){
        double preco_min;
        double preco_max;
        String lista = "";

        limpa();
        System.out.println("--- Pesquisa por preço ---\n");

        System.out.println("Digite o valor mínimo de preço para a pesquisa:");
        preco_min = input_number.nextDouble();
        System.out.println("Digite o valor máximo de preço para a pesquisa:");
        preco_max = input_number.nextDouble();

        if(preco_min >= 0 && preco_min <= preco_max){
            for(int i=0; i<produtos.size(); i++){
                if(preco.get(i) >= preco_min && preco.get(i) <= preco_max){
                    lista += (i+1) + " -> {" + produtos.get(i) + "} [" + df1.format(preco.get(i)) + " €]\n";
                }
            }

            if(lista.equalsIgnoreCase("")){
                System.out.println("Não há produtos dentro desses preços.\n");
                press_enter();
            }
            else{
                System.out.println(lista);
                press_enter();
            }
        }
        else{
            System.out.println("Valores inválidos!\n");
            press_enter();
        }
    }

    public static void listarProdutos(){
        System.out.println("--- Lista de Produtos ---\n");

        for(int i=0; i<produtos.size(); i++){
            System.out.println((i+1) + " -> {" + produtos.get(i) + "} [" + df1.format(preco.get(i)) + " €]");
        }
        System.out.println();
    }

    public static void registarVenda(){
        int pos;
        int quant;
        String insert_venda;
        double total;

        limpa();
        System.out.println("--- Registo de Vendas ---\n");

        listarProdutos();

        System.out.println("Digite o código do produto a ser vendido:");
        pos = input_number.nextInt();

        if(pos>0 && pos<=produtos.size()){
            System.out.println(pos + " -> {" + produtos.get(pos-1) + "} [" + df1.format(preco.get(pos-1)) + " €]\n");

            System.out.println("Digite a quantidade a ser vendida:");
            quant = input_number.nextInt();

            if(quant>0){
                total = preco.get(pos-1) * quant;
                insert_venda = "(" + produtos.get(pos-1) + ") x " + quant + " = [" + df1.format(total) + " €]\n";
                vendas += insert_venda;

                System.out.println(insert_venda);
                System.out.println("Sucesso!");
                press_enter();
            }
            else {
                System.out.println("Erro! Quantidade tem de ser superior a 0.");
                press_enter();
            }
        }
        else{
            System.out.println("Código inválido!\n");
            press_enter();
        }
    }

    public static void listarVendas(){
        limpa();
        System.out.println("--- Lista de Vendas ---\n");
        System.out.println(vendas);
        press_enter();
    }

    public static void apagarProduto(){
        int pos;

        limpa();
        System.out.println("--- Apagar Produto---\n");

        listarProdutos();

        System.out.println();
        System.out.println("Qual a posição a editar? (Entre 1 e " + produtos.size() +")");
        pos = input_number.nextInt();

        if(pos > 0 && pos <= produtos.size()){
            produtos.remove(pos-1);
            preco.remove(pos-1);
            System.out.println("Sucesso!\n");
            press_enter();
        }
        else{
            System.out.println("Erro! Posição inválida.\n");
            press_enter();
        }

    }

    public static boolean produtoJaExiste(String temp_produto){
        if(zeroRegistos()){
            return false;
        }
        else{
            for(int i=0; i<produtos.size(); i++){
                if(produtos.get(i).equalsIgnoreCase(temp_produto)){
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean zeroRegistos(){
        if(produtos.size() == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public static void limpa(){
        for(int i=0; i<20; i++){
            System.out.println();
        }
    }

    public static void press_enter(){
        String enter = "";
        System.out.println("\nCarregue <ENTER> para continuar.");
        enter = input_text.nextLine();
    }
}