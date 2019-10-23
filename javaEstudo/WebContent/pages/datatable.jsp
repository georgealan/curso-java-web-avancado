<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DataTable jQuery</title>

<link rel="stylesheet" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>	
<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
	
</head>
<body>

	<table id="minhatabela" class="display" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>Id</th>
				<th>Login</th>
			</tr>
		</thead>
	</table>
</body>

<script type="text/javascript">

$(document).ready(function() {// faz o processamento na hora que abre a tela
    $('#minhatabela').DataTable( {
        "processing": true,
        "serverSide": true,
        "ajax": "carregarDadosDataTable" // URL de retorno dos dados do servidor (RESPOSTA DO SERVIDOR)
    } );
} );

</script>
</html>