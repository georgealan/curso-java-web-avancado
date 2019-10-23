<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gantt View</title>
<link rel="stylesheet" type="text/css" href="../scriptGanttView/jquery-ui-1.8.4.css" />
<link rel="stylesheet" type="text/css" href="../scriptGanttView/reset.css" />
<link rel="stylesheet" type="text/css" href="../scriptGanttView/jquery.ganttView.css" />

<script type="text/javascript" src="../scriptGanttView/jquery-1.4.2.js"></script>
<script type="text/javascript" src="../scriptGanttView/date.js"></script>
<script type="text/javascript" src="../scriptGanttView/jquery-ui-1.8.4.js"></script>
<script type="text/javascript" src="../scriptGanttView/jquery.ganttView.js"></script>

<style type="text/css">
body {
	font-family: tahoma, verdana, helvetica;
	font-size: 0.8em;
	padding: 10px;
}
</style>
</head>
<body>
	<h1>Gantt View</h1>
	
	<div id="ganttChart"></div>
	<br/><br/>
	<div id="eventMessage"></div>
</body>

<script type="text/javascript">
$(document).ready(function() {
	$.get("buscarDatasPlanejamento", function(response) {
		
	// processa dados gant view INICIO	
		var ganttDataResposta = JSON.parse(response);
	
		var ganttData = "";
		 ganttData += "["; 
		 
		 //Com o JSON em mãos a gente tem que varrer ele e capturar todos os dados e fazer o processamento.
		 //A gente tem que processar os dados para depois transformar eles em JSON.
		 //index é a posição do objeto e o projeto é o objeto em si, o objeto da lista que esta sendo processado está 
		 //no parametro projeto da função.
			$.each(ganttDataResposta, function(index, projeto) {// for dos projetos
					
				ganttData += "{ \"id\": \""+projeto.id+"\", \"name\": \""+projeto.nome+"\", \"series\": [";
				
				$.each(projeto.series, function(idx, serie) {// for das series
					
					//Array referente as cores das barras do Gráfico Gantt
					var cores = "#3366FF,#00CC00".split(',');
					
					var cor;
					if (idx === 0) {//Se o indice for igual a 0...
						cor = "#CC33CC";//Ele vai iniciar com essa cor...
					}else {
						/*Se não for a primeira posição ele vai dividir o indice por dois e verificar se é um 
						inteiro, se for ele vai colocar a primeira cor, se não a segunda cor.*/
					   cor = Number.isInteger(idx / 2) ? cores[0] : cores[1];
					}
					
					/*Aqui pegamos a data inicial e final do objeto serie que está sendo varrido, e damos um slipt pois
					a data está separada com hífen 2017-00-25 no banco de dados.*/
					var datainicial = serie.datainicial.split('-');
					var datafinal = serie.datafinal.split('-');
					
					/*Montagem dos dados JSON da serie.*/
					ganttData +="{ \"name\": \""+serie.nome+"\", \"start\":\""+ new Date(datainicial[0],datainicial[1],datainicial[2])+"\", \"end\": \""+new Date(datafinal[0],datafinal[1],datafinal[2])+"\" , \"color\": \""+cor+"\", \"projeto\": \""+serie.projeto+"\" , \"serie\": \""+serie.id+"\" }";
					
					//Se tiver mais series tem que adicionar a virgula para separar elas, para não quebrar o JSON.
					if (idx < projeto.series.length - 1){
						ganttData +=",";
					}
				});// fim for da series
				
			    ganttData +="]}";// fecha o array json de series
			   
			    //Se tiver mais projetos tem que adicionar a virgula para separar elas e não quebrar o JSON.
			   if (index < ganttDataResposta.length - 1){
				   ganttData +=",";
			   }
				
			});// fim for dos projetos
		
		 ganttData += "]"; //Fechamento do JSON completo.
		
		 ganttData = JSON.parse(ganttData);//Convertendo para um objeto JSON.
		 
		// processa dados gant view FIM

		 $("#ganttChart").ganttView({ 
					data: ganttData, //Essa variavel carrega os dados formatados.
					slideWidth: 600,
					behavior: {
						onClick: function (data) { 
							var msg = "Evento de click: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
							$("#eventMessage").text(msg);
						},
						onResize: function (data) { 
							
							var start = data.start.toString("yyyy-M-d");
							var end = 	data.end.toString("yyyy-M-d");
							$.post("buscarDatasPlanejamento", { start: start, end : end, serie : data.serie, projeto : data.projeto });
							
							var msg = "Evento de errastar: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
							$("#eventMessage").text(msg);
							
						},
						onDrag: function (data) { 
							
							var start = data.start.toString("yyyy-M-d");
							var end = 	data.end.toString("yyyy-M-d");
							$.post("buscarDatasPlanejamento", { start: start, end : end, serie : data.serie, projeto : data.projeto });
							
							var msg = "Evento de errastar: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
							$("#eventMessage").text(msg);
							
						}
						
					}
				});
    });	

});
</script>
</html>