package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.SapatoDAO;
import model.Sapato;
import spark.Request;
import spark.Response;


public class SapatoService {

	private SapatoDAO sapatoDAO = new SapatoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public SapatoService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Sapato(), FORM_ORDERBY_DESCRICAO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Sapato(), orderBy);
	}

	
	public void makeForm(int tipo, Sapato sapato, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umSapato = "";
		if(tipo != FORM_INSERT) {
			umSapato += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/sapato/list/1\">Novo Sapato</a></b></font></td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t</table>";
			umSapato += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/sapato/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Sapato";
				descricao = "Modelo do sapato";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + sapato.getID();
				name = "Atualizar Sapato (ID " + sapato.getID() + ")";
				descricao = sapato.getDescricao();
				buttonLabel = "Atualizar";
			}
			umSapato += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umSapato += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			umSapato += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ sapato.getPreco() +"\"></td>";
			umSapato += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" name=\"quantidade\" value=\""+ sapato.getQuantidade() +"\"></td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t</table>";
			umSapato += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umSapato += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Sapato (ID " + sapato.getID() + ")</b></font></td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td>&nbsp;Descrição: "+ sapato.getDescricao() +"</td>";
			umSapato += "\t\t\t<td>Preco: "+ sapato.getPreco() +"</td>";
			umSapato += "\t\t\t<td>Quantidade: "+ sapato.getQuantidade() +"</td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t\t<tr>";
			umSapato += "\t\t\t<td>&nbsp;</td>";
			umSapato += "\t\t</tr>";
			umSapato += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umSapato);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Sapatos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/sapato/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/sapato/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/sapato/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Sapato> sapatos;
		if (orderBy == FORM_ORDERBY_ID) {                 	sapatos = sapatoDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		sapatos = sapatoDAO.getOrderByDescricao();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			sapatos = sapatoDAO.getOrderByPreco();
		} else {											sapatos = sapatoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Sapato p : sapatos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getDescricao() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/sapato/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/sapato/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteSapato('" + p.getID() + "', '" + p.getDescricao() + "', '" + p.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		float preco = Float.parseFloat(request.queryParams("preco"));
		int quantidade = Integer.parseInt(request.queryParams("quantidade"));
		
		String resp = "";
		
		Sapato sapato = new Sapato(-1, descricao, preco, quantidade);
		
		if(sapatoDAO.insert(sapato) == true) {
            resp = "Sapato (" + descricao + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Sapato (" + descricao + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Sapato sapato = (Sapato) sapatoDAO.get(id);
		
		if (sapato != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, sapato, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Sapato " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Sapato sapato = (Sapato) sapatoDAO.get(id);
		
		if (sapato != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, sapato, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Sapato " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Sapato sapato = sapatoDAO.get(id);
        String resp = "";       

        if (sapato != null) {
        	sapato.setDescricao(request.queryParams("descricao"));
        	sapato.setPreco(Float.parseFloat(request.queryParams("preco")));
        	sapato.setQuantidade(Integer.parseInt(request.queryParams("quantidade")));
        	sapatoDAO.update(sapato);
        	response.status(200); // success
            resp = "Sapato (ID " + sapato.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Sapato (ID \" + sapato.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Sapato sapato = sapatoDAO.get(id);
        String resp = "";       

        if (sapato != null) {
            sapatoDAO.delete(id);
            response.status(200); // success
            resp = "Sapato (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Sapato (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}