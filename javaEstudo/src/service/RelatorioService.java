package service;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* É o diretório dos relatórios, que é o package relatorios */
	private static final String FOLDER_RELATORIOS = "/relatorios"; 
	
	/* É o diretório dos sub diretórios, que são relatórios de relação de tabelas de um para muitos. */
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; 
	
	/* Esse fileseparator é as barras / ou \ depende do sistema operacional. */
	private String SEPARATOR = File.separator; 
															
	private String caminhoArquivoRelatorio = null;
	private JRExporter exporter = null;
	private String caminhoSubReport_Dir = "";
	private File arquivoGerado = null;

	
	public String gerarRelatorio(List<?> listaDataBeanColletion, HashMap parametrosRelatorio,
			String nomeRelatorioJasper, String nomeRelatorioSaida, ServletContext servletContext, String tipoExportar) throws Exception {
		
		/*Cria a lista de collectionDataSource de beans que carregam os dados para o relatório*/
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDataBeanColletion);
		
		String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);
		
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper");
		
		/*Essa verificação serve para quando a aplicação não estiver rodando somente no eclipse, mas esteja
		 * rodando no servidor, pois no servidor o caminho muda, e com essa verificação conseguimos achar 
		 * o caminho da pasta no servidor.*/
		if(caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}
		
		/*Caminho para imagens*/
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);
		
		/*Caminho completo até o relatório compilado indicado*/
		String caminhoArquivosJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper";
		
		/*Faz o carregamento do relatório*/
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivosJasper);
		
		/*Seta parametros SUBREPORT_DIR com o caminho físico para subreport*/
		caminhoSubReport_Dir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubReport_Dir);
		
		/*Carrega o arquivo*/
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);
		
		if(tipoExportar.equalsIgnoreCase("pdf")) {
			exporter = new JRPdfExporter();
		}else if(tipoExportar.equalsIgnoreCase("xls")) {
			exporter = new JRXlsExporter();
		}
		
		/*Caminho relatorio exportado*/
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + "." + tipoExportar;
		
		/*Criar novo arquivo exportado*/
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		/*Prepara a impressão do relatório*/
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
		
		/*Executa a exportação*/
		exporter.exportReport();
		
		/*Remove o arquivo do servidor após ser feito o download*/
		arquivoGerado.deleteOnExit();
		
		return caminhoArquivoRelatorio;
	}

}
