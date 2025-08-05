package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ConversaoAnoExeption;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting().create();
        String busca = "";
        List<Titulo> titulos = new ArrayList<>();

        while (true) {
            System.out.println("Digite um filme para buscar:");
            busca = input.nextLine();

            if(busca.equalsIgnoreCase("sair")) {
                break;
            }

            String endereco = "http://www.omdbapi.com/?t="+busca.replace(" ","-")+"&apikey=40a1c3cc";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(meuTituloOmdb);

            FileWriter escrita = new FileWriter("filmes.txt");
            System.out.println("--------------------------");
            try {
                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println(meuTitulo);
                escrita.write(meuTitulo.toString());
                escrita.close();

                titulos.add(meuTitulo);
            } catch (ConversaoAnoExeption e) {
                System.out.println("aconteceu um erro:\n" + e.getMessage());
            }
        }
        System.out.println(titulos);
        FileWriter escrita = new FileWriter("titulos.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();
        System.out.println("Programa finalizado");
    }
}
