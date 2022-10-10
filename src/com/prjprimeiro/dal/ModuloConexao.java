package com.prjprimeiro.dal;

import java.sql.*;

/**
 *
 * @author ph757
 */
public class ModuloConexao {

    //Método Responsável por estabelecer a conexão com o banco de dados.
    public static Connection conector() {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        //armazenando informações referentes ao banco
        String url = "jdbc:mysql://localhost:3306/idealgaseagua?characterEncoding=utf-8";
        String user = "dba";
        String password = "1101";

        //Estabelecendo a conexão com o banco de dados.
         try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            //A linha abaixo serve de apoio para esclarecer o erro
           // System.out.println(e);
            return null;        }
         
    }
    }