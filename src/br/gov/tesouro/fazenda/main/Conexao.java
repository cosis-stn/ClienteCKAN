package br.gov.tesouro.fazenda.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


public class Conexao {
	
	private String host;
	private Integer porta;
	private String apiKey;
	private String diretorio;
	
	public Conexao(String host, Integer porta, String apiKey, String diretorio) {
		super();
		this.host = host;
		this.porta = porta;
		this.apiKey = apiKey;
		this.diretorio = diretorio;
	}
	
	
	protected String Post(String path, HttpEntity httpEntity) {
		URL url = null;
		String body = "";
		HttpPost postRequest;
		HttpResponse response;

		try {
			url = new URL(this.host + ":" + this.porta + this.diretorio +  path);
		} catch (MalformedURLException mue) {
			System.err.println(mue);
			return null;
		}

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		
		postRequest = new HttpPost(url.toString());
		
		postRequest.setEntity(httpEntity);
		postRequest.setHeader("X-CKAN-API-Key", this.apiKey);
		
		try {
			response = httpclient.execute(postRequest);			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
						
			String line = "";
			while ((line = br.readLine()) != null) {
				body += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro no post");
			e.printStackTrace();
		}
		
		return body;
		
	}
	
	protected String Post(String path, String parametros) {
		URL url = null;
		String body = "";
		HttpPost postRequest;
		HttpResponse response;


		try {
			url = new URL(this.host + ":" + this.porta + this.diretorio +  path);
		} catch (MalformedURLException mue) {
			System.err.println(mue);
			return null;
		}

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		
		postRequest = new HttpPost(url.toString());
		
		
		
		StringEntity input = new StringEntity(parametros,"UTF-8");
		input.setContentType("application/json");
		postRequest.setEntity(input);
		
		postRequest.setHeader("X-CKAN-API-Key", this.apiKey);
		
		try {
			response = httpclient.execute(postRequest);			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
						
			String line = "";
			while ((line = br.readLine()) != null) {
				body += line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro no post");
			e.printStackTrace();
		}
		
		return body;
		
	}	
}
