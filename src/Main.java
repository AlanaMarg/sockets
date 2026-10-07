import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    private static final String HOST = "ip"; //ip do servidor
    private static final String IDENTIFICACAO = "Alana";

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : HOST;

        System.out.println("Conectando à porta 85");
        try (
                Socket socket = new Socket(host, 85);
                BufferedReader serverIn = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); //recebe
                PrintWriter serverOut = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)); //envia
                Scanner scanner = new Scanner(System.in) //console
        ) {
            serverOut.println(IDENTIFICACAO);

            System.out.println(serverIn.readLine()); //mensagem de bas vindas
            System.out.println(serverIn.readLine()); //recebe mensagem do prompt
            serverOut.println(scanner.nextInt()); //envia o palpite

            String concorrencia = serverIn.readLine(); //int
            String probabilidade = serverIn.readLine(); //double
            String numero = serverIn.readLine(); //int

            if (concorrencia != null && probabilidade != null && numero != null) {
                int c = Integer.parseInt(concorrencia.trim());
                double p = Double.parseDouble(probabilidade.trim());
                int nu = Integer.parseInt(numero.trim());

                System.out.println("\n" + c);
                System.out.println(p);
                System.out.println(nu);
            } else {
                System.err.println("Erro");
            }

            System.out.println("\nConexão com a porta 85 encerrada");
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
            return;
        }

        System.out.println("Sorteio na porta 88");

        try (
                Socket socket88 = new Socket(host, 88);
                BufferedReader serverIn88 = new BufferedReader(
                        new InputStreamReader(socket88.getInputStream(), StandardCharsets.UTF_8))
        ) {
            System.out.println(serverIn88.readLine()); //le a msg final
            System.out.println("Conexao com a porta 88 encerrada");
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}